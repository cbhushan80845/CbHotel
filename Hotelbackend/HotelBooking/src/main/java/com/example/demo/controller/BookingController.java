package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.InvalidBookingRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.BookedRoom;
import com.example.demo.model.Room;
import com.example.demo.response.BookingResponse;
import com.example.demo.response.RoomResponse;
import com.example.demo.service.BookingService;
import com.example.demo.service.RoomServices;
@CrossOrigin(origins = "http://44.222.81.174:5173")
@RestController
@RequestMapping("/bookings")
public class BookingController {
	@Autowired
	private BookingService bookingService;
	@Autowired
	private RoomServices roomServices;
	
	

	public BookingController(BookingService bookingService, RoomServices roomServices) {
		super();
		this.bookingService = bookingService;
		this.roomServices = roomServices;
	}

	@GetMapping("/all-bookings")
	public ResponseEntity<List<BookingResponse>> getAllBookings() {
		List<BookedRoom> bookings = bookingService.getAllBookings();
		List<BookingResponse> bookingResponses = new ArrayList<>();
		for (BookedRoom booking : bookings) {
			BookingResponse bookingResponse = getBookingResponse(booking);
			bookingResponses.add(bookingResponse);
		}

		return ResponseEntity.ok(bookingResponses);

	}

	@PostMapping("/room/{roomId}/booking")
	public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookedRoom bookedRoom) {
		try {
			String confirmationCode = bookingService.saveBooking(roomId, bookedRoom);

			return ResponseEntity
					.ok("Room booked successfully, Your booking confirmation code is :" + confirmationCode);
		 }catch (InvalidBookingRequestException e){
	            return ResponseEntity.badRequest().body(e.getMessage());
	        }
		

	}

	 @GetMapping("/confirmation/{confirmationCode}")
	    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
	        try{
	            BookedRoom booking = bookingService.findByBookingConfirmationCode(confirmationCode);
	            BookingResponse bookingResponse = getBookingResponse(booking);
	            return ResponseEntity.ok(bookingResponse);
	        }catch (ResourceNotFoundException ex){
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	        }
	    }


	@GetMapping("/email/{email}")
	public ResponseEntity<List<BookingResponse>> getBookingByUserEmail(@PathVariable String email) {
		List<BookedRoom> bookings = bookingService.getBookingsByUserEmail(email);
		List<BookingResponse> bookingResponses = new ArrayList<>();
		for (BookedRoom booking : bookings) {
			BookingResponse bookingResponse = getBookingResponse(booking);
			bookingResponses.add(bookingResponse);

		}
		return ResponseEntity.ok(bookingResponses);

	}

	@DeleteMapping("booking/{bookingId}/delete")
	public void cancleBooking(@PathVariable Long bookingId) {
		bookingService.cancelBooking(bookingId);
	}

	 private BookingResponse getBookingResponse(BookedRoom booking) {
	        Room theRoom = roomServices.getRoomById(booking.getRoom().getId()).get();
	        RoomResponse room = new RoomResponse(
	                theRoom.getId(),
	                theRoom.getRoomType(),
	                theRoom.getRoomPrice());
	        return new BookingResponse(
	                booking.getBookingId(), booking.getCheckInDate(),
	                booking.getCheckOutDate(),booking.getGuestFullName(),
	                booking.getGuestEmail(), booking.getNumOfAdults(),
	                booking.getNumOfChildren(), booking.getTotalNumOfGuest(),
	                booking.getBookingConfirmationCode(), room);
	    }

}
