package ulaval.glo2003.api.assemblers;

import jakarta.ws.rs.core.Response;
import ulaval.glo2003.application.dtos.HealthDto;
import ulaval.glo2003.application.exceptions.NullReferenceException;

public class HealthResponseAssembler {
    public static Response toGetHealthResponse(HealthDto healthDto) {
        if (healthDto == null)
            throw new NullReferenceException();

        if (!healthDto.api || !healthDto.db) {
            return Response.status(500).entity(healthDto).build();
        }

        return Response.status(200).entity(healthDto).build();
    }
}
