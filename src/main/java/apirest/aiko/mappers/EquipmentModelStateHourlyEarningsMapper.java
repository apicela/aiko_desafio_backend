package apirest.aiko.mappers;

import apirest.aiko.dtos.EquipmentModelDTO;
import apirest.aiko.dtos.EquipmentModelStateHourlyEarningsDTO;
import apirest.aiko.models.EquipmentModel;
import apirest.aiko.models.EquipmentModelStateHourlyEarnings;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EquipmentModelStateHourlyEarningsMapper {
    final ModelMapper mapper = new ModelMapper();

    public EquipmentModelStateHourlyEarnings mapDtoToEntity(EquipmentModelStateHourlyEarningsDTO dto) {
        return mapper.map(dto, EquipmentModelStateHourlyEarnings.class);
    }
}
