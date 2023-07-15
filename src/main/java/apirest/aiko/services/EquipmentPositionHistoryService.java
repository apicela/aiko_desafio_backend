package apirest.aiko.services;

import apirest.aiko.dtos.EquipmentPositionHistoryDTO;
import apirest.aiko.mappers.EquipmentPositionHistoryMapper;
import apirest.aiko.models.EquipmentPositionHistory;
import apirest.aiko.repositories.EquipmentPositionHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipmentPositionHistoryService {
    final EquipmentPositionHistoryRepository equipmentPositionHistoryRepository;
    final EquipmentPositionHistoryMapper mapper;

    public List<EquipmentPositionHistory> findLastPosition() {
        return equipmentPositionHistoryRepository.findLastPosition();
    }


    public List<EquipmentPositionHistoryDTO> findAll() {
        List<EquipmentPositionHistory> list = equipmentPositionHistoryRepository.findLastPosition();
        return mapper.mapEntityListToDtoList(list);
    }

    @Transactional
    public EquipmentPositionHistory save(EquipmentPositionHistoryDTO dto) {
        return equipmentPositionHistoryRepository.save(mapper.mapDtoToEntity(dto));
    }

    public Optional<EquipmentPositionHistoryDTO> findById(EquipmentPositionHistory.EquipmentPositionHistoryPK id) {
        Optional<EquipmentPositionHistory> optional = equipmentPositionHistoryRepository.findById(id);
        if (optional.isPresent()) {
            EquipmentPositionHistory equipment = optional.get();
            return Optional.of(mapper.mapEntityToDto(equipment));
        }
        return Optional.empty();
    }

    @Transactional
    public void delete(EquipmentPositionHistoryDTO dto) {
        equipmentPositionHistoryRepository.delete(mapper.mapDtoToEntity(dto));
    }
}
