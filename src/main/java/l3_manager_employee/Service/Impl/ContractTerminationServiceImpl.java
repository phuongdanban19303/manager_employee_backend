package l3_manager_employee.Service.Impl;

import jakarta.persistence.StoredProcedureQuery;
import l3_manager_employee.DTO.Request.ContractTerminationCreateRequest;
import l3_manager_employee.DTO.Request.LeaderHandleContractTerminationRequest;
import l3_manager_employee.DTO.Request.SubmitContractTerminationRequest;
import l3_manager_employee.DTO.Respones.ContractTerminationDetailResponse;
import l3_manager_employee.DTO.Respones.ContractTerminationPendingResponse;
import l3_manager_employee.DTO.Respones.ContractTerminationResponse;
import l3_manager_employee.DTO.Respones.CreateFormResponse;
import l3_manager_employee.Repository.ContractTerminationRepository;
import l3_manager_employee.Service.ContractTerminationService;
import l3_manager_employee.commons.exception.ErrorCodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContractTerminationServiceImpl implements ContractTerminationService {

    private final ContractTerminationRepository repository;

    // === HELPER CHUYỂN ĐỔI ===
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
    public CreateFormResponse createTermination(Long userId, ContractTerminationCreateRequest request) {
        StoredProcedureQuery result = repository.createTermination(userId, request.getEmployeeId(), request.getTerminationDate(), request.getTerminationReason());
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_error_code"));
        }
        return new CreateFormResponse((Long) result.getOutputParameterValue("o_termination_id"));
    }

    @Override
    public void submitTermination(Long userId, SubmitContractTerminationRequest request) {
        StoredProcedureQuery result = repository.submitTermination(userId, request.getFormId(), request.getReceiverId());
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_error_code"));
        }
    }

    @Override
    public void leaderHandleTermination(Long leaderId, LeaderHandleContractTerminationRequest request) {
        StoredProcedureQuery result = repository.leaderHandleTermination(leaderId, request.getFormId(), request.getAction(), request.getNote());
        if (Boolean.FALSE.equals(result.getOutputParameterValue("o_success"))) {
            throw ErrorCodeMapper.map((String) result.getOutputParameterValue("o_error_code"));
        }
    }

    @Override
    public List<ContractTerminationResponse> getTerminationList() {
        return repository.getTerminationList();
    }

    @Override
    public List<ContractTerminationPendingResponse> getPendingList() {
        return repository.getPendingList().stream().map(o -> {
            Object[] r = (Object[]) o;
            ContractTerminationPendingResponse res = new ContractTerminationPendingResponse();
            res.setFormId(((Number) r[0]).longValue());
            res.setEmployeeId(((Number) r[1]).longValue());
            res.setEmployeeCode((String) r[2]);
            res.setEmployeeName((String) r[3]);
            res.setTerminationDate(toLocalDate(r[4]));
            res.setReceiverId(r[5] != null ? ((Number) r[5]).longValue() : null);
            res.setReceiverName((String) r[6]);
            res.setStatus((String) r[7]);
            res.setSubmitDate(toLocalDateTime(r[8]));
            return res;
        }).collect(Collectors.toList());
    }

    @Override
    public ContractTerminationDetailResponse getDetail(Long formId) {
        Object[] r = (Object[]) repository.getDetail(formId);
        ContractTerminationDetailResponse res = new ContractTerminationDetailResponse();
        res.setId(((Number) r[0]).longValue());
        res.setEmployeeId(((Number) r[1]).longValue());
        res.setEmployeeCode((String) r[2]);
        res.setEmployeeName((String) r[3]);
        res.setTeam((String) r[4]);
        res.setTerminationDate(toLocalDate(r[5]));
        res.setTerminationReason((String) r[6]);
        res.setReceiverId(r[7] != null ? ((Number) r[7]).longValue() : null);
        res.setReceiverName((String) r[8]);
        res.setStatus((String) r[9]);
        res.setSubmitDate(toLocalDateTime(r[10]));
        res.setApproveDate(toLocalDate(r[11]));
        res.setLeaderNote((String) r[12]);
        res.setCreatedBy(((Number) r[13]).longValue());
        res.setCreatedAt(toLocalDateTime(r[14]));
        res.setUpdatedBy(r[15] != null ? ((Number) r[15]).longValue() : null);
        res.setUpdatedAt(toLocalDateTime(r[16]));
        return res;
    }
}