package apirest.aiko.testes.unitarios;

import apirest.aiko.builder.EquipmentBuilder;
import apirest.aiko.controllers.EquipmentController;
import apirest.aiko.dtos.EquipmentDTO;
import apirest.aiko.dtos.EquipmentModelDTO;
import apirest.aiko.mappers.EquipmentMapper;
import apirest.aiko.models.Equipment;
import apirest.aiko.models.EquipmentModel;
import apirest.aiko.repositories.EquipmentModelRepository;
import apirest.aiko.repositories.EquipmentRepository;
import apirest.aiko.services.EquipmentModelService;
import apirest.aiko.services.EquipmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
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


    @Autowired
    private ObjectMapper objectMapper;


}
