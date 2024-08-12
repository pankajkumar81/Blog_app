package pankaj.blog.services.impl;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pankaj.blog.entity.Comment;
import pankaj.blog.entity.Post;
import pankaj.blog.exceptions.ResourceNotFoundException;

import pankaj.blog.payloads.CommentDto;
import pankaj.blog.repositories.CommentRespository;
import pankaj.blog.repositories.PostRepository;
import pankaj.blog.services.CommentService;

@Service
public class CommentServiceImp implements CommentService {
	
	@Autowired
	private CommentRespository commentRespository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	


	@Override
	public CommentDto addComment(CommentDto commentDto,Integer postId) {
		
		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		Comment comment = this.modelMapper.map(commentDto,Comment.class);
		comment.setPost(post);
		Comment SaveComment=this.commentRespository.save(comment);
		return this.modelMapper.map(SaveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		commentRespository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
		commentRespository.deleteById(commentId);
	}

	@Override
	public CommentDto getComment(Integer commentId) {
		// TODO Auto-generated method stub
		Comment comment= commentRespository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment", "commentId", commentId));
		return this.modelMapper.map(comment, CommentDto.class);
	}

}
