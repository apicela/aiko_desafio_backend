package apirest.aiko.dtos;

import apirest.aiko.models.EquipmentPositionHistory;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data // getters and setters
@NoArgsConstructor
public class EquipmentPositionHistoryDTO {
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
    public EquipmentPositionHistoryDTO(EquipmentPositionHistory.EquipmentPositionHistoryPK equipmentPositionHistoryPK,
                                       double lat, double lon) {
        this.equipment_id = equipmentPositionHistoryPK.getEquipment_id();
        this.date = equipmentPositionHistoryPK.getDate();
        this.lat = lat;
        this.lon = lon;
    }

}