package pankaj.blog.payloads;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PostDto {

	private Integer postId;
	private String postTitle;
	private Date addDate;
	private String content;
	private String image;
	
	private UserDto user;
	private CategoryDto category;
	
	private Set<CommentDto> comments=new HashSet<>(); 

}
