package apirest.aiko.services;

import apirest.aiko.models.EquipmentModel;
import apirest.aiko.repositories.EquipmentModelRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class EquipmentModelService {
    final EquipmentModelRepository equipmentModelRepository;

    public EquipmentModelService(EquipmentModelRepository equipmentModelRepository) {
        this.equipmentModelRepository = equipmentModelRepository;
    }


    public List<EquipmentModel> findAll() {
        return equipmentModelRepository.findAll();
    }

    public EquipmentModel save(EquipmentModel equipmentModel) {
        return equipmentModelRepository.save(equipmentModel);
    }

    public Optional<EquipmentModel> findById(UUID id) {
        return equipmentModelRepository.findById(id);
    }

    @Transactional
    public void delete(EquipmentModel equipmentModel) {
        equipmentModelRepository.delete(equipmentModel);
    }
}
