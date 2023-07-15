package apirest.aiko.services;

import apirest.aiko.dtos.EquipmentStateHistoryDTO;
import apirest.aiko.mappers.EquipmentStateHistoryMapper;
import apirest.aiko.models.EquipmentStateHistory;
import apirest.aiko.repositories.EquipmentStateHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipmentStateHistoryService {
    final EquipmentStateHistoryRepository equipmentStateHistoryRepository;
    final EquipmentStateHistoryMapper mapper;

    public List<EquipmentStateHistory> findAll() {
        return equipmentStateHistoryRepository.findAll();
    }

    public List<EquipmentStateHistoryDTO> findLastState() {
        List<EquipmentStateHistory> list = equipmentStateHistoryRepository.findLastState();
        return mapper.mapEntityListToDtoList(list);
    }

    public String findState(UUID equipment_id) {
        return equipmentStateHistoryRepository.findState(equipment_id);
    }

    @Transactional
    public EquipmentStateHistory save(EquipmentStateHistoryDTO dto) {
        return equipmentStateHistoryRepository.save(mapper.mapDtoToEntity(dto));
    }

    public Optional<EquipmentStateHistoryDTO> findById(EquipmentStateHistory.EquipmentSH_ID id) {
        Optional<EquipmentStateHistory> optional = equipmentStateHistoryRepository.findById(id);
        if (optional.isPresent()) {
            var equipment = optional.get();
            return Optional.of(mapper.mapEntityToDto(equipment));
        }
        return Optional.empty();
    }

    @Transactional
    public void delete(EquipmentStateHistoryDTO equipmentStateHistory) {
        equipmentStateHistoryRepository.delete(mapper.mapDtoToEntity(equipmentStateHistory));
    }
}
