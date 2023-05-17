package apirest.aiko.dtos;

import jakarta.validation.constraints.NotBlank;

public class EquipmentStateDTO {

    @NotBlank
    private String color;
    @NotBlank
    private String name;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
