package pankaj.blog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Comment")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer commentId;
	
	private String content;
	
	@ManyToOne
	private Post post;

}
