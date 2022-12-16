package com.mindtree.exceptions;

public class UnableToConnectException extends ServiceException {

	@Override
	public String getMessage() {
		return "Unable to Connect, please try again later";
	}
}
