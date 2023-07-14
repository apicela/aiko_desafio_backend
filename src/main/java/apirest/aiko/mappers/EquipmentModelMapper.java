package apirest.aiko.mappers;

import apirest.aiko.dtos.EquipmentModelDTO;

import apirest.aiko.models.EquipmentModel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentModelMapper {
    final ModelMapper mapper = new ModelMapper();

    public EquipmentModel mapDtoToEntity(EquipmentModelDTO equipmentModelDTO) {
        return mapper.map(equipmentModelDTO, EquipmentModel.class);
    }
}
