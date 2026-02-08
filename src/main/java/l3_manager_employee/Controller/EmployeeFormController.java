package l3_manager_employee.Controller;

import l3_manager_employee.Config.SecurityUtils;
import l3_manager_employee.DTO.Request.*;
import l3_manager_employee.DTO.Respones.*;
import l3_manager_employee.Service.EmployeeFormService;
import l3_manager_employee.commons.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
public class EmployeeFormController {

    private final EmployeeFormService employeeFormService;

    @PostMapping("/promotion")
    public ApiResponse<CreateFormResponse> createPromotion(@RequestBody PromotionRequest dto) {
        Long userId = SecurityUtils.getUserId();
        return ApiResponse.success(employeeFormService.createPromotion(userId, dto));
    }

    @PostMapping("/proposal")
    public ApiResponse<CreateFormResponse> createProposal(@RequestBody ProposalRequest dto) {
        Long userId = SecurityUtils.getUserId();
        return ApiResponse.success(employeeFormService.createProposal(userId, dto));
    }

    @PostMapping("/salary-increase")
    public ApiResponse<CreateFormResponse> createSalaryIncrease(@RequestBody SalaryIncreaseRequest dto) {
        Long userId = SecurityUtils.getUserId();
        return ApiResponse.success(employeeFormService.createSalaryIncrease(userId, dto));
    }

    @PostMapping("/promotion/submit")
    public ApiResponse<Void> submitPromotion(@RequestBody SubmitFormRequest request) {
        Long userId = SecurityUtils.getUserId();
        employeeFormService.submitPromotion(userId, request);
        return ApiResponse.success(null);
    }

    @PostMapping("/promotion/process")
    public ApiResponse<Void> processPromotion(@RequestBody ProcessFormRequest request) {
        Long userId = SecurityUtils.getUserId();
        employeeFormService.processPromotion(userId, request);
        return ApiResponse.success(null);
    }


    @PostMapping("/proposal/submit")
    public ApiResponse<Void> submitProposal(@RequestBody SubmitFormRequest request) {
        Long userId = SecurityUtils.getUserId();
        employeeFormService.submitProposal(userId, request);
        return ApiResponse.success(null);
    }

    @PostMapping("/proposal/process")
    public ApiResponse<Void> processProposal(@RequestBody ProcessFormRequest request) {
        Long userId = SecurityUtils.getUserId();
        employeeFormService.processProposal(userId, request);
        return ApiResponse.success(null);
    }

    /* ===== SALARY INCREASE ===== */

    @PostMapping("/salary/submit")
    public ApiResponse<Void> submitSalaryIncrease(@RequestBody SubmitFormRequest request) {
        Long userId = SecurityUtils.getUserId();
        employeeFormService.submitSalaryIncrease(userId, request);
        return ApiResponse.success(null);
    }

    @PostMapping("/salary/process")
    public ApiResponse<Void> processSalaryIncrease(@RequestBody ProcessFormRequest request) {
        Long userId = SecurityUtils.getUserId();
        employeeFormService.processSalaryIncrease(userId, request);
        return ApiResponse.success(null);
    }
    @GetMapping("/{employeeId}/promotions")
    public ApiResponse<List<FormPromotionResponse>> getPromotions(
            @PathVariable Long employeeId
    ) {
        return ApiResponse.success(
                employeeFormService.getPromotionByEmployee(employeeId)
        );
    }

    @GetMapping("/{employeeId}/proposals")
    public ApiResponse<List<FormProposalResponse>> getProposals(
            @PathVariable Long employeeId
    ) {
        return ApiResponse.success(
                employeeFormService.getProposalByEmployee(employeeId)
        );
    }

    @GetMapping("/{employeeId}/salary-increases")
    public ApiResponse<List<FormSalaryIncreaseResponse>> getSalaryIncreases(
            @PathVariable Long employeeId
    ) {
        return ApiResponse.success(
                employeeFormService.getSalaryIncreaseByEmployee(employeeId)
        );
    }
    @PutMapping("/salary-increase/{formId}")
    public ApiResponse<Void> updateSalaryIncrease(
            @PathVariable Long formId,
            @RequestBody SalaryIncreaseUpdateRequest request
    ) {
        Long userId = SecurityUtils.getUserId();
        employeeFormService.updateSalaryIncrease(userId, formId, request);
        return ApiResponse.success(null);
    }

    @PutMapping("proposal/{formId}")
    public ApiResponse<Void> updateProposal(
            @PathVariable Long formId,
            @RequestBody ProposalUpdateRequest request
    ) {
        Long userId = SecurityUtils.getUserId();

        employeeFormService.updateProposal(userId, formId, request);
        return ApiResponse.success(null);
    }
    @PutMapping("/promotion/{formId}")
    public ApiResponse<Void> updatePromotion(
            @PathVariable Long formId,
            @RequestBody PromotionUpdateRequest request
    ) {
        Long userId = SecurityUtils.getUserId();
        employeeFormService.updatePromotion(userId, formId, request);
        return ApiResponse.success(null);
    }

    @GetMapping("/pending/Proposal")
    public ApiResponse<List<ProposalPendingResponse>> getPendingProposal() {
        Long leaderId = SecurityUtils.getUserId();
        return ApiResponse.success(
                employeeFormService.getPendingProposals(leaderId)
        );
    }

    @GetMapping("/pending/Salary")
    public ApiResponse<List<SalaryIncreasePendingResponse>> getPendingSalary() {
        Long leaderId = SecurityUtils.getUserId();
        return ApiResponse.success(
                employeeFormService.getPendingSalaryIncreases(leaderId)
        );
    }
    @GetMapping("/pending/Promotion")
    public ApiResponse<List<PromotionPendingResponse>> getPendingPromotion() {
        Long leaderId = SecurityUtils.getUserId();
        return ApiResponse.success(
                employeeFormService.getPendingPromotions(leaderId)
        );
    }
}