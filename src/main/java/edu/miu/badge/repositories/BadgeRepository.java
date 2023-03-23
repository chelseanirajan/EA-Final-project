package edu.miu.badge.repositories;

import edu.miu.badge.domains.Badge;
import edu.miu.badge.enumeration.BadgeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge,Integer> {
    @Query("SELECT b FROM Badge b WHERE b.member.id = ?1 AND b.badgeStatus = 'ACTIVE'")
    Optional<Badge> getActiveBadge(int memberId);

    @Query("SELECT b FROM Badge b WHERE b.badgeStatus = 'ACTIVE'")
    Optional<List<Badge>> getActiveBadges();

    @Query("SELECT b FROM Badge b WHERE b.badgeNumber = ?1")
    Optional<Badge> getBadgeByBadgeNumber( int badgeNumber);

    @Query("select b from Badge b where b.badgeNumber = ?1 and b.badgeStatus = edu.miu.badge.enumeration.BadgeStatus.ACTIVE")
    Optional<Badge> getActiveBadgeByBadgeNumber(int badgeNumber);

    //inactive all badges of a member
    @Modifying
    @Query("update Badge b set b.badgeStatus = :status where b.member.id = :id")
    void updateBadgeStatusOfMember(@Param("id") int id, @Param("status") BadgeStatus status);



}
