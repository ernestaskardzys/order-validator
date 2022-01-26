package info.ernestas.godtask.model.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisWorkOrder extends WorkOrder {

    private List<Part> parts = new ArrayList<>();

}
