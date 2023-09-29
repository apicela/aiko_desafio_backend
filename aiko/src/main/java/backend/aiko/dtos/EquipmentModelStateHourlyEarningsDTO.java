package backend.aiko.dtos;

import backend.aiko.models.EquipmentModelStateHourlyEarnings;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data // getters and setters
@NoArgsConstructor
public class EquipmentModelStateHourlyEarningsDTO {

    @NotNull
    private UUID equipment_model_id;
    @NotNull
    private UUID equipment_state_id;
    @NotNull
    private double value;

    public EquipmentModelStateHourlyEarningsDTO(EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID equipmentMSHE_id) {
        this.equipment_model_id = equipmentMSHE_id.getEquipment_model_id();
        this.equipment_state_id = equipmentMSHE_id.getEquipment_state_id();
        this.value = value;
    }

}
