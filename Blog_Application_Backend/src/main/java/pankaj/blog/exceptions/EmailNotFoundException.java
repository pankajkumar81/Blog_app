package pankaj.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailNotFoundException extends RuntimeException {

	@java.io.Serial
    static final long serialVersionUID = -7034897190745766939L;
	private String resourceName;
	private String fieldName;
	private String fieldEmail;
	

	public EmailNotFoundException(String resourceName, String fieldName, String fieldEmail) {
		super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldEmail));
		// TODO Auto-generated constructor stub
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldEmail = fieldEmail;
	}
	
}
