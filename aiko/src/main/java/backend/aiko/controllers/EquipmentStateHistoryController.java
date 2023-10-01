package backend.aiko.controllers;

import backend.aiko.dtos.EquipmentStateHistoryDTO;
import backend.aiko.mappers.EquipmentStateHistoryMapper;
import backend.aiko.models.EquipmentStateHistory;
import backend.aiko.services.EquipmentStateHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/equipment_shc")
@CrossOrigin("*")
@AllArgsConstructor
@Tag(name = "6. Equipment State History", description = "CRUD")

public class EquipmentStateHistoryController {
    public static final String ENDPOINT = "/equipment_shc";
    final EquipmentStateHistoryService equipmentStateHistoryService;
    final EquipmentStateHistoryMapper mapper;

    @PostMapping
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity" +
            "<br>STATES ID:" +
            "<br>baff9783-84e8-4e01-874b-6fd743b875ad = Stopped" +
            "<br>03b2d446-e3ba-4c82-8dc2-a5611fea6e1f = Maintenance" +
            "<br>0808344c-454b-4c36-89e8-d7687e692d57 = Operating")
    public ResponseEntity<Object> saveEquipmentStateHistory(@RequestBody @Valid EquipmentStateHistoryDTO equipmentStateHistoryDTO) {
        var equip = new EquipmentStateHistory(equipmentStateHistoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentStateHistoryService.save(equip));
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Here, you can get a list of objects")
    public ResponseEntity<List<EquipmentStateHistory>> getAllEquipmentStateHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentStateHistoryService.findAll());
    }

    @GetMapping("/{equipment_id}/{date}/{state_id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public ResponseEntity<Object> getOneEquipmentStateHistory(
            @PathVariable(value = "equipment_id") UUID equipment_id,
            @PathVariable(value = "date") String customDate,
            @PathVariable(value = "state_id") UUID state_id) {
        if (equipment_id == null || state_id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        LocalDateTime date = LocalDateTime.parse(customDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var compositeKey = new EquipmentStateHistory.EquipmentSH_ID(equipment_id, date, state_id);
        Optional<EquipmentStateHistory> equipmentStateHistoryModelOptional = equipmentStateHistoryService.findById(compositeKey);
        if (!equipmentStateHistoryModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentStateHistory not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(equipmentStateHistoryModelOptional.get());
    }

    @DeleteMapping("/{equipment_id}/{date}/{state_id}")
    @Operation(summary = "DELETE", description = "Here, you can delete a specific object by your ID")
    public ResponseEntity<Object> deleteEquipmentStateHistory(
            @PathVariable(value = "equipment_id") UUID equipment_id,
            @PathVariable(value = "date") String customDate,
            @PathVariable(value = "state_id") UUID state_id) {
        if (equipment_id == null || state_id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        LocalDateTime date = LocalDateTime.parse(customDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var compositeKey = new EquipmentStateHistory.EquipmentSH_ID(equipment_id, date, state_id);
        Optional<EquipmentStateHistory> equipmentStateHistoryModelOptional = equipmentStateHistoryService.findById(compositeKey);
        if (!equipmentStateHistoryModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentStateHistory not found.");
        }
        equipmentStateHistoryService.delete(equipmentStateHistoryModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("EquipmentStateHistory deleted successfully.");
    }

    @PutMapping("/{equipment_id}/{date}/{state_id}")
    @Operation(summary = "EDIT", description = "Here, you can edit infos about an specific ID" +
            "<br>STATES ID:" +
            "<br>baff9783-84e8-4e01-874b-6fd743b875ad = Stopped" +
            "<br>03b2d446-e3ba-4c82-8dc2-a5611fea6e1f = Maintenance" +
            "<br>0808344c-454b-4c36-89e8-d7687e692d57 = Operating")
    public ResponseEntity<Object> updateEquipmentStateHistory(@PathVariable(value = "equipment_id") UUID equipment_id,
                                                              @PathVariable(value = "date") String customDate,
                                                              @PathVariable(value = "state_id") UUID state_id,
                                                              @RequestBody @Valid EquipmentStateHistoryDTO equipmentStateHistoryDTO) {
        if (equipment_id == null || state_id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        LocalDateTime date = LocalDateTime.parse(customDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var compositeKey = new EquipmentStateHistory.EquipmentSH_ID(equipment_id, date, state_id);
        Optional<EquipmentStateHistory> optional = equipmentStateHistoryService.findById(compositeKey);
        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentStateHistory not found.");
        }
        var equip = optional.get();
        BeanUtils.copyProperties(equipmentStateHistoryDTO, equip);
        return ResponseEntity.status(HttpStatus.OK).body(equipmentStateHistoryService.save(equip));
    }

}
