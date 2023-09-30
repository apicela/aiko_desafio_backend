package backend.aiko.controllers;

import backend.aiko.dtos.EquipmentStateDTO;
import backend.aiko.mappers.EquipmentStateMapper;
import backend.aiko.models.EquipmentState;
import backend.aiko.services.EquipmentStateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/equipment_state")
@CrossOrigin("*")
@AllArgsConstructor
@Tag(name = "4. Equipment State", description = "CRUD")
@Log4j2
public class EquipmentStateController {
    public static final String ENDPOINT = "/equipment_state";

    final EquipmentStateService equipmentStateService;
    final EquipmentStateMapper mapper;


    @PostMapping
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity")
    public ResponseEntity<Object> saveEquipmentState(@RequestBody @Valid EquipmentStateDTO equipmentStateDTO) {
        log.info(equipmentStateDTO.toString());
        var equipment = mapper.mapDtoToEntity(equipmentStateDTO);
        log.info(equipment.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentStateService.save(equipment));
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Here, you can get a list of objects")
    public ResponseEntity<List<EquipmentState>> getAllEquipmentState() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentStateService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public ResponseEntity<Object> getOneEquipmentState(@PathVariable(value = "id") UUID id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        Optional<EquipmentState> equipmentStateModelOptional = equipmentStateService.findById(id);
        if (!equipmentStateModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentState not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(equipmentStateModelOptional.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETE", description = "Here, you can delete a specific object by your ID")
    public ResponseEntity<Object> deleteEquipmentState(@PathVariable(value = "id") UUID id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        Optional<EquipmentState> equipmentStateModelOptional = equipmentStateService.findById(id);
        if (!equipmentStateModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentState not found.");
        }
        equipmentStateService.delete(equipmentStateModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("EquipmentState deleted successfully.");
    }

    @PutMapping("/{id}")
    @Operation(summary = "EDIT", description = "Here, you can edit infos about an specific ID")
    public ResponseEntity<Object> updateEquipmentState(@PathVariable(value = "id") UUID id,
                                                       @RequestBody @Valid EquipmentStateDTO equipmentStateDto) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        Optional<EquipmentState> optional = equipmentStateService.findById(id);
        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentState not found.");
        }
        var equipmentState = optional.get();
        BeanUtils.copyProperties(equipmentStateDto, equipmentState);
        return ResponseEntity.status(HttpStatus.OK).body(equipmentStateService.save(equipmentState));
    }
}