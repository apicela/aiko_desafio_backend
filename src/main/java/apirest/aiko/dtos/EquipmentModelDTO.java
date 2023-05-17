package apirest.aiko.dtos;

import jakarta.validation.constraints.NotBlank;

public class EquipmentModelDTO {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
