package l3_manager_employee.Controller;

import l3_manager_employee.Config.SecurityUtils;
import l3_manager_employee.DTO.Request.ContractTerminationCreateRequest;
import l3_manager_employee.DTO.Request.LeaderHandleContractTerminationRequest;
import l3_manager_employee.DTO.Request.SubmitContractTerminationRequest;
import l3_manager_employee.DTO.Respones.ContractTerminationDetailResponse;
import l3_manager_employee.DTO.Respones.ContractTerminationPendingResponse;
import l3_manager_employee.DTO.Respones.ContractTerminationResponse;
import l3_manager_employee.DTO.Respones.CreateFormResponse;
import l3_manager_employee.Service.ContractTerminationService;
import l3_manager_employee.commons.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contract-termination")
@RequiredArgsConstructor
public class ContractTerminationController {

    private final ContractTerminationService service;

    @PostMapping("/create")
    public ApiResponse<CreateFormResponse> create(
            @RequestBody ContractTerminationCreateRequest request
    ) {
        Long userId = SecurityUtils.getUserId();

        return ApiResponse.success( service.createTermination(userId, request));
    }
    @PostMapping("/submit")
    public ApiResponse<Void> submitTermination(
            @RequestBody SubmitContractTerminationRequest request
    ) {
        Long userId = SecurityUtils.getUserId();
        service.submitTermination(userId, request);
        return ApiResponse.success(null);
    }

    /* ===============================
       LEADER HANDLE TERMINATION
       =============================== */
        @PostMapping("/leader-handle")
    public ApiResponse<Void> leaderHandleTermination(
            @RequestBody LeaderHandleContractTerminationRequest request
    ) {
        Long leaderId = SecurityUtils.getUserId();
        service.leaderHandleTermination(leaderId, request);
        return ApiResponse.success(null);
    }

    @GetMapping
    public ApiResponse<List<ContractTerminationResponse>> list() {
        return ApiResponse.success(service.getTerminationList());
    }
    // List pending
    @GetMapping("/pending")
    public List<ContractTerminationPendingResponse> getPendingList() {
        return service.getPendingList();
    }

    // Detail
    @GetMapping("/{id}")
    public ContractTerminationDetailResponse getDetail(@PathVariable Long id) {
        return service.getDetail(id);
    }
}
