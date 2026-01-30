package com.hstls.intigration.exception;

@SuppressWarnings("serial")
public class RoomAlreadyExistsException extends RuntimeException{
	public RoomAlreadyExistsException(String message) {
		super(message);
	}
}
