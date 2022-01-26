package info.ernestas.godtask.model.resource;

import info.ernestas.godtask.model.orders.WorkOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateRequestResource {

    @Valid
    private WorkOrder request;

}
