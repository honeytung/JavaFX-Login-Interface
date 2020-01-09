package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Student {
    private String name;
    private List<StudyRoom> studyRooms;

    // MODIFIES: This
    // EFFECTS:  Constructor to initialize the student name and get the study rooms booked from file
    public Student(String name) {
        this.name = name;
        this.studyRooms = new ArrayList<StudyRoom>();
    }

    // MODIFIES: This, StudyRoom
    // EFFECTS:  Adds study room to array, adds student to study room
    public void addStudyRoom(StudyRoom room) {
        if (!studyRooms.contains(room)) {
            studyRooms.add(room);
            room.addStudent(this);
        }
    }

    // MODIFIES: This, StudyRoom
    // EFFECTS:  Removes study room from array, removes student from study room if no time slots is booked by
    //           that student
    public void removeStudyRoom(StudyRoom room) {
        if (studyRooms.contains(room)) {
            studyRooms.remove(room);
            room.removeStudent(this);
        }
    }

    // EFFECTS:  Returns the name of the student
    public String getName() {
        return name;
    }

    // EFFECTS:  Returns the list of study rooms that the student currently booked
    public List<StudyRoom> getStudyRooms() {
        return studyRooms;
    }

    @Override
    // EFFECTS:  Compare Student Objects with same name and same study rooms will return true
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Student student = (Student) o;
        return Objects.equals(name, student.name) && Objects.equals(studyRooms, student.studyRooms);
    }

    @Override
    // EFFECTS:  HashCode for Student
    public int hashCode() {
        return Objects.hash(name, studyRooms);
    }
}
