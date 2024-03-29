package backend.aiko.controllers;

import backend.aiko.models.EquipmentPositionHistory;
import backend.aiko.models.EquipmentStateHistory;
import backend.aiko.services.EquipmentPositionHistoryService;
import backend.aiko.services.EquipmentStateHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "1. Last status of Equipments", description = "Estado e Posição mais recente dos equipamentos")
@RequestMapping("/last")
@CrossOrigin("*")
public class LastItems {

    final EquipmentPositionHistoryService equipmentPositionHistoryService;
    final EquipmentStateHistoryService equipmentStateHistoryService;

    public LastItems(EquipmentPositionHistoryService equipmentPositionHistoryService, EquipmentStateHistoryService equipmentStateHistoryService) {
        this.equipmentPositionHistoryService = equipmentPositionHistoryService;
        this.equipmentStateHistoryService = equipmentStateHistoryService;
    }

    @GetMapping("/state")
    @Operation(summary = "Get all", description = "Get last states of your Equipments")
    public ResponseEntity<List<EquipmentStateHistory>> listResponseEntity() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentStateHistoryService.findLastState());
    }

    @GetMapping("/position")
    @Operation(summary = "Get all", description = "Get last position of your Equipments")
    public ResponseEntity<List<EquipmentPositionHistory>> listResponseEntity2() {
        return ResponseEntity.status(HttpStatus.OK).body(equipmentPositionHistoryService.findLastPosition());
    }


}
