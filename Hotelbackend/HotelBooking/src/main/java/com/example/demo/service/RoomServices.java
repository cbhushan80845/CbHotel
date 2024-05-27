package com.example.demo.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.exception.InternalServerException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.exception.RoomNotAvalableException;
import com.example.demo.model.Room;
import com.example.demo.repository.RoomRepository;

@Service
public class RoomServices implements IRoomServices {
	@Autowired
	private RoomRepository roomRepository;

	public RoomServices(RoomRepository roomRepository) {
		super();
		this.roomRepository = roomRepository;
	}

	public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice)
			throws IOException, SerialException, SQLException {
		Room room = new Room();
		room.setRoomType(roomType);
		room.setRoomPrice(roomPrice);
		if (!file.isEmpty()) {
			byte[] photoBytes = file.getBytes();
			Blob PhotoBlob = new SerialBlob(photoBytes);
			room.setPhoto(PhotoBlob);

		}

		return roomRepository.save(room);
	}

	public List<String> getAllRoomTypes() {
		// TODO Auto-generated method stub
		return roomRepository.findDistinctRoomTypes();
	}

	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}

	public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
		Optional<Room> theRoom = roomRepository.findById(roomId);
		if (theRoom.isEmpty()) {
			 throw new ResourceNotFoundException("Sorry, Room not found!");
		}
		Blob photoBlob = theRoom.get().getPhoto();
		if (photoBlob != null) {
			return photoBlob.getBytes(1, (int) photoBlob.length());
		}
		return null;
	}

	public void deleteRoom(Long roomId) {
		Optional<Room> findById = roomRepository.findById(roomId);
		if (findById.isPresent()) {
			roomRepository.deleteById(roomId);
		} else {
			throw new RoomNotAvalableException("Room not deleted");
		}

	}

	@Override
	public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
		Room room = roomRepository.findById(roomId).get();
		if (roomType != null)
			room.setRoomType(roomType);
		if (roomPrice != null)
			room.setRoomPrice(roomPrice);
		if (photoBytes != null && photoBytes.length > 0) {
			try {
				room.setPhoto(new SerialBlob(photoBytes));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new InternalServerException("Fail updating room");
				
			}

		}
		return roomRepository.saveAndFlush(room);
	}

	@Override
	public Optional<Room> getRoomById(Long roomId) {
		// TODO Auto-generated method stub
		return Optional.of(roomRepository.findById(roomId).get());
	}

	@Override
	public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		// TODO Auto-generated method stub
		return roomRepository.findAvailableRoomsByDatesAndType(checkInDate, checkOutDate, roomType);
	}

}
