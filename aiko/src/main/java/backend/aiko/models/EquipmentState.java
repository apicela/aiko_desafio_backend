package backend.aiko.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "equipment_state", schema = "operation")
public class EquipmentState implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "equipmentState", cascade = CascadeType.ALL)
    private List<EquipmentModelStateHourlyEarnings> equipmentModelStateHourlyEarnings;
    @OneToMany(mappedBy = "equipmentState", cascade = CascadeType.ALL)
    private List<EquipmentStateHistory> equipmentStateHistory;

    private String color;
    private String name;

    @JsonIgnore
    public List<EquipmentModelStateHourlyEarnings> getEquipmentModelStateHourlyEarnings() {
        return equipmentModelStateHourlyEarnings;
    }


    @JsonIgnore
    public List<EquipmentStateHistory> getEquipmentStateHistory() {
        return equipmentStateHistory;
    }

    @Override
    public String toString() {
        return "Equipment State" +
                "\nid: " + id +
                "\ncolor: " + color +
                "\nname: " + name;
    }
}
