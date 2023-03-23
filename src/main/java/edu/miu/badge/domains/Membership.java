package edu.miu.badge.domains;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="[start_date]", nullable = false)
    private LocalDate startDate;
    @Column(name="[end_date]", nullable = false)
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name="[member_id]", nullable = false)
    @JsonBackReference
    private Member member;
    @ManyToOne
    @JoinColumn(name="[plan_id]", nullable = false)
    private Plan plan;
    @ManyToOne
    @JoinColumn(name="[plan_type_id]", nullable = false)
    private PlanType planType;
    @Column(name="[used_allowances]")
    private int usedAllowances;

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", plan=" + plan +
                ", planType=" + planType +
                ", numberOfAllowance=" + usedAllowances +
                '}';
    }
}
