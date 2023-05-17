package apirest.aiko.dtos;

import apirest.aiko.models.EquipmentPositionHistory;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;


public class EquipmentPositionHistoryDTO {

    public EquipmentPositionHistoryDTO(){}
    public EquipmentPositionHistoryDTO(EquipmentPositionHistory.EquipmentPositionHistoryPK equipmentPositionHistoryPK,
                                       double lat, double lon)
    {
        this.equipment_id = equipmentPositionHistoryPK.getEquipment_id();
        this.date = equipmentPositionHistoryPK.getDate();
        this.lat = lat;
        this.lon = lon;
    }

    @NotNull
    private UUID equipment_id;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:ss", example = "2001-12-25 17:15:22")
    private LocalDateTime date;

    @NotNull
    private double lat;
    @NotNull
    private double lon;

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


}