package l3_manager_employee.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DetailRespository {

    private final EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public List<Object[]> callFnEmployeeDetail(Long userId, Long employeeId) {

        String sql = """
            SELECT *
            FROM fn_employee_detail(?, ?)
        """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.setParameter(2, employeeId);

        return query.getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<Object[]> getFamilyRelations(Long userId, Long employeeId){
        String sql = """
                SELECT *
                FROM fn_employee_family_relations(?,?)
            """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.setParameter(2, employeeId);
        return query.getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<Object[]> getDetailEmpolyeeCertificates(Long userId, Long employeeId){
        String sql = """
                SELECT *
                FROM fn_employee_certificates(?,?)
            """;
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.setParameter(2, employeeId);
        return query.getResultList();
    }
    @SuppressWarnings("unchecked")
    public List<Object[]> getRegistrationDetail(Long userId, Long formId) {
        String sql = """
            SELECT *
            FROM fn_form_employee_registration_detail(?, ?)
        """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.setParameter(2, formId);

        return query.getResultList();
    }

    /* ================= PROMOTION ================= */

    @SuppressWarnings("unchecked")
    public List<Object[]> getPromotionDetail(Long userId, Long formId) {
        String sql = """
            SELECT *
            FROM fn_form_promotion_detail(?, ?)
        """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.setParameter(2, formId);

        return query.getResultList();
    }

    /* ================= PROPOSAL ================= */

    @SuppressWarnings("unchecked")
    public List<Object[]> getProposalDetail(Long userId, Long formId) {
        String sql = """
            SELECT *
            FROM fn_form_proposal_detail(?, ?)
        """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.setParameter(2, formId);

        return query.getResultList();
    }

    /* ================= SALARY INCREASE ================= */

    @SuppressWarnings("unchecked")
    public List<Object[]> getSalaryIncreaseDetail(Long userId, Long formId) {
        String sql = """
            SELECT *
            FROM fn_form_salary_increase_detail(?, ?)
        """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, userId);
        query.setParameter(2, formId);

        return query.getResultList();
    }
}
