package info.ernestas.godtask.model.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.ernestas.godtask.service.validator.constraint.AfterDateAndBeforeDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@AfterDateAndBeforeDate(baseField = "analysisDate", startDateField = "startDate", endDateField = "endDate", message = "{god.incorrectAnalysisDate}")
@AfterDateAndBeforeDate(baseField = "testDate", startDateField = "analysisDate", endDateField = "endDate", message = "{god.incorrectTestDate}")
public class RepairWorkOrder extends WorkOrder {

    @JsonProperty("analysis_date")
    private LocalDate analysisDate;

    @JsonProperty("responsible_person")
    @NotEmpty
    private String responsiblePerson;

    @JsonProperty("test_date")
    private LocalDate testDate;

    @Size(min=1, message = "Must be not empty")
    private List<Part> parts = new ArrayList<>();

}
