package edu.miu.badge.dto;


import edu.miu.badge.domains.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestMemberDTO {
    private String firstName;
    private String lastName;
    private String email;
    private List<Integer> roles;
    private String username;
    private String password;


}
