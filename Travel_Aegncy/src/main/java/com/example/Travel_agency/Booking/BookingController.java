package com.example.Travel_agency.Booking;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Fetch all bookings of a user


    @GetMapping("/user/bookings")
    public ResponseEntity<List<Booking>> getUserBookings(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("Session userId in getUserBookings: " + userId); // Debug log
        if (userId == null) {
            System.out.println("No userId in session - user not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<Booking> bookings = bookingService.getUserBookings(userId);
        System.out.println("Bookings retrieved: " + bookings); // Debug log
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    // Book a room for a user
    @PostMapping("/room/{roomId}/checkin/{checkInDate}/checkout/{checkOutDate}"  )
    public ResponseEntity<String> bookRoomForUser(
                                                   @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkInDate,
                                                   @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkOutDate, HttpSession session

            ,@PathVariable Long roomId) {


        Long userId = (Long) session.getAttribute("userId");
        String responseMessage = bookingService.bookRoomForUser(userId, roomId, checkInDate, checkOutDate);
        if (responseMessage.startsWith("Booking done")) {
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED); // Booking successful
        } else {
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST); // Room not available or user not found
        }
    }

    @PostMapping("/hotel/{hotelId}/checkin/{checkInDate}/checkout/{checkOutDate}"  )
    public ResponseEntity<String> bookHotelForUser( @PathVariable Long hotelId,
                                                    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkInDate,
                                                    @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date checkOutDate, HttpSession session) {

           Long userId = (Long) session.getAttribute("userId");
        String responseMessage = bookingService.bookHotelForUser(userId, hotelId, checkInDate, checkOutDate);
        if (responseMessage.startsWith("Booking done")) {
            return new ResponseEntity<>(responseMessage, HttpStatus.CREATED); // Booking successful
        } else {
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST); // Room not available or user not found
        }
    }

    // Endpoint to get the duration of a stay by booking ID
    @GetMapping("/{bookingId}/duration")
    public long getStayDuration(@PathVariable Long bookingId) {
        return bookingService.calculateStayDuration(bookingId);
    }

    // Cancel a booking for a user
    @DeleteMapping(" /room/{roomId}")
    public ResponseEntity<Void> cancelBookingForUser(
            @PathVariable Long roomId,HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        boolean isCanceled = bookingService.cancelBookingForUser(userId, roomId);
        if (isCanceled) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Booking canceled successfully
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // User or booking not found
        }
    }
}
