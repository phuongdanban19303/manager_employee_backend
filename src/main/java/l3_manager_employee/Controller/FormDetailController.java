package l3_manager_employee.Controller;

import l3_manager_employee.Config.SecurityUtils;
import l3_manager_employee.DTO.Respones.*;

import l3_manager_employee.Repository.DetailRespository;
import l3_manager_employee.Service.EmployeeFormService;
import l3_manager_employee.commons.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/form-detail")
@RequiredArgsConstructor
public class FormDetailController {

    private final EmployeeFormService formDetailService;

    /* ================= EMPLOYEE REGISTRATION ================= */

    @GetMapping("/registration/{formId}")
    public ApiResponse<EmployeeRegistrationDetailResponse> getRegistrationDetail(
            @PathVariable("formId") Long formId
    ) {
        Long userId = SecurityUtils.getUserId();

        EmployeeRegistrationDetailResponse response =
                formDetailService.getDetailRegistration(userId, formId);

        return ApiResponse.success(response);
    }

    /* ================= PROMOTION ================= */

    @GetMapping("/promotion/{formId}")
    public ApiResponse<PromotionDetailResponse> getPromotionDetail(
            @PathVariable("formId") Long formId
    ) {
        Long userId = SecurityUtils.getUserId();

        PromotionDetailResponse response =
                formDetailService.getDetailPromotionDetai(userId, formId);

        return ApiResponse.success(response);
    }

    /* ================= PROPOSAL ================= */

    @GetMapping("/proposal/{formId}")
    public ApiResponse<ProposalDetailResponse> getProposalDetail(
            @PathVariable("formId") Long formId
    ) {
        Long userId = SecurityUtils.getUserId();

        ProposalDetailResponse response =
                formDetailService.getDetailProposal(userId, formId);

        return ApiResponse.success(response);
    }

    /* ================= SALARY INCREASE ================= */

    @GetMapping("/salary-increase/{formId}")
    public ApiResponse<SalaryIncreaseDetailResponse> getSalaryIncreaseDetail(
            @PathVariable("formId") Long formId
    ) {
        Long userId = SecurityUtils.getUserId();

        SalaryIncreaseDetailResponse response =
                formDetailService.getDetailIncrease(userId, formId);

        return ApiResponse.success(response);
    }
}
