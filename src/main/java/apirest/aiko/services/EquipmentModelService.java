package apirest.aiko.services;

import apirest.aiko.dtos.EquipmentModelDTO;
import apirest.aiko.mappers.EquipmentModelMapper;
import apirest.aiko.models.EquipmentModel;
import apirest.aiko.repositories.EquipmentModelRepository;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class EquipmentModelService {
    final EquipmentModelRepository equipmentModelRepository;
    final EquipmentModelMapper mapper;

    public List<EquipmentModelDTO> findAll() {
        List<EquipmentModel> list = equipmentModelRepository.findAll();
        return mapper.mapEntityListToDtoList(list);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK - Criado com sucesso", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public EquipmentModel save(EquipmentModel dto) {
        log.info("Criado {}", dto);
        return equipmentModelRepository.save((dto));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - requisição bem sucedida e com retorno.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado no banco de dados.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public Optional<EquipmentModelDTO> findByIdDTO(UUID id) {
        Optional<EquipmentModel> optional = equipmentModelRepository.findById(id);
        if (optional.isPresent()) {
            var equipment = optional.get();
            return Optional.of(mapper.mapEntityToDto(equipment));
        }
        return Optional.empty();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - requisição bem sucedida e com retorno.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado no banco de dados.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public Optional<EquipmentModel> findById(UUID id) {
        Optional<EquipmentModel> optional = equipmentModelRepository.findById(id);
        if (optional.isPresent()) {
            var equipment = optional.get();
            return Optional.of(equipment);
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
    public void delete(EquipmentModel equipmentModel) {
        log.info("Deletado {}", equipmentModel);
        equipmentModelRepository.delete(equipmentModel);
    }
}
