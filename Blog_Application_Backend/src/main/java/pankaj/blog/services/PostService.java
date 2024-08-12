package pankaj.blog.services;


import java.util.List;

import pankaj.blog.payloads.PostDto;
import pankaj.blog.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	PostDto updatePost(PostDto postDto,Integer postId);
	PostResponse getPosts(Integer pageNumber,Integer pageSize, String sortBy, String sortDir);
	PostDto getPost(Integer postId);
	void deletePost(Integer postId);
	List<PostDto> findByUser(Integer userId);
	List<PostDto> findByCategory(Integer categoryId);
	
}
