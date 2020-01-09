package model;
import model.exceptions.TimeInvalidException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Student
public class TestStudent {
    private Student student;
    private String name = "TestStudent1";
    private StudyRoom room;
    private int roomNumber = 999;

    @BeforeEach
    public void runBefore() throws IOException {
        student = new Student(name);
        room = new StudyRoom(roomNumber);
    }

    @Test
    public void testaddStudyRoom() {
        student.addStudyRoom(room);
        assertTrue(student.getStudyRooms().contains(room));

        student.removeStudyRoom(room);
        assertFalse(student.getStudyRooms().contains(room));
    }

    @Test
    public void testremoveStudyRoom() {
        student.addStudyRoom(room);
        assertTrue(student.getStudyRooms().contains(room));

        student.removeStudyRoom(room);
        assertFalse(student.getStudyRooms().contains(room));
    }

    @Test
    public void testgetName() {
        assertEquals(name, student.getName());
    }

    @Test
    public void testequals() throws IOException {
        Student student1 = new Student("TestStudent1");
        Student student2 = new Student("TestStudent2");

        assertTrue(student.equals(student1));
        assertFalse(student.equals(student2));
    }

    @Test
    public void testhasCode() throws IOException {
        Student student1 = new Student("TestStudent1");
        Student student2 = new Student("TestStudent2");

        assertEquals(student.hashCode(), student1.hashCode());
        assertNotEquals(student.hashCode(), student2.hashCode());
    }
}
