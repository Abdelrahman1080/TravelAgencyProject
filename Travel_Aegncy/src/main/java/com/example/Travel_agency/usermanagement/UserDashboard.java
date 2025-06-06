package com.example.Travel_agency.usermanagement;

import com.example.Travel_agency.Booking.Booking;
import com.example.Travel_agency.hotelbooking.Hotel;
import com.example.Travel_agency.Rooms.Room;

import java.time.temporal.Temporal;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.time.temporal.ChronoUnit;

public class UserDashboard {
    private String username;
    private List<Map<String, String>> hotelBookings;
    private List<Map<String, String>> eventBookings;

    public void processData(User user, List<Booking> hotelBookings) {
        this.username = user != null ? user.getUsername() : "Unknown User";
        processHotelBookings(hotelBookings);

    }

    private void processHotelBookings(List<Booking> bookings) {
        hotelBookings = new ArrayList<>();
        if (bookings == null || bookings.isEmpty()) return;

        for (Booking booking : bookings) {
            if (booking == null || booking.getRoom() == null) continue;

            Room room = booking.getRoom();
            Hotel hotel = room.getHotel();

            Map<String, String> bookingInfo = new HashMap<>();
            bookingInfo.put("hotelName", hotel != null ? hotel.getName() : "Unknown");
            bookingInfo.put("roomType", "room.getCapacity()");
            bookingInfo.put("duration", String.valueOf(
                ChronoUnit.DAYS.between((Temporal) booking.getCheckInDate(), (Temporal)booking.getCheckOutDate())
            ) + " days");

            hotelBookings.add(bookingInfo);
        }
    }


    // Getters
    public String getUsername() { return username; }
    public List<Map<String, String>> getHotelBookings() { return hotelBookings; }
    public List<Map<String, String>> getEventBookings() { return eventBookings; }

    @Override
    public String toString() {
        return "UserDashboard{" +
               "username='" + username + '\'' +
               ", hotelBookings=" + hotelBookings +
               ", eventBookings=" + eventBookings +
               '}';
    }
}
