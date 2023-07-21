package apirest.aiko.testes.unitarios;

import apirest.aiko.builder.EquipmentBuilder;
import apirest.aiko.controllers.EquipmentModelController;
import apirest.aiko.mappers.EquipmentModelMapper;
import apirest.aiko.models.EquipmentPositionHistory;
import apirest.aiko.repositories.EquipmentModelRepository;
import apirest.aiko.services.EquipmentModelService;
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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
public class TestEquipmentModel {
    @Autowired
    EquipmentBuilder builder;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private EquipmentModelService equipmentModelService;
    @Autowired
    private EquipmentModelRepository equipmentModelRepository;
    @Autowired
    private EquipmentModelMapper mapper;
    @Autowired
    private MockMvc mvc;

    // test unitario
    @Test
    @Order(0)
    @DisplayName("TESTE SERVICE - save, findby")
    void testService() throws Exception {
        var equip = equipmentModelRepository.findAll().get(0);
        var saved = equipmentModelService.findById(equipmentModelRepository.findAll().get(0).getId()).get();
        assertNotNull(saved);
        Assertions.assertEquals(equip.getName(), saved.getName());
        log.info(equipmentModelRepository.findAll());
    }

    @Test
    void teste() throws Exception {
        log.info(equipmentModelRepository.findAll());
    }

    @Test()
    @Order(1)
    @DisplayName("TESTE SERVICE -  save, delete")
    void testServiceDelete_status200() throws Exception {
        var saved = equipmentModelRepository.findAll().get(0);
        assertNotNull(saved);
        log.info("saved {}", saved);
        equipmentModelService.delete(saved);
        assertThrows(IndexOutOfBoundsException.class, () -> equipmentModelRepository.findAll().get(0));

    }


    // test chamadas http
    @Test
    @Order(2)
    @DisplayName("POST - CREATED")
    void postEquipModel_http201() throws Exception {
        var equipModel = builder.createEquipmentModel();

        mvc.perform(MockMvcRequestBuilders.post(EquipmentModelController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipModel))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(6)
    @DisplayName("POST - BAD REQUEST")
    void postEquipModel_http400() throws Exception {
        EquipmentPositionHistory equip = new EquipmentPositionHistory();
        log.info(equip.getClass());
        mvc.perform(MockMvcRequestBuilders.post(EquipmentModelController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equip))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print());
    }

    @Test
    @Order(3)
    @DisplayName("GET - OK")
    void getEquipModel_http200() throws Exception {

        equipmentModelRepository.save(builder.createEquipmentModel());
        var equipModel = equipmentModelRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.get(EquipmentModelController.ENDPOINT.concat("/" + equipModel.getId().toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equipModel.getName()))
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(7)
    @DisplayName("GET - BAD REQUEST")
    void getEquipModel_http400() throws Exception {
        int id = 25;
        mvc.perform(MockMvcRequestBuilders.get(EquipmentModelController.ENDPOINT.concat("/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(print())
                .andReturn();
    }

    @Test
    @Order(11)
    @DisplayName("GET - NOT FOUND")
    void getEquipModel_http404() throws Exception {
        var id = UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.get(EquipmentModelController.ENDPOINT.concat("/" + id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(4)
    @DisplayName("PUT - OK")
    @Transactional
    void putEquipModel_http200() throws Exception {
        equipmentModelRepository.save(builder.createEquipmentModel());
        var equipModel = equipmentModelRepository.findAll().get(0);
        var oldname = equipModel.getName();
        equipModel.setName("new name");
        mvc.perform(MockMvcRequestBuilders.put(EquipmentModelController.ENDPOINT + "/" + equipModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipModel))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(equipModel.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equipModel.getName()))
                .andDo(print());

        assertNotEquals(equipModel.getName(), oldname);

        log.info(equipmentModelRepository.findAll());

    }

    @Test
    @Order(8)
    @DisplayName("PUT - BAD REQUEST")
    @Transactional
    void putEquipModel_http400() throws Exception {
        equipmentModelRepository.save(builder.createEquipmentModel());
        var equipModel = equipmentModelRepository.findAll().get(0);
        var oldname = equipModel.getName();
        equipModel.setName(null);
        mvc.perform(MockMvcRequestBuilders.put(EquipmentModelController.ENDPOINT + "/" + equipModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipModel))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        log.info(equipmentModelRepository.findAll());

    }

    @Test
    @Order(12)
    @DisplayName("PUT - NOT FOUND")
    @Transactional
    void putEquipModel_http404() throws Exception {
        var id = UUID.randomUUID();
        equipmentModelRepository.save(builder.createEquipmentModel());
        var equipModel = equipmentModelRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.put(EquipmentModelController.ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipModel))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Order(5)
    @DisplayName("DELETE - OK")
    @Transactional
    void deleteEquipModel_http200() throws Exception {
        equipmentModelRepository.save(builder.createEquipmentModel());
        var equipModel = equipmentModelRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentModelController.ENDPOINT + "/" + equipModel.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        log.info(equipmentModelRepository.findAll());

    }

    @Test
    @Order(9)
    @DisplayName("DELETE - BAD REQUEST")
    @Transactional
    void deleteEquipModel_http400() throws Exception {
        int id = 5;
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentModelController.ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        log.info(equipmentModelRepository.findAll());

    }

    @Test
    @Order(13)
    @DisplayName("DELETE - NOT FOUND")
    @Transactional
    void deleteEquipModel_http404() throws Exception {
        var id = UUID.randomUUID();
        mvc.perform(MockMvcRequestBuilders.delete(EquipmentModelController.ENDPOINT + "/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }


}
