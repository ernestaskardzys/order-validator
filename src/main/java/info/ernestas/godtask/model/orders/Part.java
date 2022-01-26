package info.ernestas.godtask.model.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Part {

    @JsonProperty("inventory_number")
    private String inventoryNumber;

    private String name;

    private Integer count;

}
