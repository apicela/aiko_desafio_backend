package apirest.aiko.services;

import apirest.aiko.models.Equipment;
import apirest.aiko.repositories.EquipmentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquipmentService {
    final EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public List<Equipment> findAll() {
        return equipmentRepository.findAll();
    }

    @Transactional
    public Equipment save(Equipment equipment) {
        return equipmentRepository.save(equipment);
    }

    public Optional<Equipment> findById(UUID id) {
        return equipmentRepository.findById(id);
    }

    @Transactional
    public void delete(Equipment equipment) {
        equipmentRepository.delete(equipment);
    }
}
