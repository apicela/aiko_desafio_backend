package apirest.aiko.testes.unitarios;

import apirest.aiko.builder.EquipmentBuilder;
import apirest.aiko.dtos.EquipmentDTO;
import apirest.aiko.services.EquipmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
public class TestEquipment {

    @Autowired
    EquipmentService equipmentService;

    @Autowired
    EquipmentBuilder builder;

    @DisplayName("Test criação equipamento")
    @Test
    public void createEquip() throws  Exception{
        var equip = equipmentService.save(builder.createEquipmentModelDTO(),UUID.randomUUID());
    }

}
