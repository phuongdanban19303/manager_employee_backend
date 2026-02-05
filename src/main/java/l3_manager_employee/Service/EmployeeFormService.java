package l3_manager_employee.Service;

import l3_manager_employee.DTO.Request.*;
import l3_manager_employee.DTO.Respones.*;

import java.util.List;

public interface EmployeeFormService {
    CreateFormResponse createPromotion(Long userId, PromotionRequest dto);

    CreateFormResponse createProposal(Long userId, ProposalRequest dto);

    CreateFormResponse createSalaryIncrease(Long userId, SalaryIncreaseRequest dto);

    void submitPromotion(Long userId, SubmitFormRequest request);

    void processPromotion(Long managerId, ProcessFormRequest request);

    void submitProposal(Long userId, SubmitFormRequest request);

    void processProposal(Long managerId, ProcessFormRequest request);

    void submitSalaryIncrease(Long userId, SubmitFormRequest request);

    void processSalaryIncrease(Long managerId, ProcessFormRequest request);

    EmployeeRegistrationDetailResponse getDetailRegistration(
            Long userId,
            Long formId
    );

    SalaryIncreaseDetailResponse getDetailIncrease (
            Long userId,
            Long formId
    );
    PromotionDetailResponse getDetailPromotionDetai(
            Long userId,
            Long formId
    );
    ProposalDetailResponse getDetailProposal(
            Long userId,
            Long formId
    );
    List<FormPromotionResponse> getPromotionByEmployee(Long employeeId);

    List<FormProposalResponse> getProposalByEmployee(Long employeeId);

    List<FormSalaryIncreaseResponse> getSalaryIncreaseByEmployee(Long employeeId);
    void updatePromotion(
            Long userId,
            Long formId,
            PromotionUpdateRequest req
    );
    void updateProposal(
            Long userId,
            Long formId,
            ProposalUpdateRequest req
    );
    void updateSalaryIncrease(
            Long userId,
            Long formId,
            SalaryIncreaseUpdateRequest req
    );
    List<PromotionPendingResponse> getPendingPromotions(Long userId);
    List<ProposalPendingResponse> getPendingProposals(Long userId);
    List<SalaryIncreasePendingResponse> getPendingSalaryIncreases(Long userId);

}
