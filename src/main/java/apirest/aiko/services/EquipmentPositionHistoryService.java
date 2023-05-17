package apirest.aiko.services;

import apirest.aiko.models.EquipmentPositionHistory;
import apirest.aiko.repositories.EquipmentPositionHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentPositionHistoryService {
    final EquipmentPositionHistoryRepository equipmentPositionHistoryRepository;

    public EquipmentPositionHistoryService(EquipmentPositionHistoryRepository equipmentPositionHistoryRepository) {
        this.equipmentPositionHistoryRepository = equipmentPositionHistoryRepository;
    }

    public List<EquipmentPositionHistory> findLastPosition() {
        return equipmentPositionHistoryRepository.findLastPosition();
    }


    public List<EquipmentPositionHistory> findAll() {
        return equipmentPositionHistoryRepository.findAll();
    }

    @Transactional
    public EquipmentPositionHistory save(EquipmentPositionHistory equipmentPositionHistory) {
        return equipmentPositionHistoryRepository.save(equipmentPositionHistory);
    }

    public Optional<EquipmentPositionHistory> findById(EquipmentPositionHistory.EquipmentPositionHistoryPK id) {
        return equipmentPositionHistoryRepository.findById(id);
    }

    @Transactional
    public void delete(EquipmentPositionHistory id) {
        equipmentPositionHistoryRepository.delete(id);
    }
}
