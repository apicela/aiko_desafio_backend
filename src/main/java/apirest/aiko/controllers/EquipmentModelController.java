package apirest.aiko.controllers;

import apirest.aiko.dtos.EquipmentModelDTO;
import apirest.aiko.models.EquipmentModel;
import apirest.aiko.services.EquipmentModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
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
        var equipmentModelModel = new EquipmentModel();
        BeanUtils.copyProperties(equipmentModelDTO, equipmentModelModel);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created successfully." + equipmentModelService.save(equipmentModelModel));
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Here, you can get a list of objects")
    public ResponseEntity<List<EquipmentModel>> getAllEquipmentModel() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentModelService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public ResponseEntity<Object> getOneEquipmentModelModel(@PathVariable(value = "id") UUID id) {
        Optional<EquipmentModel> equipmentModelModelOptional = equipmentModelService.findById(id);
        if (!equipmentModelModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentModelModel not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(equipmentModelModelOptional.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETE", description = "Here, you can delete a specific object by your ID")
    public ResponseEntity<Object> deleteEquipmentModelModel(@PathVariable(value = "id") UUID id) {
        Optional<EquipmentModel> equipmentModelModelOptional = equipmentModelService.findById(id);
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
        Optional<EquipmentModel> equipmentModelModelOptional = equipmentModelService.findById(id);
        if (!equipmentModelModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentModelModel not found.");
        }
        var equipmentModelModel = new EquipmentModel();
        BeanUtils.copyProperties(equipmentModelDto, equipmentModelModel);
        equipmentModelModel.setId(equipmentModelModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body("Modified.\n" + equipmentModelService.save(equipmentModelModel));
    }


}
