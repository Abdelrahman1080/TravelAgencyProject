package com.example.Travel_agency.Booking;

import com.example.Travel_agency.Rooms.Room;
import com.example.Travel_agency.hotelbooking.Hotel;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

import com.example.Travel_agency.usermanagement.User;

@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User associated with the booking

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = true)
    private Room room; // Room being booked

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = true)
    private Hotel hotel; // Room being booked


    @Column(nullable = true)

    private Date checkInDate;

    @Column(nullable = true)

    private Date checkOutDate;

    @Column(nullable = true)
    private boolean isCancelled = false;

    // Constructors
    public Booking() {}

    public Booking(User user, Room room, Date checkInDate, Date checkOutDate) {
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public void setHotel( Hotel hotel) {
        this.hotel=hotel;
    }
}
