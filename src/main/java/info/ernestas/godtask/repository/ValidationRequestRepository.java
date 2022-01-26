package info.ernestas.godtask.repository;

import info.ernestas.godtask.entity.ValidationRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ValidationRequestRepository extends CrudRepository<ValidationRequest, UUID> {

    List<ValidationRequest> findAll();

    List<ValidationRequest> findAllByOrderByDate();

}
