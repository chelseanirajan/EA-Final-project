package edu.miu.badge.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.miu.badge.enumeration.RoleType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
    @Id
    @GeneratedValue
    private int id;

    private String roleType;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<Member> member;
}
