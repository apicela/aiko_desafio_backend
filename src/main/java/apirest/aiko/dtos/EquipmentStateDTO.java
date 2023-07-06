package apirest.aiko.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipmentStateDTO {
    @NotBlank
    private String color;
    @NotBlank
    private String name;

}
