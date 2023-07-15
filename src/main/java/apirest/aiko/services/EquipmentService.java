package apirest.aiko.services;

import apirest.aiko.dtos.EquipmentDTO;
import apirest.aiko.mappers.EquipmentMapper;
import apirest.aiko.models.Equipment;
import apirest.aiko.repositories.EquipmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    final EquipmentRepository equipmentRepository;
    final EquipmentMapper mapper;

    public List<EquipmentDTO> findAll() {
        List<Equipment> list = equipmentRepository.findAll();
        return mapper.mapEntityListToDtoList(list);
    }

    @Transactional
    public Equipment save(EquipmentDTO equipment) {
        return equipmentRepository.save(mapper.mapDtoToEntity(equipment));
    }

    public Optional<EquipmentDTO> findById(UUID id) {
        Optional<Equipment> equipmentOptional = equipmentRepository.findById(id);
        if (equipmentOptional.isPresent()) {
            var equipment = equipmentOptional.get();
            return Optional.of(mapper.mapEntityToDto(equipment));
        }
        return Optional.empty();
    }

    @Transactional
    public void delete(EquipmentDTO equipment) {
        equipmentRepository.delete(mapper.mapDtoToEntity(equipment));
    }
}
