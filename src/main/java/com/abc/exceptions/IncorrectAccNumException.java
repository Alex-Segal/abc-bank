package com.abc.exceptions;

public class IncorrectAccNumException extends Exception {

	private static final long serialVersionUID = 7160971699956069351L;

	public IncorrectAccNumException() {
	}
	
	public IncorrectAccNumException(String message){
		super(message);
	}
	
	public IncorrectAccNumException(Throwable cause){
		super(cause);
	}
	
	public IncorrectAccNumException(String message, Throwable cause){
		super(message,cause);
	}

}
