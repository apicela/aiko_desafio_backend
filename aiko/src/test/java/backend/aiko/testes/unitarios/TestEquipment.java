package backend.aiko.testes.unitarios;

import backend.aiko.builder.EquipmentBuilder;
import backend.aiko.controllers.EquipmentController;
import backend.aiko.mappers.EquipmentMapper;
import backend.aiko.models.EquipmentPositionHistory;
import backend.aiko.repositories.EquipmentRepository;
import backend.aiko.services.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Order(1)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
public class TestEquipment {



    @Autowired
    EquipmentService equipmentService;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    EquipmentMapper mapper;

    @Autowired
    EquipmentBuilder builder;

    @Autowired
    private MockMvc mvc;


    private ObjectMapper objectMapper = new ObjectMapper();

    // test unitario
    @Test
    @Order(0)
    @DisplayName("TESTE SERVICE - save, findby")
    void testService() throws Exception {
        equipmentRepository.deleteAll();
        builder.initialize();
        // creating a model
        var equip = equipmentRepository.findAll().get(0);
        var saved = equipmentService.findById(equipmentRepository.findAll().get(0).getId()).get();
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(equip.getName(), saved.getName());
        log.info(equipmentRepository.findAll());

    }


    @Test()
    @Order(1)
    @DisplayName("TESTE SERVICE -  save, delete")
    void testServiceDelete_status200() throws Exception {

        var saved = equipmentRepository.findAll().get(0);
        Assertions.assertNotNull(saved);
        log.info("saved {}", saved);
        equipmentService.delete(saved);
        assertThrows(IndexOutOfBoundsException.class, () -> equipmentRepository.findAll().get(0));

    }


    // test chamadas http
    @Test
    @Order(2)
    @DisplayName("POST - CREATED")
    void postEquipment_http201() throws Exception {
        var equip = builder.createEquipment();

        mvc.perform(MockMvcRequestBuilders.post(EquipmentController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(6)
    @DisplayName("POST - BAD REQUEST")
    void postEquipment_http400() throws Exception {
        var content = BigDecimal.ZERO;
        mvc.perform(MockMvcRequestBuilders.post(EquipmentController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Order(3)
    @DisplayName("GET - OK")
    void getEquipment_http200() throws Exception {
        equipmentRepository.save(builder.createEquipment());
        var equip = equipmentRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.get(EquipmentController.ENDPOINT.concat("/" + equip.getId().toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equip.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_model_id").value(equip.getEquipment_model_id().toString()))
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(7)
    @DisplayName("GET - BAD REQUEST")
    void getEquipment_http400() throws Exception {
        int id = 25;
        mvc.perform(MockMvcRequestBuilders.get(EquipmentController.ENDPOINT.concat("/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(11)
    @DisplayName("GET - NOT FOUND")
    void getEquipment_http404() throws Exception {
        var id = UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.get(EquipmentController.ENDPOINT.concat("/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(4)
    @DisplayName("PUT - OK")
    @Transactional
    void putEquipment_http200() throws Exception {
        equipmentRepository.save(builder.createEquipment());
        var equip = equipmentRepository.findAll().get(0);
        var oldname = equip.getName();
        equip.setName("new name");
        mvc.perform(MockMvcRequestBuilders.put(EquipmentController.ENDPOINT + "/" + equip.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(equip.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equip.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_model_id").value(equip.getEquipment_model_id().toString()))
                .andDo(print());

        assertNotEquals(equip.getName(), oldname);

        log.info(equipmentRepository.findAll());

    }

    @Test
    @Order(8)
    @DisplayName("PUT - BAD REQUEST")
    @Transactional
    void putEquipment_http400() throws Exception {
        var equip = equipmentRepository.findAll().get(0);
        var content = BigDecimal.ONE;
        mvc.perform(MockMvcRequestBuilders.put(EquipmentController.ENDPOINT + "/" + equip.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @Order(12)
    @DisplayName("PUT - NOT FOUND")
    @Transactional
    void putEquipment_http404() throws Exception {
        var id = UUID.randomUUID();
        equipmentRepository.save(builder.createEquipment());
        var equip = equipmentRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.put(EquipmentController.ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(5)
    @DisplayName("DELETE - OK")
    @Transactional
    void deleteEquipment_http200() throws Exception {
        equipmentRepository.save(builder.createEquipment());
        var equip = equipmentRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentController.ENDPOINT + "/" + equip.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        log.info(equipmentRepository.findAll());

    }

    @Test
    @Order(9)
    @DisplayName("DELETE - BAD REQUEST")
    @Transactional
    void deleteEquipment_http400() throws Exception {
        int id = 5;
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentController.ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        log.info(equipmentRepository.findAll());

    }

    @Test
    @Order(13)
    @DisplayName("DELETE - NOT FOUND")
    @Transactional
    void deleteEquipment_http404() throws Exception {
        var id = UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentController.ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
