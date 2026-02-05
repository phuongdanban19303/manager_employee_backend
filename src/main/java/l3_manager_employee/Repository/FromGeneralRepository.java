package l3_manager_employee.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import l3_manager_employee.DTO.Request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FromGeneralRepository {
    private final EntityManager entityManager;

    public StoredProcedureQuery createPromotion(
            Long userId,
            PromotionRequest req
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_create_form_promotion");

        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_old_position", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_new_position", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_reason", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_receiver_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_form_id", Long.class, ParameterMode.OUT);


        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", req.getEmployeeId());
        query.setParameter("p_old_position", req.getOldPosition());
        query.setParameter("p_new_position", req.getNewPosition());
        query.setParameter("p_reason", req.getReason());
        query.setParameter("p_receiver_id", req.getReceiverId());

        query.execute();
        return query;
    }

    public StoredProcedureQuery createProposal(
            Long userId,
            ProposalRequest req
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_create_form_proposal");

        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_content", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_detail", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_receiver_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_form_id", Long.class, ParameterMode.OUT);


        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", req.getEmployeeId());
        query.setParameter("p_content", req.getContent());
        query.setParameter("p_detail", req.getDetail());
        query.setParameter("p_receiver_id", req.getReceiverId());

        query.execute();
        return query;
    }
    public StoredProcedureQuery createSalaryIncrease(
            Long userId,
            SalaryIncreaseRequest req
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_create_form_salary_increase");

        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_times", Integer.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_old_level", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_new_level", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_reason", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_receiver_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_form_id", Long.class, ParameterMode.OUT);


        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", req.getEmployeeId());
        query.setParameter("p_times", req.getTimes());
        query.setParameter("p_old_level", req.getOldLevel());
        query.setParameter("p_new_level", req.getNewLevel());
        query.setParameter("p_reason", req.getReason());
        query.setParameter("p_receiver_id", req.getReceiverId());

        query.execute();
        return query;
    }
    public StoredProcedureQuery submitPromotion(
            Long userId,
            Long formId,
            Long receiverId
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_submit_form_promotion");

        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_receiver_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_receiver_id", receiverId);

        query.execute();
        return query;
    }

    /* ===== PROCESS ===== */
    public StoredProcedureQuery processPromotion(
            Long managerId,
            Long formId,
            String action,
            String note
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_process_form_promotion");

        query.registerStoredProcedureParameter("p_manager_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_action", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_note", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_manager_id", managerId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_action", action);
        query.setParameter("p_note", note);

        query.execute();
        return query;
    }
    public StoredProcedureQuery submitProposal(Long userId, Long formId, Long receiverId) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_submit_form_proposal");

        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_receiver_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_receiver_id", receiverId);

        query.execute();
        return query;
    }

    public StoredProcedureQuery processProposal(
            Long managerId,
            Long formId,
            String action,
            String note
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_process_form_proposal");

        query.registerStoredProcedureParameter("p_manager_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_action", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_note", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_manager_id", managerId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_action", action);
        query.setParameter("p_note", note);

        query.execute();
        return query;
    }
    public StoredProcedureQuery submitSalaryIncrease(Long userId, Long formId, Long receiverId) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_submit_form_salary_increase");

        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_receiver_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_receiver_id", receiverId);

        query.execute();
        return query;
    }

    public StoredProcedureQuery processSalaryIncrease(
            Long managerId,
            Long formId,
            String action,
            String note
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_process_form_salary_increase");

        query.registerStoredProcedureParameter("p_manager_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_action", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_note", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_manager_id", managerId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_action", action);
        query.setParameter("p_note", note);

        query.execute();
        return query;
    }
    public List<Object[]> getFormPromotionByEmployee(Long employeeId) {
        String sql = """
                SELECT *
                FROM fn_get_form_promotion_by_employee(?)
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, employeeId);
        return query.getResultList();
    }
    public List<Object[]> getFormProposalByEmployee(Long employeeId) {
        String sql = """
                SELECT *
                FROM fn_get_form_proposal_by_employee(?)
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, employeeId);
        return query.getResultList();
    }
    public List<Object[]> getFormSalaryIncreaseByEmployee(Long employeeId) {
        String sql = """
                SELECT *
                FROM fn_get_form_salary_increase_by_employee(?)
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, employeeId);
        return query.getResultList();
    }


    public StoredProcedureQuery updatePromotion(
            Long userId,
            Long formID,
            PromotionUpdateRequest req
    ) {
        StoredProcedureQuery q =
                entityManager.createStoredProcedureQuery("sp_update_form_promotion");

        q.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(5, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(8, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(9, Boolean.class, ParameterMode.OUT);
        q.registerStoredProcedureParameter(10, String.class, ParameterMode.OUT);

        q.setParameter(1, formID);
        q.setParameter(2, req.getOldPosition());
        q.setParameter(3, req.getNewPosition());
        q.setParameter(4, req.getReason());
        q.setParameter(5, req.getReceiverId());
        q.setParameter(6, req.getStatus());
        q.setParameter(7, req.getLeaderNote());
        q.setParameter(8, userId);

        q.execute();
        return q;

}
    public StoredProcedureQuery callUpdateProposal(
            Long userId,
            Long formId,
            ProposalUpdateRequest req
    ) {
        StoredProcedureQuery q =
                entityManager.createStoredProcedureQuery("sp_update_form_proposal");

        q.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(4, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(6, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(7, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(8, Boolean.class, ParameterMode.OUT);
        q.registerStoredProcedureParameter(9, String.class, ParameterMode.OUT);

        q.setParameter(1, formId);
        q.setParameter(2, req.getContent());
        q.setParameter(3, req.getDetail());
        q.setParameter(4, req.getReceiverId());
        q.setParameter(5, req.getStatus());
        q.setParameter(6, req.getLeaderNote());
        q.setParameter(7, userId);

        q.execute();
        return q;
    }
    public StoredProcedureQuery callUpdateSalaryIncrease(
            Long userId,
            Long formId,
            SalaryIncreaseUpdateRequest req
    ) {
        StoredProcedureQuery q =
                entityManager.createStoredProcedureQuery("sp_update_form_salary_increase");

        q.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(5, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(6, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(7, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(8, String.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(9, Long.class, ParameterMode.IN);
        q.registerStoredProcedureParameter(10, Boolean.class, ParameterMode.OUT);
        q.registerStoredProcedureParameter(11, String.class, ParameterMode.OUT);

        q.setParameter(1, formId);
        q.setParameter(2, req.getTimes());
        q.setParameter(3, req.getOldLevel());
        q.setParameter(4, req.getNewLevel());
        q.setParameter(5, req.getReason());
        q.setParameter(6, req.getReceiverId());
        q.setParameter(7, req.getStatus());
        q.setParameter(8, req.getLeaderNote());
        q.setParameter(9, userId);

        q.execute();
        return q;
    }
    //
    @SuppressWarnings("unchecked")
    public List<Object[]> getPendingpromotionList(Long userId) {
        String sql = """
            SELECT *
            FROM fn_list_promotion_pending(?)
        """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> getPendingproposalList(Long userId) {
        String sql = """
            SELECT *
            FROM fn_list_proposal_pending(?)
        """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        return query.getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<Object[]> getPendingsalaryList(Long userId) {
        String sql = """
            SELECT *
            FROM fn_list_salary_increase_pending(?)
        """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        return query.getResultList();
    }
}
