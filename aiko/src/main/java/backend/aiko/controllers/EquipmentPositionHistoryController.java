package backend.aiko.controllers;

import backend.aiko.dtos.EquipmentPositionHistoryDTO;
import backend.aiko.mappers.EquipmentPositionHistoryMapper;
import backend.aiko.models.EquipmentPositionHistory;
import backend.aiko.services.EquipmentPositionHistoryService;
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
@RequestMapping("/equipment_position_history")
@CrossOrigin("*")
@AllArgsConstructor
@Tag(name = "7. Equipment Position History", description = "CRUD")
public class EquipmentPositionHistoryController {
    public static final String ENDPOINT = "/equipment_position_history";

    final EquipmentPositionHistoryService equipmentPositionHistoryService;
    final EquipmentPositionHistoryMapper mapper;

    @PostMapping
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity")
    public ResponseEntity<Object> saveEquipmentPositionHistory(@RequestBody @Valid EquipmentPositionHistoryDTO equipmentPositionHistoryDTO) {
        var equip = new EquipmentPositionHistory(equipmentPositionHistoryDTO);
        equipmentPositionHistoryService.save(equip);
        return ResponseEntity.status(HttpStatus.CREATED).body(equip.toString());
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Here, you can get a list of objects")
    public ResponseEntity<List<EquipmentPositionHistory>> getAllEquipmentPositionHistory() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentPositionHistoryService.findAll());
    }

    @GetMapping("/{equipment_id}/{date}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public ResponseEntity<Object> getOneEquipmentPositionHistory(
            @PathVariable(value = "equipment_id") UUID equipment_id,
            @PathVariable(value = "date") LocalDateTime date) {
        if (equipment_id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        EquipmentPositionHistory.EquipmentPositionHistoryPK compositeKey = new EquipmentPositionHistory.EquipmentPositionHistoryPK(equipment_id, date);
        Optional<EquipmentPositionHistory> equipmentPositionHistoryModelOptional = equipmentPositionHistoryService.findById(compositeKey);
        if (!equipmentPositionHistoryModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentPositionHistory not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(equipmentPositionHistoryModelOptional.get());
    }

    @DeleteMapping("/{equipment_id}/{date}")
    @Operation(summary = "DELETE", description = "Here, you can delete a specific object by your ID")
    public ResponseEntity<Object> deleteEquipmentPositionHistory(
            @PathVariable(value = "equipment_id") UUID equipment_id,
            @PathVariable(value = "date") LocalDateTime date) {
        if (equipment_id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        EquipmentPositionHistory.EquipmentPositionHistoryPK compositeKey = new EquipmentPositionHistory.EquipmentPositionHistoryPK(equipment_id, date);
        Optional<EquipmentPositionHistory> equipmentPositionHistoryModelOptional = equipmentPositionHistoryService.findById(compositeKey);
        if (!equipmentPositionHistoryModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentPositionHistory not found.");
        }
        equipmentPositionHistoryService.delete(equipmentPositionHistoryModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("EquipmentPositionHistory deleted successfully.");
    }

    @PutMapping("/{equipment_id}/{date}")
    @Operation(summary = "EDIT", description = "Here, you can edit infos about an specific ID")
    public ResponseEntity<Object> updateEquipmentPositionHistory(
            @PathVariable(value = "equipment_id") UUID equipment_id,
            @PathVariable(value = "date") String customDate,
            @RequestBody @Valid EquipmentPositionHistoryDTO equipmentPositionHistoryDto) {
        if (equipment_id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        LocalDateTime date = LocalDateTime.parse(customDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        var compositeKey = new EquipmentPositionHistory.EquipmentPositionHistoryPK(equipment_id, date);
        Optional<EquipmentPositionHistory> optional = equipmentPositionHistoryService.findById(compositeKey);
        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentPositionHistory not found.");
        }
        var equip = optional.get();
        BeanUtils.copyProperties(equipmentPositionHistoryDto, equip);
        return ResponseEntity.status(HttpStatus.OK).body(equipmentPositionHistoryService.save(equip));
    }

}
