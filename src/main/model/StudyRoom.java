package model;

import model.exceptions.TimeInvalidException;

import java.io.*;
import java.util.*;

public class StudyRoom {
    private int roomNumber;
    private boolean[] booked = new boolean[12];
    private Student[] bookedStudentNames = new Student[12];
    private String[] timetable = new String[12];
    private List<Student> students;
    private Student noStudent = new Student("NONE");
    private File studyRoomSave;

    // MODIFIES: This
    // EFFECTS:  Constructor for initialize all time slots to available for booking
    public StudyRoom(int roomNumber) throws IOException {
        this.roomNumber = roomNumber;
        this.students = new ArrayList<Student>();
        this.studyRoomSave = new File("data/studyRooms/" + roomNumber + ".txt");

        for (int i = 0; i < bookedStudentNames.length; i++) {
            bookedStudentNames[i] = noStudent;
            booked[i] = false;
            if (i == 0) {
                timetable[i] = "0" + (i + 9) + ":00";
            } else {
                timetable[i] = (i + 9) + ":00";
            }
        }

        try {
            getData();
        } catch (FileNotFoundException e) {
            studyRoomSave.createNewFile();
        }
    }

    // EFFECTS: Return the Study Room Number
    public int getRoomNumber() {
        return roomNumber;
    }

    // EFFECTS: Print out study room book status
    public boolean printStatus() {
        System.out.println("= = = = = = = = = = ROOM " + roomNumber + " = = = = = = = = = =");
        System.out.println("|    Time    |    Availability    |");
        for (int i = 0; i < booked.length; i++) {
            System.out.println("    " + timetable[i] + "             " + (booked[i] ? "NO " : "YES"));
        }
        return true;
    }

    // MODIFIES: This
    // EFFECTS:  Check if room is booked already, if booked return false
    //           If not booked then return true
    public boolean checkNotBooked(int time) throws TimeInvalidException {
        if (time > 20 || time < 9) {
            throw new TimeInvalidException();
        }
        if (!booked[time - 9]) {
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: This
    // EFFECTS:  Book room for that time
    public void bookRoom(int time, Student student) throws TimeInvalidException {
        if (time > 20 || time < 9) {
            throw new TimeInvalidException();
        }
        booked[time - 9] = true;
        bookedStudentNames[time - 9] = student;
        addStudent(student);
    }

    // MODIFIES: This
    // EFFECTS:  Cancel booking for that time
    public void cancelRoom(int time, Student student) throws TimeInvalidException {
        if (time > 20 || time < 9) {
            throw new TimeInvalidException();
        }
        booked[time - 9] = false;
        bookedStudentNames[time - 9] = noStudent;

        boolean checkStudent = false;
        for (int i = 0; i < bookedStudentNames.length; i++) {
            if (bookedStudentNames[i].equals(student)) {
                checkStudent = true;
                break;
            }
        }
        if (!checkStudent) {
            removeStudent(student);
        }
    }

    // MODIFIES: This, Student
    // EFFECTS:  Adds student to array, adds study room to student
    public void addStudent(Student student) {
        if (!students.contains(student)) {
            students.add(student);
            student.addStudyRoom(this);
        }
    }

    // MODIFIES: This, Student
    // EFFECTS:  Removes student from array, removes study room from student
    public void removeStudent(Student student) {
        if (students.contains(student)) {
            students.remove(student);
            student.removeStudyRoom(this);
        }
    }

    // MODIFIES: This
    // EFFECTS:  Read booking status from file and save to booked array and bookedStudentNames array
    private void getData() throws IOException {
        String data;
        int i = 0;
        Scanner scan = new Scanner(studyRoomSave);
        Student student;

        while (scan.hasNextLine()) {
            data = scan.nextLine();
            if (data.equals("NONE")) {
                booked[i] = false;
                bookedStudentNames[i] = noStudent;
            } else {
                booked[i] = true;
                student = new Student(data);
                bookedStudentNames[i] = student;
                addStudent(student);
            }
            i++;
        }
        scan.close();
    }

    // EFFECTS:  Write current booking status back to file
    public void saveData() throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(studyRoomSave);

        for (int i = 0; i < booked.length; i++) {
            if (!bookedStudentNames[i].equals(noStudent)) {
                writer.println(bookedStudentNames[i].getName());
            } else {
                writer.println("NONE");
            }
        }
        writer.close();
    }

    @Override
    // EFFECTS:  Compare StudyRoom Objects with same room number and room status will return true
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StudyRoom studyRoom = (StudyRoom) o;
        return roomNumber == studyRoom.roomNumber
                && Arrays.equals(booked, studyRoom.booked)
                && Arrays.equals(bookedStudentNames, studyRoom.bookedStudentNames)
                && Objects.equals(students, studyRoom.students);
    }

    @Override
    // EFFECTS:  HashCode for StudyRoom
    public int hashCode() {
        int result = Objects.hash(roomNumber, students);
        result = 31 * result + Arrays.hashCode(booked);
        result = 31 * result + Arrays.hashCode(bookedStudentNames);
        return result;
    }
}
