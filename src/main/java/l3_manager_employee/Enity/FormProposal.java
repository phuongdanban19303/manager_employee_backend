package l3_manager_employee.Enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "form_proposal")
public class FormProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "form_proposal_id_gen")
    @SequenceGenerator(name = "form_proposal_id_gen", sequenceName = "form_proposal_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "employee_id", nullable = false)
    private Long employeeId;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;

    @Column(name = "detail", length = Integer.MAX_VALUE)
    private String detail;

    @Column(name = "receiver_id")
    private Long receiverId;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "submit_date")
    private Instant submitDate;

    @Column(name = "approve_date")
    private Instant approveDate;

    @Column(name = "leader_note", length = Integer.MAX_VALUE)
    private String leaderNote;

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