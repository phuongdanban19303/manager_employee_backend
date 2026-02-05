package l3_manager_employee.Enity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_permission")
public class TblPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_permission_id_gen")
    @SequenceGenerator(name = "tbl_permission_id_gen", sequenceName = "tbl_permission_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "object", nullable = false, length = 50)
    private String object;

    @Column(name = "action", nullable = false, length = 50)
    private String action;

    @Column(name = "states")
    private String states;

}