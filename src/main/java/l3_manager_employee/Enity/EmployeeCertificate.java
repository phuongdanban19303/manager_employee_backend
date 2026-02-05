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
@Table(name = "employee_certificates")
public class EmployeeCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_certificates_id_gen")
    @SequenceGenerator(name = "employee_certificates_id_gen", sequenceName = "employee_certificates_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "field_url", length = 100)
    private String fieldUrl;

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