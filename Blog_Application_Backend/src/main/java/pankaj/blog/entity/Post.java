package pankaj.blog.entity;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name="Post")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name = "postid")
	private Integer postId;
	@Column(name = "posttitle")
	private String postTitle;
	@Column(name = "adddate")
	private Date addDate;
	
	@Size(max = 10000)
	@Column(name = "content")
	private String content;
	@Column(name = "image")
	private String image;
	
	@ManyToOne
	private User user;
	@ManyToOne
	@JoinTable(name="category_id")
	private Category category;
	
	@OneToMany(mappedBy ="post",cascade = CascadeType.ALL)
	private Set<Comment> comments=new HashSet<>(); 
	
}
