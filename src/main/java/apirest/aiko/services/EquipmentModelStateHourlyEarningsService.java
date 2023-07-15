package apirest.aiko.services;

import apirest.aiko.dtos.EquipmentModelStateHourlyEarningsDTO;
import apirest.aiko.mappers.EquipmentModelStateHourlyEarningsMapper;
import apirest.aiko.models.EquipmentModelStateHourlyEarnings;
import apirest.aiko.repositories.EquipmentModelStateHourlyEarningsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentModelStateHourlyEarningsService {
    final EquipmentModelStateHourlyEarningsRepository equipmentModelStateHourlyEarningsRepository;
    final EquipmentModelStateHourlyEarningsMapper mapper;

    public List<EquipmentModelStateHourlyEarningsDTO> findAll() {
        List<EquipmentModelStateHourlyEarnings> list = equipmentModelStateHourlyEarningsRepository.findAll();
        return mapper.mapEntityListToDtoList(list);
    }

    @Transactional
    public EquipmentModelStateHourlyEarnings save(EquipmentModelStateHourlyEarningsDTO dto) {
        return equipmentModelStateHourlyEarningsRepository.save(mapper.mapDtoToEntity(dto));
    }

    public Optional<EquipmentModelStateHourlyEarningsDTO> findById(EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID id) {
        Optional<EquipmentModelStateHourlyEarnings> optional = equipmentModelStateHourlyEarningsRepository.findById(id);
        if (optional.isPresent()) {
            var equipment = optional.get();
            return Optional.of(mapper.mapEntityToDto(equipment));
        }
        return Optional.empty();
    }

    @Transactional
    public void delete(EquipmentModelStateHourlyEarningsDTO dto) {
        equipmentModelStateHourlyEarningsRepository.delete(mapper.mapDtoToEntity(dto));
    }
}