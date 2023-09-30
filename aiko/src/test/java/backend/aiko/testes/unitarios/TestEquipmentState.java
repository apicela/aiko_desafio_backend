package backend.aiko.testes.unitarios;

import backend.aiko.builder.EquipmentBuilder;
import backend.aiko.controllers.EquipmentStateController;
import backend.aiko.dtos.EquipmentModelDTO;
import backend.aiko.mappers.EquipmentMapper;
import backend.aiko.models.EquipmentPositionHistory;
import backend.aiko.repositories.EquipmentStateRepository;
import backend.aiko.services.EquipmentStateService;
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
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
public class TestEquipmentState {

    @Autowired
    EquipmentStateService equipmentStateService;
    @Autowired
    EquipmentStateRepository equipmentStateRepository;
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
        builder.initialize();
        // creating a model
        var equip = equipmentStateRepository.findAll().get(0);
        var saved = equipmentStateService.findById(equipmentStateRepository.findAll().get(0).getId()).get();
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(equip.getName(), saved.getName());
        log.info(equipmentStateRepository.findAll());
    }


    @Test()
    @Order(1)
    @DisplayName("TESTE SERVICE -  save, delete")
    void testServiceDelete_status200() throws Exception {
        var size = equipmentStateRepository.findAll().size();
        var saved = equipmentStateRepository.findAll().get(size-1);
        Assertions.assertNotNull(saved);
        log.info("saved {}", saved);
        equipmentStateService.delete(saved);
        assertThrows(IndexOutOfBoundsException.class, () -> equipmentStateRepository.findAll().get(size-1));

    }

    // test chamadas http
    @Test
    @Order(2)
    @DisplayName("POST - CREATED")
    void postEquipState_http201() throws Exception {
        var equip = builder.createEquipmentState();

        mvc.perform(MockMvcRequestBuilders.post(EquipmentStateController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(6)
    @DisplayName("POST - BAD REQUEST")
    void postEquipState_http400() throws Exception {
        var equip = (BigDecimal.ONE);
        var s = objectMapper.writeValueAsString(equip);
        mvc.perform(MockMvcRequestBuilders.post(EquipmentStateController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
        System.out.println(equipmentStateRepository.findAll());
    }

    @Test
    @Order(3)
    @DisplayName("GET - OK")
    void getEquipState_http200() throws Exception {
        equipmentStateRepository.save(builder.createEquipmentState());
        var equip = equipmentStateRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.get(EquipmentStateController.ENDPOINT.concat("/" + equip.getId().toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equip.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(equip.getColor().toString()))
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(7)
    @DisplayName("GET - BAD REQUEST")
    void getEquipState_http400() throws Exception {
        int id = 25;
        mvc.perform(MockMvcRequestBuilders.get(EquipmentStateController.ENDPOINT.concat("/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(11)
    @DisplayName("GET - NOT FOUND")
    void getEquipState_http404() throws Exception {
        var id = UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.get(EquipmentStateController.ENDPOINT.concat("/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(4)
    @DisplayName("PUT - OK")
    @Transactional
    void putEquipState_http200() throws Exception {
        equipmentStateRepository.save(builder.createEquipmentState());
        var equip = equipmentStateRepository.findAll().get(0);
        var oldname = equip.getName();
        equip.setName("new name");
//        log.info(equipmentStateRepository.findAll());

        mvc.perform(MockMvcRequestBuilders.put(EquipmentStateController.ENDPOINT + "/" + equip.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(equip.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equip.getName().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(equip.getColor().toString()))
                .andDo(print());

        assertNotEquals(equip.getName(), oldname);

        log.info(equipmentStateRepository.findAll());

    }

    @Test
    @Order(8)
    @DisplayName("PUT - BAD REQUEST")
    @Transactional
    void putEquipState_http400() throws Exception {
        equipmentStateRepository.save(builder.createEquipmentState());
        var equip = equipmentStateRepository.findAll().get(0);
        var content = BigDecimal.ONE;
        mvc.perform(MockMvcRequestBuilders.put(EquipmentStateController.ENDPOINT + "/" + equip.getId())
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
    void putEquipState_http404() throws Exception {
        var id = UUID.randomUUID();
        equipmentStateRepository.save(builder.createEquipmentState());
        var equip = equipmentStateRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.put(EquipmentStateController.ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(5)
    @DisplayName("DELETE - OK")
    @Transactional
    void deleteEquipState_http200() throws Exception {
        equipmentStateRepository.save(builder.createEquipmentState());
        var equip = equipmentStateRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentStateController.ENDPOINT + "/" + equip.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        log.info(equipmentStateRepository.findAll());

    }

    @Test
    @Order(9)
    @DisplayName("DELETE - BAD REQUEST")
    @Transactional
    void deleteEquipState_http400() throws Exception {
        int id = 5;
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentStateController.ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        log.info(equipmentStateRepository.findAll());

    }

    @Test
    @Order(13)
    @DisplayName("DELETE - NOT FOUND")
    @Transactional
    void deleteEquipState_http404() throws Exception {
        var id = UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentStateController.ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }

}
