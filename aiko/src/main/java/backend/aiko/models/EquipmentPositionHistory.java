package backend.aiko.models;

import backend.aiko.dtos.EquipmentPositionHistoryDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "equipment_position_history", schema = "operation")
@NoArgsConstructor
public class EquipmentPositionHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "equipment_id", nullable = false, insertable = false, updatable = false)
    private UUID equipment_id;

    @EmbeddedId
    private EquipmentPositionHistoryPK equipmentPositionHistoryPK;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipment_id")
    @JoinColumn(name = "equipment_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_equipment", value = ConstraintMode.NO_CONSTRAINT),
            updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Equipment equipment;

    private double lat;
    private double lon;
    @Column(name = "date", nullable = false, insertable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:ss", example = "2001-12-25 17:15:22")
    private LocalDateTime date;


    public EquipmentPositionHistory(EquipmentPositionHistoryDTO equipmentPositionHistoryDTO) {
        this.equipment = new Equipment();
        equipmentPositionHistoryPK = new EquipmentPositionHistoryPK(equipmentPositionHistoryDTO.getEquipment_id(), equipmentPositionHistoryDTO.getDate());
        equipment.setId(equipmentPositionHistoryDTO.getEquipment_id());
        this.lat = equipmentPositionHistoryDTO.getLat();
        this.lon = equipmentPositionHistoryDTO.getLon();
    }

    @JsonIgnore
    public Equipment getEquipment() {
        return equipment;
    }

    @JsonIgnore
    public EquipmentPositionHistoryPK getEquipmentPositionHistoryPK() {
        return equipmentPositionHistoryPK;
    }

    @Override
    public String toString() {
        return "Equipment Position History:" +
                "\nequipment_id: " + equipmentPositionHistoryPK.equipment_id +
                "\ndate: " + equipmentPositionHistoryPK.date +
                "\nlatitude: " + lat +
                "\nlongitude: " + lon;
    }

    @Data
    @Embeddable
    @NoArgsConstructor
    public static class EquipmentPositionHistoryPK implements Serializable {
        @Column(name = "equipment_id", nullable = false)
        private UUID equipment_id;
        @Column(name = "date", nullable = false)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        private LocalDateTime date;

        public EquipmentPositionHistoryPK(UUID equipment_id, LocalDateTime date) {
            this.equipment_id = equipment_id;
            this.date = date;
        }

    }
}


