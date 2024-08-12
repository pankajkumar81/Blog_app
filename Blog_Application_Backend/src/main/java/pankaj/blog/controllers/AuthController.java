package pankaj.blog.controllers;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pankaj.blog.entity.User;
import pankaj.blog.exceptions.APIException;
import pankaj.blog.payloads.JwtAuthRequest;
import pankaj.blog.payloads.JwtAuthResponse;
import pankaj.blog.payloads.UserDto;
import pankaj.blog.security.JwtTokenHelper;
import pankaj.blog.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@RequestBody JwtAuthRequest request) throws Exception{
    	
        this.doAuthenticate(request.getUsername(),request.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse response = new JwtAuthResponse();
                response.setToken(token);
                response.setUser(this.modelMapper.map((User)userDetails, UserDto.class));
                
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    private void doAuthenticate(String email, String password) throws Exception {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
        	this.authenticationManager.authenticate(authentication);
        }
        catch (BadCredentialsException e) {
			throw new APIException("Invalid Username and Password");
		}
           
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        //TODO: process POST request
        UserDto registerDto=this.userService.registerNewUser(userDto);
        return new ResponseEntity<>(registerDto,HttpStatus.CREATED);
    }
    

}
