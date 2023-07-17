package apirest.aiko.testes.unitarios;

import apirest.aiko.builder.EquipmentBuilder;
import apirest.aiko.controllers.EquipmentController;
import apirest.aiko.dtos.EquipmentDTO;
import apirest.aiko.mappers.EquipmentMapper;
import apirest.aiko.models.Equipment;
import apirest.aiko.models.EquipmentModel;
import apirest.aiko.repositories.EquipmentModelRepository;
import apirest.aiko.repositories.EquipmentRepository;
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

    EquipmentService equipmentService;

   @Mock
    EquipmentRepository equipmentRepository;

    @Autowired
    EquipmentMapper mapper;

    @Autowired
    EquipmentBuilder builder;

    @Autowired
    private MockMvc mvc;


    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        equipmentService = new EquipmentService(equipmentRepository, mapper); // Adjust the constructor as needed
    }

//    @Test
//    public void testSavesEquipment() {
//        // Crie um objeto EquipmentDTO
//        EquipmentDTO equipmentDTO = builder.createEquipmentDTO();
//
//        // Configure o comportamento do mock para o método save antes de chamar o serviço
//        Equipment equip = mapper.mapDtoToEntity(equipmentDTO);
//        when(equipmentRepository.save(any(Equipment.class))).thenReturn(equip);
//
//        // Chame o método save do serviço
//        Equipment savedEquipment = equipmentService.save(equipmentDTO);
//
//        // Verifica se o método save do EquipmentRepository foi chamado uma vez com qualquer objeto Equipment como argumento
//        verify(equipmentRepository, times(1)).save(any(Equipment.class));
//
//        // Outros testes, se necessário
//    }

    @Autowired
    EquipmentRepository repo;
    @Autowired
    EquipmentModelRepository repoModel;
    @Test
    public void testSaveEquipment() {
        EquipmentModel dto = new EquipmentModel();
        dto.setName("rs");
        dto.setId(UUID.randomUUID());

        repoModel.save(dto);
        var test = repoModel.findById(dto.getId()).get();
        log.info(test);
//       Equipment equip = new Equipment( new EquipmentDTO("oi",dto.getId()));
//       repo.save(equip);
//       var saved = repo.findById(equip.getId());
//       log.info(saved.get().toString());
    }
    @DisplayName("Test criação equipamento")
    @Test
    public void createEquip_status201() throws  Exception{

//        var equipment = builder.createEquipment();
//        var equipDTO = builder.createEquipmentDTO();
//        log.info("MOCKITO TEST:");
//        Mockito.when(equipmentService.save(equipDTO))
//                .thenReturn(equipment);
//        Mockito.verify(equipmentService).save(Mockito.any(EquipmentDTO.class));
//        log.info("Equipment: {}", equipment);
//
//        log.info("MOCK MVC TEST:");
//



}

    @Test
    public void test() throws Exception{
        Equipment equipment = builder.createEquipment();
        log.info(equipment.toString());
        log.info("----------------------");
        equipmentRepository.save(equipment);
        Equipment savedEquipment = equipmentRepository.findById(equipment.getId()).orElse(null);

        // Verifica se o equipamento foi salvo corretamente
       // assertNotNull(savedEquipment);
//        log.info("PRE SERVICE: "+mapper.mapEntityToDto(equipment));
//        equipmentService.save(mapper.mapEntityToDto(equipment));
//        log.info("XD"+Optional.of(equipmentRepository.findById(equipment.getId())));
        // UTILIZANDO MOCKITO PARA TESTAR CONSISTENCIA DE DADOS DO REPOSITORY E SERVICE
//        when(equipmentRepository.findById(equipment.getId()))
//                .thenReturn(Optional.of(equipment));
//        log.info(equipment.toString());
//        Optional<EquipmentDTO> equipmentFromService = equipmentService.findById(equipment.getId());
//
//        Assertions.assertEquals(equipment.getName(), equipmentFromService.get().getName());
//        Assertions.assertEquals(equipment.getEquipment_model_id(), equipmentFromService.get().getEquipment_model_id());
//
//        // testando a chamada http
//        mvc.perform(MockMvcRequestBuilders.get(EquipmentController.ENDPOINT.concat("/"+equipment.getId().toString()))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(equipment.getName()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.equipment_model_id").value(equipment.getEquipment_model_id()))
//                .andDo(print())
//                .andReturn();

    }

}
