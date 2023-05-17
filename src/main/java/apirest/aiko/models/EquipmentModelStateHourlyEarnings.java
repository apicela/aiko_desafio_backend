package apirest.aiko.models;

import apirest.aiko.dtos.EquipmentModelStateHourlyEarningsDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "equipment_model_state_hourly_earnings", schema = "operation")
public class EquipmentModelStateHourlyEarnings implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "equipment_state_id", nullable = false, insertable = false, updatable = false)
    private UUID equipment_state_id;
    @Column(name = "equipment_model_id", nullable = false, insertable = false, updatable = false)
    private UUID equipment_model_id;
    @EmbeddedId
    private EquipmentMSHE_ID equipmentMSHE_id;

    @Column(nullable = false)
    private double value;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipment_model_id")
    @JoinColumn(name = "equipment_model_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_equipment_model"),
            updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private EquipmentModel equipmentModel;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipment_state_id")
    @JoinColumn(name = "equipment_state_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_equipment_state"),
            updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private EquipmentState equipmentState;

    public EquipmentModelStateHourlyEarnings() {
    }

    public EquipmentModelStateHourlyEarnings(EquipmentModelStateHourlyEarningsDTO equipmentModelStateHourlyEarningsDTO) {
        equipmentMSHE_id = new EquipmentMSHE_ID(equipmentModelStateHourlyEarningsDTO.getEquipment_model_id(), equipmentModelStateHourlyEarningsDTO.getEquipment_state_id());
        equipmentModel = new EquipmentModel();
        equipmentState = new EquipmentState();
        this.value = equipmentModelStateHourlyEarningsDTO.getValue();
        equipmentModel.setId(equipmentModelStateHourlyEarningsDTO.getEquipment_model_id());
        equipmentState.setId(equipmentModelStateHourlyEarningsDTO.getEquipment_state_id());
    }

    public UUID getEquipment_state_id() {
        return equipment_state_id;
    }

    public void setEquipment_state_id(UUID equipment_state_id) {
        this.equipment_state_id = equipment_state_id;
    }

    public UUID getEquipment_model_id() {
        return equipment_model_id;
    }

    public void setEquipment_model_id(UUID equipment_model_id) {
        this.equipment_model_id = equipment_model_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return "Equipment Model State Hourly Earnings:" +
                "\nequipment_model_id: " + equipmentMSHE_id.equipment_model_id +
                "\nequipment_state_id: " + equipmentMSHE_id.equipment_state_id +
                "\nvalue: " + getValue();
    }

    @JsonIgnore
    public EquipmentMSHE_ID getEquipmentMSHE_id() {
        return equipmentMSHE_id;
    }

    public void setEquipmentMSHE_id(EquipmentMSHE_ID equipmentMSHE_id) {
        this.equipmentMSHE_id = equipmentMSHE_id;
    }

    @JsonIgnore
    public EquipmentModel getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(EquipmentModel equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    @JsonIgnore
    public EquipmentState getEquipmentState() {
        return equipmentState;
    }

    public void setEquipmentState(EquipmentState equipmentState) {
        this.equipmentState = equipmentState;
    }

    @Embeddable
    public static class EquipmentMSHE_ID implements Serializable //Equipment Model State Hourly Earnings
    {

        @Column(name = "equipment_model_id", nullable = false)
        private UUID equipment_model_id;
        @Column(name = "equipment_state_id", nullable = false)
        private UUID equipment_state_id;

        public EquipmentMSHE_ID() {

        }

        public EquipmentMSHE_ID(UUID id1, UUID id2) {
            this.equipment_model_id = id1;
            this.equipment_state_id = id2;
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
}


