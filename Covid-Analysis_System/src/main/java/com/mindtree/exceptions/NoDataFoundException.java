package com.mindtree.exceptions;

public class NoDataFoundException extends ServiceException{

	@Override
	public String getMessage() {
		return "No data present";
	}

	
}
