package tests;

import io.qameta.allure.internal.shadowed.jackson.core.JsonProcessingException;
import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import org.junit.Test;
import pageobjects.api.Auth;
import pageobjects.api.Booking;
import pageobjects.api.BookingDates;
import pageobjects.api.Specifications;

import java.util.List;

import static io.restassured.RestAssured.given;

public class Exercise2 {
	private final static String URL = "https://restful-booker.herokuapp.com/";

	@Test
	public void createBooking() throws JsonProcessingException {
		Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

		Auth auth = new Auth("admin", "password123");
		BookingDates bookingdates = new BookingDates("2018-01-01", "2019-01-01");

		Booking booking = new Booking("Jim", "Brown", 111, true, bookingdates, "Breakfast");
		Booking newBooking = new Booking("Jim", "Brown", 112, true, bookingdates, "Breakfast");

		// Perform /auth, use the returned token to create a new booking
		ObjectMapper Obj = new ObjectMapper();
		String jsonStr = Obj.writeValueAsString(auth);

		String token = given()
				.body(jsonStr)
				.when()
				.post("auth")
				.then().log().all()
				.extract().response().jsonPath().get("token").toString();

		String id = given()
				.auth()
				.oauth2(token)
				.body(booking)
				.when()
				.post("booking")
				.then().log().all()
				.extract().response().jsonPath().get("bookingid").toString();


		// Get details of newly created booking, and ensure it has all details as were specified during its creation
		Object bookingDetails = given()
				.when()
				.get("booking/" + id)
				.then().log().all()
				.extract().body().jsonPath().param("bookingid", booking);

		// Update the booking details (e.g. update "totalprice" field)
		given()
				.body(newBooking)
				.when()
				.put("booking/" + id)
				.then().log().all()
				.extract().response();
		// Test failed: Forbidden

//		// Get all bookings and check them have a newly created booking
		List<Integer> allBookings = given()
				.when()
				.get("booking")
				.then().log().all()
				.extract().body().jsonPath().get("bookingid");

		// Delete the booking
		given()
				.when()
				.delete("booking/" + id)
				.then().log().all();
		// Test failed: Forbidden
	}
}
