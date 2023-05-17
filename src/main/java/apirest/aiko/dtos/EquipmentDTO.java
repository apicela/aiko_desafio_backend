package apirest.aiko.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class EquipmentDTO {

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("equipment_model_id")
    private UUID equipment_model_id;

    public EquipmentDTO() {
    }

    public EquipmentDTO(String name, UUID equipmentModelId) {
        this.name = name;
        this.equipment_model_id = equipmentModelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getEquipment_model_id() {
        return equipment_model_id;
    }

    public void setEquipment_model_id(UUID equipment_model_id) {
        this.equipment_model_id = equipment_model_id;
    }
}


