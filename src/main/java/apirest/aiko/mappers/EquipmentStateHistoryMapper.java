package apirest.aiko.mappers;

import apirest.aiko.dtos.EquipmentStateHistoryDTO;
import apirest.aiko.models.EquipmentStateHistory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EquipmentStateHistoryMapper {
    final ModelMapper mapper = new ModelMapper();

    public EquipmentStateHistory mapDtoToEntity(EquipmentStateHistoryDTO dto) {
        return mapper.map(dto, EquipmentStateHistory.class);
    }

    public EquipmentStateHistoryDTO mapEntityToDto(EquipmentStateHistory equipment) {
        return mapper.map(equipment, EquipmentStateHistoryDTO.class);
    }

    public List<EquipmentStateHistoryDTO> mapEntityListToDtoList(List<EquipmentStateHistory> equipmentList) {
        return equipmentList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
