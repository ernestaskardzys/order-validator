package info.ernestas.godtask.model.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import info.ernestas.godtask.service.validator.constraint.AfterDate;
import info.ernestas.godtask.service.validator.constraint.BeforeToday;
import info.ernestas.godtask.service.validator.constraint.ValidDepartment;
import info.ernestas.godtask.service.validator.constraint.Iso4217Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AnalysisWorkOrder.class, name = "ANALYSIS"),
        @JsonSubTypes.Type(value = RepairWorkOrder.class, name = "REPAIR"),
        @JsonSubTypes.Type(value = ReplacementWorkOrder.class, name = "REPLACEMENT"),
        @JsonSubTypes.Type(value = DestroyingWorkOrder.class, name = "DESTROYING")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@AfterDate(baseField = "endDate", startDateField = "startDate", message = "{god.incorrectEndDate}")
public abstract class WorkOrder {

    private OrderType type;

    @ValidDepartment
    private String department;

    @JsonProperty("start_date")
    @BeforeToday
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @Iso4217Currency
    private String currency;

    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal cost;

}
