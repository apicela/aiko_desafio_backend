package backend.aiko.models;

import backend.aiko.dtos.EquipmentModelStateHourlyEarningsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "equipment_model_state_hourly_earnings", schema = "operation")
@Data // create all getters and setters
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentModelStateHourlyEarnings implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "equipment_state_id", nullable = false, insertable = false, updatable = false)
    private UUID equipment_state_id;
    @Column(name = "equipment_model_id", nullable = false, insertable = false, updatable = false)
    private UUID equipment_model_id;
    @EmbeddedId
    private EquipmentMSHE_ID equipmentMSHE_id;

    @Column(name = "\"value\"", nullable = false)
    private double value;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipment_model_id")
    @JoinColumn(name = "equipment_model_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_equipment_model", value = ConstraintMode.NO_CONSTRAINT),
            updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private EquipmentModel equipmentModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipment_state_id")
    @JoinColumn(name = "equipment_state_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_equipment_state", value = ConstraintMode.NO_CONSTRAINT),
            updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private EquipmentState equipmentState;


    public EquipmentModelStateHourlyEarnings(EquipmentModelStateHourlyEarningsDTO equipmentModelStateHourlyEarningsDTO) {
        equipmentMSHE_id = new EquipmentMSHE_ID(equipmentModelStateHourlyEarningsDTO.getEquipment_model_id(), equipmentModelStateHourlyEarningsDTO.getEquipment_state_id());
        this.value = equipmentModelStateHourlyEarningsDTO.getValue();
    }

    @JsonIgnore
    public EquipmentMSHE_ID getEquipmentMSHE_id() {
        return equipmentMSHE_id;
    }

    @JsonIgnore
    public EquipmentModel getEquipmentModel() {
        return equipmentModel;
    }

    @JsonIgnore
    public EquipmentState getEquipmentState() {
        return equipmentState;
    }

    @Override
    public String toString() {
        return "Equipment Model State Hourly Earnings:" +
                "\nequipment_model_id: " + equipmentMSHE_id.equipment_model_id +
                "\nequipment_state_id: " + equipmentMSHE_id.equipment_state_id +
                "\nvalue: " + getValue();
    }

    @Embeddable
    @Data // create all getters and setters
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EquipmentMSHE_ID implements Serializable //Equipment Model State Hourly Earnings
    {

        @Column(name = "equipment_model_id", nullable = false)
        private UUID equipment_model_id;
        @Column(name = "equipment_state_id", nullable = false)
        private UUID equipment_state_id;


    }
}


