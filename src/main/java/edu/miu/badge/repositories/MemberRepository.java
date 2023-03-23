package edu.miu.badge.repositories;


import edu.miu.badge.domains.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {
        //active badge of a member
        @Query("select b from Member m join m.badges b where m.id = ?1 and b.badgeStatus = 'ACTIVE'")
        Badge activeBadgeOfMember(int memberId);

        //list of All badges of a member
        @Query("select b from Member m join m.badges b where m.id = ?1")
        List<Badge> allBadgesOfMember(int memberId);

        //list of Transactions of a member
        @Query("select t from Transaction t where t.member.id = ?1")
        List<Transaction> allTransactionsOfMember(int memberId);


}
