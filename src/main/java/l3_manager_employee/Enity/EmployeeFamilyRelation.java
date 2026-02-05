package l3_manager_employee.Enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employee_family_relations")
public class EmployeeFamilyRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_family_relations_id_gen")
    @SequenceGenerator(name = "employee_family_relations_id_gen", sequenceName = "employee_family_relations_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "identity_card_number", length = 20)
    private String identityCardNumber;

    @Column(name = "relationship", length = 50)
    private String relationship;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "updated_at")
    private Instant updatedAt;

}