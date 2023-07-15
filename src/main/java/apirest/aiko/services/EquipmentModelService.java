package apirest.aiko.services;

import apirest.aiko.dtos.EquipmentModelDTO;
import apirest.aiko.mappers.EquipmentModelMapper;
import apirest.aiko.models.EquipmentModel;
import apirest.aiko.repositories.EquipmentModelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class EquipmentModelService {
    final EquipmentModelRepository equipmentModelRepository;
    final EquipmentModelMapper mapper;

    public List<EquipmentModelDTO> findAll() {
        List<EquipmentModel> list = equipmentModelRepository.findAll();
        return mapper.mapEntityListToDtoList(list);
    }

    public EquipmentModel save(EquipmentModelDTO dto) {
        return equipmentModelRepository.save(mapper.mapDtoToEntity(dto));
    }

    public Optional<EquipmentModelDTO> findById(UUID id) {
        Optional<EquipmentModel> optional = equipmentModelRepository.findById(id);
        if (optional.isPresent()) {
            var equipment = optional.get();
            return Optional.of(mapper.mapEntityToDto(equipment));
        }
        return Optional.empty();
    }

    @Transactional
    public void delete(EquipmentModelDTO equipmentModel) {
        equipmentModelRepository.delete(mapper.mapDtoToEntity(equipmentModel));
    }
}
