package l3_manager_employee.Enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "tbl_employee")
public class TblEmployee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_employee_id_gen")
    @SequenceGenerator(name = "tbl_employee_id_gen", sequenceName = "tbl_employee_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "employee_code", nullable = false, length = 50)
    private String employeeCode;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender", length = 10)
    private String gender;

    @NotNull(message = "Date of birth is required")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address", length = 500)
    private String address;

    @Column(name = "team")
    private String team;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "identity_number", length = 50)
    private String identityNumber;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "status_updated_at")
    private Instant statusUpdatedAt;

    @Column(name = "terminated_at")
    private LocalDate terminatedAt;

    @Column(name = "termination_reason", length = 500)
    private String terminationReason;

    @Column(name = "archive_date")
    private LocalDate archiveDate;

    @Column(name = "archive_number", length = 100)
    private String archiveNumber;

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