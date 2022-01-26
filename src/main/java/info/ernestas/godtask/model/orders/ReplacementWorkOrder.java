package info.ernestas.godtask.model.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.ernestas.godtask.service.validator.constraint.RequireInventoryNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplacementWorkOrder extends WorkOrder {

    @JsonProperty("factory_name")
    @NotEmpty
    private String factoryName;

    @Pattern(regexp = "[A-Za-z]{2}[0-9]{8}$")
    @JsonProperty("factory_order_number")
    private String factoryOrderNumber;

    @RequireInventoryNumber
    private List<Part> parts = new ArrayList<>();

}
