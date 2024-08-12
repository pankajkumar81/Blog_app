package pankaj.blog.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;  

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {

	List<PostDto> content;
	private Integer pageNumber;
	private Integer pageSize;
	private long totalElements;
	private Integer totalPages;
	private boolean lastPage;
}
