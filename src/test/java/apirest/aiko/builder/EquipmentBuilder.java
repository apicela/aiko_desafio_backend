package apirest.aiko.builder;

import apirest.aiko.models.EquipmentModel;
import com.github.javafaker.Faker;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import apirest.aiko.dtos.EquipmentDTO;
import apirest.aiko.dtos.EquipmentModelDTO;
import apirest.aiko.models.Equipment;

import java.util.UUID;

@Component
@Log4j2
public class EquipmentBuilder {

        Faker faker = new Faker();

    public EquipmentModel createEquipmentModelDTO(){
        // criando um modelo de equip
        EquipmentModel model = new EquipmentModel().setName(faker.pokemon().name());
        log.info(model.toString());
        return model;
    }
        public EquipmentDTO createEquipmentDTO(){
            // criando um modelo de equip
            var equipmentModelDTO = createEquipmentModelDTO();
            var model = new EquipmentModel().;
            var equipment = new EquipmentDTO(new EquipmentDTO(model.getName(),model.getId()));
            log.info(equipment.toString());
            return equipment;
        }
}
