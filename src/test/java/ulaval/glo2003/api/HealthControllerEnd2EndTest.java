package ulaval.glo2003.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ulaval.glo2003.Environment;
import ulaval.glo2003.Main;

import java.io.IOException;

import static io.restassured.RestAssured.when;
import static spark.Spark.stop;

class HealthControllerEnd2EndTest {

    public static Response getHealth() {
        return when().get(ControllerPaths.HEALTH + "/");
    }

    @BeforeAll
    static void startServer() throws IOException {
        Main.environment = Environment.TEST;
        Main.main(new String[0]);
    }

    @AfterAll
    static void tearDownServer() {
        stop();
        Main.server.shutdown();
    }

    @Test
    void givernStartedServer_whenGetHealth_thenResponseHasOkStatus() {
        var healthRequest = getHealth();

        healthRequest.then().statusCode(200);
    }
}