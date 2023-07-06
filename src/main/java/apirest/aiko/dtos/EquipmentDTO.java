package apirest.aiko.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data // getters and setters
@NoArgsConstructor
public class EquipmentDTO {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("equipment_model_id")
    private UUID equipment_model_id;

    public EquipmentDTO(String name, UUID equipmentModelId) {
        this.name = name;
        this.equipment_model_id = equipmentModelId;
    }

}


