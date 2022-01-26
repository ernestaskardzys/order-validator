package info.ernestas.godtask.model.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DestroyingWorkOrder extends WorkOrder {

    @NotEmpty
    private String action;

}
