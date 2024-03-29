package backend.aiko.controllers;

import backend.aiko.dtos.EquipmentDTO;
import backend.aiko.models.Equipment;
import backend.aiko.services.EquipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController()
@Tag(name = "3. Equipment ", description = "CRUD")
@RequestMapping("/equipment")
@Log4j2
@CrossOrigin("*")
public class EquipmentController {
    public static final String ENDPOINT = "/equipment";

    final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }


    @PostMapping
    @Operation(summary = "CREATE", description = "Here, you can create a new object for your entity")
    public ResponseEntity<Object> saveEquipment(@RequestBody @Valid EquipmentDTO equipmentDTO) {
        var equipmentModel = new Equipment(equipmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.save(equipmentModel));
    }

    @GetMapping
    @Operation(summary = "Get all objects", description = "Here, you can get a list of objects")
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find object by Id", description = "Here, you can get a specific object filtering by your ID")
    public ResponseEntity<Object> getOneEquipment(@PathVariable(value = "id") UUID id) {
        Optional<Equipment> equipmentModelOptional = equipmentService.findById(id);
        if (!equipmentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(equipmentModelOptional.get());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "DELETE", description = "Here, you can delete a specific object by your ID")
    public ResponseEntity<Object> deleteEquipment(@PathVariable(value = "id") UUID id) {
        Optional<Equipment> equipmentModelOptional = equipmentService.findById(id);
        if (!equipmentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found.");
        }
        equipmentService.delete(equipmentModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Equipment deleted successfully.");
    }

    @PutMapping("/{id}")
    @Operation(summary = "EDIT", description = "Here,\n you can edit \r\ninfos about an specific ID")
    public ResponseEntity<Object> updateEquipment(@PathVariable(value = "id") UUID id,
                                                  @RequestBody @Valid EquipmentDTO equipmentDto) {
        Optional<Equipment> equipmentModelOptional = equipmentService.findById(id);
        if (!equipmentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Equipment not found.");
        }
        var equipmentModel = new Equipment(equipmentDto);
        equipmentModel.setId(equipmentModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(equipmentService.save(equipmentModel));

    }
}
