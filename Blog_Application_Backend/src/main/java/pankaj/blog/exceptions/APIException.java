package pankaj.blog.exceptions;

public class APIException extends RuntimeException {

	public APIException() {
		super();
	}
	
	public APIException(String message) {
		super(message);
	}

}
