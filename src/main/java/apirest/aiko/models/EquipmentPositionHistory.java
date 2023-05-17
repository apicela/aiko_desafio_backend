package apirest.aiko.models;

import apirest.aiko.dtos.EquipmentPositionHistoryDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "equipment_position_history", schema = "operation")
public class EquipmentPositionHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "equipment_id", nullable = false, insertable = false, updatable = false)
    private UUID equipment_id;

    @EmbeddedId
    private EquipmentPositionHistoryPK equipmentPositionHistoryPK;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("equipment_id")
    @JoinColumn(name = "equipment_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_equipment"),
            updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Equipment equipment;
    @Column(nullable = false)
    private double lat;
    @Column(nullable = false)
    private double lon;

    public EquipmentPositionHistory() {
    }

    public EquipmentPositionHistory(EquipmentPositionHistoryDTO equipmentPositionHistoryDTO) {
        equipment = new Equipment();
        System.out.println("HAHAHAHAHHAHAHA");
        equipmentPositionHistoryPK = new EquipmentPositionHistoryPK(equipmentPositionHistoryDTO.getEquipment_id(), equipmentPositionHistoryDTO.getDate()  );
        equipment.setId(equipmentPositionHistoryDTO.getEquipment_id());
        this.lat = equipmentPositionHistoryDTO.getLat();
        this.lon = equipmentPositionHistoryDTO.getLon();
    }

    @JsonIgnore
    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public UUID getEquipment_id() {
        return equipment_id;
    }

    public void setEquipment_id(UUID equipment_id) {
        this.equipment_id = equipment_id;
    }

    @Column(name = "date", nullable = false, insertable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:ss", example = "2001-12-25 17:15:22")
    private LocalDateTime date;
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @JsonIgnore
    public EquipmentPositionHistoryPK getEquipmentPositionHistoryPK() {
        return equipmentPositionHistoryPK;
    }

    public void setEquipmentPositionHistoryPK(EquipmentPositionHistoryPK equipmentPositionHistoryPK) {
        this.equipmentPositionHistoryPK = equipmentPositionHistoryPK;
    }


    @Override
    public String toString() {
        return "Equipment Position History:" +
                "\nequipment_id: " + equipmentPositionHistoryPK.equipment_id +
                "\ndate: " + equipmentPositionHistoryPK.date +
                "\nlatitude: " + lat +
                "\nlongitude: " + lon;
    }

    public static class EquipmentPositionHistoryPK implements Serializable {

        public EquipmentPositionHistoryPK(){}

        public EquipmentPositionHistoryPK(UUID equipment_id, LocalDateTime date)
        {
            this.equipment_id=equipment_id;
            this.date=date;
        }
        @Column(name = "equipment_id", nullable = false)
        private UUID equipment_id;

        @Column(name = "date", nullable = false)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        private LocalDateTime date;

        public UUID getEquipment_id() {
            return equipment_id;
        }

        public void setEquipment_id(UUID equipment_id) {
            this.equipment_id = equipment_id;
        }

        public LocalDateTime getDate() {
            return date;
        }

        public void setDate(LocalDateTime date) {
            this.date = date;
        }
    }
}


