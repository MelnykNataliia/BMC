package pageobjects.api;

import com.github.javafaker.Faker;
import lombok.Builder;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

@Builder
@Data
public class BookingDataBuilder {

	private static final Faker FAKER = new Faker();

	public static BookingData getBookingData() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return BookingData.builder()
						  .firstname(FAKER.name()
										  .firstName())
						  .lastname(FAKER.name()
										 .lastName())
						  .totalprice(FAKER.number()
										   .numberBetween(1, 2000))
						  .depositpaid(true)
						  .bookingdates(BookingDates.builder()
													.checkin(formatter.format(FAKER.date()
																				   .past(20, TimeUnit.DAYS)))
													.checkout(formatter.format(FAKER.date()
																					.future(5, TimeUnit.DAYS)))
													.build())
						  .additionalneeds("Breakfast")
						  .build();
	}

	public static PartialBookingData getPartialBookingData() {
		return PartialBookingData.builder()
								 .totalprice(FAKER.number()
												  .numberBetween(100, 5000))
								 .build();
	}
}

