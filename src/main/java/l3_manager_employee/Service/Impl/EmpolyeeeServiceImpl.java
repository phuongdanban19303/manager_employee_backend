package l3_manager_employee.Service.Impl;

import jakarta.persistence.StoredProcedureQuery;
import l3_manager_employee.DTO.Request.EmployeeCertificateRequest;
import l3_manager_employee.DTO.Request.EmployeeFamilyRequest;
import l3_manager_employee.DTO.Request.EmployeeRequest;
import l3_manager_employee.DTO.Request.EmployeeUpdateRequest;
import l3_manager_employee.DTO.Respones.*;
import l3_manager_employee.Repository.DetailRespository;
import l3_manager_employee.Repository.EmployeeRepository;
import l3_manager_employee.Service.EmpolyeeService;
import l3_manager_employee.commons.exception.AppException;
import l3_manager_employee.commons.exception.ErrorCode;
import l3_manager_employee.commons.exception.ErrorCodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.Date;
import java.sql.Timestamp; // Import thêm cái này
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpolyeeeServiceImpl implements EmpolyeeService {
    private final EmployeeRepository repository;
    private final ObjectMapper objectMapper;
    private final DetailRespository detailRepository;

    // === SỬA LẠI ĐOẠN NÀY ===
    private LocalDate toLocalDate(Object o) {
        if (o == null) return null;
        if (o instanceof java.sql.Date) {
            return ((java.sql.Date) o).toLocalDate();
        }
        if (o instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) o).toLocalDateTime().toLocalDate();
        }
        return (LocalDate) o;
    }

    private LocalDateTime toLocalDateTime(Object o) {
        if (o == null) return null;
        // Kiểm tra nếu là Timestamp thì convert sang LocalDateTime
        if (o instanceof java.sql.Timestamp) {
            return ((java.sql.Timestamp) o).toLocalDateTime();
        }
        // Trường hợp hiếm hoi nó đã là LocalDateTime rồi
        return (LocalDateTime) o;
    }
    // ========================

    @Override
    public EmployeeResponse createEmployee(Long userId, EmployeeRequest req) {
        // ... (Giữ nguyên code cũ)
        StoredProcedureQuery sp = repository.callCreateEmployee(
                userId, req.getFullName(), req.getGender(), req.getDateOfBirth(),
                req.getAddress(), req.getTeam(), req.getAvatarUrl(),
                req.getIdentityNumber(), req.getPhone(), req.getEmail()
        );
        Long employeeId = (Long) sp.getOutputParameterValue("o_employee_id");
        Boolean success = (Boolean) sp.getOutputParameterValue("o_success");
        String errorCode = (String) sp.getOutputParameterValue("o_error_code");

        if (Boolean.FALSE.equals(success)) {
            log.error("Create Employee Failed. Error Code from DB: {}", errorCode);
            throw ErrorCodeMapper.map(errorCode);
        }
        return new EmployeeResponse(employeeId);
    }

    @Override
    @Transactional
    public void addCertificates(Long userId, Long employeeId, List<EmployeeCertificateRequest> certificates) {
        try {
            String certificatesJson = objectMapper.writeValueAsString(certificates);
            StoredProcedureQuery result = repository.callAddEmployeeCertificate(userId, employeeId, certificatesJson);
            Boolean success = (Boolean) result.getOutputParameterValue("o_success");
            String errorCode = (String) result.getOutputParameterValue("o_error_code");
            if (Boolean.FALSE.equals(success)) {
                throw ErrorCodeMapper.map(errorCode);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting certificates to JSON", e);
        }
    }

    @Override
    public void addEmpolyeeFamily(Long userId, Long certId, List<EmployeeFamilyRequest> EmployeeFamily) {
        try {
            String EmployeeFamilyJson = objectMapper.writeValueAsString(EmployeeFamily);
            StoredProcedureQuery result = repository.callAddEmployeeFamily(userId, certId, EmployeeFamilyJson);
            Boolean success = (Boolean) result.getOutputParameterValue("o_success");
            String errorCode = (String) result.getOutputParameterValue("o_error_code");
            if (Boolean.FALSE.equals(success)) {
                throw ErrorCodeMapper.map(errorCode);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting family info to JSON", e);
        }
    }

    @Override
    public void updateEmpolyeeCertificate(Long userId, Long certId, EmployeeCertificateRequest req) {
        try {
            String EmployeeCertificateJson = objectMapper.writeValueAsString(req);
            StoredProcedureQuery result = repository.callUpdateEmployeeCertificate(userId, certId, EmployeeCertificateJson);
            Boolean success = (Boolean) result.getOutputParameterValue("o_success");
            String errorCode = (String) result.getOutputParameterValue("o_error_code");
            if (Boolean.FALSE.equals(success)) {
                log.error("Root exception message family: {}", errorCode);
                throw ErrorCodeMapper.map(errorCode);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting certificate update to JSON", e);
        }
    }

    @Override
    public void updateEmployeeFamily(Long userId, Long familyId, EmployeeFamilyRequest req) {
        try {
            String EmployeeFamilyJson = objectMapper.writeValueAsString(req);
            StoredProcedureQuery result = repository.callUpdateEmployeeFamily(userId, familyId, EmployeeFamilyJson);
            Boolean success = (Boolean) result.getOutputParameterValue("o_success");
            String errorCode = (String) result.getOutputParameterValue("o_error_code");
            if (Boolean.FALSE.equals(success)) {
                log.error("Root exception fml: {}", errorCode);
                throw ErrorCodeMapper.map(errorCode);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting family update to JSON", e);
        }
    }

    @Override
    public void updateEmployeeInfo(Long userId, Long employeeId, EmployeeUpdateRequest request) {
        try {
            String employeeJson = objectMapper.writeValueAsString(request);
            StoredProcedureQuery result = repository.callUpdateEmployeeInfo(userId, employeeId, employeeJson);
            Boolean success = (Boolean) result.getOutputParameterValue("o_success");
            String errorCode = (String) result.getOutputParameterValue("o_error_code");
            if (Boolean.FALSE.equals(success)) {
                throw ErrorCodeMapper.map(errorCode);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting employee info to JSON", e);
        }
    }

    @Override
    public void deleteEmployee(Long userId, Long employeeId) {
        StoredProcedureQuery result = repository.callDeleteEmployee(userId, employeeId);
        Boolean success = (Boolean) result.getOutputParameterValue("o_success");
        String errorCode = (String) result.getOutputParameterValue("o_error_code");
        if (Boolean.FALSE.equals(success)) {
            throw ErrorCodeMapper.map(errorCode);
        }
    }

    @Override
    public EmployeeDetailResponse getEmployeeDetail(Long userId, Long employeeId) {
        try {
            List<Object[]> result = detailRepository.callFnEmployeeDetail(userId, employeeId);
            if (result.isEmpty()) {
                throw new AppException(ErrorCode.EMP_NOT_FOUND);
            }

            Object[] r = result.get(0);
            EmployeeDetailResponse res = new EmployeeDetailResponse();

            // Lưu ý: Đảm bảo index khớp với hàm SQL
            res.setId(((Number) r[0]).longValue());
            res.setEmployeeCode((String) r[1]);
            res.setFullName((String) r[2]);
            res.setGender((String) r[3]);
            res.setDateOfBirth(toLocalDate(r[4]));
            res.setAddress((String) r[5]);
            res.setTeam((String) r[6]);
            res.setAvatarUrl((String) r[7]);
            res.setIdentityNumber((String) r[8]);
            res.setPhone((String) r[9]);
            res.setEmail((String) r[10]);
            res.setStatus((String) r[11]);
            res.setStatusUpdatedAt(toLocalDateTime(r[12]));
            res.setTerminatedAt(toLocalDate(r[13]));
            res.setTerminationReason((String) r[14]);
            res.setArchiveDate(toLocalDate(r[15]));
            res.setArchiveNumber((String) r[16]);
            res.setCreatedBy(((Number) r[17]).longValue());
            res.setCreatedAt(toLocalDateTime(r[18]));
            res.setUpdatedBy(r[19] != null ? ((Number) r[19]).longValue() : null);
            res.setUpdatedAt(toLocalDateTime(r[20]));

            return res;
        } catch (AppException ae) {
            throw ae;
        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            log.error(root.getMessage(), root);
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }

    @Override
    public List<EmployeeFamilyRelationResponse> getFamilyRelations(Long userId, Long employeeId) {
        try {
            List<Object[]> result = detailRepository.getFamilyRelations(userId, employeeId);
            return result.stream().map(r ->
                    EmployeeFamilyRelationResponse.builder()
                            .id(((Number) r[0]).intValue())
                            .employeeId(((Number) r[1]).intValue())
                            .full_name((String) r[2])
                            .gender((Integer) r[3])
                            .date_of_birth(toLocalDate(r[4]))
                            .identity_card_number((String) r[5])
                            .relationship((String) r[6])
                            .address((String) r[7])
                            .createdBy(((Number) r[8]).longValue())
                            .createdAt(toLocalDateTime(r[9]))
                            .updatedBy(r[10] != null ? ((Number) r[10]).longValue() : null)
                            .updatedAt(toLocalDateTime(r[11]))
                            .build()
            ).toList();
        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            log.error("Root exception", root);
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }

    @Override
    public List<EmployeeCertificateDetailResponse> getDetailEmployeeCertificate(Long userId, Long employeeId) {
        try {
            List<Object[]> result = detailRepository.getDetailEmpolyeeCertificates(userId, employeeId);
            return result.stream().map(r ->
                    EmployeeCertificateDetailResponse.builder()
                            .id(((Number) r[0]).longValue())
                            .employeeId(((Number) r[1]).longValue())
                            .name((String) r[2])
                            .issue_date(toLocalDate(r[3]))
                            .content((String) r[4])
                            .field_url((String) r[5])
                            .createdBy(((Number) r[6]).longValue())
                            .createdAt(toLocalDateTime(r[7]))
                            .updatedBy(r[8] != null ? ((Number) r[8]).longValue() : null)
                            .updatedAt(toLocalDateTime(r[9]))
                            .build()
            ).toList();
        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            log.error("Root exception", root);
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }

    @Override
    public List<EmployeeListResponse> getEmployeeListByUser(Long userId) {
        try {
            List<Object[]> result = repository.getEmployeeListByUser(userId);
            return result.stream().map(r -> {
                int i = 0;
                return EmployeeListResponse.builder()
                        .id(((Number) r[i++]).longValue())
                        .employeeCode((String) r[i++])
                        .fullName((String) r[i++])
                        .team((String) r[i++])
                        .status((String) r[i++])
                        .build();
            }).toList();
        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }

    @Override
    public EmployeeViewDetailResponse viewEmployeeDetail(Long userId, Long employeeId) {
        Object result = repository.getEmployeeViewDetail(userId, employeeId);

        if (result == null) {
            throw new AppException(ErrorCode.EMP_NOT_FOUND);
        }

        try {
            String json = result instanceof String
                    ? (String) result
                    : result.toString();

            Map<String, Object> data = objectMapper.readValue(
                    json,
                    new TypeReference<Map<String, Object>>() {}
            );

            return EmployeeViewDetailResponse.builder()
                    .employee(data.get("employee"))
                    .certificates(data.get("certificates"))
                    .familyRelations(data.get("family_relations"))
                    .build();

        } catch (JsonProcessingException e) {
            log.error("Parse employee view detail error", e);
            throw new AppException(ErrorCode.SYSTEM_ERROR);
        }
    }

    @Override
    public List<EmployeeListResponse> getEmployeeListApprovedByUser(Long userId) {
        try {
            List<Object[]> result = repository.getEmployeeListApprovedByUser(userId);
            return result.stream().map(r ->
                    EmployeeListResponse.builder()
                            .id(((Number) r[0]).longValue())
                            .employeeCode((String) r[1])
                            .fullName((String) r[2])
                            .team((String) r[3])
                            .status((String) r[4])
                            .build()
            ).toList();
        } catch (Exception ex) {
            Throwable root = ex.getCause() != null ? ex.getCause() : ex;
            log.error("Root exception", root);
            throw ErrorCodeMapper.map(root.getMessage());
        }
    }
}