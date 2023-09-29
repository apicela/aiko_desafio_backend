package backend.aiko.services;

import backend.aiko.mappers.EquipmentPositionHistoryMapper;
import backend.aiko.models.EquipmentPositionHistory;
import backend.aiko.repositories.EquipmentPositionHistoryRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentPositionHistoryService {
    final EquipmentPositionHistoryRepository equipmentPositionHistoryRepository;
    final EquipmentPositionHistoryMapper mapper;

    public List<EquipmentPositionHistory> findLastPosition() {
        return equipmentPositionHistoryRepository.findLastPosition();
    }


    public List<EquipmentPositionHistory> findAll() {
        return equipmentPositionHistoryRepository.findLastPosition();
    }

    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK - Criado com sucesso", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public EquipmentPositionHistory save(EquipmentPositionHistory equipmentPositionHistory) {
        return equipmentPositionHistoryRepository.save(equipmentPositionHistory);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - requisição bem sucedida e com retorno.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado no banco de dados.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public Optional<EquipmentPositionHistory> findById(EquipmentPositionHistory.EquipmentPositionHistoryPK id) {
        Optional<EquipmentPositionHistory> optional = equipmentPositionHistoryRepository.findById(id);
        if (optional.isPresent()) {
            return Optional.of(optional.get());
        }
        return Optional.empty();
    }

    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - deletado com sucesso", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado no banco de dados.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public void delete(EquipmentPositionHistory equip) {
        equipmentPositionHistoryRepository.delete(equip);
    }
}
