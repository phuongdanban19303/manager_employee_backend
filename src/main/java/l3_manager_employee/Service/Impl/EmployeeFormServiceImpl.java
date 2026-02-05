package l3_manager_employee.Service.Impl;

import jakarta.persistence.StoredProcedureQuery;
import l3_manager_employee.DTO.Request.*;
import l3_manager_employee.DTO.Respones.*;
import l3_manager_employee.Repository.DetailRespository;
import l3_manager_employee.Repository.EmployeeRepository;
import l3_manager_employee.Repository.FromGeneralRepository;
import l3_manager_employee.Service.EmployeeFormService;
import l3_manager_employee.commons.exception.AppException;
import l3_manager_employee.commons.exception.ErrorCode;
import l3_manager_employee.commons.exception.ErrorCodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeFormServiceImpl implements EmployeeFormService {

    private final FromGeneralRepository fromGeneralRepository;
    private final DetailRespository DetailRespository;

    @Override
    @Transactional(rollbackFor = Exception.class) // Thêm dòng này
    public CreateFormResponse createPromotion(Long userId, PromotionRequest dto) {
        StoredProcedureQuery result = fromGeneralRepository.createPromotion(userId, dto);
        Boolean ready = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        Long formID = (Long) result.getOutputParameterValue("o_form_id");

        if (Boolean.FALSE.equals(ready)) {
            log.error(errorCode);
            throw (ErrorCodeMapper.map(errorCode));
        }
         return new CreateFormResponse(formID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // Thêm dòng này
    public CreateFormResponse createProposal(Long userId, ProposalRequest dto) {
        StoredProcedureQuery result = fromGeneralRepository.createProposal(userId, dto);
        Boolean ready = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        Long formID = (Long) result.getOutputParameterValue("o_form_id");

        if (Boolean.FALSE.equals(ready)) {
            throw (ErrorCodeMapper.map(errorCode));
        }
        return new CreateFormResponse(formID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // Thêm dòng này
    public CreateFormResponse createSalaryIncrease(Long userId, SalaryIncreaseRequest dto) {
        StoredProcedureQuery result = fromGeneralRepository.createSalaryIncrease(userId, dto);
        Boolean ready = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        Long formID = (Long) result.getOutputParameterValue("o_form_id");

        if (Boolean.FALSE.equals(ready)) {
            log.error(errorCode);
            throw (ErrorCodeMapper.map(errorCode));
        }
        return new CreateFormResponse(formID);
    }

    @Override
    public void submitPromotion(Long userId, SubmitFormRequest req) {
        StoredProcedureQuery result =
                fromGeneralRepository.submitPromotion(userId, req.getFormId(), req.getReceiverId());
        Boolean ready = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(ready)) {
            throw (ErrorCodeMapper.map(errorCode));
        }

    }

    @Override
    public void processPromotion(Long managerId, ProcessFormRequest req) {
        StoredProcedureQuery result =
                fromGeneralRepository.processPromotion(managerId, req.getFormId(),
                        req.getAction(), req.getLeaderNote());
        Boolean ready = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(ready)) {
            throw (ErrorCodeMapper.map(errorCode));
        }

    }

    @Override
    public void submitProposal(Long userId, SubmitFormRequest req) {
        StoredProcedureQuery result =
                fromGeneralRepository.submitProposal(userId, req.getFormId(), req.getReceiverId());
        Boolean ready = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(ready)) {
            throw (ErrorCodeMapper.map(errorCode));
        }

    }

    @Override
    public void processProposal(Long managerId, ProcessFormRequest req) {
        StoredProcedureQuery result =
                fromGeneralRepository.processProposal(managerId, req.getFormId(),
                        req.getAction(), req.getLeaderNote());
        Boolean ready = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(ready)) {
            throw (ErrorCodeMapper.map(errorCode));
        }

    }

    @Override
    public void submitSalaryIncrease(Long userId, SubmitFormRequest req) {
        StoredProcedureQuery result =
                fromGeneralRepository.submitSalaryIncrease(userId, req.getFormId(), req.getReceiverId());
        Boolean ready = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(ready)) {
            throw (ErrorCodeMapper.map(errorCode));
        }

    }

    @Override
    public void processSalaryIncrease(Long managerId, ProcessFormRequest req) {
        StoredProcedureQuery result =
                fromGeneralRepository.processSalaryIncrease(managerId, req.getFormId(),
                        req.getAction(), req.getLeaderNote());
        Boolean ready = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(ready)) {
            throw (ErrorCodeMapper.map(errorCode));
        }
    }

    @Override
    public EmployeeRegistrationDetailResponse getDetailRegistration(
            Long userId,
            Long formId
    ) {
        List<Object[]> result =
                DetailRespository.getRegistrationDetail(userId, formId);

        Object[] r = result.get(0);
        int i = 0;

        return EmployeeRegistrationDetailResponse.builder()
                .id(((Number) r[i++]).longValue())
                .employeeId(((Number) r[i++]).longValue())
                .resume((String) r[i++])
                .cvUrl((String) r[i++])
                .note((String) r[i++])
                .jobPosition((String) r[i++])
                .receiverId(((Number) r[i++]).longValue())
                .status((String) r[i++])
                .submitDate((LocalDateTime) r[i++])
                .approveDate((LocalDateTime) r[i++])
                .leaderNote((String) r[i++])
                .createdBy(((Number) r[i++]).longValue())
                .createdAt((LocalDateTime) r[i++])
                .updatedBy(
                        r[i++] != null
                                ? ((Number) r[i - 1]).longValue()
                                : null
                )
                .updatedAt(
                        r[i++] != null
                                ? (LocalDateTime) r[i - 1]
                                : null
                )
                .build();
    }

    /* ================= PROMOTION ================= */

    @Override
    public PromotionDetailResponse getDetailPromotionDetai(
            Long userId,
            Long formId
    ) {
        List<Object[]> result =
                DetailRespository.getPromotionDetail(userId, formId);

        Object[] r = result.get(0);
        int i = 0;

        return PromotionDetailResponse.builder()
                .id(((Number) r[i++]).longValue())
                .employeeId(((Number) r[i++]).longValue())
                .oldPosition((String) r[i++])
                .newPosition((String) r[i++])
                .reason((String) r[i++])
                .receiverId(((Number) r[i++]).longValue())
                .status((String) r[i++])
                .submitDate((LocalDateTime) r[i++])
                .approveDate((LocalDateTime) r[i++])
                .leaderNote((String) r[i++])
                .createdBy(((Number) r[i++]).longValue())
                .createdAt((LocalDateTime) r[i++])
                .updatedBy(
                        r[i++] != null
                                ? ((Number) r[i - 1]).longValue()
                                : null
                )
                .updatedAt(
                        r[i++] != null
                                ? (LocalDateTime) r[i - 1]
                                : null
                )
                .build();
    }

    /* ================= PROPOSAL ================= */

    @Override
    public ProposalDetailResponse getDetailProposal(
            Long userId,
            Long formId
    ) {
        List<Object[]> result =
                DetailRespository.getProposalDetail(userId, formId);

        Object[] r = result.get(0);
        int i = 0;

        return ProposalDetailResponse.builder()
                .id(((Number) r[i++]).longValue())
                .employeeId(((Number) r[i++]).longValue())
                .content((String) r[i++])
                .detail((String) r[i++])
                .receiverId(((Number) r[i++]).longValue())
                .status((String) r[i++])
                .submitDate((LocalDateTime) r[i++])
                .approveDate((LocalDateTime) r[i++])
                .leaderNote((String) r[i++])
                .createdBy(((Number) r[i++]).longValue())
                .createdAt((LocalDateTime) r[i++])
                .updatedBy(
                        r[i++] != null
                                ? ((Number) r[i - 1]).longValue()
                                : null
                )
                .updatedAt(
                        r[i++] != null
                                ? (LocalDateTime) r[i - 1]
                                : null
                )
                .build();
    }

    /* ================= SALARY INCREASE ================= */

    @Override
    public SalaryIncreaseDetailResponse getDetailIncrease(
            Long userId,
            Long formId
    ) {
        List<Object[]> result =
                DetailRespository.getSalaryIncreaseDetail(userId, formId);

        Object[] r = result.get(0);
        int i = 0;

        return SalaryIncreaseDetailResponse.builder()
                .id(((Number) r[i++]).longValue())
                .employeeId(((Number) r[i++]).longValue())
                .times(((Number) r[i++]).intValue())
                .oldLevel((String) r[i++])
                .newLevel((String) r[i++])
                .reason((String) r[i++])
                .receiverId(((Number) r[i++]).longValue())
                .status((String) r[i++])
                .submitDate((LocalDateTime) r[i++])
                .approveDate((LocalDateTime) r[i++])
                .leaderNote((String) r[i++])
                .createdBy(((Number) r[i++]).longValue())
                .createdAt((LocalDateTime) r[i++])
                .updatedBy(
                        r[i++] != null
                                ? ((Number) r[i - 1]).longValue()
                                : null
                )
                .updatedAt(
                        r[i++] != null
                                ? (LocalDateTime) r[i - 1]
                                : null
                )
                .build();
    }
    @Override
    public List<FormPromotionResponse> getPromotionByEmployee(Long employeeId) {
        try {
            List<Object[]> result = fromGeneralRepository.getFormPromotionByEmployee(employeeId);

            return result.stream().map(r ->
                    FormPromotionResponse.builder()
                            .id(((Number) r[0]).longValue())
                            .employeeId(((Number) r[1]).longValue())
                            .oldPosition((String) r[2])
                            .newPosition((String) r[3])
                            .reason((String) r[4])
                            .receiverId(r[5] != null ? ((Number) r[5]).longValue() : null)
                            .status((String) r[6])
                            .submitDate((LocalDateTime) r[7])
                            .approveDate((LocalDateTime) r[8])
                            .leaderNote((String) r[9])
                            .createdAt((LocalDateTime) r[10])
                            .build()
            ).toList();

        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }
    @Override
    public List<FormProposalResponse> getProposalByEmployee(Long employeeId) {
        try {
            List<Object[]> result = fromGeneralRepository.getFormProposalByEmployee(employeeId);

            return result.stream().map(r ->
                    FormProposalResponse.builder()
                            .id(((Number) r[0]).longValue())
                            .employeeId(((Number) r[1]).longValue())
                            .content((String) r[2])
                            .detail((String) r[3])
                            .receiverId(r[4] != null ? ((Number) r[4]).longValue() : null)
                            .status((String) r[5])
                            .submitDate((LocalDateTime) r[6])
                            .approveDate((LocalDateTime) r[7])
                            .leaderNote((String) r[8])
                            .createdAt((LocalDateTime) r[9])
                            .build()
            ).toList();

        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }
    @Override
    public List<FormSalaryIncreaseResponse> getSalaryIncreaseByEmployee(Long employeeId) {
        try {
            List<Object[]> result = fromGeneralRepository.getFormSalaryIncreaseByEmployee(employeeId);

            return result.stream().map(r ->
                    FormSalaryIncreaseResponse.builder()
                            .id(((Number) r[0]).longValue())
                            .employeeId(((Number) r[1]).longValue())
                            .times(r[2] != null ? ((Number) r[2]).intValue() : null)
                            .oldLevel((String) r[3])
                            .newLevel((String) r[4])
                            .reason((String) r[5])
                            .receiverId(r[6] != null ? ((Number) r[6]).longValue() : null)
                            .status((String) r[7])
                            .submitDate((LocalDateTime) r[8])
                            .approveDate((LocalDateTime) r[9])
                            .leaderNote((String) r[10])
                            .createdAt((LocalDateTime) r[11])
                            .build()
            ).toList();

        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }
    @Override
    public void updatePromotion(
            Long userId,
            Long formId,
            PromotionUpdateRequest req
    ) {
        StoredProcedureQuery result =
                fromGeneralRepository.updatePromotion(userId, formId, req);

        Boolean success =
                (Boolean) result.getOutputParameterValue("o_success");

        String errorCode =
                (String) result.getOutputParameterValue("o_message");

        if (Boolean.FALSE.equals(success)) {
            log.error("updatePromotion failed: {}", errorCode);
            throw ErrorCodeMapper.map(errorCode);
        }
    }
    @Override
    public void updateProposal(
            Long userId,
            Long formId,
            ProposalUpdateRequest req
    ) {
        StoredProcedureQuery result =
                fromGeneralRepository.callUpdateProposal(userId, formId, req);

        Boolean success =
                (Boolean) result.getOutputParameterValue("o_success");

        String errorCode =
                (String) result.getOutputParameterValue("o_message");

        if (Boolean.FALSE.equals(success)) {
            log.error("updateProposal failed: {}", errorCode);
            throw ErrorCodeMapper.map(errorCode);
        }
    }
    @Override
    public void updateSalaryIncrease(
            Long userId,
            Long formId,
            SalaryIncreaseUpdateRequest req
    ) {
        StoredProcedureQuery result =
                fromGeneralRepository.callUpdateSalaryIncrease(userId, formId, req);

        Boolean success =
                (Boolean) result.getOutputParameterValue("o_success");

        String errorCode =
                (String) result.getOutputParameterValue("o_message");

        if (Boolean.FALSE.equals(success)) {
            log.error("updateSalaryIncrease failed: {}", errorCode);
            throw ErrorCodeMapper.map(errorCode);
        }
    }

    @Override
    public List<PromotionPendingResponse> getPendingPromotions(Long userId) {
        try {
            List<Object[]> result = fromGeneralRepository.getPendingpromotionList(userId);

            return result.stream()
                    .map(r -> {
                        int i = 0;
                        return PromotionPendingResponse.builder()
                                .formId(((Number) r[i++]).longValue())
                                .employeeId(((Number) r[i++]).longValue())
                                .employeeCode((String) r[i++])
                                .employeeName((String) r[i++])
                                .oldPosition((String) r[i++])
                                .newPosition((String) r[i++])
                                .receiverId(((Number) r[i++]).longValue())
                                .status((String) r[i++])
                                .submitDate((LocalDateTime) r[i++])
                                .createdAt((LocalDateTime) r[i++])
                                .build();
                    })
                    .toList();

        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }
    @Override
    public List<ProposalPendingResponse> getPendingProposals(Long userId) {
        try {
            List<Object[]> result = fromGeneralRepository.getPendingproposalList(userId);

            return result.stream()
                    .map(r -> {
                        int i = 0;
                        return ProposalPendingResponse.builder()
                                .formId(((Number) r[i++]).longValue())
                                .employeeId(((Number) r[i++]).longValue())
                                .employeeCode((String) r[i++])
                                .employeeName((String) r[i++])
                                .content((String) r[i++])
                                .receiverId(((Number) r[i++]).longValue())
                                .status((String) r[i++])
                                .submitDate((LocalDateTime) r[i++])
                                .createdAt((LocalDateTime) r[i++])
                                .build();
                    })
                    .toList();

        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }
    @Override
    public List<SalaryIncreasePendingResponse> getPendingSalaryIncreases(Long userId) {
        try {
            List<Object[]> result = fromGeneralRepository.getPendingsalaryList(userId);

            return result.stream()
                    .map(r -> {
                        int i = 0;
                        return SalaryIncreasePendingResponse.builder()
                                .formId(((Number) r[i++]).longValue())
                                .employeeId(((Number) r[i++]).longValue())
                                .employeeCode((String) r[i++])
                                .employeeName((String) r[i++])
                                .times(((Number) r[i++]).intValue())
                                .oldLevel((String) r[i++])
                                .newLevel((String) r[i++])
                                .receiverId(((Number) r[i++]).longValue())
                                .status((String) r[i++])
                                .submitDate((LocalDateTime) r[i++])
                                .createdAt((LocalDateTime) r[i++])
                                .build();
                    })
                    .toList();

        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }
}

