package apirest.aiko.controllers;

import apirest.aiko.dtos.EquipmentStateDTO;
import apirest.aiko.services.EquipmentStateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/equipment_state")
@CrossOrigin("*")
@Tag(name = "4. Equipment State", description = "CRUD")
public class EquipmentStateController {
    final EquipmentStateService equipmentStateService;

    public EquipmentStateController(EquipmentStateService equipmentStateService) {
        this.equipmentStateService = equipmentStateService;
    }

    @PostMapping
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity")
    public ResponseEntity<Object> saveEquipmentState(@RequestBody @Valid EquipmentStateDTO equipmentStateDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully." + equipmentStateService.save(equipmentStateDTO));
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Here, you can get a list of objects")
    public ResponseEntity<List<EquipmentStateDTO>> getAllEquipmentState() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentStateService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public ResponseEntity<Object> getOneEquipmentState(@PathVariable(value = "id") UUID id) {
        Optional<EquipmentStateDTO> equipmentStateModelOptional = equipmentStateService.findById(id);
        if (!equipmentStateModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentState not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(equipmentStateModelOptional.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETE", description = "Here, you can delete a specific object by your ID")
    public ResponseEntity<Object> deleteEquipmentState(@PathVariable(value = "id") UUID id) {
        Optional<EquipmentStateDTO> equipmentStateModelOptional = equipmentStateService.findById(id);
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
        Optional<EquipmentStateDTO> equipmentStateModelOptional = equipmentStateService.findById(id);
        if (!equipmentStateModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentState not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Modified.\n" + equipmentStateService.save(equipmentStateDto));
    }
}