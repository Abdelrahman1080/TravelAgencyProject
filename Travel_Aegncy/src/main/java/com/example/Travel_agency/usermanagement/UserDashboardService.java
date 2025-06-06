package com.example.Travel_agency.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.Travel_agency.Booking.BookingService;
import com.example.Travel_agency.Booking.Booking;

import java.util.List;

@Service
public class UserDashboardService {
    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;


    public UserDashboard getUserDashboard(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        UserDashboard dashboard = new UserDashboard();

        // Fetch user profile
        User user = userService.getUserProfile(userId);

        // Fetch bookings
        List<Booking> hotelBookings = bookingService.getUserBookings(userId);

        // Process and populate dashboard
        dashboard.processData(user, hotelBookings );

        return dashboard;
    }
}
