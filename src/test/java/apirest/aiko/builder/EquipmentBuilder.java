package apirest.aiko.builder;

import apirest.aiko.dtos.EquipmentStateDTO;
import apirest.aiko.models.EquipmentModel;
import apirest.aiko.models.EquipmentState;
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

        // CRIANDO ENTIDADES
        public EquipmentModel createEquipmentModel(){
            // criando um modelo de equip
            EquipmentModel model = new EquipmentModel();
            model.setName(faker.pokemon().name());
            model.setId(UUID.randomUUID());
//            log.info(model.toString());
            return model;
        }

            public Equipment createEquipment(){
                // criando um modelo de equip
                var model = createEquipmentModel();
                var equipment = new Equipment();
                equipment.setId(UUID.randomUUID());
                equipment.setName(faker.funnyName().name());
                equipment.setEquipment_model_id(model.getId());
                equipment.setEquipmentModel(model); // Adicione esta linha para configurar o equipmentModel
//                log.info(equipment.toString());
                return equipment;
            }



            public EquipmentState createEquipmentState(){
                var equipmentState = new EquipmentState();
                equipmentState.setId(UUID.randomUUID());
                equipmentState.setColor(faker.color().name());
                equipmentState.setName(faker.relationships().any());
//                log.info(equipmentState.toString());
                return equipmentState;
            }

            // CRIANDO DTO's PARA TESTE

        public EquipmentModelDTO createEquipmentModelDTO(){
            // criando um modelo de equip
            EquipmentModelDTO model = new EquipmentModelDTO();
            model.setName(faker.pokemon().name());
//            log.info(model.toString());
            return model;
        }

        public EquipmentDTO createEquipmentDTO(){
            // criando um modelo de equip
            EquipmentModel model = createEquipmentModel();
            var equipment = new EquipmentDTO();
            equipment.setName(faker.funnyName().name());
            equipment.setEquipment_model_id(model.getId());
//            log.info(equipment.toString());
            return equipment;
        }

        public EquipmentStateDTO createEquipmentStateDTO(){
            var equipmentState = new EquipmentStateDTO();
            equipmentState.setColor(faker.color().name());
            equipmentState.setName(faker.relationships().any());
//            log.info(equipmentState.toString());
            return equipmentState;
        }

}
