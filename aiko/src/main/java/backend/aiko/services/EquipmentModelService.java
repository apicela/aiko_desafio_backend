package backend.aiko.services;

import backend.aiko.mappers.EquipmentModelMapper;
import backend.aiko.models.EquipmentModel;
import backend.aiko.repositories.EquipmentModelRepository;
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

    public List<EquipmentModel> findAll() {
        return equipmentModelRepository.findAll();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK - Criado com sucesso", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public EquipmentModel save(EquipmentModel equipmentModel) {
        log.info("Criado {}", equipmentModel);
        return equipmentModelRepository.save((equipmentModel));
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
