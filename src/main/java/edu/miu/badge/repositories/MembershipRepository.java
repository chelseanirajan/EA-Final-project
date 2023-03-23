package edu.miu.badge.repositories;

import edu.miu.badge.domains.Membership;
import edu.miu.badge.domains.Plan;
import edu.miu.badge.enumeration.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Integer> {

    @Query("select ms from Membership ms where ms.member.id = :member_id")
    List<Membership> findMembershipsByMemberId(@Param("member_id") int member_id);
    @Query("select ms.plan from Membership ms where ms.member.id = :memberId")
    List<Plan> findPlansByMemberId(@PathVariable("memberId") int memberId);

    @Transactional
    @Modifying
    @Query("UPDATE Membership m SET m.usedAllowances = 0 WHERE m.member IN" +
            " (SELECT r.member FROM Role r WHERE r.roleType =  edu.miu.badge.enumeration.RoleType.STUDENT)")
    void updateMembershipsMealCountByStudentRole();

    @Transactional
    @Modifying
    @Query("UPDATE Membership m SET m.usedAllowances = 0 WHERE m.member IN" +
            " (SELECT r.member FROM Role r WHERE r.roleType =  edu.miu.badge.enumeration.RoleType.STAFF)")
    void updateMembershipsMealCountByStaffRole();
    @Transactional
    @Modifying
    @Query("UPDATE Membership m SET m.usedAllowances = 0 WHERE m.member IN" +
            " (SELECT r.member FROM Role r WHERE r.roleType =  edu.miu.badge.enumeration.RoleType.FACULTY)")
    void updateMembershipsMealCountByFacultyRole();


}
