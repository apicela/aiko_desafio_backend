package apirest.aiko.models;

import apirest.aiko.dtos.EquipmentDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "equipment", schema = "operation")
@Data
@NoArgsConstructor
public class Equipment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(insertable = false, updatable = false)
    private UUID equipment_model_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_model_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_equipment_model"),
            updatable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private EquipmentModel equipmentModel;
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private List<EquipmentPositionHistory> equipmentPositionHistories;
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL)
    private List<EquipmentStateHistory> equipmentStateHistories;

    public Equipment(EquipmentDTO equipmentDTO) {
        equipmentModel = new EquipmentModel();
        equipmentModel.setId(equipmentDTO.getEquipment_model_id());
        this.name = equipmentDTO.getName();
        this.equipmentModel = equipmentModel;
    }


    //Getters and Setters

    @JsonIgnore
    public EquipmentModel getEquipmentModel() {
        return equipmentModel;
    }

    @JsonIgnore
    public List<EquipmentPositionHistory> getEquipmentPositionHistories() {
        return equipmentPositionHistories;
    }

    @JsonIgnore
    public List<EquipmentStateHistory> getEquipmentStateHistories() {
        return equipmentStateHistories;
    }
    @Override
    public String toString() {
        return "Equipment:\n" +
                "Id:" + id +
                "\nName:" + name
                + "\nequipment_model_id:" + equipmentModel.getId()
                ;
    }

}
