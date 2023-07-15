package apirest.aiko.testes.unitarios;

import apirest.aiko.builder.EquipmentBuilder;
import apirest.aiko.controllers.EquipmentController;
import apirest.aiko.dtos.EquipmentDTO;
import apirest.aiko.mappers.EquipmentMapper;
import apirest.aiko.services.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@Log4j2
public class TestEquipment {

    @MockBean
    EquipmentService equipmentService;

    @Autowired
    EquipmentBuilder builder;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EquipmentMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Test criação equipamento")
    @Test
    public void createEquip_status201() throws  Exception{


        var equipment = builder.createEquipment();
        log.info("MOCKITO TEST:");
        Mockito.when(equipmentService.save(Mockito.any(EquipmentDTO.class)))
                .thenReturn((equipment));

        log.info("MOCK MVC TEST:");
        mvc.perform(MockMvcRequestBuilders.post(EquipmentController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipment)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
}
}
