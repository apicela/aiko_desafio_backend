package backend.aiko.builder;

import backend.aiko.models.*;
import backend.aiko.repositories.EquipmentModelRepository;
import backend.aiko.repositories.EquipmentRepository;
import backend.aiko.repositories.EquipmentStateRepository;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Log4j2
public class EquipmentBuilder {

    @Autowired
    EquipmentModelRepository equipmentModelRepository;
    @Autowired
    EquipmentRepository equipmentRepository;
    @Autowired
    EquipmentStateRepository equipmentStateRepository;
    Faker faker = new Faker();

    @PostConstruct
    public void initialize() {
        EquipmentModel model = new EquipmentModel();
        model.setName("EQUIPMENT MODEL TESTE");
        equipmentModelRepository.save(model);
        var modelCREATED = equipmentModelRepository.findAll().get(0);

        Equipment equipment = new Equipment();
        equipment.setEquipment_model_id(modelCREATED.getId());
        equipment.setEquipmentModel(modelCREATED);
        equipment.setName("EQUIPMENT NAME TESTE");
        equipmentRepository.save(equipment);

        EquipmentState state = new EquipmentState();
        state.setName("STATE NAME TESTE");
        state.setColor(faker.color().name());
        equipmentStateRepository.save(state);
    }


    // CRIANDO ENTIDADES
    public EquipmentModel createEquipmentModel() {
        // criando um modelo de equip
        EquipmentModel model = new EquipmentModel();
        model.setName(faker.pokemon().name());
        model.setId(UUID.randomUUID());
        return model;
    }

    public Equipment createEquipment() {
        var saved = equipmentModelRepository.findAll().get(0);
        var equipment = new Equipment();
        equipment.setName(faker.funnyName().name());
        equipment.setEquipment_model_id(saved.getId());
        equipment.setId(UUID.randomUUID());
        equipment.setEquipmentModel(saved); // Adicione esta linha para configurar o equipmentModel
        return equipment;
    }


    public EquipmentState createEquipmentState() {
        var equipmentState = new EquipmentState();
        equipmentState.setId(UUID.randomUUID());
        equipmentState.setColor(faker.color().name());
        equipmentState.setName(faker.relationships().any());
        return equipmentState;
    }

    public EquipmentModelStateHourlyEarnings createEquipmentMSHE() {
        var model = equipmentModelRepository.findAll().get(0);
        var state = equipmentStateRepository.findAll().get(0);

// Persist the equipmentModel and equipmentState objects
        equipmentModelRepository.save(model);
        equipmentStateRepository.save(state);

// Create the EquipmentModelStateHourlyEarnings object
        var equipmentMSHE = new EquipmentModelStateHourlyEarnings();
        var id = new EquipmentModelStateHourlyEarnings.EquipmentMSHE_ID();
        id.setEquipment_model_id(model.getId());
        id.setEquipment_state_id(state.getId());
        equipmentMSHE.setEquipmentMSHE_id(id);

// Assign the ids of the equipmentModel and equipmentState objects to the equipmentMSHE_id property
        equipmentMSHE.setEquipment_model_id(model.getId());
        equipmentMSHE.setEquipment_state_id(state.getId());
        equipmentMSHE.setEquipmentModel(model);
        equipmentMSHE.setEquipmentState(state);

        return equipmentMSHE;
    }

    public EquipmentStateHistory createEquipmentStateHistory() {
        var equipment = equipmentRepository.findAll().get(0);
        var state = equipmentStateRepository.findAll().get(0);
        var equipmentStateHistory = new EquipmentStateHistory();
        var id = new EquipmentStateHistory.EquipmentSH_ID(equipment.getId(), LocalDateTime.of(2001, 12, 25, 17, 14, 22), state.getId());
        equipmentStateHistory.setDate(LocalDateTime.of(2001, 12, 25, 17, 14, 22));
        equipmentStateHistory.setEquipmentSH_id(id);
        equipmentStateHistory.setEquipment(equipment);
        equipmentStateHistory.setEquipmentState(state);
        equipmentStateHistory.setEquipment_state_id(state.getId());
        equipmentStateHistory.setEquipment_id(equipment.getId());
        return equipmentStateHistory;
    }

    public EquipmentPositionHistory createEquipmentPositionHistory() {
        var equipment = equipmentRepository.findAll().get(0);
        var equipmentPositionHistory = new EquipmentPositionHistory();
        var compositeKey = new EquipmentPositionHistory.EquipmentPositionHistoryPK(equipmentPositionHistory.getEquipment_id(), LocalDateTime.of(2001, 12, 25, 17, 14, 22));
        equipmentPositionHistory.setEquipment_id(equipment.getId());
        equipmentPositionHistory.setEquipment(equipment);
        equipmentPositionHistory.setEquipmentPositionHistoryPK(compositeKey);
        equipmentPositionHistory.setLon(faker.number().numberBetween(-50, 50));
        equipmentPositionHistory.setLat(faker.number().numberBetween(-50, 50));
        equipmentPositionHistory.setDate(LocalDateTime.of(2001, 12, 25, 17, 14, 22));
        return equipmentPositionHistory;
    }
}
