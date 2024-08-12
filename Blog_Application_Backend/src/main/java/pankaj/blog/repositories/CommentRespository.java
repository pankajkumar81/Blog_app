package pankaj.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pankaj.blog.entity.Comment;

public interface CommentRespository extends JpaRepository<Comment, Integer> {

}
