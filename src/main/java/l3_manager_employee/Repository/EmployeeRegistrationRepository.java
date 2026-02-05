package l3_manager_employee.Repository;

import jakarta.persistence.*;
import l3_manager_employee.DTO.Request.EmployeeCertificateSubmitRequest;
import l3_manager_employee.DTO.Respones.EmployeeRegistrationPendingResponse;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class EmployeeRegistrationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public StoredProcedureQuery callCheckReady(Long userId, Long employeeId) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_check_employee_ready_for_registration");

        query.registerStoredProcedureParameter("p_user_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class,jakarta.persistence.ParameterMode.IN);

        query.registerStoredProcedureParameter("o_ready", Boolean.class,jakarta.persistence.ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class,jakarta.persistence.ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", employeeId);

        query.execute();
        return query;
    }

    public StoredProcedureQuery callCreateRegistration(
            Long userId,
            Long employeeId,
            String formJson
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_create_employee_registration");

        query.registerStoredProcedureParameter("p_user_id", Long.class,jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class,jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_data", String.class,jakarta.persistence.ParameterMode.IN);

        query.registerStoredProcedureParameter("o_success", Boolean.class,jakarta.persistence.ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class,jakarta.persistence.ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_form_id", Long.class, ParameterMode.OUT);


        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", employeeId);
        query.setParameter("p_form_data", formJson);

        query.execute();
        return query;
    }
    public StoredProcedureQuery submitRegistration(Long userId, Long formId, EmployeeCertificateSubmitRequest request) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_submit_employee_registration");

        query.registerStoredProcedureParameter("p_user_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_note", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_receiver_id", Long.class, jakarta.persistence.ParameterMode.IN);


        query.registerStoredProcedureParameter("o_success", Boolean.class, jakarta.persistence.ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, jakarta.persistence.ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_note", request.getNote());
        query.setParameter("p_receiver_id",  request.getReceiverId());


        query.execute();
        return query;
    }

    /* ===============================
       PROCESS REGISTRATION
       =============================== */
    public StoredProcedureQuery processRegistration(
            Long leaderId,
            Long formId,
            String action,
            String note,
            LocalDate actionDate
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_process_employee_registration");

        query.registerStoredProcedureParameter("p_leader_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_form_id", Long.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_action", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_note", String.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("p_action_date", java.time.LocalDate.class, jakarta.persistence.ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, jakarta.persistence.ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, jakarta.persistence.ParameterMode.OUT);

        query.setParameter("p_leader_id", leaderId);
        query.setParameter("p_form_id", formId);
        query.setParameter("p_action", action);
        query.setParameter("p_note", note);
        query.setParameter("p_action_date", actionDate);

        query.execute();
        return query;
    }
    @SuppressWarnings("unchecked")
    public List<Object[]> getPendingList(Long userId) {
            String sql = """
                SELECT *
                FROM fn_list_employee_registration_pending(?)
            """;
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, userId);
            return query.getResultList();

    }
    public List<Object[]> getManagerList() {

        String sql = """
            SELECT *
            FROM fn_list_get_user_managers()
        """;

        Query query = entityManager.createNativeQuery(sql);
        return query.getResultList();
    }

    public Object callFnGetByEmployee(Long employeeId) {
        Query query = entityManager.createNativeQuery(
                "SELECT fn_get_employee_registration_by_employee(?)"
        );
        query.setParameter(1, employeeId);
        return query.getSingleResult();
    }

    public StoredProcedureQuery callUpdateRegistration(
            Long id,
            String resume,
            String cvUrl,
            String note,
            String jobPosition,
            Long userId
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_update_employee_registration");

        query.registerStoredProcedureParameter("p_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_resume", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cv_url", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_note", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_job_position", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);

        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error", String.class, ParameterMode.OUT);

        query.setParameter("p_id", id);
        query.setParameter("p_resume", resume);
        query.setParameter("p_cv_url", cvUrl);
        query.setParameter("p_note", note);
        query.setParameter("p_job_position", jobPosition);
        query.setParameter("p_user_id", userId);

        query.execute();
        return query;
    }
}
