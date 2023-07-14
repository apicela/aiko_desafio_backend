package apirest.aiko.mappers;

import apirest.aiko.dtos.EquipmentModelStateHourlyEarningsDTO;
import apirest.aiko.dtos.EquipmentPositionHistoryDTO;
import apirest.aiko.models.EquipmentModelStateHourlyEarnings;
import apirest.aiko.models.EquipmentPositionHistory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentPositionHistoryMapper {
    final ModelMapper mapper = new ModelMapper();

    public EquipmentPositionHistory mapDtoToEntity(EquipmentPositionHistoryDTO dto) {
        return mapper.map(dto, EquipmentPositionHistory.class);
    }
}
