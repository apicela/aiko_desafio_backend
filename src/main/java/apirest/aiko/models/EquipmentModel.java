package apirest.aiko.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "equipment_model", schema = "operation")
public class EquipmentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @JsonProperty("equipment_model_id")
    private UUID id;

    @Column(nullable = false)
    private String name;


    @OneToMany(mappedBy = "equipmentModel", cascade = CascadeType.ALL)
    private List<Equipment> equipments;

    @OneToMany(mappedBy = "equipmentModel", cascade = CascadeType.ALL)
    private List<EquipmentModelStateHourlyEarnings> equipmentModelStateHourlyEarnings;


    //Getters and Setters


    @JsonIgnore
    public List<EquipmentModelStateHourlyEarnings> getEquipmentModelStateHourlyEarnings() {
        return equipmentModelStateHourlyEarnings;
    }

    public void setEquipmentModelStateHourlyEarnings(List<EquipmentModelStateHourlyEarnings> equipmentModelStateHourlyEarnings) {
        this.equipmentModelStateHourlyEarnings = equipmentModelStateHourlyEarnings;
    }


    @Override
    public String toString() {
        return "Equipment Model:\n" +
                "Id:" + id +
                "\nName:" + name;
    }

    @JsonIgnore
    public List<Equipment> getEquipments() {
        return equipments;
    }

    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
