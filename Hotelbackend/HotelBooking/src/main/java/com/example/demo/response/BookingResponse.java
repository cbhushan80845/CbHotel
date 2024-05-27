package com.example.demo.response;

import java.time.LocalDate;
import java.time.LocalDateTime;





public class BookingResponse {
	private Long bookingId;
	
	private LocalDateTime checkInDate;
	
	private LocalDateTime checkOutDate;
	
	private String guestFullName;
	
	private String guestEmail;

	private int numOfAdults;

	private int numOfChildren;

	private int totalNumOfGuest;
	
	private String bookingConfirmationCode;

	
	private RoomResponse room;
	
	


	public Long getBookingId() {
		return bookingId;
	}


	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}


	public LocalDateTime getCheckInDate() {
		return checkInDate;
	}


	public void setCheckInDate(LocalDateTime checkInDate) {
		this.checkInDate = checkInDate;
	}


	public LocalDateTime getCheckOutDate() {
		return checkOutDate;
	}


	public void setCheckOutDate(LocalDateTime checkOutDate) {
		this.checkOutDate = checkOutDate;
	}


	public String getGuestFullName() {
		return guestFullName;
	}


	public void setGuestFullName(String guestFullName) {
		this.guestFullName = guestFullName;
	}


	public String getGuestEmail() {
		return guestEmail;
	}


	public void setGuestEmail(String guestEmail) {
		this.guestEmail = guestEmail;
	}


	public int getNumOfAdults() {
		return numOfAdults;
	}


	public void setNumOfAdults(int numOfAdults) {
		this.numOfAdults = numOfAdults;
	}


	public int getNumOfChildren() {
		return numOfChildren;
	}


	public void setNumOfChildren(int numOfChildren) {
		this.numOfChildren = numOfChildren;
	}


	public int getTotalNumOfGuest() {
		return totalNumOfGuest;
	}


	public void setTotalNumOfGuest(int totalNumOfGuest) {
		this.totalNumOfGuest = totalNumOfGuest;
	}


	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}


	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}


	public RoomResponse getRoom() {
		return room;
	}


	public void setRoom(RoomResponse room) {
		this.room = room;
	}


	public BookingResponse(Long bookingId, LocalDateTime checkInDate, LocalDateTime checkOutDate,
			String bookingConfirmationCode) {
		super();
		this.bookingId = bookingId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.bookingConfirmationCode = bookingConfirmationCode;
	}


	public BookingResponse(Long bookingId, LocalDateTime checkInDate, LocalDateTime checkOutDate, String guestFullName,
			String guestEmail, int numOfAdults, int numOfChildren, int totalNumOfGuest, String bookingConfirmationCode,
			RoomResponse room) {
		super();
		this.bookingId = bookingId;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.guestFullName = guestFullName;
		this.guestEmail = guestEmail;
		this.numOfAdults = numOfAdults;
		this.numOfChildren = numOfChildren;
		this.totalNumOfGuest = totalNumOfGuest;
		this.bookingConfirmationCode = bookingConfirmationCode;
		this.room = room;
	}


	public BookingResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}
