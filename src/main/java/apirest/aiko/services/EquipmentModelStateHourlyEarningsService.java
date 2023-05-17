package apirest.aiko.services;

import apirest.aiko.models.EquipmentModelStateHourlyEarnings;
import apirest.aiko.repositories.EquipmentModelStateHourlyEarningsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentModelStateHourlyEarningsService {
    final EquipmentModelStateHourlyEarningsRepository equipmentModelStateHourlyEarningsRepository;

    public EquipmentModelStateHourlyEarningsService(EquipmentModelStateHourlyEarningsRepository equipmentModelStateHourlyEarningsRepository) {
        this.equipmentModelStateHourlyEarningsRepository = equipmentModelStateHourlyEarningsRepository;
    }

    public List<EquipmentModelStateHourlyEarnings> findAll() {
        return equipmentModelStateHourlyEarningsRepository.findAll();
    }

    @Transactional
    public EquipmentModelStateHourlyEarnings save(EquipmentModelStateHourlyEarnings equipmentModelStateHourlyEarnings) {
        return equipmentModelStateHourlyEarningsRepository.save(equipmentModelStateHourlyEarnings);
    }

    public Optional<EquipmentModelStateHourlyEarnings> findById(EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID id) {
        return equipmentModelStateHourlyEarningsRepository.findById(id);
    }

    @Transactional
    public void delete(EquipmentModelStateHourlyEarnings equipmentModelStateHourlyEarnings) {
        equipmentModelStateHourlyEarningsRepository.delete(equipmentModelStateHourlyEarnings);
    }
}