package pankaj.blog.services;

import java.util.List;

import pankaj.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto userDto);
	
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto,Integer id);
	UserDto getUserById(Integer id);
	List<UserDto> getAllUser();
	void delete(Integer id);
	UserDto getUserByEmail(String email);
	

}
