package edu.miu.badge.domains;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @ManyToMany
    //@Column(nullable = false) what on earth is this line doing??
    @JoinTable(name = "Plan_Plantypes",
            joinColumns = {@JoinColumn(name = "plan_id")},
            inverseJoinColumns = {@JoinColumn(name = "plantype_id")}
    )
    private List<PlanType> planTypes;

    @ManyToMany
    @Column(nullable = false)
    @JoinTable(name = "Plan_Locations",
            joinColumns = {@JoinColumn(name = "plan_id")},
            inverseJoinColumns = {@JoinColumn(name = "location_id")}
    )

    private List<Location> locations;

    @ManyToMany
    @JoinTable(name = "Plan_Role",
            joinColumns = {@JoinColumn(name = "plan_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    @Column(nullable = false)
    private List<Role> allowedRoles;

}
