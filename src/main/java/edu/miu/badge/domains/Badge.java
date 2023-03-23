package edu.miu.badge.domains;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.miu.badge.enumeration.BadgeStatus;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Badge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "[badge_number]")
    private int badgeNumber;
    @Column(name = "[is_active]")
    @Enumerated(EnumType.STRING)
    private BadgeStatus badgeStatus;
    @ManyToOne(cascade = CascadeType.ALL)
  //  @JoinColumn(name = "[member_id]")
    @JsonBackReference
    private Member member;

}
