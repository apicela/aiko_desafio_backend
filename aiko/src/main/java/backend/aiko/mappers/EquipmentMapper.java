package backend.aiko.mappers;

import backend.aiko.dtos.EquipmentDTO;
import backend.aiko.models.Equipment;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EquipmentMapper {
    final ModelMapper mapper = new ModelMapper();

    public Equipment mapDtoToEntity(EquipmentDTO equipmentDTO) {
        return mapper.map(equipmentDTO, Equipment.class);
    }

    public EquipmentDTO mapEntityToDto(Equipment equipment) {
        return mapper.map(equipment, EquipmentDTO.class);
    }

    public List<EquipmentDTO> mapEntityListToDtoList(List<Equipment> equipmentList) {
        return equipmentList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

}
