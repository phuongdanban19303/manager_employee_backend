package l3_manager_employee.Service;

import l3_manager_employee.DTO.Request.ContractTerminationCreateRequest;
import l3_manager_employee.DTO.Request.LeaderHandleContractTerminationRequest;
import l3_manager_employee.DTO.Request.SubmitContractTerminationRequest;
import l3_manager_employee.DTO.Respones.ContractTerminationDetailResponse;
import l3_manager_employee.DTO.Respones.ContractTerminationPendingResponse;
import l3_manager_employee.DTO.Respones.ContractTerminationResponse;
import l3_manager_employee.DTO.Respones.CreateFormResponse;

import java.util.List;

public interface ContractTerminationService {

    CreateFormResponse createTermination(Long userId, ContractTerminationCreateRequest request);

    List<ContractTerminationResponse> getTerminationList();
    void submitTermination(Long userId, SubmitContractTerminationRequest request);

    void leaderHandleTermination(Long leaderId, LeaderHandleContractTerminationRequest request);

    List<ContractTerminationPendingResponse> getPendingList();

    ContractTerminationDetailResponse getDetail(Long formId);
}

