package l3_manager_employee.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.Query;
import jakarta.persistence.StoredProcedureQuery;
import l3_manager_employee.DTO.Respones.EmployeeFamilyRelationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {

    private final EntityManager entityManager;

    public StoredProcedureQuery callCreateEmployee(
            Long userId,
            String fullName,
            String gender,
            java.time.LocalDate dob,
            String address,
            String team,
            String avatarUrl,
            String identityNumber,
            String phone,
            String email
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_add_employee");

        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_full_name", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_gender", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_date_of_birth", java.sql.Date.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_address", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_team", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_avatar_url", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_identity_number", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_phone", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN);

        query.registerStoredProcedureParameter("o_employee_id", Long.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_full_name", fullName);
        query.setParameter("p_gender", gender);
        query.setParameter("p_date_of_birth", java.sql.Date.valueOf(dob));
        query.setParameter("p_address", address);
        query.setParameter("p_team", team);
        query.setParameter("p_avatar_url", avatarUrl);
        query.setParameter("p_identity_number", identityNumber);
        query.setParameter("p_phone", phone);
        query.setParameter("p_email", email);

        query.execute();
        return query;
    }

    public StoredProcedureQuery callAddEmployeeCertificate(
            Long userId,
            Long employeeId,
            String certificatesJson   // JSON string
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_add_employee_certificates");

        // ===== IN PARAM (đúng thứ tự) =====
        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_certificates", String.class, ParameterMode.IN);

        // ===== OUT PARAM (đúng thứ tự DB) =====
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", employeeId);
        query.setParameter("p_certificates", certificatesJson);

        query.execute();
        return query;
    }

    public StoredProcedureQuery callAddEmployeeFamily(Long userId,
                                                      Long employeeId,
                                                      String familyrelations ){
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_add_employee_family");

        // ===== IN PARAM (đúng thứ tự) =====
        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_family_relations", String.class, ParameterMode.IN);

        // ===== OUT PARAM (đúng thứ tự DB) =====
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", employeeId);
        query.setParameter("p_family_relations", familyrelations);

        query.execute();
        return query;
    }

    public StoredProcedureQuery callUpdateEmployeeCertificate(Long userId, Long certId, String p_cert_data) {
        StoredProcedureQuery query= entityManager.createStoredProcedureQuery("sp_update_employee_certificate");
        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cert_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_cert_data", String.class, ParameterMode.IN);

        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_cert_id", certId);
        query.setParameter("p_cert_data", p_cert_data);
        query.execute();
        return query;
    }
    public StoredProcedureQuery callUpdateEmployeeFamily(Long userId, Long familyId, String familyrelations) {

        StoredProcedureQuery query= entityManager.createStoredProcedureQuery("sp_update_employee_family");
        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_family_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_family_data", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);
        query.setParameter("p_user_id", userId);
        query.setParameter("p_family_id", familyId);
        query.setParameter("p_family_data", familyrelations);
        query.execute();
        return query;
    }

    public StoredProcedureQuery callUpdateEmployeeInfo(
            Long userId,
            Long employeeId,
            String employeeData
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_update_employee_info");

        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_data", String.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", employeeId);
        query.setParameter("p_employee_data", employeeData);

        query.execute();
        return query;
    }
    public StoredProcedureQuery callDeleteEmployee(
            Long userId,
            Long employeeId
    ) {
        StoredProcedureQuery query =
                entityManager.createStoredProcedureQuery("sp_employee_delete");

        query.registerStoredProcedureParameter("p_user_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("p_employee_id", Long.class, ParameterMode.IN);
        query.registerStoredProcedureParameter("o_success", Boolean.class, ParameterMode.OUT);
        query.registerStoredProcedureParameter("o_error_code", String.class, ParameterMode.OUT);

        query.setParameter("p_user_id", userId);
        query.setParameter("p_employee_id", employeeId);

        query.execute();
        return query;
    }

    @SuppressWarnings("unchecked")
    public List<Object[]> getEmployeeListByUser(Long userId) {
        String sql = """
                SELECT *
                FROM fn_employee_list_by_user(?)
                """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        return query.getResultList();
    }

    public Object getEmployeeViewDetail(Long userId, Long employeeId) {

        String sql = """
                SELECT fn_employee_view_detail(?, ?)
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.setParameter(2, employeeId);

        return query.getSingleResult(); // jsonb
    }
    @SuppressWarnings("unchecked")
    public List<Object[]> getEmployeeListApprovedByUser(Long userId) {
        String sql = """
        SELECT *
        FROM fn_employee_list_approved_by_user(?)
    """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);

        return query.getResultList();
    }
}
