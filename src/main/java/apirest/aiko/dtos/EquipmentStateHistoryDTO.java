package apirest.aiko.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public class EquipmentStateHistoryDTO {
    @NotNull
    private UUID equipment_id;
    @NotNull
    private UUID equipment_state_id;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(type = "string", pattern = "yyyy-MM-dd HH:mm:ss", example = "2001-12-25 17:15:22")
    private LocalDateTime date;

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
