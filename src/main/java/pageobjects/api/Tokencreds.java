package pageobjects.api;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Tokencreds {
	private String username;
	private String password;
}
