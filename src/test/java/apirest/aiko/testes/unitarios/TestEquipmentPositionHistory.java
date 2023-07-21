package apirest.aiko.testes.unitarios;

import apirest.aiko.builder.EquipmentBuilder;
import apirest.aiko.controllers.EquipmentPositionHistoryController;
import apirest.aiko.mappers.EquipmentModelStateHourlyEarningsMapper;
import apirest.aiko.models.EquipmentPositionHistory;
import apirest.aiko.repositories.EquipmentPositionHistoryRepository;
import apirest.aiko.services.EquipmentPositionHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
public class TestEquipmentPositionHistory {

    @Autowired
    EquipmentPositionHistoryService equipmentPositionHistoryService;
    @Autowired
    EquipmentPositionHistoryRepository equipmentPositionHistoryRepository;
    @Autowired
    EquipmentModelStateHourlyEarningsMapper mapper;

    @Autowired
    EquipmentBuilder builder;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Autowired
    private MockMvc mvc;
    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // test unitario
    @Test
    @Order(0)
    @DisplayName("TESTE SERVICE - save, findby")
    void testService() throws Exception {
        // creating a model
        var equip = builder.createEquipmentPositionHistory();
        // saving into db using service
        equipmentPositionHistoryService.save(equip);
        // no argumento de findByID, busca o primeiro elemento do banco de dados e após isso pega seu ID
        var saved = equipmentPositionHistoryService.findById(equipmentPositionHistoryRepository.findAll().get(0).getEquipmentPositionHistoryPK()).get();
        log.info(saved.toString());
        log.info(equip.toString());
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(formatter.format(equip.getDate()), formatter.format(saved.getDate()));
        Assertions.assertEquals(equip.getLon(), saved.getLon());
        Assertions.assertEquals(equip.getLat(), saved.getLat());
        log.info(equipmentPositionHistoryRepository.findAll());
        equipmentPositionHistoryRepository.deleteAll();
    }


    @Test()
    @Order(1)
    @DisplayName("TESTE SERVICE -  save, delete")
    void testServiceDelete_status200() throws Exception {
        // creating a model
        var equip = builder.createEquipmentPositionHistory();
        // saving into db using service
        equipmentPositionHistoryService.save(equip);
        var saved = equipmentPositionHistoryRepository.findAll().get(0);
        Assertions.assertNotNull(saved);
        log.info("saved {}", saved);
        equipmentPositionHistoryService.delete(saved);
        assertThrows(IndexOutOfBoundsException.class, () -> equipmentPositionHistoryRepository.findAll().get(0));

    }

    // test chamadas http


    @Test
    @Order(2)
    @DisplayName("POST - CREATED")
    void postEquipMSHE_http201() throws Exception {
        var equip = builder.createEquipmentPositionHistory();
        equipmentPositionHistoryRepository.save(equip);
        mvc.perform(MockMvcRequestBuilders.post(EquipmentPositionHistoryController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
        equipmentPositionHistoryService.delete(equip);
    }

    @Test
    @Order(6)
    @DisplayName("POST - BAD REQUEST")
    void postEquipMSHE_http400() throws Exception {
        EquipmentPositionHistory equip = new EquipmentPositionHistory();
        log.info(equip.getClass());
        mvc.perform(MockMvcRequestBuilders.post(EquipmentPositionHistoryController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
        equipmentPositionHistoryService.delete(equip);

    }

    @Test
    @Order(3)
    @DisplayName("GET - OK")
    void getEquipMSHE_http200() throws Exception {
        equipmentPositionHistoryRepository.save(builder.createEquipmentPositionHistory());
        var equip = equipmentPositionHistoryRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.get(String.format("%s/%s/%s", EquipmentPositionHistoryController.ENDPOINT, equip.getEquipment_id().toString(), equip.getDate().toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(formatter.format(equip.getDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lon").value(equip.getLon()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lat").value(equip.getLat()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_id").value(equip.getEquipment_id().toString()))
                .andDo(print())
                .andReturn();
        equipmentPositionHistoryService.delete(equip);

    }

    @Test
    @Order(7)
    @DisplayName("GET - BAD REQUEST")
    void getEquipMSHE_http400() throws Exception {
        int id = 25;
        mvc.perform(MockMvcRequestBuilders.get(EquipmentPositionHistoryController.ENDPOINT.concat("/" + id + "/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(11)
    @DisplayName("GET - NOT FOUND")
    void getEquipMSHE_http404() throws Exception {
        var id = UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.get(EquipmentPositionHistoryController.ENDPOINT.concat("/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(4)
    @DisplayName("PUT - OK")
    @Transactional
    void putEquipMSHE_http200() throws Exception {
        equipmentPositionHistoryRepository.save(builder.createEquipmentPositionHistory());
        var equip = equipmentPositionHistoryRepository.findAll().get(0);
        var oldLat = equip.getLat();
        var date = formatter.format(equip.getDate());
        equip.setLat(-9);
        mvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s/%s", EquipmentPositionHistoryController.ENDPOINT,
                                equip.getEquipment_id(), date))
                        .content(objectMapper.writeValueAsString(equip))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lat").value(equip.getLat()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lon").value(equip.getLon()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_id").value(equip.getEquipment_id().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(formatter.format(equip.getDate())))
                .andDo(print())
                .andReturn();

        assertNotEquals(equip.getLat(), oldLat);

        log.info(equipmentPositionHistoryRepository.findAll());

    }

    @Test
    @Order(8)
    @DisplayName("PUT - BAD REQUEST")
    @Transactional
    void putEquipMSHE_http400() throws Exception {
        var equip = builder.createEquipmentPositionHistory();

        mvc.perform(MockMvcRequestBuilders.put(String.format("%s/%d/%d", EquipmentPositionHistoryController.ENDPOINT,
                                3, 2))
                        .content(objectMapper.writeValueAsString(equip))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(12)
    @DisplayName("PUT - NOT FOUND")
    @Transactional
    void putEquipMSHE_http404() throws Exception {
        var id = UUID.randomUUID();
        var equip = builder.createEquipmentPositionHistory();
        var date = formatter.format(equip.getDate());
        mvc.perform(MockMvcRequestBuilders.put(EquipmentPositionHistoryController.ENDPOINT + "/" + id + "/" + date)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    //
    @Test
    @Order(5)
    @DisplayName("DELETE - OK")
    @Transactional
    void deleteEquipMSHE_http200() throws Exception {
        equipmentPositionHistoryRepository.save(builder.createEquipmentPositionHistory());
        var equip = equipmentPositionHistoryRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s/%s", EquipmentPositionHistoryController.ENDPOINT,
                                equip.getEquipment_id(), equip.getDate()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        log.info(equipmentPositionHistoryRepository.findAll());

    }

    @Test
    @Order(9)
    @DisplayName("DELETE - BAD REQUEST")
    @Transactional
    void deleteEquipMSHE_http400() throws Exception {
        int id = 5;
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentPositionHistoryController.ENDPOINT + "/" + id + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        log.info(equipmentPositionHistoryRepository.findAll());

    }

    @Test
    @Order(13)
    @DisplayName("DELETE - NOT FOUND")
    @Transactional
    void deleteEquipMSHE_http404() throws Exception {
        var id = UUID.randomUUID();
        var date = LocalDateTime.now();
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentPositionHistoryController.ENDPOINT + "/" + id + "/" + date)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());


    }

}
