package pankaj.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pankaj.blog.entity.Category;
import pankaj.blog.entity.Post;
import pankaj.blog.entity.User;

public interface PostRepository extends JpaRepository<Post,Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
		
}

