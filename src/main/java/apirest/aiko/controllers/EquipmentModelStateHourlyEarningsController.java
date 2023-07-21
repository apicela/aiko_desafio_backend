package apirest.aiko.controllers;


import apirest.aiko.dtos.EquipmentModelStateHourlyEarningsDTO;
import apirest.aiko.mappers.EquipmentModelStateHourlyEarningsMapper;
import apirest.aiko.models.EquipmentModelStateHourlyEarnings;
import apirest.aiko.services.EquipmentModelStateHourlyEarningsService;
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
@RequestMapping("/equipment_model_state_hourly_earnings")
@CrossOrigin("*")
@AllArgsConstructor
@Log4j2
@Tag(name = "5. Equipment Model State Hourly Earnings", description = "CRUD")

public class EquipmentModelStateHourlyEarningsController {
    public static final String ENDPOINT = "/equipment_model_state_hourly_earnings";

    final EquipmentModelStateHourlyEarningsService equipmentModelStateHourlyEarningsService;
    final EquipmentModelStateHourlyEarningsMapper mapper;

    @PostMapping
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity")
    public ResponseEntity<Object> saveEquipmentModelStateHourlyEarnings(@RequestBody @Valid EquipmentModelStateHourlyEarningsDTO equipmentModelStateHourlyEarningsDTO) {
        var equipmentModelStateHourlyEarningsModel = new EquipmentModelStateHourlyEarnings(equipmentModelStateHourlyEarningsDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentModelStateHourlyEarningsService.save(equipmentModelStateHourlyEarningsModel));
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Here, you can get a list of objects")
    public ResponseEntity<List<EquipmentModelStateHourlyEarnings>> getAllEquipmentModelStateHourlyEarnings() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentModelStateHourlyEarningsService.findAll());
    }

    @GetMapping("/{model_ID}/{state_ID}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public ResponseEntity<Object> getOneEquipmentModelStateHourlyEarnings(
            @PathVariable(value = "model_ID") UUID model_ID,
            @PathVariable(value = "state_ID") UUID state_ID) {
        if (model_ID == null || state_ID == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        var compositeKey = new EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID(model_ID, state_ID);
        Optional<EquipmentModelStateHourlyEarnings> equipmentModelStateHourlyEarningsModelOptional = equipmentModelStateHourlyEarningsService.findById(compositeKey);
        if (!equipmentModelStateHourlyEarningsModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentModelStateHourlyEarnings not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(equipmentModelStateHourlyEarningsModelOptional.get());
    }

    @DeleteMapping("/{model_ID}/{state_ID}")
    @Operation(summary = "DELETE", description = "Here, you can delete a specific object by your ID")
    public ResponseEntity<Object> deleteEquipmentModelStateHourlyEarnings(
            @PathVariable(value = "model_ID") UUID model_ID,
            @PathVariable(value = "state_ID") UUID state_ID) {
        if (model_ID == null || state_ID == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        var compositeKey = new EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID(model_ID, state_ID);
        Optional<EquipmentModelStateHourlyEarnings> equipmentModelStateHourlyEarningsModelOptional = equipmentModelStateHourlyEarningsService.findById(compositeKey);
        if (!equipmentModelStateHourlyEarningsModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentModelStateHourlyEarnings not found.");
        }
        equipmentModelStateHourlyEarningsService.delete(equipmentModelStateHourlyEarningsModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("EquipmentModelStateHourlyEarnings deleted successfully.");
    }

    @PutMapping("/{model_ID}/{state_ID}")
    @Operation(summary = "EDIT", description = "Here, you can edit infos about an specific ID")
    public ResponseEntity<Object> updateEquipmentModelStateHourlyEarnings(
            @PathVariable(value = "model_ID") UUID model_ID,
            @PathVariable(value = "state_ID") UUID state_ID,
            @RequestBody @Valid EquipmentModelStateHourlyEarningsDTO equipmentModelStateHourlyEarningsDto) {
        if (model_ID == null || state_ID == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid ID.");
        }
        var compositeKey = new EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID(model_ID, state_ID);
        Optional<EquipmentModelStateHourlyEarnings> optional = equipmentModelStateHourlyEarningsService.findById(compositeKey);
        if (!optional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("EquipmentModelStateHourlyEarnings not found.");
        }
        var equip = optional.get();
        BeanUtils.copyProperties(equipmentModelStateHourlyEarningsDto, equip);
        return ResponseEntity.status(HttpStatus.OK).body(equipmentModelStateHourlyEarningsService.save(equip));
    }
}
