package backend.aiko.mappers;

import backend.aiko.dtos.EquipmentStateDTO;
import backend.aiko.models.EquipmentState;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EquipmentStateMapper {

    private final ModelMapper mapper = new ModelMapper();

    public EquipmentState mapDtoToEntity(EquipmentStateDTO dto) {
        return mapper.map(dto, EquipmentState.class);
    }

    public EquipmentStateDTO mapEntityToDto(EquipmentState equipment) {
        return mapper.map(equipment, EquipmentStateDTO.class);
    }

    public List<EquipmentStateDTO> mapEntityListToDtoList(List<EquipmentState> equipmentList) {
        return equipmentList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
