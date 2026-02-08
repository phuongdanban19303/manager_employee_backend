package l3_manager_employee.Controller;

import jakarta.validation.Valid;
import l3_manager_employee.Config.SecurityUtils;
import l3_manager_employee.DTO.Request.EmployeeCertificateRequest;
import l3_manager_employee.DTO.Request.EmployeeFamilyRequest;
import l3_manager_employee.DTO.Request.EmployeeRequest;
import l3_manager_employee.DTO.Request.EmployeeUpdateRequest;
import l3_manager_employee.DTO.Respones.*;
import l3_manager_employee.Service.EmpolyeeService;
import l3_manager_employee.Service.Impl.AuthServiceImpl;
import l3_manager_employee.commons.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmpolyeeService empolyeeService;

    @PostMapping("")
    public ApiResponse<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {

        Long userId = SecurityUtils.getUserId();
        EmployeeResponse employeeId = empolyeeService.createEmployee(userId, request);
        return ApiResponse.success(employeeId);
    }

    @PostMapping("/{id}/certificates")
    public ApiResponse<Void> addCertificates(@PathVariable Long id, @Valid @RequestBody List<EmployeeCertificateRequest> certificates) {
        Long userId = SecurityUtils.getUserId();
        empolyeeService.addCertificates(userId, id, certificates);
        return ApiResponse.success(null);
    }

    @PostMapping("/{id}/empolyfamily")
    public ApiResponse<Void> addEmpolyeefamily(@PathVariable Long id, @RequestBody List<EmployeeFamilyRequest> EmployeeFamily) {
        Long userId = SecurityUtils.getUserId();
        empolyeeService.addEmpolyeeFamily(userId, id, EmployeeFamily);
        return ApiResponse.success(null);
    }

    @PutMapping("{certId}/certificates")
    public ApiResponse<Void> updateEmployeeCertificate(
            @PathVariable Long certId,
            @RequestBody EmployeeCertificateRequest request
    ) {
        Long userId = SecurityUtils.getUserId();
        empolyeeService.updateEmpolyeeCertificate(userId, certId, request);
        return ApiResponse.success(null);
    }


    @PutMapping("/{familyId}/empolyfamily")
    public ApiResponse<Void> updateEmployeeFamily(
            @PathVariable Long familyId,
            @RequestBody EmployeeFamilyRequest request
    ) {
        Long userId = SecurityUtils.getUserId();
        empolyeeService.updateEmployeeFamily(userId, familyId, request);
        return ApiResponse.success(null);
    }
    @PutMapping("/{id}")
    public ApiResponse<Void> updateEmployeeInfo(
            @PathVariable("id") Long employeeId,
            @RequestBody EmployeeUpdateRequest request
    ) {
        Long userId = SecurityUtils.getUserId();
        empolyeeService.updateEmployeeInfo(userId, employeeId, request);
        return ApiResponse.success(null);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteEmployee(@PathVariable("id") Long employeeId) {
        Long userId = SecurityUtils.getUserId();
        empolyeeService.deleteEmployee(userId, employeeId);
        return ApiResponse.success(null);
    }

    @GetMapping("/Detail/{id}")
    public ApiResponse<EmployeeDetailResponse> getEmployeeDetail(
            @PathVariable("id") Long employeeId
    ) {
        Long userId = SecurityUtils.getUserId();

        EmployeeDetailResponse response =
                empolyeeService.getEmployeeDetail(userId, employeeId);

        return ApiResponse.success(response);
    }
    @GetMapping("/{id}/family")
    public ApiResponse<List<EmployeeFamilyRelationResponse>> getFamilyDetail(@PathVariable("id" )Long EmpolyeeId ){
        Long userId = SecurityUtils.getUserId();
        List<EmployeeFamilyRelationResponse> response = empolyeeService.getFamilyRelations(userId,EmpolyeeId);
        return ApiResponse.success(response);
    }
    @GetMapping("/{id}/certificate")
    public ApiResponse<List<EmployeeCertificateDetailResponse>> getEmployeeCertificateDetai(@PathVariable("id" )Long EmpolyeeId ){
        Long userId = SecurityUtils.getUserId();
        List<EmployeeCertificateDetailResponse> response = empolyeeService.getDetailEmployeeCertificate(userId,EmpolyeeId);
        return ApiResponse.success(response);
    }
    @GetMapping("/list")
    public ApiResponse<List<EmployeeListResponse>> getEmployeeListByUser() {
        Long userId = SecurityUtils.getUserId();
        List<EmployeeListResponse> response =
                empolyeeService.getEmployeeListByUser(userId);
        return ApiResponse.success(response);
    }
    @GetMapping("/{employeeId}/view-detail")
    public ApiResponse<EmployeeViewDetailResponse> viewEmployeeDetail(
            @PathVariable Long employeeId
    ) {
        Long userId = SecurityUtils.getUserId();
        return ApiResponse.success(
                empolyeeService.viewEmployeeDetail(userId, employeeId)
        );
    }
    @GetMapping("/list/approved")
    public ApiResponse<List<EmployeeListResponse>> getEmployeeListApprovedByUser() {
        Long userId = SecurityUtils.getUserId();

        List<EmployeeListResponse> response =
                empolyeeService.getEmployeeListApprovedByUser(userId);

        return ApiResponse.success(response);
}}

