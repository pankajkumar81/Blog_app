package pankaj.blog;


import java.util.List;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pankaj.blog.entity.Role;
import pankaj.blog.payloads.ConstantValue;
import pankaj.blog.repositories.RoleRepository;

@SpringBootApplication
public class BlogApplicationApiApplication implements CommandLineRunner{
	
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		
		try {
			Role role=new Role();
			role.setId(ConstantValue.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1=new Role();
			role1.setId(ConstantValue.NORAMAL_USER);
			role1.setName("NORAML_USER");
			
			List<Role> roles= List.of(role,role1);
			
			List<Role> roleSave=this.roleRepository.saveAll(roles);
			
			roleSave.forEach(r->{
				System.out.println(r.getName());
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogApplicationApiApplication.class, args);
	}

    @Bean
    ModelMapper modelMapper()
	{
		return new ModelMapper();
	}

    
}
