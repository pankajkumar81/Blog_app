package pankaj.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import pankaj.blog.entity.Category;
import pankaj.blog.entity.Post;
import pankaj.blog.entity.User;
import pankaj.blog.exceptions.ResourceNotFoundException;
import pankaj.blog.payloads.PostDto;
import pankaj.blog.payloads.PostResponse;
import pankaj.blog.repositories.CategoryRepository;
import pankaj.blog.repositories.PostRepository;
import pankaj.blog.repositories.UserRepository;
import pankaj.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		// TODO Auto-generated method stub
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "userId", userId));
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		Post post=this.modelMapper.map(postDto,Post.class);
		post.setAddDate(new Date());
		post.setImage("Default.png");
		post.setUser(user);
		post.setCategory(category);
		
		Post savePost=this.postRepository.save(post);
		
		return this.modelMapper.map(savePost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto,Integer postId) {
		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "postId", postId));
		post.setPostTitle(postDto.getPostTitle());
		post.setContent(postDto.getContent());
		post.setImage(postDto.getImage());
		postRepository.save(post);
		
		return this.modelMapper.map(post, PostDto.class );
	}

	@Override
	public PostResponse getPosts(Integer pageNumber,Integer pageSize,String sortBy, String sortDir) {
		
		Sort sorted=(sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize,sorted);
		//Pageable pageable=PageRequest.of(pageNumber, pageSize,Sort.by(sortby));
		

		
		Page<Post> pagePost=this.postRepository.findAll(pageable);
		List<Post> allPost=pagePost.getContent();
		
		
		List<PostDto> postDtos=allPost.stream().map(post->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPost(Integer postId) {
		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {

		Post post=this.postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","postId", postId));	
		this.postRepository.delete(post);

	}

	@Override
	public List<PostDto> findByUser(Integer userId) {
		
		User user=this.userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "user_id", userId));
		List<Post> posts=this.postRepository.findByUser(user);
		List<PostDto> postdtos=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postdtos;
	}

	@Override
	public List<PostDto> findByCategory(Integer categoryId) {
		
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts=this.postRepository.findByCategory(category);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}
	

}
