package apirest.aiko.mappers;

import apirest.aiko.dtos.EquipmentModelStateHourlyEarningsDTO;
import apirest.aiko.models.EquipmentModelStateHourlyEarnings;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EquipmentModelStateHourlyEarningsMapper {
    final ModelMapper mapper = new ModelMapper();

    public EquipmentModelStateHourlyEarnings mapDtoToEntity(EquipmentModelStateHourlyEarningsDTO dto) {
        return mapper.map(dto, EquipmentModelStateHourlyEarnings.class);
    }


    public EquipmentModelStateHourlyEarningsDTO mapEntityToDto(EquipmentModelStateHourlyEarnings equipment) {
        return mapper.map(equipment, EquipmentModelStateHourlyEarningsDTO.class);
    }

    public List<EquipmentModelStateHourlyEarningsDTO> mapEntityListToDtoList(List<EquipmentModelStateHourlyEarnings> equipmentList) {
        return equipmentList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }
}
