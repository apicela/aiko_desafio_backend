package apirest.aiko.mappers;

import apirest.aiko.dtos.EquipmentDTO;
import apirest.aiko.models.Equipment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
@RequiredArgsConstructor
public class EquipmentMapper {
    final ModelMapper mapper = new ModelMapper();

    public Equipment mapDtoToEntity(EquipmentDTO equipmentDTO) {
        return mapper.map(equipmentDTO, Equipment.class);
    }
}
