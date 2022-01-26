package info.ernestas.godtask.entity;

import info.ernestas.godtask.model.orders.OrderType;
import info.ernestas.godtask.model.ValidationRequestStatus;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class ValidationRequest {

    @Id
    private UUID validationId = UUID.randomUUID();

    private LocalDate date;

    @Enumerated(value = EnumType.STRING)
    private OrderType type;

    private String department;

    @Enumerated(value = EnumType.STRING)
    private ValidationRequestStatus status;

    private String request;

}
