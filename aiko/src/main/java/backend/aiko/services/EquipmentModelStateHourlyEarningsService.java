package backend.aiko.services;

import backend.aiko.models.EquipmentModelStateHourlyEarnings;
import backend.aiko.repositories.EquipmentModelStateHourlyEarningsRepository;
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
public class EquipmentModelStateHourlyEarningsService {
    final EquipmentModelStateHourlyEarningsRepository equipmentModelStateHourlyEarningsRepository;

    public List<EquipmentModelStateHourlyEarnings> findAll() {
        return equipmentModelStateHourlyEarningsRepository.findAll();
    }

    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK - Criado com sucesso", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public EquipmentModelStateHourlyEarnings save(EquipmentModelStateHourlyEarnings equipmentModelStateHourlyEarnings) {
        return equipmentModelStateHourlyEarningsRepository.save(equipmentModelStateHourlyEarnings);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK - requisição bem sucedida e com retorno.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", description = "Bad Request - requisição mal feita.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Not Found - recurso não encontrado no banco de dados.", content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - situação inesperada no servidor.", content = {@Content(mediaType = "application/json")})
    })
    public Optional<EquipmentModelStateHourlyEarnings> findById(EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID id) {
        Optional<EquipmentModelStateHourlyEarnings> optional = equipmentModelStateHourlyEarningsRepository.findById(id);
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
    public void delete(EquipmentModelStateHourlyEarnings equip) {
        equipmentModelStateHourlyEarningsRepository.delete(equip);
    }
}