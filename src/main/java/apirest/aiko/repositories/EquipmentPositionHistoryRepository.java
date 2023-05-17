package apirest.aiko.repositories;

import apirest.aiko.models.EquipmentPositionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentPositionHistoryRepository extends JpaRepository<EquipmentPositionHistory, EquipmentPositionHistory.EquipmentPositionHistoryPK> {
    @Query(value = "SELECT eh.equipment_id, eh.date, eh.lat, eh.lon " +
            "FROM operation.equipment_position_history " +
            "eh INNER JOIN (SELECT equipment_id, MAX(date) as max_date" +
            " FROM operation.equipment_position_history " +
            "GROUP BY equipment_id) " +
            "eh2 ON eh.equipment_id = eh2.equipment_id AND eh.date = eh2.max_date",
            nativeQuery = true)
    List<EquipmentPositionHistory> findLastPosition();


}

