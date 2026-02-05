package l3_manager_employee.Service;

import l3_manager_employee.DTO.Request.EmployeeCertificateSubmitRequest;
import l3_manager_employee.DTO.Request.EmployeeRegistrationRequest;
import l3_manager_employee.DTO.Request.EmployeeRegistrationUpdateRequest;
import l3_manager_employee.DTO.Request.ProcessEmployeeRegistrationRequest;
import l3_manager_employee.DTO.Respones.EmployeeListRegistrationResponse;
import l3_manager_employee.DTO.Respones.EmployeeRegistrationPendingResponse;
import l3_manager_employee.DTO.Respones.EmployeeRegistrationResponse;
import l3_manager_employee.DTO.Respones.UserManagerResponse;

import java.util.List;

public interface EmployeeRegistrationService {
    boolean checkReadyForRegistration(Long userId, Long employeeId);

    EmployeeRegistrationResponse createRegistration(
            Long userId,
            Long employeeId,
            EmployeeRegistrationRequest request
    );
    void submitRegistration(Long userId, Long formId, EmployeeCertificateSubmitRequest req);

    void processRegistration(Long leaderId, ProcessEmployeeRegistrationRequest request);
    List<EmployeeRegistrationPendingResponse> getPendingRegistrations(Long userId);
    List<UserManagerResponse> getListManagers();
    EmployeeListRegistrationResponse getByEmployee(Long userId, Long employeeId);
    void update(Long userId, EmployeeRegistrationUpdateRequest request);
}
