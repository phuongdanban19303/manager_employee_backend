package l3_manager_employee.Service.Impl;

import jakarta.persistence.StoredProcedureQuery;
import l3_manager_employee.DTO.Request.EmployeeCertificateSubmitRequest;
import l3_manager_employee.DTO.Request.EmployeeRegistrationRequest;
import l3_manager_employee.DTO.Request.EmployeeRegistrationUpdateRequest;
import l3_manager_employee.DTO.Request.ProcessEmployeeRegistrationRequest;
import l3_manager_employee.DTO.Respones.EmployeeListRegistrationResponse;
import l3_manager_employee.DTO.Respones.EmployeeRegistrationPendingResponse;
import l3_manager_employee.DTO.Respones.EmployeeRegistrationResponse;
import l3_manager_employee.DTO.Respones.UserManagerResponse;
import l3_manager_employee.Repository.EmployeeRegistrationRepository;
import l3_manager_employee.Service.EmployeeRegistrationService;
import l3_manager_employee.commons.exception.AppException;
import l3_manager_employee.commons.exception.ErrorCode;
import l3_manager_employee.commons.exception.ErrorCodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// === ĐÃ SỬA IMPORT ĐÚNG ===
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
// ==========================

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeRegistrationServiceImpl implements EmployeeRegistrationService {

    private final EmployeeRegistrationRepository repository;
    private final ObjectMapper objectMapper;

    @Override
    public boolean checkReadyForRegistration(Long userId, Long employeeId) {
        StoredProcedureQuery result = repository.callCheckReady(userId, employeeId);
        Boolean ready = (Boolean) result.getOutputParameterValue("o_ready");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(ready)) {
            throw ErrorCodeMapper.map(errorCode);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public EmployeeRegistrationResponse createRegistration(Long userId, Long employeeId, EmployeeRegistrationRequest request) {
        try {
            // ObjectMapper ném ra JsonProcessingException nên phải try-catch
            String json = objectMapper.writeValueAsString(request);

            StoredProcedureQuery result = repository.callCreateRegistration(userId, employeeId, json);
            Object formIdObj = result.getOutputParameterValue("o_form_id");
            Long from_id = (formIdObj != null) ? ((Number) formIdObj).longValue() : null;
            Boolean success = (Boolean) result.getOutputParameterValue("o_success");
            String errorCode = (String) result.getOutputParameterValue("o_error_code");

            if (Boolean.FALSE.equals(success)) {
                throw ErrorCodeMapper.map(errorCode);
            }
            return new EmployeeRegistrationResponse(from_id);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting request to JSON", e);
        }
    }

    @Override
    public void submitRegistration(Long userId, Long formId, EmployeeCertificateSubmitRequest req) {
        StoredProcedureQuery result = repository.submitRegistration(userId, formId, req);
        Boolean success = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(success)) {
            throw ErrorCodeMapper.map(errorCode);
        }
    }

    @Override
    public void processRegistration(Long leaderId, ProcessEmployeeRegistrationRequest request) {
        StoredProcedureQuery result = repository.processRegistration(leaderId, request.getFormId(), request.getAction(), request.getNote(), request.getActionDate());
        Boolean success = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(success)) {
            throw ErrorCodeMapper.map(errorCode);
        }
    }

    @Override
    public List<EmployeeRegistrationPendingResponse> getPendingRegistrations(Long userId) {
        try {
            List<Object[]> reslut = repository.getPendingList(userId);
            return reslut.stream()
                    .map(r -> {
                        int i = 0;
                        return EmployeeRegistrationPendingResponse.builder()
                                .formId(((Number) r[i++]).longValue())
                                .employeeId(((Number) r[i++]).longValue())
                                .employeeCode((String) r[i++])
                                .employeeName((String) r[i++])
                                .jobPosition((String) r[i++])
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
    public List<UserManagerResponse> getListManagers() {
        List<Object[]> results = repository.getManagerList();
        return results.stream()
                .map(obj -> UserManagerResponse.builder()
                        .id(((Number) obj[0]).longValue())
                        .username((String) obj[1])
                        .fullname((String) obj[2])
                        .role((String) obj[3])
                        .status((String) obj[4])
                        .createdAt((LocalDateTime) obj[5])
                        .team((String) obj[6])
                        .build()
                )
                .toList();
    }

    @Override
    public EmployeeListRegistrationResponse getByEmployee(Long userId, Long employeeId) {
        Object result = repository.callFnGetByEmployee(employeeId);
        if (result == null) {
            throw new AppException(ErrorCode.SYSTEM_ERROR_DB);
        }
        try {
            return objectMapper.readValue(
                    result.toString(),
                    EmployeeListRegistrationResponse.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON response", e);
        }
    }

    @Override
    public void update(Long userId, EmployeeRegistrationUpdateRequest req) {
        StoredProcedureQuery sp = repository.callUpdateRegistration(
                req.getId(),
                req.getResume(),
                req.getCv_url(),
                req.getNote(),
                req.getJob_position(),
                userId
        );
        Boolean success = (Boolean) sp.getOutputParameterValue("o_success");
        String errorCode = (String) sp.getOutputParameterValue("o_error");
        if (!Boolean.TRUE.equals(success)) {
            throw ErrorCodeMapper.map(errorCode);
        }
    }
}