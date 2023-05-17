package apirest.aiko.models;

import apirest.aiko.dtos.EquipmentStateDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
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
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private String name;

    public EquipmentState() {

    }

    public EquipmentState(EquipmentStateDTO equipmentStateDTO) {
        this.name = equipmentStateDTO.getName();
        this.color = equipmentStateDTO.getColor();
    }

    @JsonIgnore
    public List<EquipmentModelStateHourlyEarnings> getEquipmentModelStateHourlyEarnings() {
        return equipmentModelStateHourlyEarnings;
    }

    public void setEquipmentModelStateHourlyEarnings(List<EquipmentModelStateHourlyEarnings> equipmentModelStateHourlyEarnings) {
        this.equipmentModelStateHourlyEarnings = equipmentModelStateHourlyEarnings;
    }

    @JsonIgnore
    public List<EquipmentStateHistory> getEquipmentStateHistory() {
        return equipmentStateHistory;
    }

    public void setEquipmentStateHistory(List<EquipmentStateHistory> equipmentStateHistory) {
        this.equipmentStateHistory = equipmentStateHistory;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Equipment State" +
                "\nid: " + id +
                "\ncolor: " + color +
                "\nname: " + name;
    }
}
