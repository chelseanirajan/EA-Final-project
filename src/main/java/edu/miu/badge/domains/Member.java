package edu.miu.badge.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @ManyToMany
    private Set<Role> roles;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Badge> badges;
    @OneToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Membership> memberships;
}
