package pankaj.blog.payloads;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtAuthResponse {

	private String token;
	private UserDto user;
}
