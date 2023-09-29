package backend.aiko.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // getters and setters
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentModelDTO {
    @NotBlank
    private String name;
}
