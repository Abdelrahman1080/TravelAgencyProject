package com.example.Travel_agency.Rooms;

import com.example.Travel_agency.hotelbooking.Hotel;
import com.example.Travel_agency.hotelbooking.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    @Autowired
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Room addRoom(RoomRequestDTO roomRequest) {
        Hotel hotel = hotelRepository.findById(roomRequest.getHotelId())
                .orElseThrow(() -> new IllegalArgumentException("Hotel with ID " + roomRequest.getHotelId() + " not found"));
        Room room = new Room(

                roomRequest.getRoomNumber(),
                roomRequest.getRoomType(),
                roomRequest.getCapacity(),
                roomRequest.getPricePerNight(),
                hotel

        );
        // Assuming a HotelRepository is available to fetch the hotel
        // This is a placeholder; implement actual hotel fetching logic
        // Hotel hotel = hotelRepository.findById(roomRequest.getHotelId()).orElseThrow(() -> new RuntimeException("Hotel not found"));
        // room.setHotel(hotel);
        return roomRepository.save(room);
    }

    public List<Room> searchRooms(String roomType, Long hotelId) {
        // Placeholder: Implement logic to filter rooms by type and hotelId
        return roomRepository.findAll().stream()
                .filter(room -> room.getRoomType().equalsIgnoreCase(roomType) && room.getHotel().getId().equals(hotelId))
                .toList();
    }

    public Room getRoomById(Long roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        return room.orElse(null);
    }

    public boolean checkRoomAvailability(Long roomId) {
        Room room = getRoomById(roomId);
        return room != null && room.isAvailable();
    }

    public Room markRoomAsBooked(Long roomId) {
        Room room = getRoomById(roomId);
        if (room != null && room.isAvailable()) {
            room.setAvailable(false);
            return roomRepository.save(room);
        }
        return null;
    }

    public Room markRoomAsAvailable(Long roomId) {
        Room room = getRoomById(roomId);
        if (room != null && !room.isAvailable()) {
            room.setAvailable(true);
            return roomRepository.save(room);
        }
        return null;
    }
}