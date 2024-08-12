package pankaj.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pankaj.blog.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	Optional<User> findByEmail(String email);

}
