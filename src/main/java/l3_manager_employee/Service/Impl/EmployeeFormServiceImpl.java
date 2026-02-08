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
    private final DetailRespository detailRespository;

    // === HÀM HELPER CHUYỂN ĐỔI "BẤT TỬ" ===
    private LocalDateTime toLocalDateTime(Object o) {
        if (o == null) return null;
        if (o instanceof java.sql.Timestamp) return ((java.sql.Timestamp) o).toLocalDateTime();
        return (LocalDateTime) o;
    }

    private LocalDate toLocalDate(Object o) {
        if (o == null) return null;
        if (o instanceof java.sql.Date) return ((java.sql.Date) o).toLocalDate();
        if (o instanceof java.sql.Timestamp) return ((java.sql.Timestamp) o).toLocalDateTime().toLocalDate();
        return (LocalDate) o;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
    @Transactional(rollbackFor = Exception.class)
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
        StoredProcedureQuery result = fromGeneralRepository.submitPromotion(userId, req.getFormId(), req.getReceiverId());
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_error_code"));
        }
    }

    @Override
    public void processPromotion(Long managerId, ProcessFormRequest req) {
        StoredProcedureQuery result = fromGeneralRepository.processPromotion(managerId, req.getFormId(), req.getAction(), req.getLeaderNote());
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_error_code"));
        }
    }

    @Override
    public void submitProposal(Long userId, SubmitFormRequest req) {
        StoredProcedureQuery result = fromGeneralRepository.submitProposal(userId, req.getFormId(), req.getReceiverId());
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_error_code"));
        }
    }

    @Override
    public void processProposal(Long managerId, ProcessFormRequest req) {
        StoredProcedureQuery result = fromGeneralRepository.processProposal(managerId, req.getFormId(), req.getAction(), req.getLeaderNote());
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_error_code"));
        }
    }

    @Override
    public void submitSalaryIncrease(Long userId, SubmitFormRequest req) {
        StoredProcedureQuery result = fromGeneralRepository.submitSalaryIncrease(userId, req.getFormId(), req.getReceiverId());
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_error_code"));
        }
    }

    @Override
    public void processSalaryIncrease(Long managerId, ProcessFormRequest req) {
        StoredProcedureQuery result = fromGeneralRepository.processSalaryIncrease(managerId, req.getFormId(), req.getAction(), req.getLeaderNote());
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_error_code"));
        }
    }

    @Override
    public EmployeeRegistrationDetailResponse getDetailRegistration(Long userId, Long formId) {
        List<Object[]> result = detailRespository.getRegistrationDetail(userId, formId);
        if (result.isEmpty()) throw new AppException(ErrorCode.EMP_NOT_FOUND);

        Object[] r = result.get(0);

        return EmployeeRegistrationDetailResponse.builder()
                .id(((Number) r[10]).longValue())             // id
                .employeeId(((Number) r[5]).longValue())     // employee_id
                .resume((String) r[7])                       // resume
                .cvUrl((String) r[0])                        // cv_url
                .note((String) r[1])                         // note
                .jobPosition((String) r[11])                 // job_position
                .receiverId(r[2] != null ? ((Number) r[2]).longValue() : null) // receiver_id
                .status((String) r[9])                       // status
                .submitDate(toLocalDateTime(r[13]))          // submit_date
                .approveDate(toLocalDateTime(r[3]))           // approve_date
                .leaderNote((String) r[4])                   // leader_note
                .createdBy(((Number) r[14]).longValue())     // created_by
                .createdAt(toLocalDateTime(r[12]))          // created_at
                .updatedBy(r[8] != null ? ((Number) r[8]).longValue() : null) // updated_by
                .updatedAt(toLocalDateTime(r[6]))           // updated_at
                .build();
    }

    @Override
    public PromotionDetailResponse getDetailPromotionDetai(Long userId, Long formId) {
        List<Object[]> result = detailRespository.getPromotionDetail(userId, formId);
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
                .submitDate(toLocalDateTime(r[i++]))
                .approveDate(toLocalDateTime(r[i++]))
                .leaderNote((String) r[i++])
                .createdBy(((Number) r[i++]).longValue())
                .createdAt(toLocalDateTime(r[i++]))
                .updatedBy(r[i++] != null ? ((Number) r[i - 1]).longValue() : null)
                .updatedAt(toLocalDateTime(r[i - 1]))
                .build();
    }

    @Override
    public ProposalDetailResponse getDetailProposal(Long userId, Long formId) {
        List<Object[]> result = detailRespository.getProposalDetail(userId, formId);
        Object[] r = result.get(0);
        int i = 0;
        return ProposalDetailResponse.builder()
                .id(((Number) r[i++]).longValue())
                .employeeId(((Number) r[i++]).longValue())
                .content((String) r[i++])
                .detail((String) r[i++])
                .receiverId(((Number) r[i++]).longValue())
                .status((String) r[i++])
                .submitDate(toLocalDateTime(r[i++]))
                .approveDate(toLocalDateTime(r[i++]))
                .leaderNote((String) r[i++])
                .createdBy(((Number) r[i++]).longValue())
                .createdAt(toLocalDateTime(r[i++]))
                .updatedBy(r[i++] != null ? ((Number) r[i - 1]).longValue() : null)
                .updatedAt(toLocalDateTime(r[i - 1]))
                .build();
    }

    @Override
    public SalaryIncreaseDetailResponse getDetailIncrease(Long userId, Long formId) {
        List<Object[]> result = detailRespository.getSalaryIncreaseDetail(userId, formId);
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
                .submitDate(toLocalDateTime(r[i++]))
                .approveDate(toLocalDateTime(r[i++]))
                .leaderNote((String) r[i++])
                .createdBy(((Number) r[i++]).longValue())
                .createdAt(toLocalDateTime(r[i++]))
                .updatedBy(r[i++] != null ? ((Number) r[i - 1]).longValue() : null)
                .updatedAt(toLocalDateTime(r[i - 1]))
                .build();
    }

    @Override
    public List<FormPromotionResponse> getPromotionByEmployee(Long employeeId) {
        List<Object[]> result = fromGeneralRepository.getFormPromotionByEmployee(employeeId);
        return result.stream().map(r -> FormPromotionResponse.builder()
                .id(((Number) r[0]).longValue())
                .employeeId(((Number) r[1]).longValue())
                .oldPosition((String) r[2])
                .newPosition((String) r[3])
                .reason((String) r[4])
                .receiverId(r[5] != null ? ((Number) r[5]).longValue() : null)
                .status((String) r[6])
                .submitDate(toLocalDateTime(r[7]))
                .approveDate(toLocalDateTime(r[8]))
                .leaderNote((String) r[9])
                .createdAt(toLocalDateTime(r[10]))
                .build()).toList();
    }

    @Override
    public List<FormProposalResponse> getProposalByEmployee(Long employeeId) {
        List<Object[]> result = fromGeneralRepository.getFormProposalByEmployee(employeeId);
        return result.stream().map(r -> FormProposalResponse.builder()
                .id(((Number) r[0]).longValue())
                .employeeId(((Number) r[1]).longValue())
                .content((String) r[2])
                .detail((String) r[3])
                .receiverId(r[4] != null ? ((Number) r[4]).longValue() : null)
                .status((String) r[5])
                .submitDate(toLocalDateTime(r[6]))
                .approveDate(toLocalDateTime(r[7]))
                .leaderNote((String) r[8])
                .createdAt(toLocalDateTime(r[9]))
                .build()).toList();
    }

    @Override
    public List<FormSalaryIncreaseResponse> getSalaryIncreaseByEmployee(Long employeeId) {
        List<Object[]> result = fromGeneralRepository.getFormSalaryIncreaseByEmployee(employeeId);
        return result.stream().map(r -> FormSalaryIncreaseResponse.builder()
                .id(((Number) r[0]).longValue())
                .employeeId(((Number) r[1]).longValue())
                .times(r[2] != null ? ((Number) r[2]).intValue() : null)
                .oldLevel((String) r[3])
                .newLevel((String) r[4])
                .reason((String) r[5])
                .receiverId(r[6] != null ? ((Number) r[6]).longValue() : null)
                .status((String) r[7])
                .submitDate(toLocalDateTime(r[8]))
                .approveDate(toLocalDateTime(r[9]))
                .leaderNote((String) r[10])
                .createdAt(toLocalDateTime(r[11]))
                .build()).toList();
    }

    @Override
    public List<PromotionPendingResponse> getPendingPromotions(Long userId) {
        List<Object[]> result = fromGeneralRepository.getPendingpromotionList(userId);
        return result.stream().map(r -> {
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
                    .submitDate(toLocalDateTime(r[i++]))
                    .createdAt(toLocalDateTime(r[i++]))
                    .build();
        }).toList();
    }

    @Override
    public List<ProposalPendingResponse> getPendingProposals(Long userId) {
        List<Object[]> result = fromGeneralRepository.getPendingproposalList(userId);
        return result.stream().map(r -> {
            int i = 0;
            return ProposalPendingResponse.builder()
                    .formId(((Number) r[i++]).longValue())
                    .employeeId(((Number) r[i++]).longValue())
                    .employeeCode((String) r[i++])
                    .employeeName((String) r[i++])
                    .content((String) r[i++])
                    .receiverId(((Number) r[i++]).longValue())
                    .status((String) r[i++])
                    .submitDate(toLocalDateTime(r[i++]))
                    .createdAt(toLocalDateTime(r[i++]))
                    .build();
        }).toList();
    }

    @Override
    public List<SalaryIncreasePendingResponse> getPendingSalaryIncreases(Long userId) {
        List<Object[]> result = fromGeneralRepository.getPendingsalaryList(userId);
        return result.stream().map(r -> {
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
                    .submitDate(toLocalDateTime(r[i++]))
                    .createdAt(toLocalDateTime(r[i++]))
                    .build();
        }).toList();
    }

    @Override
    public void updatePromotion(Long userId, Long formId, PromotionUpdateRequest req) {
        StoredProcedureQuery result = fromGeneralRepository.updatePromotion(userId, formId, req);
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_message"));
        }
    }

    @Override
    public void updateProposal(Long userId, Long formId, ProposalUpdateRequest req) {
        StoredProcedureQuery result = fromGeneralRepository.callUpdateProposal(userId, formId, req);
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_message"));
        }
    }

    @Override
    public void updateSalaryIncrease(Long userId, Long formId, SalaryIncreaseUpdateRequest req) {
        StoredProcedureQuery result = fromGeneralRepository.callUpdateSalaryIncrease(userId, formId, req);
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_message"));
        }
    }
}