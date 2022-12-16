package com.mindtree.exceptions;

public class InvalidDateRangeException extends ServiceException {

	@Override
	public String getMessage() {
		return "Invalid Date Range, Please check your input";
	}
}
