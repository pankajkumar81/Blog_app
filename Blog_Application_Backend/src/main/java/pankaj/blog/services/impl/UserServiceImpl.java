package pankaj.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import pankaj.blog.entity.Role;
import pankaj.blog.entity.User;
import pankaj.blog.exceptions.ResourceNotFoundException;
import pankaj.blog.payloads.ConstantValue;
import pankaj.blog.payloads.UserDto;
import pankaj.blog.repositories.RoleRepository;
import pankaj.blog.repositories.UserRepository;
import pankaj.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user=this.dtoToUser(userDto);
		User saveUser=userRepository.save(user);
		
		
		return this.userToDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		
		User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id",id));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		User updateUser=userRepository.save(user);
		
		UserDto userDto1=this.userToDto(updateUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer id) {
		
		User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User", "id", id));
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		List<User> users=userRepository.findAll();
		List<UserDto> userDto=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void delete(Integer id) {
		
		userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User","id", id));
		
		this.userRepository.deleteById(id);
		
	}
	
	@Override
	public UserDto getUserByEmail(String email) {
		
		User user=userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User", "Email : "+email, 0));
		
		return this.userToDto(user);
	}
	
	public User dtoToUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto, User.class);
		//user.setId(userDto.getId());
		//user.setName(userDto.getName());
		//user.setEmail(userDto.getEmail()); 
		//user.setPassword(userDto.getPassword());
		//user.setAbout(userDto.getAbout()); 
		
		return user;
		

	}	
	public UserDto userToDto(User user) {
		
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		//userDto.setId(user.getId());
		//userDto.setName(user.getName());
		//userDto.setEmail(user.getEmail());
		//userDto.setPassword(user.getPassword());
		//userDto.setAbout(user.getAbout());
		
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user=this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		Role role= this.roleRepository.findById(ConstantValue.NORAMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser=this.userRepository.save(user);
		
		
		return this.modelMapper.map(newUser, UserDto.class);
	}
	
}
