package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

//@RunWith(Cucumber.class)
public class StepDefination extends Utils {

	RequestSpecification reqSpec;
	ResponseSpecification respSpec;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String placeID;
	
	@Given("Add Place Playload with {string} {string} {string}")
	public void add_place_playload(String name, String language, String address) throws IOException {
		//

		// RestAssured.baseURI = "https://rahulshettyacademy.com";
		// Response response = given().log().all().queryParam("key",
		// "qaclick123").body(p).when()
		// .post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response();
		// String responseString = response.asString();
		// System.out.println(responseString);

		// respSpec = new
		// ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		reqSpec = given().spec(requestSpecification()).body(data.addPlacePayload(name, language, address));
	}

	@When("user calls {string} with {string} HTTP Request")
	public void user_calls_with_post_http_request(String resource, String method) {
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());

		respSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST"))
			response = reqSpec.when().post(resourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = reqSpec.when().get(resourceAPI.getResource());
	}

	@Then("The API Call got success with Status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String expectedValue) {
		assertEquals(getJsonPath(response, keyValue), expectedValue);
	}

	@Then("Verify place ID created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		placeID = getJsonPath(response, "place_id");
		reqSpec = given().spec(requestSpecification()).queryParam("place_id", placeID);
		user_calls_with_post_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(expectedName, actualName);
	}

	@Given("DeletePlace payload")
	public void delete_place_payload() throws IOException {
		reqSpec = given().spec(requestSpecification()).body(data.deletePlacePayload(placeID));
		
	}

}
