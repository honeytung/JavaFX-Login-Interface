package model;
import model.exceptions.TimeInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for StudyRoom
class TestStudyRoom {
    private StudyRoom room;
    private int roomNumber = 999;

    @BeforeEach
    public void runBefore() throws IOException {
        room = new StudyRoom(roomNumber);
    }

    @Test
    public void testgetRoomNumber() {
        assertEquals(roomNumber, room.getRoomNumber());
    }

    @Test
    public void testcheckNotBooked() throws TimeInvalidException, IOException {
        Student student = new Student("TestStudent1");

        room.bookRoom(9, student);
        assertFalse(room.checkNotBooked(9));

        room.cancelRoom(9, student);
        assertTrue(room.checkNotBooked(9));

        assertThrows(TimeInvalidException.class, ()->{room.checkNotBooked(8);});
        assertThrows(TimeInvalidException.class, ()->{room.checkNotBooked(21);});
    }

    @Test
    public void testbookRoom() throws TimeInvalidException, IOException {
        room.bookRoom(9, new Student("TestStudent1"));
        assertFalse(room.checkNotBooked(9));
        room.bookRoom(20, new Student("TestStudent2"));
        assertFalse(room.checkNotBooked(20));
        room.bookRoom(15, new Student("TestStudent3"));
        assertFalse(room.checkNotBooked(15));

        room.cancelRoom(9, new Student("TestStudent1"));
        room.cancelRoom(20, new Student("TestStudent2"));
        room.cancelRoom(15, new Student("TestStudent3"));

        assertThrows(TimeInvalidException.class, ()->{room.bookRoom(8, new Student("TestStudent1"));});
        assertThrows(TimeInvalidException.class, ()->{room.bookRoom(21, new Student("TestStudent1"));});
    }

    @Test
    public void testcancelRoom() throws TimeInvalidException, IOException {
        room.bookRoom(9, new Student("TestStudent1"));
        room.cancelRoom(9, new Student("TestStudent1"));
        assertTrue(room.checkNotBooked(9));

        room.bookRoom(20, new Student("TestStudent2"));
        room.cancelRoom(20, new Student("TestStudent2"));
        assertTrue(room.checkNotBooked(20));

        room.bookRoom(15, new Student("TestStudent3"));
        room.cancelRoom(15, new Student("TestStudent3"));
        assertTrue(room.checkNotBooked(15));

        room.bookRoom(9, new Student("TestStudent1"));
        room.bookRoom(10, new Student("TestStudent1"));
        room.cancelRoom(9, new Student("TestStudent1"));
        room.cancelRoom(10, new Student("TestStudent1"));
        assertTrue(room.checkNotBooked(9));
        assertTrue(room.checkNotBooked(10));

        assertThrows(TimeInvalidException.class, ()->{room.cancelRoom(8, new Student("TestStudent1"));});
        assertThrows(TimeInvalidException.class, ()->{room.cancelRoom(21, new Student("TestStudent1"));});
    }

    @Test
    public void testgetData() throws IOException, TimeInvalidException {
        File file = new File("data/studyRooms/" + Integer.toString(roomNumber) + ".txt");
        file.createNewFile();

        Scanner scan = new Scanner(file);
        String data = scan.nextLine();
        scan.close();

        if (data.equals("NONE")) {
            assertTrue(room.checkNotBooked(9));
        } else {
            assertFalse(room.checkNotBooked(9));
        }

    }

    @Test
    public void testsaveData() throws IOException, TimeInvalidException {
        room.bookRoom(9, new Student("TestStudent1"));
        room.saveData();
        File file = new File("data/studyRooms/" + Integer.toString(roomNumber) + ".txt");
        file.createNewFile();

        Scanner scan = new Scanner(file);
        String data = scan.nextLine();
        scan.close();

        assertEquals("TestStudent1", data);

        room.cancelRoom(9, new Student("TestStudent1"));
        room.saveData();
    }

    @Test
    public void testprintStatus() {
        assertTrue(room.printStatus());
    }

    @Test
    public void testhashCode() throws IOException {
        StudyRoom room1 = new StudyRoom(roomNumber);
        StudyRoom room2 = new StudyRoom(998);

        assertEquals(room.hashCode(), room1.hashCode());
        assertNotEquals(room.hashCode(), room2.hashCode());
    }

    @Test
    public void testequals() throws IOException {
        StudyRoom room1 = new StudyRoom(roomNumber);
        assertTrue(room1.equals(room));
    }
}