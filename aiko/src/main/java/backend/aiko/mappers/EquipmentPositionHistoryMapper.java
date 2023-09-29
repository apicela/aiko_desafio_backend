package backend.aiko.mappers;

import backend.aiko.dtos.EquipmentPositionHistoryDTO;
import backend.aiko.models.EquipmentPositionHistory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EquipmentPositionHistoryMapper {
    final ModelMapper mapper = new ModelMapper();

    public EquipmentPositionHistory mapDtoToEntity(EquipmentPositionHistoryDTO dto) {
        return mapper.map(dto, EquipmentPositionHistory.class);
    }

    public EquipmentPositionHistoryDTO mapEntityToDto(EquipmentPositionHistory equipment) {
        return mapper.map(equipment, EquipmentPositionHistoryDTO.class);
    }

    public List<EquipmentPositionHistoryDTO> mapEntityListToDtoList(List<EquipmentPositionHistory> equipmentList) {
        return equipmentList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
