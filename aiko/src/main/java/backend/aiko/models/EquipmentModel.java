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
@Table(name = "equipment_model", schema = "operation")
public class EquipmentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    private String name;


    @OneToMany(mappedBy = "equipmentModel", cascade = CascadeType.ALL)
    private List<Equipment> equipments;

    @OneToMany(mappedBy = "equipmentModel", cascade = CascadeType.ALL)
    private List<EquipmentModelStateHourlyEarnings> equipmentModelStateHourlyEarnings;


    //Json ignore on @Data

    @JsonIgnore
    public List<EquipmentModelStateHourlyEarnings> getEquipmentModelStateHourlyEarnings() {
        return equipmentModelStateHourlyEarnings;
    }

    @JsonIgnore
    public List<Equipment> getEquipments() {
        return equipments;
    }

    @Override
    public String toString() {
        return "Equipment Model:\n" +
                "Id:" + id +
                "\nName:" + name;
    }
}
