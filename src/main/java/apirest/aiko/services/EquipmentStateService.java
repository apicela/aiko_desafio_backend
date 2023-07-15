package apirest.aiko.services;

import apirest.aiko.dtos.EquipmentStateDTO;
import apirest.aiko.mappers.EquipmentStateMapper;
import apirest.aiko.models.EquipmentState;
import apirest.aiko.repositories.EquipmentStateRepository;
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
public class EquipmentStateService {
    final EquipmentStateRepository equipmentStateRepository;
    final EquipmentStateMapper mapper;


    public List<EquipmentStateDTO> findAll() {
        List<EquipmentState> list = equipmentStateRepository.findAll();
        return mapper.mapEntityListToDtoList(list);
    }

    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK - Criado com sucesso", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public EquipmentState save(EquipmentStateDTO dto) {
        return equipmentStateRepository.save(mapper.mapDtoToEntity(dto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - requisição bem sucedida e com retorno.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado no banco de dados.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public Optional<EquipmentStateDTO> findById(UUID id) {
        Optional<EquipmentState> optional = equipmentStateRepository.findById(id);
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
    public void delete(EquipmentStateDTO equipmentState) {
        equipmentStateRepository.delete(mapper.mapDtoToEntity(equipmentState));
    }
}
