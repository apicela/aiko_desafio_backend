package backend.aiko.WebPages;

import backend.aiko.models.EquipmentPositionHistory;
import backend.aiko.models.EquipmentStateHistory;
import backend.aiko.services.EquipmentPositionHistoryService;
import backend.aiko.services.EquipmentStateHistoryService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    final EquipmentStateHistoryService equipmentStateHistoryService;
    final EquipmentPositionHistoryService equipmentPositionHistoryService;

    public MainController(EquipmentStateHistoryService equipmentStateHistoryService,
                          EquipmentPositionHistoryService equipmentPositionHistoryService) {
        this.equipmentStateHistoryService = equipmentStateHistoryService;
        this.equipmentPositionHistoryService = equipmentPositionHistoryService;
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }

    @GetMapping("/stateHistory")
    public String positionHistory(Model model) {
        List<EquipmentStateHistory> stateHistoriesList = equipmentStateHistoryService.findAll();
        model.addAttribute("lista", stateHistoriesList);
        return "stateHistory";
    }

    @GetMapping("/projeto")
    public String projeto() {
        return "projeto";
    }

    @GetMapping("/positionMap")
    public String positionMap(Model model) {
        List<EquipmentPositionHistory> positionHistoryList = equipmentPositionHistoryService.findLastPosition();
        JSONArray jsonArray = new JSONArray();

        for (EquipmentPositionHistory positionHistory : positionHistoryList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lat", positionHistory.getLat());
            jsonObject.put("lon", positionHistory.getLon());
            jsonObject.put("date", positionHistory.getDate());
            jsonObject.put("nameEquipment", positionHistory.getEquipment().getName());
            System.out.println("EQUIP ID:" + positionHistory.getEquipment_id());
            jsonObject.put("statusEquipment", equipmentStateHistoryService.findState(positionHistory.getEquipment_id()));
            System.out.println("SERVICE:" + equipmentStateHistoryService.findState(positionHistory.getEquipment_id()));
            jsonArray.put(jsonObject);
        }
        String positionHistoryJson = jsonArray.toString();
        model.addAttribute("positionsHistoryJson", positionHistoryJson);

        return "positionMap";
    }

    @GetMapping("/swagger")
    public String redirectToUi() {
        return "swagger";
    }


}
