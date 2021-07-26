import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class CowinTestApi {
	
	public int statusCode;
	
	//It needs OTP from generate OTP API. I used OTP from Cowin site.
	@Test (priority = 1)
	public void postConfirmOtp() {
		
		Map<String, Object> map = new HashMap<String, Object>();
	
	JSONObject request = new JSONObject(map);
	
	map.put("OTP", "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92");
	map.put("Transaction", "3fa85f64-5717-4562-b3fc-2c963f66afa6");
	
	given()
	.header("Content-Type", "application/json")
	.contentType(ContentType.JSON).accept(ContentType.JSON)
	.body(request.toJSONString())
	.when()
	.post("https://cdn-api.co-vin.in/api/v2/auth/public/confirmOTP")
	.then()
	.statusCode(201);
		
	}

	//Needs Authentication from OTP, hence Auth. error 401
	@Test (priority = 2)
	void getStateResponse() {
		
		Response response = get("https://cdn-api.co-vin.in/api/v2/admin/location/states");
				
		given()
		.contentType(ContentType.JSON).accept(ContentType.JSON)
		.get("https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/110001")	
		.then()
		.statusCode(200)
		.body("states.state_id", equalTo(58))
		.body("states.state_name", equalTo("Andaman and Nicobar Islands"));
		
		
		statusCode = response.getStatusCode();
	
		Assert.assertEquals(statusCode, 200);
		
	}
}
