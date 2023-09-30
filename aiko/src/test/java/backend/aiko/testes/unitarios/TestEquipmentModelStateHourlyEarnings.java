package backend.aiko.testes.unitarios;

import backend.aiko.builder.EquipmentBuilder;
import backend.aiko.controllers.EquipmentModelStateHourlyEarningsController;
import backend.aiko.mappers.EquipmentModelStateHourlyEarningsMapper;
import backend.aiko.models.EquipmentPositionHistory;
import backend.aiko.repositories.EquipmentModelStateHourlyEarningsRepository;
import backend.aiko.services.EquipmentModelStateHourlyEarningsService;
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
public class TestEquipmentModelStateHourlyEarnings  {

    @Autowired
    EquipmentModelStateHourlyEarningsService equipmentModelStateHourlyEarningsService;
    @Autowired
    EquipmentModelStateHourlyEarningsRepository equipmentModelStateHourlyEarningsRepository;
    @Autowired
    EquipmentModelStateHourlyEarningsMapper mapper;

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
        // creating a model
        var equip = builder.createEquipmentMSHE();
        // saving into db using service
        equipmentModelStateHourlyEarningsService.save(equip);
        // no argumento de findByID, busca o primeiro elemento do banco de dados e após isso pega seu ID
        var saved = equipmentModelStateHourlyEarningsService.findById(equipmentModelStateHourlyEarningsRepository.findAll().get(0).getEquipmentMSHE_id()).get();
        Assertions.assertNotNull(saved);
        Assertions.assertEquals(equip.getEquipmentMSHE_id(), saved.getEquipmentMSHE_id());
        Assertions.assertEquals(equip.getValue(), saved.getValue());

        log.info(equipmentModelStateHourlyEarningsRepository.findAll());
    }


    @Test()
    @Order(1)
    @DisplayName("TESTE SERVICE -  save, delete")
    void testServiceDelete_status200() throws Exception {
        // creating a model
        var equip = builder.createEquipmentMSHE();
        // saving into db using service
        equipmentModelStateHourlyEarningsService.save(equip);
        var saved = equipmentModelStateHourlyEarningsRepository.findAll().get(0);
        Assertions.assertNotNull(saved);
        log.info("saved {}", saved);
        equipmentModelStateHourlyEarningsService.delete(saved);
        assertThrows(IndexOutOfBoundsException.class, () -> equipmentModelStateHourlyEarningsRepository.findAll().get(0));

    }

    // test chamadas http

    @Test
    @Order(2)
    @DisplayName("POST - CREATED")
    void postEquipMSHE_http201() throws Exception {
        var equip = builder.createEquipmentMSHE();
        equipmentModelStateHourlyEarningsRepository.save(equip);
        if (equip.getEquipmentModel() != null) {
            mvc.perform(MockMvcRequestBuilders.post(EquipmentModelStateHourlyEarningsController.ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(equip))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated())
                    .andDo(print());
        }
    }

    @Test
    @Order(6)
    @DisplayName("POST - BAD REQUEST")
    void postEquipMSHE_http400() throws Exception {
        var content = BigDecimal.ZERO;
        mvc.perform(MockMvcRequestBuilders.post(EquipmentModelStateHourlyEarningsController.ENDPOINT)
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
        equipmentModelStateHourlyEarningsRepository.save(builder.createEquipmentMSHE());
        var equip = equipmentModelStateHourlyEarningsRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.get(String.format("%s/%s/%s", EquipmentModelStateHourlyEarningsController.ENDPOINT, equip.getEquipment_model_id(), equip.getEquipment_state_id()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(equip.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_model_id").value(equip.getEquipment_model_id().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_state_id").value(equip.getEquipment_state_id().toString()))
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(7)
    @DisplayName("GET - BAD REQUEST")
    void getEquipMSHE_http400() throws Exception {
        int id = 25;
        mvc.perform(MockMvcRequestBuilders.get(EquipmentModelStateHourlyEarningsController.ENDPOINT.concat("/" + id + "/" + id))
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
        mvc.perform(MockMvcRequestBuilders.get(EquipmentModelStateHourlyEarningsController.ENDPOINT.concat("/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(4)
    @DisplayName("PUT - OK")
    @Transactional
    void putEquipMSHE_http200() throws Exception {
        equipmentModelStateHourlyEarningsRepository.save(builder.createEquipmentMSHE());
        var equip = equipmentModelStateHourlyEarningsRepository.findAll().get(0);
        var oldValue = equip.getValue();
        equip.setValue(888);
        mvc.perform(MockMvcRequestBuilders.put(String.format("%s/%s/%s", EquipmentModelStateHourlyEarningsController.ENDPOINT,
                                equip.getEquipment_model_id(), equip.getEquipment_state_id()))
                        .content(objectMapper.writeValueAsString(equip))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(equip.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_model_id").value(equip.getEquipment_model_id().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_state_id").value(equip.getEquipment_state_id().toString()))
                .andDo(print())
                .andReturn();

        assertNotEquals(equip.getValue(), oldValue);

        log.info(equipmentModelStateHourlyEarningsRepository.findAll());

    }

    @Test
    @Order(8)
    @DisplayName("PUT - BAD REQUEST")
    @Transactional
    void putEquipMSHE_http400() throws Exception {
        equipmentModelStateHourlyEarningsRepository.save(builder.createEquipmentMSHE());
        var equip = equipmentModelStateHourlyEarningsRepository.findAll().get(0);

        mvc.perform(MockMvcRequestBuilders.put(String.format("%s/%d/%d", EquipmentModelStateHourlyEarningsController.ENDPOINT,
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
        equipmentModelStateHourlyEarningsRepository.save(builder.createEquipmentMSHE());
        var equip = equipmentModelStateHourlyEarningsRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.put(EquipmentModelStateHourlyEarningsController.ENDPOINT + "/" + id + "/" + id)
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
        equipmentModelStateHourlyEarningsRepository.save(builder.createEquipmentMSHE());
        var equip = equipmentModelStateHourlyEarningsRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.delete(String.format("%s/%s/%s", EquipmentModelStateHourlyEarningsController.ENDPOINT,
                                equip.getEquipment_model_id(), equip.getEquipment_state_id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        log.info(equipmentModelStateHourlyEarningsRepository.findAll());

    }

    @Test
    @Order(9)
    @DisplayName("DELETE - BAD REQUEST")
    @Transactional
    void deleteEquipMSHE_http400() throws Exception {
        int id = 5;
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentModelStateHourlyEarningsController.ENDPOINT + "/" + id + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        log.info(equipmentModelStateHourlyEarningsRepository.findAll());

    }

    @Test
    @Order(13)
    @DisplayName("DELETE - NOT FOUND")
    @Transactional
    void deleteEquipMSHE_http404() throws Exception {
        var id = UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentModelStateHourlyEarningsController.ENDPOINT + "/" + id + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());


    }

}