package apirest.aiko.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getters and setters
@NoArgsConstructor
public class EquipmentModelDTO {
    @NotBlank
    private String name;
}
