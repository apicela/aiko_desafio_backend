package apirest.aiko.services;

import apirest.aiko.dtos.EquipmentStateHistoryDTO;
import apirest.aiko.mappers.EquipmentStateHistoryMapper;
import apirest.aiko.models.EquipmentStateHistory;
import apirest.aiko.repositories.EquipmentStateHistoryRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentStateHistoryService {
    final EquipmentStateHistoryRepository equipmentStateHistoryRepository;
    final EquipmentStateHistoryMapper mapper;

    public List<EquipmentStateHistory> findAll() {
        return equipmentStateHistoryRepository.findAll();
    }

    public List<EquipmentStateHistoryDTO> findLastState() {
        List<EquipmentStateHistory> list = equipmentStateHistoryRepository.findLastState();
        return mapper.mapEntityListToDtoList(list);
    }

    public String findState(UUID equipment_id) {
        return equipmentStateHistoryRepository.findState(equipment_id);
    }

    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK - Criado com sucesso", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public EquipmentStateHistory save(EquipmentStateHistoryDTO dto) {
        return equipmentStateHistoryRepository.save(mapper.mapDtoToEntity(dto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - requisição bem sucedida e com retorno.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado no banco de dados.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public Optional<EquipmentStateHistoryDTO> findById(EquipmentStateHistory.EquipmentSH_ID id) {
        Optional<EquipmentStateHistory> optional = equipmentStateHistoryRepository.findById(id);
        if (optional.isPresent()) {
            var equipment = optional.get();
            return Optional.of(mapper.mapEntityToDto(equipment));
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
    public void delete(EquipmentStateHistoryDTO equipmentStateHistory) {
        equipmentStateHistoryRepository.delete(mapper.mapDtoToEntity(equipmentStateHistory));
    }
}
