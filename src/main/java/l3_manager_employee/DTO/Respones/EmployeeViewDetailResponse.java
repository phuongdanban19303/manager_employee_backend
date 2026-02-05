package l3_manager_employee.DTO.Respones;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeViewDetailResponse {

    private Object employee;
    private Object certificates;
    private Object familyRelations;
}