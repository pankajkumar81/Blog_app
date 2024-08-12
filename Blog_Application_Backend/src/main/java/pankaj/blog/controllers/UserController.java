package pankaj.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pankaj.blog.payloads.UserDto;
import pankaj.blog.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/createuser")
	public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto)
	{
		this.userService.createUser(userDto);
		return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
	}
	
	@PutMapping("/updateuser/{id}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable String id)
	{
		UserDto userDto2=this.userService.updateUser(userDto, Integer.parseUnsignedInt(id));
		return new ResponseEntity<>(userDto2,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/getuser/{userId}")
	public ResponseEntity<UserDto> getUser(@PathVariable int id)
	{
		UserDto userDto=this.userService.getUserById(id);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
	
	@GetMapping("/getusers")
	public ResponseEntity<List<UserDto>> getUsers()
	{
		List<UserDto> userDto=this.userService.getAllUser();
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer id)
	{
		this.userService.delete(id);
		return new ResponseEntity<>("User Deleted Successfully",HttpStatus.OK);
	}
}
