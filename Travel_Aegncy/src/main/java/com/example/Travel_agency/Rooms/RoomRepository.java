package com.example.Travel_agency.Rooms;

import com.example.Travel_agency.hotelbooking.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Custom query to find rooms by type and hotel
    List<Room> findByRoomTypeAndHotel(String roomType, Hotel hotel);

    // Other query methods you may have:

    //List<Room> findByHotelId(Long hotelId);
    //List<Room> findByAvailableTrue();

}
