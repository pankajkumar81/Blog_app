package pankaj.blog.services.impl;





import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import pankaj.blog.entity.User;
import pankaj.blog.exceptions.ResourceNotFoundException;
import pankaj.blog.payloads.UserDto;
import pankaj.blog.repositories.UserRepository;
import pankaj.blog.services.UserService;

@Service
public class EmailService {
	
	@Autowired
	public UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public void sendPasswordMail(String to, String subject,int otp) throws MessagingException
	{
		User user=userRepository.findByEmail(to).orElseThrow(()->new ResourceNotFoundException(to, "to :"+to, 0));
		MimeMessage msg=javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper=new MimeMessageHelper(msg,true);
		
		helper.setTo(to);
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
        helper.setSubject(subject);

        
        //Adding html template
        String content="<p>Hello "+ userDto.getName() + "</p>"
                + "<p>Your OTP "
                + "</p>"
                + "<p><b>" + otp+ "</b></p>"
                + "<br>"
                + "<p>Note: Please save this password somewhere else.</p>"
                + "<p><b>Thanks & Regards </b></p>"
                +"<p><b>Blog-App Team </b></p>"
                + "<br>";

        helper.setText(content, true);

        javaMailSender.send(msg);
    }

		
}
