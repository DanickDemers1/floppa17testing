package ulaval.glo2003.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import ulaval.glo2003.api.assemblers.HealthResponseAssembler;
import ulaval.glo2003.application.assemblers.HealthAssembler;
import ulaval.glo2003.application.dtos.HealthDto;
import ulaval.glo2003.infrastructure.persistence.DbConnection;

@Path(ControllerPaths.HEALTH)
public class HealthController {

    private final DbConnection dbConnection;

    public HealthController(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @GET
    public Response getHealth() {
        boolean apiOk = true;
        boolean dbOk = dbConnection.canConnectToProduction();

        HealthDto healthDto = HealthAssembler.toDto(apiOk, dbOk);

        return HealthResponseAssembler.toGetHealthResponse(healthDto);
    }
}
