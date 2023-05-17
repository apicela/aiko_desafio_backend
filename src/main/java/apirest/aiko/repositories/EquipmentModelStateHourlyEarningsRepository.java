package apirest.aiko.repositories;

import apirest.aiko.models.EquipmentModelStateHourlyEarnings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentModelStateHourlyEarningsRepository extends JpaRepository<EquipmentModelStateHourlyEarnings, EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID> {
}
