package pankaj.blog.services;

import pankaj.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto addComment(CommentDto commentDto, Integer postId);
	void deleteComment(Integer commentId);
	CommentDto getComment(Integer commentId );
}
