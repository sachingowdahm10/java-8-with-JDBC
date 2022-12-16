package com.mindtree.exceptions;

public class InvalidStateCodeException extends ServiceException {

	@Override
	public String getMessage() {
		return "Invalid State code, please check your input";
	}
}
