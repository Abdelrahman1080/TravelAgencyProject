package com.example.Travel_agency.Booking;

import com.example.Travel_agency.Rooms.RoomService;
import com.example.Travel_agency.Rooms.Room;
import com.example.Travel_agency.hotelbooking.Hotel;
import com.example.Travel_agency.hotelbooking.HotelRepository;
import com.example.Travel_agency.usermanagement.User;
import com.example.Travel_agency.usermanagement.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.temporal.Temporal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private HotelRepository hotelRepository;

    private EntityManager entityManager;

    public BookingService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Fetch all bookings of a user by userId

    public List<Booking> getUserBookings(Long userId) {
        try {
            entityManager.clear(); // Ensure fresh data from the database
            List<Booking> bookings = entityManager.createQuery(
                            "SELECT b FROM Booking b WHERE b.user.id = :userId", Booking.class)
                    .setParameter("userId", userId)
                    .getResultList();
            System.out.println("Fetched bookings for userId " + userId + ", count: " + (bookings != null ? bookings.size() : 0));
            return bookings;
        } catch (Exception e) {
            System.err.println("Error fetching bookings for userId " + userId + ": " + e.getMessage());
            return Collections.emptyList();
        }
    }





    // Book a room for a user with check-in and check-out dates
    public String bookRoomForUser(Long userId, Long roomId, Date checkInDate, Date checkOutDate) {
        Optional<User> user = userRepository.findById(userId);

        // Check if the user exists and room is available for booking
        if (user.isPresent()  ) {
            Room bookedRoom = new Room(); // Mark room as booked
            if (bookedRoom != null) {
                // Create and save the booking
                Booking booking = new Booking();
                booking.setUser(user.get());
                booking.setRoom(bookedRoom);
                booking.setCheckInDate(checkInDate);  // Set check-in date
                booking.setCheckOutDate(checkOutDate);  // Set check-out date
                booking.setCancelled(false);  // Set isCancelled to false by default

                // Save the booking to the database
                bookingRepository.save(booking);

                return "Booking done for " + user.get().getUsername();  // Return confirmation message
            }
        }
        return "Room is not available or user not found";  // Error message if room unavailable or user not found
    }

    public String bookHotelForUser(Long userId, Long hotelId , Date checkInDate, Date checkOutDate) {
        Optional<User> user = userRepository.findById(userId);
        Hotel bookedHotel = hotelRepository.getReferenceById(hotelId);
        // Check if the user exists and room is available for booking
      try {
                // Create and save the booking
                Booking booking = new Booking();
                booking.setUser(user.get());
                booking.setHotel(bookedHotel);
                booking.setCheckInDate(checkInDate);  // Set check-in date
                booking.setCheckOutDate(checkOutDate);  // Set check-out date
                booking.setCancelled(false);  // Set isCancelled to false by default

                // Save the booking to the database
          bookingRepository.flush();
          bookingRepository.saveAndFlush(booking);
                bookingRepository.save(booking);

                return "Booking done for " + user.get().getUsername();  // Return confirmation message

        }
      catch (Exception e) {
          return "Hotel is not available or user not found";
      }
    }

    // Method to calculate the duration of stay in days
    public long calculateStayDuration(Long bookingId) {
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            Date checkInDate = booking.getCheckInDate();
            Date checkOutDate = booking.getCheckOutDate();

            // Calculate the duration between check-in and check-out dates
            return ChronoUnit.DAYS.between((Temporal) checkInDate, (Temporal) checkOutDate);
        }
        return 0;  // Return 0 if the booking doesn't exist
    }

    // Cancel a booking for a user
    public boolean cancelBookingForUser(Long userId, Long roomId) {
        Optional<User> user = userRepository.findById(userId);

        // Check if the user exists
        if (user.isPresent()) {
            // Find the booking associated with the room and user
            Optional<Booking> booking = user.get().getBookings().stream()
                    .filter(b -> b.getRoom().getId().equals(roomId))
                    .findFirst();

            // If booking exists, cancel it
            if (booking.isPresent()) {
                Booking canceledBooking = booking.get();
                user.get().getBookings().remove(canceledBooking);  // Remove booking from user's list
                bookingRepository.delete(canceledBooking);  // Delete the booking from the database
                userRepository.save(user.get());  // Save the updated user

                return true; // Return true to indicate successful cancellation
            }
        }
        return false; // Return false if user or booking is not found
    }
}
