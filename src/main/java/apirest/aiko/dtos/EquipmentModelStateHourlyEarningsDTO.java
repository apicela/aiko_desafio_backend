package apirest.aiko.dtos;

import apirest.aiko.models.EquipmentModelStateHourlyEarnings;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/equipment-model")
@CrossOrigin("*")
public class EquipmentModelStateHourlyEarningsDTO {

    @NotNull
    private UUID equipment_model_id;
    @NotNull
    private UUID equipment_state_id;
    @NotNull
    private double value;

    public EquipmentModelStateHourlyEarningsDTO() {

    }

    public EquipmentModelStateHourlyEarningsDTO(EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID equipmentMSHE_id) {
        this.equipment_model_id = equipmentMSHE_id.getEquipment_model_id();
        this.equipment_state_id = equipmentMSHE_id.getEquipment_state_id();
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public UUID getEquipment_model_id() {
        return equipment_model_id;
    }

    public void setEquipment_model_id(UUID equipment_model_id) {
        this.equipment_model_id = equipment_model_id;
    }

    public UUID getEquipment_state_id() {
        return equipment_state_id;
    }

    public void setEquipment_state_id(UUID equipment_state_id) {
        this.equipment_state_id = equipment_state_id;
    }
}
