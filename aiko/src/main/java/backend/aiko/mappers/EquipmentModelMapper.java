package backend.aiko.mappers;

import backend.aiko.dtos.EquipmentModelDTO;
import backend.aiko.models.EquipmentModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EquipmentModelMapper {
    final ModelMapper mapper = new ModelMapper();

    public EquipmentModel mapDtoToEntity(EquipmentModelDTO equipmentModelDTO) {
        return mapper.map(equipmentModelDTO, EquipmentModel.class);
    }

    public EquipmentModelDTO mapEntityToDto(EquipmentModel equipment) {
        return mapper.map(equipment, EquipmentModelDTO.class);
    }

    public List<EquipmentModelDTO> mapEntityListToDtoList(List<EquipmentModel> equipmentList) {
        return equipmentList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
