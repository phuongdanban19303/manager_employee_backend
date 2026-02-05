package l3_manager_employee.Service;

import l3_manager_employee.DTO.Request.EmployeeCertificateRequest;
import l3_manager_employee.DTO.Request.EmployeeFamilyRequest;
import l3_manager_employee.DTO.Request.EmployeeRequest;
import l3_manager_employee.DTO.Request.EmployeeUpdateRequest;
import l3_manager_employee.DTO.Respones.*;

import java.util.List;

public interface EmpolyeeService {
    EmployeeResponse createEmployee(Long userId, EmployeeRequest req);

    void addCertificates(
            Long userId,
            Long employeeId,
            List<EmployeeCertificateRequest> certificates
    );

    void addEmpolyeeFamily(Long userId, Long employeeId, List<EmployeeFamilyRequest> EmployeeFamilyJson);

    void updateEmployeeFamily(Long userId, Long employeeId, EmployeeFamilyRequest req);

    void updateEmpolyeeCertificate(Long userId, Long employeeId, EmployeeCertificateRequest req);

    void updateEmployeeInfo(Long userId, Long employeeId, EmployeeUpdateRequest request);

    void deleteEmployee(Long userId, Long employeeId);

    EmployeeDetailResponse getEmployeeDetail(Long userId, Long employeeId);

    List<EmployeeFamilyRelationResponse> getFamilyRelations(
            Long userId,
            Long employeeId
    );
    List<EmployeeCertificateDetailResponse> getDetailEmployeeCertificate(
            Long userId,
            Long formId
    );
    List<EmployeeListResponse> getEmployeeListByUser(Long userId);
    EmployeeViewDetailResponse viewEmployeeDetail(Long userId, Long employeeId);
    List<EmployeeListResponse> getEmployeeListApprovedByUser(Long userId);
}
