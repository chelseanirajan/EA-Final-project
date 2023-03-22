package edu.miu.badge.dto;

import edu.miu.badge.domains.Badge;
import edu.miu.badge.domains.Membership;
import edu.miu.badge.domains.Role;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString(exclude = {"badges", "memberships"})
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMemberDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;
    private List<Badge> badges;
    private List<Membership> memberships;

}
