package l3_manager_employee.Repository;

import jakarta.persistence.*;
import l3_manager_employee.DTO.Respones.ContractTerminationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ContractTerminationRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    /* ===============================
       CREATE TERMINATION
       =============================== */
    public StoredProcedureQuery createTermination(
            Long userId,
            Long employeeId,
            LocalDate terminationDate,
            String reason
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_create_contract_termination");

        query.registerStoredProcedureParameter("p_user_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_termination_date", java.time.LocalDate.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_termination_reason", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("o_termination_id", Long.class, jakarta.persistence.ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_success", Boolean.class, jakarta.persistence.ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, jakarta.persistence.ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", employeeId);
        query.setParameter("p_termination_date", terminationDate);
        query.setParameter("p_termination_reason", reason);

        query.execute();
        return query;
    }

    public StoredProcedureQuery submitTermination(
            Long userId,
            Long formId,
            Long receiverId
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_submit_contract_termination");

        query.registerStoredProcedureParameter("p_user_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_receiver_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, jakarta.persistence.ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, jakarta.persistence.ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_receiver_id", receiverId);

        query.execute();
        return query;
    }

    /* ===============================
       LEADER HANDLE TERMINATION
       =============================== */
    public StoredProcedureQuery leaderHandleTermination(
            Long leaderId,
            Long formId,
            String action,
            String note
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_leader_handle_contract_termination");

        query.registerStoredProcedureParameter("p_leader_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_action", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_note", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, jakarta.persistence.ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, jakarta.persistence.ParameterMode.OUT);

        query.setParameter("p_leader_id", leaderId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_action", action);
        query.setParameter("p_note", note);

        query.execute();
        return query;
    }
    @SuppressWarnings("unchecked")
    public List<ContractTerminationResponse> getTerminationList() {
        List<Object[]> rows = entityManager
                .createNativeQuery("SELECT * FROM fn_list_contract_termination()")
                .getResultList();

        return rows.stream()
                .map(r -> new ContractTerminationResponse(
                        ((Number) r[0]).longValue(),
                        ((Number) r[1]).longValue(),
                        (String) r[2],
                        (String) r[3],
                        ((java.sql.Date) r[4]).toLocalDate(),
                        (String) r[5],
                        r[6] != null ? ((java.sql.Date) r[6]).toLocalDate() : null,
                        (String) r[7]
                ))
                .toList();
    }

    // Danh sách pending
    @SuppressWarnings("unchecked")
    public List<Object[]> getPendingList() {
        String sql = "SELECT * FROM fn_contract_termination_list_pending()";
        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    // Chi tiết theo form id
    public Object getDetail(Long formId) {
        String sql = "SELECT * FROM fn_contract_termination_detail(:p_form_id)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("p_form_id", formId);
        return query.getSingleResult();
    }
}