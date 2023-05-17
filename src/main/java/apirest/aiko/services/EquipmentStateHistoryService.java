package apirest.aiko.services;

import apirest.aiko.models.EquipmentStateHistory;
import apirest.aiko.repositories.EquipmentStateHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EquipmentStateHistoryService {
    final EquipmentStateHistoryRepository equipmentStateHistoryRepository;

    public EquipmentStateHistoryService(EquipmentStateHistoryRepository equipmentStateHistoryRepository) {
        this.equipmentStateHistoryRepository = equipmentStateHistoryRepository;
    }

    public List<EquipmentStateHistory> findAll() {
        return equipmentStateHistoryRepository.findAll();
    }

    public List<EquipmentStateHistory> findLastState() {
        return equipmentStateHistoryRepository.findLastState();
    }

    public String findState(UUID equipment_id) {
        return equipmentStateHistoryRepository.findState(equipment_id);
    }

    @Transactional
    public EquipmentStateHistory save(EquipmentStateHistory equipmentStateHistory) {
        return equipmentStateHistoryRepository.save(equipmentStateHistory);
    }

    public Optional<EquipmentStateHistory> findById(EquipmentStateHistory.EquipmentSH_ID id) {
        return equipmentStateHistoryRepository.findById(id);
    }

    @Transactional
    public void delete(EquipmentStateHistory equipmentStateHistory) {
        equipmentStateHistoryRepository.delete(equipmentStateHistory);
    }
}
