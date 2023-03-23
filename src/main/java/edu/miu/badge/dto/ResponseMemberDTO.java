package edu.miu.badge.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private List<RolesDTO> roles;

}
