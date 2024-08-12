package pankaj.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pankaj.blog.payloads.CommentDto;
import pankaj.blog.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/create")
	public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto,
			@PathVariable Integer postId) {
		
		CommentDto commentDto2=commentService.addComment(commentDto,postId);
		return new ResponseEntity<CommentDto>(commentDto2,HttpStatus.CREATED);
	}
	
	@DeleteMapping("delete/{commentId}")
	public String delete(@PathVariable Integer commentId)
	{
		this.commentService.deleteComment(commentId);
		return "Deleted Suceesfully";
	}
	
	@GetMapping("get/{commentId}")
	public ResponseEntity<CommentDto> getComment(@PathVariable Integer commentId)
	{
		CommentDto commentDto=this.commentService.getComment(commentId);
		return new ResponseEntity<CommentDto>(commentDto,HttpStatus.OK);
	}
}
