package info.ernestas.godtask.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "god")
public class GodConfigurationProperties {

    private List<String> validDepartments;

}
