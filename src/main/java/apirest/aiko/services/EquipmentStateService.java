package apirest.aiko.services;

import apirest.aiko.dtos.EquipmentStateDTO;
import apirest.aiko.mappers.EquipmentStateMapper;
import apirest.aiko.models.EquipmentState;
import apirest.aiko.repositories.EquipmentStateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentStateService {
    final EquipmentStateRepository equipmentStateRepository;
    final EquipmentStateMapper mapper;


    public List<EquipmentStateDTO> findAll() {
        List<EquipmentState> list = equipmentStateRepository.findAll();
        return mapper.mapEntityListToDtoList(list);
    }

    @Transactional
    public EquipmentState save(EquipmentStateDTO dto) {
        return equipmentStateRepository.save(mapper.mapDtoToEntity(dto));
    }

    public Optional<EquipmentStateDTO> findById(UUID id) {
        Optional<EquipmentState> optional = equipmentStateRepository.findById(id);
        if (optional.isPresent()) {
            var equipment = optional.get();
            return Optional.of(mapper.mapEntityToDto(equipment));
        }
        return Optional.empty();
    }

    @Transactional
    public void delete(EquipmentStateDTO equipmentState) {
        equipmentStateRepository.delete(mapper.mapDtoToEntity(equipmentState));
    }
}
