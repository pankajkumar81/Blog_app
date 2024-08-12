package pankaj.blog.controllers;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import pankaj.blog.entity.User;
import pankaj.blog.exceptions.ResourceNotFoundException;
import pankaj.blog.payloads.UserDto;
import pankaj.blog.repositories.UserRepository;
import pankaj.blog.services.UserService;
import pankaj.blog.services.impl.EmailService;
import pankaj.blog.services.impl.OTPService;

@RestController
@RequestMapping("/api/mail")
public class MailController {
	
	@Autowired
    public OTPService otpService;
	
	@Autowired
	public EmailService emailService;
	
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Logger logger=LoggerFactory.getLogger(MailController.class);
	
	
	@PostMapping("/send")
	public ResponseEntity<String> mailSent(@RequestBody UserDto userDto) throws MessagingException, RuntimeException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchPaddingException
	{
		
		User user=this.modelMapper.map(userDto, User.class);
		User user1=userRepository.findByEmail(user.getEmail()).orElseThrow(()->new ResourceNotFoundException("User", "Email : "+user.getEmail(), 0));

		int otp = otpService.generateOTP(user1.getEmail());
		
		emailService.sendPasswordMail(user1.getEmail(),"Blog Account Password",otp);
		return new ResponseEntity<>("Password sent to email id",HttpStatus.OK);
	}
	
	@GetMapping("/validateOtp")
    public @ResponseBody String validateOtp(@Valid @RequestParam(required = false,defaultValue = "0")Integer otpnum,String email,String password) throws MessagingException {
    	
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";

        UserDto userdto=userService.getUserByEmail(email);

        User user=this.modelMapper.map(userdto, User.class);

        if(otpnum >= 0){

            int serverOtp = otpService.getOtp(email);

            if(serverOtp > 0){

                if(otpnum == serverOtp){
                    otpService.clearOTP(email);
                    user.setPassword(passwordEncoder.encode(password));
                    userRepository.save(user);
                    return "Password Changed Successfully Now You can Login with your new Password !!";
                }
                else {
                    return FAIL;
                }
            }else {
                return FAIL;
            }
        }else {
            return FAIL;
        }
    }

}
