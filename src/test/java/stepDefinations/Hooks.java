package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		// write a code which will give you place ID and execute only if place id is
		// null

		StepDefination sdef = new StepDefination();

		if (StepDefination.placeID == null) {
			sdef.add_place_playload("fromHooks", "PT", "Rio-de-Jenerio");
			sdef.user_calls_with_post_http_request("addPlaceAPI", "POST");
			sdef.verify_place_id_created_maps_to_using("fromHooks", "getPlaceAPI");
		}
	}

}
