package apirest.aiko.repositories;

import apirest.aiko.dtos.EquipmentDTO;
import apirest.aiko.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, UUID> {

    default Equipment saveEquipmentFromDTO(EquipmentDTO equipmentDTO) {
        Equipment equipment = new Equipment(equipmentDTO);
        return save(equipment);
    }
}
