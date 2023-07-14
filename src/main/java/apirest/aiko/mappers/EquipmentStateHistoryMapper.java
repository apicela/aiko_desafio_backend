package apirest.aiko.mappers;

import apirest.aiko.dtos.EquipmentPositionHistoryDTO;
import apirest.aiko.dtos.EquipmentStateHistoryDTO;
import apirest.aiko.models.EquipmentPositionHistory;
import apirest.aiko.models.EquipmentStateHistory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentStateHistoryMapper {
    final ModelMapper mapper = new ModelMapper();

    public EquipmentStateHistory mapDtoToEntity(EquipmentStateHistoryDTO dto) {
        return mapper.map(dto, EquipmentStateHistory.class);
    }
}
