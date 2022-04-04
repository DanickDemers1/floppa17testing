package ulaval.glo2003.application.assemblers;

import ulaval.glo2003.application.dtos.HealthDto;

public class HealthAssembler {
    public static HealthDto toDto(boolean api, boolean db) {
        var healthDto = new HealthDto();
        healthDto.api = api;
        healthDto.db = db;

        return healthDto;
    }
}
