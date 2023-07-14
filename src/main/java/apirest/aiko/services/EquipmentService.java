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

//    public EquipmentService(EquipmentRepository equipmentRepository) {
//        this.equipmentRepository = equipmentRepository;
//    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    @Transactional
    public Equipment save(EquipmentDTO equipment) {
        return equipmentRepository.save(mapper.mapDtoToEntity(equipment));
    }

    public Optional<Equipment> findById(UUID id) {

        return equipmentRepository.findById(id);
    }

    @Transactional
    public void delete(Equipment equipment) {
        equipmentRepository.delete(equipment);
    }
}
