package pankaj.blog.payloads;


import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pankaj.blog.entity.Role;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	@NotBlank
	@Size(min = 4,max = 50,message = "Please Enter Name Field")
	private String name;
	@Email(message = "Please enter valid email id !!")
	private String email;
	@NotBlank
	@Size(min=4,max = 12,message = "Please enter min 4 words and max 12 words in password !!")
	private String password;
	
	@NotBlank
	@Size(max=100)
	private String about;
	
	private Set<RoleDto> roles= new HashSet<>();
	
}
