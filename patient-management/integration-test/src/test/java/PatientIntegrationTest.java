import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PatientIntegrationTest {

    private static String adminToken;

    @BeforeAll
    static void setUp() {
        // Gateway Port ကို အသုံးပြုပါ
        RestAssured.baseURI = "http://localhost:4004";

        // 1. အရင်ဆုံး Login ဝင်ပြီး Token ကို ယူထားပါမယ် (Authentication Step)
        String loginPayload = """
                {
                  "email": "testuser@test.com",
                  "password": "password123"
                }
                """;

        adminToken = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .path("token");
    }

    @Test
    public void shouldGetAllPatientsWithValidToken() {
        // 2. ရလာတဲ့ Token ကို သုံးပြီး Patient Service ကို လှမ်းခေါ်ပါမယ်
        given()
                .header("Authorization", "Bearer " + adminToken) // Token ထည့်ပေးရန်
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200) // OK
                .body("size()", greaterThanOrEqualTo(0)) // List တစ်ခု ပြန်လာရမယ်
                .body("[0].name", notNullValue()); // ပထမဆုံးလူရဲ့ နာမည် ပါရမယ်
    }

    @Test
    public void shouldReturnUnauthorizedWhenTokenMissing() {
        // Token မပါဘဲ ခေါ်ရင် 401 Unauthorized ဖြစ်ရပါမယ်
        given()
                .when()
                .get("/api/patients")
                .then()
                .statusCode(401);
    }
}