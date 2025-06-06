package com.example.Travel_agency.Rooms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // Get all rooms (from DB or external API)
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    // Add a new room linked to a hotel
    @PostMapping
    public Room addRoom(@RequestBody RoomRequestDTO room) {
        Room savedRoom = null;
        try {
            savedRoom = roomService.addRoom(room);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return savedRoom;
    }

    // Search rooms by type and hotel ID
    @GetMapping("/search")
    public ResponseEntity<List<Room>> searchRooms(@RequestParam String roomType, @RequestParam Long hotelId) {
        List<Room> rooms = roomService.searchRooms(roomType, hotelId);
        return rooms != null && !rooms.isEmpty() ? new ResponseEntity<>(rooms, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Get room details by room ID
    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId);
        return room != null ? new ResponseEntity<>(room, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Check room availability by room ID
    @GetMapping("/{roomId}/availability")
    public ResponseEntity<String> checkRoomAvailability(@PathVariable Long roomId) {
        boolean isAvailable = roomService.checkRoomAvailability(roomId);
        return isAvailable ? new ResponseEntity<>("Room is available", HttpStatus.OK)
                : new ResponseEntity<>("Room is not available", HttpStatus.OK);
    }

    // Mark room as booked (unavailable)
    @PutMapping("/{roomId}/book")
    public ResponseEntity<Room> markRoomAsBooked(@PathVariable Long roomId) {
        Room room = roomService.markRoomAsBooked(roomId);
        return room != null ? new ResponseEntity<>(room, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Mark room as available (on cancellation)
    @PutMapping("/{roomId}/available")
    public ResponseEntity<Room> markRoomAsAvailable(@PathVariable Long roomId) {
        Room room = roomService.markRoomAsAvailable(roomId);
        return room != null ? new ResponseEntity<>(room, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
