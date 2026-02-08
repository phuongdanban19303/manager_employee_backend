package l3_manager_employee.Controller;

import l3_manager_employee.Config.SecurityUtils;
import l3_manager_employee.DTO.Request.EmployeeCertificateSubmitRequest;
import l3_manager_employee.DTO.Request.EmployeeRegistrationRequest;
import l3_manager_employee.DTO.Request.EmployeeRegistrationUpdateRequest;
import l3_manager_employee.DTO.Request.ProcessEmployeeRegistrationRequest;
import l3_manager_employee.DTO.Respones.EmployeeListRegistrationResponse;
import l3_manager_employee.DTO.Respones.EmployeeRegistrationPendingResponse;
import l3_manager_employee.DTO.Respones.EmployeeRegistrationResponse;
import l3_manager_employee.DTO.Respones.UserManagerResponse;
import l3_manager_employee.Service.EmployeeRegistrationService;
import l3_manager_employee.commons.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeRegistrationController {

    private final EmployeeRegistrationService registrationService;

    /* ===============================
       CHECK READY
       =============================== */
    @GetMapping("/{id}/registration/check")
    public ApiResponse<Boolean> checkReady(@PathVariable Long id) {
        Long userId = SecurityUtils.getUserId();
        boolean ready = registrationService.checkReadyForRegistration(userId, id);
        return ApiResponse.success(ready);
    }

    @PostMapping("/{id}/registration")
    public ApiResponse<EmployeeRegistrationResponse> createRegistration(
            @PathVariable Long id,
            @RequestBody EmployeeRegistrationRequest request
    ) {
        Long userId = SecurityUtils.getUserId();
        EmployeeRegistrationResponse EmployeeRegistration = registrationService.createRegistration(userId, id, request);
        return ApiResponse.success(EmployeeRegistration);
    }
    @PostMapping("/{formId}/submit")
    public ApiResponse<Void> submitRegistration(@PathVariable Long formId, @RequestBody EmployeeCertificateSubmitRequest req) {
        Long userId = SecurityUtils.getUserId();
        registrationService.submitRegistration(userId, formId,req);
        return ApiResponse.success(null);
    }

    @PostMapping("/registration/process")
    public ApiResponse<Void> processRegistration(
            @RequestBody ProcessEmployeeRegistrationRequest
                    request
    ) {
        Long leaderId = SecurityUtils.getUserId();
        registrationService.processRegistration(leaderId, request);
        return ApiResponse.success(null);
    }
    @GetMapping("registration/pending")
    public ApiResponse<List<EmployeeRegistrationPendingResponse>> getPending(
    ) {
        Long leaderId = SecurityUtils.getUserId();
        return ApiResponse.success(
                registrationService.getPendingRegistrations(leaderId)
        );
    }
    @GetMapping("/users/managers")
    public ApiResponse<List<UserManagerResponse>> getManagers() {

        return ApiResponse.success(
                registrationService.getListManagers()
        );
    }
    @GetMapping("/{id}/registration")
    public ApiResponse<EmployeeListRegistrationResponse> getRegistrationByEmployee(
            @PathVariable("id") Long employeeId
    ) {
        Long userId = SecurityUtils.getUserId();

        EmployeeListRegistrationResponse response =
                registrationService.getByEmployee(userId, employeeId);

        return ApiResponse.success(response);
    }

    @PutMapping("/registration")
    public ApiResponse<Void> updateRegistration(
            @RequestBody EmployeeRegistrationUpdateRequest request
    ) {
        Long userId = SecurityUtils.getUserId();

        registrationService.update(userId, request);

        return ApiResponse.success(null);
    }
}
