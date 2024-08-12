package pankaj.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pankaj.blog.entity.User;
import pankaj.blog.exceptions.EmailNotFoundException;
import pankaj.blog.exceptions.ResourceNotFoundException;
import pankaj.blog.repositories.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user= userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User", "email :"+email,0));
		
		return user;
	}

}
