package apirest.aiko.services;

import apirest.aiko.models.EquipmentState;
import apirest.aiko.repositories.EquipmentStateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquipmentStateService {
    final EquipmentStateRepository equipmentStateRepository;

    public EquipmentStateService(EquipmentStateRepository equipmentStateRepository) {
        this.equipmentStateRepository = equipmentStateRepository;
    }


    public List<EquipmentState> findAll() {
        return equipmentStateRepository.findAll();
    }

    @Transactional
    public EquipmentState save(EquipmentState equipmentState) {
        return equipmentStateRepository.save(equipmentState);
    }

    public Optional<EquipmentState> findById(UUID id) {
        return equipmentStateRepository.findById(id);
    }

    @Transactional
    public void delete(EquipmentState equipmentState) {
        equipmentStateRepository.delete(equipmentState);
    }
}
