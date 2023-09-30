package backend.aiko.testes.unitarios;

import backend.aiko.builder.EquipmentBuilder;
import backend.aiko.controllers.EquipmentStateHistoryController;
import backend.aiko.mappers.EquipmentModelStateHourlyEarningsMapper;
import backend.aiko.models.EquipmentStateHistory;
import backend.aiko.repositories.EquipmentStateHistoryRepository;
import backend.aiko.services.EquipmentStateHistoryService;
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

import java.math.BigDecimal;
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
public class TestEquipmentStateHistoryController  {
    @Autowired
    EquipmentStateHistoryService equipmentStateHistoryService;
    @Autowired
    EquipmentStateHistoryRepository equipmentStateHistoryRepository;
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
        builder.initialize();
        // creating a model
        var equip = builder.createEquipmentStateHistory();
        // saving into db using service
        equipmentStateHistoryService.save(equip);
        // no argumento de findByID, busca o primeiro elemento do banco de dados e após isso pega seu ID
        var saved = equipmentStateHistoryService.findById(equipmentStateHistoryRepository.findAll().get(0).getEquipmentSH_id()).get();
        log.info(saved.toString());
        log.info(equip.toString());
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(formatter.format(equip.getDate()), formatter.format(saved.getDate()));
        Assertions.assertEquals(equip.getEquipment_id(), saved.getEquipment_id());
        Assertions.assertEquals(equip.getEquipment_state_id(), saved.getEquipment_state_id());
        log.info(equipmentStateHistoryRepository.findAll());
        equipmentStateHistoryRepository.deleteAll();
    }


    @Test()
    @Order(1)
    @DisplayName("TESTE SERVICE -  save, delete")
    void testServiceDelete_status200() throws Exception {
        // creating a model
        var equip = builder.createEquipmentStateHistory();
        // saving into db using service
        equipmentStateHistoryService.save(equip);
        var saved = equipmentStateHistoryRepository.findAll().get(0);
        Assertions.assertNotNull(saved);
        log.info("saved {}", saved);
        equipmentStateHistoryService.delete(saved);
        assertThrows(IndexOutOfBoundsException.class, () -> equipmentStateHistoryRepository.findAll().get(0));

    }

    // test chamadas http
    @Test
    @Order(2)
    @DisplayName("POST - CREATED")
    void postEquipMSHE_http201() throws Exception {
        var equip = builder.createEquipmentStateHistory();
        equipmentStateHistoryRepository.save(equip);
        mvc.perform(MockMvcRequestBuilders.post(EquipmentStateHistoryController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
        equipmentStateHistoryService.delete(equip);
    }

    @Test
    @Order(6)
    @DisplayName("POST - BAD REQUEST")
    void postEquipMSHE_http400() throws Exception {
        var content = BigDecimal.ZERO;
        mvc.perform(MockMvcRequestBuilders.post(EquipmentStateHistoryController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(content))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());

    }

    @Test
    @Order(3)
    @DisplayName("GET - OK")
    void getEquipMSHE_http200() throws Exception {
        equipmentStateHistoryRepository.save(builder.createEquipmentStateHistory());
        var equip = equipmentStateHistoryRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.get(String.format("%s/%s/%s/%s", EquipmentStateHistoryController.ENDPOINT,
                                equip.getEquipment_id().toString(), formatter.format(equip.getDate()), equip.getEquipment_state_id()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(formatter.format(equip.getDate())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_state_id").value(equip.getEquipment_state_id().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_id").value(equip.getEquipment_id().toString()))
                .andDo(print())
                .andReturn();
        equipmentStateHistoryService.delete(equip);

    }

    @Test
    @Order(7)
    @DisplayName("GET - BAD REQUEST")
    void getEquipMSHE_http400() throws Exception {
        int id = 25;
        mvc.perform(MockMvcRequestBuilders.get(EquipmentStateHistoryController.ENDPOINT.concat("/" + id + "/" + id + "/" + id))
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
        mvc.perform(MockMvcRequestBuilders.get(EquipmentStateHistoryController.ENDPOINT.concat("/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(4)
    @DisplayName("PUT - OK")
    @Transactional
    void putEquipMSHE_http200() throws Exception {
        equipmentStateHistoryRepository.save(builder.createEquipmentStateHistory());
        var equip = equipmentStateHistoryRepository.findAll().get(0);
        var oldDate = formatter.format(equip.getDate());
        var newDate = LocalDateTime.now();
        equip.setDate(newDate);
        mvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s/%s/%s", EquipmentStateHistoryController.ENDPOINT,
                                equip.getEquipment_id(), oldDate, equip.getEquipment_state_id()))
                        .content(objectMapper.writeValueAsString(equip))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(formatter.format(newDate)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_state_id").value(equip.getEquipment_state_id().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_id").value(equip.getEquipment_id().toString()))
                .andDo(print())
                .andReturn();

        assertNotEquals(formatter.format(equip.getDate()), oldDate);

        log.info(equipmentStateHistoryRepository.findAll());

    }

    @Test
    @Order(8)
    @DisplayName("PUT - BAD REQUEST")
    @Transactional
    void putEquipMSHE_http400() throws Exception {
        var content = BigDecimal.ZERO;
        mvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s/%s/%s", EquipmentStateHistoryController.ENDPOINT,
                                3, 2, 2))
                        .content(objectMapper.writeValueAsString(content))
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
        var equip = builder.createEquipmentStateHistory();
        var date = formatter.format(equip.getDate());
        mvc.perform(MockMvcRequestBuilders.put(EquipmentStateHistoryController.ENDPOINT + "/" + id + "/" + date)
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
        equipmentStateHistoryRepository.save(builder.createEquipmentStateHistory());
        var equip = equipmentStateHistoryRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s/%s/%s", EquipmentStateHistoryController.ENDPOINT,
                                equip.getEquipment_id(), formatter.format(equip.getDate()), equip.getEquipment_state_id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        log.info(equipmentStateHistoryRepository.findAll());

    }

    @Test
    @Order(9)
    @DisplayName("DELETE - BAD REQUEST")
    @Transactional
    void deleteEquipMSHE_http400() throws Exception {
        int id = 5;
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentStateHistoryController.ENDPOINT + "/" + id + "/" + id + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        log.info(equipmentStateHistoryRepository.findAll());

    }

    @Test
    @Order(13)
    @DisplayName("DELETE - NOT FOUND")
    @Transactional
    void deleteEquipMSHE_http404() throws Exception {
        var id = UUID.randomUUID();
        var date = LocalDateTime.now();
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentStateHistoryController.ENDPOINT + "/" + id + "/" + date)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());


    }

}
