package apirest.aiko.testes.unitarios;

import apirest.aiko.builder.EquipmentBuilder;
import apirest.aiko.controllers.EquipmentModelController;
import apirest.aiko.dtos.EquipmentModelDTO;
import apirest.aiko.mappers.EquipmentModelMapper;
import apirest.aiko.models.EquipmentModel;
import apirest.aiko.repositories.EquipmentModelRepository;
import apirest.aiko.services.EquipmentModelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Log4j2
public class TestEquipmentModel {
    @Autowired
    private EquipmentModelService equipmentModelService;

    @Autowired
    private EquipmentModelRepository equipmentModelRepository;
    @Autowired
    private EquipmentModelMapper mapper;
    @Autowired
    EquipmentBuilder builder;
    @Autowired
    private MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();

    // test unitario
    @Test
    @Order(0)
    @DisplayName("teste save and find by id")
    void testSave() throws Exception{
        // creating a model
        var modelDTO = builder.createEquipmentModel();
        // saving into db using service
        equipmentModelService.save(modelDTO);
        var saved = equipmentModelRepository.findAll().get(0);
        assertNotNull(saved);
        Assertions.assertEquals(modelDTO.getName(), saved.getName());
        log.info(equipmentModelRepository.findAll());

        // delete data
        equipmentModelRepository.deleteAll();
        log.info(equipmentModelRepository.findAll());
    }

    @Test()
    @Order(1)
    @DisplayName("teste delete")
    void testDelete() throws Exception{
        // creating a model
        var model = builder.createEquipmentModel();
        // saving into db using service
        equipmentModelService.save(model);
        var saved = equipmentModelRepository.findAll().get(0);
        assertNotNull(saved);
        log.info("saved {}",saved);
        equipmentModelService.delete(saved);
        assertThrows(IndexOutOfBoundsException.class, () -> equipmentModelRepository.findAll().get(0));

    }

    // test mvc
    @Test
    @Order(2)
    @DisplayName("")
    void postEquipModel_http201() throws Exception{
        var equipModel = builder.createEquipmentModel();

        mvc.perform(MockMvcRequestBuilders.post(EquipmentModelController.ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(equipModel))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }

    @Test
    @Order(3)
    @DisplayName("")
    void getEquipModel_http200() throws Exception{

        equipmentModelRepository.save(builder.createEquipmentModel());
        var equipModel = equipmentModelRepository.findAll().get(0);
        mvc.perform(MockMvcRequestBuilders.get(EquipmentModelController.ENDPOINT.concat("/"+equipModel.getId().toString()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equipModel.getName()))
                .andDo(print())
                .andReturn();
    }


    @Test
    @Order(4)
    @DisplayName("")
    @Transactional
    void putEquipModel_http200() throws Exception{
        equipmentModelRepository.save(builder.createEquipmentModel());
        var equipModel = equipmentModelRepository.findAll().get(0);
        var oldname = equipModel.getName();
        equipModel.setName("new name");
        mvc.perform(MockMvcRequestBuilders.put(EquipmentModelController.ENDPOINT+"/"+equipModel.getId())
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


}
