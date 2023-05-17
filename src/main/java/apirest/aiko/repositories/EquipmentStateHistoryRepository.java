package apirest.aiko.repositories;

import apirest.aiko.models.EquipmentStateHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EquipmentStateHistoryRepository extends JpaRepository<EquipmentStateHistory, EquipmentStateHistory.EquipmentSH_ID> {
    @Query(value = "SELECT eh.equipment_id, eh.date,eh.equipment_state_id " +
            "FROM operation.equipment_state_history " +
            "eh INNER JOIN (SELECT equipment_id, MAX(date) as max_date" +
            " FROM operation.equipment_state_history " +
            "GROUP BY equipment_id) " +
            "eh2 ON eh.equipment_id = eh2.equipment_id AND eh.date = eh2.max_date",
            nativeQuery = true)
    List<EquipmentStateHistory> findLastState();

    @Query(value = "SELECT equipment_state_id FROM operation.equipment_state_history WHERE equipment_id = :equipmentId ORDER BY date DESC LIMIT 1", nativeQuery = true)
    String findState(@Param("equipmentId") UUID equipment);


}
