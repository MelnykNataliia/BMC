package tests;

import org.hamcrest.Matchers;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pageobjects.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static pageobjects.api.BookingDataBuilder.getBookingData;
import static pageobjects.api.BookingDataBuilder.getPartialBookingData;
import static pageobjects.api.TokenBuilder.getToken;

public class Exercise2 extends Specifications {
	private BookingData newBooking;
	private BookingData updatedBooking;
	private Tokencreds tokenCreds;
	private PartialBookingData partialUpdateBooking;

	@BeforeTest
	public void testSetup() {
		newBooking = getBookingData();
		updatedBooking = getBookingData();
		tokenCreds = getToken();
		partialUpdateBooking = getPartialBookingData();
	}

	@Test
	public void createBookingTest() {
		// Perform /auth, use the returned token to create a new booking
		String token = given()
				.body(tokenCreds)
				.when()
				.post("auth")
				.then()
				.statusCode(200)
				.and()
				.assertThat()
				.body("token", Matchers.notNullValue())
				.extract()
				.path("token");

		int bookingId = given()
				.body(newBooking)
				.when()
				.header("Cookie", "token=" + token)
				.post("booking")
				.then()
				.statusCode(200)
				.and()
				.extract()
				.path("bookingid");

		// Get details of newly created booking, and ensure it has all details as were specified during its creation
		given()
				.get("booking/" + bookingId)
				.then()
				.statusCode(200)
				.and()
				.assertThat()
				.body("firstname", equalTo(newBooking.getFirstname()), "lastname", equalTo(newBooking.getLastname()),
						"totalprice", equalTo(newBooking.getTotalprice()), "depositpaid",
						equalTo(newBooking.isDepositpaid()), "bookingdates.checkin", equalTo(newBooking.getBookingdates()
																									   .getCheckin()), "bookingdates.checkout", equalTo(newBooking.getBookingdates().getCheckout()), "additionalneeds", equalTo(newBooking.getAdditionalneeds()));

		// Update the booking details
		given()
				.body(updatedBooking)
				.when()
				.header("Cookie", "token=" + token)
				.put("booking/" + bookingId)
				.then()
				.statusCode(200)
				.and()
				.assertThat()
				.body("firstname", equalTo(updatedBooking.getFirstname()), "lastname",
						equalTo(updatedBooking.getLastname()), "totalprice", equalTo(updatedBooking.getTotalprice()),
						"depositpaid", equalTo(updatedBooking.isDepositpaid()), "bookingdates.checkin", equalTo(
								updatedBooking.getBookingdates()
											  .getCheckin()), "bookingdates.checkout", equalTo(updatedBooking.getBookingdates()
																											 .getCheckout()), "additionalneeds", equalTo(updatedBooking.getAdditionalneeds()));
		// Update "totalprice" field
		given()
				.body(partialUpdateBooking)
				.when()
				.header("Cookie", "token=" + token)
				.patch("booking/" + bookingId)
				.then()
				.statusCode(200)
				.and()
				.assertThat()
				.body("firstname", equalTo(updatedBooking.getFirstname()), "lastname",
						equalTo(updatedBooking.getLastname()), "totalprice", equalTo(partialUpdateBooking.getTotalprice()),
						"depositpaid", equalTo(updatedBooking.isDepositpaid()), "bookingdates.checkin", equalTo(
								updatedBooking.getBookingdates()
											  .getCheckin()), "bookingdates.checkout", equalTo(updatedBooking.getBookingdates()
																											 .getCheckout()), "additionalneeds", equalTo(updatedBooking.getAdditionalneeds()));

		// Delete the booking
		given()
				.header("Cookie", "token=" + token)
				.when()
				.delete("booking/" + bookingId)
				.then()
				.statusCode(201);

		// Check if the booking has been deleted
		given()
				.get("booking/" + bookingId)
				.then()
				.statusCode(404);
	}
}
