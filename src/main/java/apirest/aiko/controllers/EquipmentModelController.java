package apirest.aiko.controllers;

import apirest.aiko.dtos.EquipmentModelDTO;
import apirest.aiko.services.EquipmentModelService;
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
@RequestMapping("/equipment_model")
@CrossOrigin("*")
@Tag(name = "2. Equipment Model", description = "CRUD")

public class EquipmentModelController {
    final EquipmentModelService equipmentModelService;

    public EquipmentModelController(EquipmentModelService equipmentModelService) {
        this.equipmentModelService = equipmentModelService;
    }


    @PostMapping
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity")
    public ResponseEntity<Object> saveEquipmentModel(@RequestBody @Valid EquipmentModelDTO equipmentModelDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully." + equipmentModelService.save(equipmentModelDTO));
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Here, you can get a list of objects")
    public ResponseEntity<List<EquipmentModelDTO>> getAllEquipmentModel() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentModelService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public ResponseEntity<Object> getOneEquipmentModelModel(@PathVariable(value = "id") UUID id) {
        Optional<EquipmentModelDTO> equipmentModelModelOptional = equipmentModelService.findById(id);
        if (!equipmentModelModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentModelModel not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(equipmentModelModelOptional.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETE", description = "Here, you can delete a specific object by your ID")
    public ResponseEntity<Object> deleteEquipmentModelModel(@PathVariable(value = "id") UUID id) {
        Optional<EquipmentModelDTO> equipmentModelModelOptional = equipmentModelService.findById(id);
        if (!equipmentModelModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentModelModel not found.");
        }
        equipmentModelService.delete(equipmentModelModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("EquipmentModelModel deleted successfully.");
    }

    @PutMapping("/{id}")
    @Operation(summary = "EDIT", description = "Here, you can edit infos about an specific ID")
    public ResponseEntity<Object> updateEquipmentModelModel(@PathVariable(value = "id") UUID id,
                                                            @RequestBody @Valid EquipmentModelDTO equipmentModelDto) {
        Optional<EquipmentModelDTO> equipmentModelModelOptional = equipmentModelService.findById(id);
        if (!equipmentModelModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentModelModel not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Modified.\n" + equipmentModelService.save(equipmentModelDto));
    }


}
