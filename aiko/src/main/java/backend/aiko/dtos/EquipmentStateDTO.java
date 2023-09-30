package backend.aiko.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EquipmentStateDTO {
    @NotBlank
    @NotNull
    private String color;
    @NotBlank
    private String name;

}
