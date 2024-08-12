package pankaj.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import pankaj.blog.payloads.ApiResponse;
import pankaj.blog.payloads.ConstantValue;
import pankaj.blog.payloads.PostDto;
import pankaj.blog.payloads.PostResponse;
import pankaj.blog.services.FileService;
import pankaj.blog.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	//private Logger logger=LoggerFactory.getLogger(PostController.class);

	@PostMapping("user/{userId}/category/{categoryId}/create")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId)
	{
		PostDto createPost=postService.createPost(postDto, userId, categoryId);
		
		return new ResponseEntity<>(createPost,HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
			@PathVariable Integer postId)
	{
		PostDto updatePost=postService.updatePost(postDto,postId);
		return new ResponseEntity<>(updatePost,HttpStatus.OK);
	}
	
	@GetMapping("/gets")
	public ResponseEntity<PostResponse> getPosts(
			@RequestParam(value="pageNumber",defaultValue=ConstantValue.pageNumber,required = false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue = ConstantValue.pageSize,required = false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue = ConstantValue.sortBy,required = false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = ConstantValue.sortDir,required = false) String sortDir)
	{
		PostResponse getPosts=postService.getPosts(pageNumber, pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(getPosts,HttpStatus.OK);
	}
	
	@GetMapping("/get/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer postId)
	{
		PostDto getPost=postService.getPost(postId);
		
		return new ResponseEntity<>(getPost,HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId)
	{
		postService.deletePost(postId);
		
		return new ApiResponse("Post has been deleted Successfully",true);
	}
	//@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/user/{user_id}")
	public ResponseEntity<List <PostDto>> getByUser(@PathVariable Integer userId)
	{
		 List<PostDto> posts=postService.findByUser(userId);
		 
		 return new ResponseEntity<>(posts,HttpStatus.OK);
		
	}
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List <PostDto>> getByCategory(@PathVariable Integer categoryId)
	{
		 List<PostDto> posts=postService.findByCategory(categoryId);
		 
		 return new ResponseEntity<>(posts,HttpStatus.OK);
		
	}
	
	//Post Image Upload
	@PostMapping("/image/upload/{postid}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postid) throws IOException
	{
		PostDto postDto=this.postService.getPost(postid);
		String fileName = this.fileService.uploadImage(path, image);
		
		
		postDto.setImage(fileName);
		PostDto updatePost = postService.updatePost(postDto, postid);
		return new ResponseEntity<>(updatePost,HttpStatus.OK);
	}
	
	//method to serve file
	@GetMapping(value="/image/{imageName}",produces = MediaType.IMAGE_PNG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileService.getResource(path, imageName);
		
		
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		StreamUtils.copy(resource,response.getOutputStream());
	}
}
