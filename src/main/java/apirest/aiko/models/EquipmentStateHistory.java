package apirest.aiko.models;

import apirest.aiko.dtos.EquipmentStateHistoryDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "equipment_state_history", schema = "operation")
public class EquipmentStateHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private EquipmentSH_ID equipmentSH_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipment_id")
    @JoinColumn(name = "equipment_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_equipment"),
            updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Equipment equipment;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipment_state_id")
    @JoinColumn(name = "equipment_state_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_equipment_state"),
            updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private EquipmentState equipmentState;

    @Column(name = "equipment_id", nullable = false, insertable = false, updatable = false)
    private UUID equipment_id;
    @Column(name = "date", nullable = false, insertable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT")
    @Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:ss", example = "2001-12-25 17:15:22")
    private LocalDateTime date;
    @Column(name = "equipment_state_id", nullable = false, insertable = false, updatable = false)
    private UUID equipment_state_id;

    public EquipmentStateHistory() {
    }
    public EquipmentStateHistory(EquipmentStateHistoryDTO equipmentStateHistoryDTO) {
        equipmentState = new EquipmentState();
        equipment = new Equipment();
        equipmentSH_id = new EquipmentSH_ID();
        equipmentState.setId(equipmentStateHistoryDTO.getEquipment_state_id());
        equipment.setId(equipmentStateHistoryDTO.getEquipment_id());
        equipmentSH_id.setDate(equipmentStateHistoryDTO.getDate());
    }

    public UUID getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(UUID equipment_id) {
        this.equipment_id = equipment_id;
    }

    public UUID getEquipment_state_id() {
        return equipment_state_id;
    }

    public void setEquipment_state_id(UUID equipment_state_id) {
        this.equipment_state_id = equipment_state_id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @JsonIgnore
    public EquipmentSH_ID getEquipmentSH_id() {
        return equipmentSH_id;
    }

    public void setEquipmentSH_id(EquipmentSH_ID equipmentSH_id) {
        this.equipmentSH_id = equipmentSH_id;
    }

    @JsonIgnore
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @JsonIgnore
    public EquipmentState getEquipmentState() {
        return equipmentState;
    }

    public void setEquipmentState(EquipmentState equipmentState) {
        this.equipmentState = equipmentState;
    }

    @Override
    public String toString() {
        return "Equipment State History" +
                "\nequipment_id: " + equipmentSH_id.equipment_id +
                "\ndate: " + equipmentSH_id.date +
                "\nequipment_state_id: " + equipmentSH_id.equipment_state_id;
    }

    @Embeddable
    public static class EquipmentSH_ID implements Serializable {

        @Column(name = "equipment_id", nullable = false)
        private UUID equipment_id;
        @Column(name = "equipment_state_id", nullable = false)
        private UUID equipment_state_id;
        @Column(name = "date", nullable = false)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        private LocalDateTime date;

        public EquipmentSH_ID() {
        }

        public EquipmentSH_ID(UUID equipment_id, LocalDateTime date, UUID state_id) {
            this.equipment_id = equipment_id;
            this.date = date;
            this.equipment_state_id = state_id;
        }

        public UUID getEquipment_id() {
            return equipment_id;
        }

        public void setEquipment_id(UUID equipment_id) {
            this.equipment_id = equipment_id;
        }

        public UUID getEquipment_state_id() {
            return equipment_state_id;
        }

        public void setEquipment_state_id(UUID equipment_state_id) {
            this.equipment_state_id = equipment_state_id;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }
    }
}
