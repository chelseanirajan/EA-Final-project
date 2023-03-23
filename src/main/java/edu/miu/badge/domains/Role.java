package edu.miu.badge.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import edu.miu.badge.enumeration.RoleType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "[roles]")
public class Role {
    @Id
    @GeneratedValue
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType roleType;
    @JsonBackReference
    @ManyToMany(mappedBy = "roles")
    private List<Member> member;
}
