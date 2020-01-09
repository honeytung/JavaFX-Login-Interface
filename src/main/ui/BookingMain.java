package ui;

import model.*;
import model.exceptions.TimeInvalidException;
import network.TimeAPI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class BookingMain {
    private Scanner scanOption = new Scanner(System.in);
    private StudyRoom room;
    private Student student;
    private int time;
    private int option;
    private int roomNumber;
    private TimeAPI currentTime;

    // Variables for Main
    private static String[] input = {"-1", "-1"};
    private static Scanner scan = new Scanner(System.in);

    // REQUIRES: The roomNumber is greater than 0
    // MODIFIES: This
    // EFFECTS:  Constructor that creates a study room and a student
    public BookingMain(String studentName, String roomNumber) {
        try {
            student = new Student(studentName);
            room = new StudyRoom(Integer.parseInt(roomNumber));
        } catch (IOException e) {
            System.out.println("ERROR: IOException, cannot get file");
        }

        currentTime = TimeAPI.getInstance();

        this.roomNumber = Integer.parseInt(roomNumber);
    }

    // MODIFIES: room
    // EFFECTS:  Check valid time from input time and book the study room at that time
    public boolean bookRoom(int time) {
        try {
            if (!room.checkNotBooked(time)) {
                System.out.println("Error: Room is booked already");
                return false;
            }
            room.bookRoom(time, student);
        } catch (TimeInvalidException e) {
            System.out.println("Error: Time invalid");
            return false;
        }
        System.out.println("Book Success!");
        return true;
    }

    // MODIFIES: room
    // EFFECTS:  Check valid time from input time and cancel study room at that time
    public boolean cancelRoom(int time) {
        try {
            if (room.checkNotBooked(time)) {
                System.out.println("Error: Room is empty");
                return false;
            }
            room.cancelRoom(time, student);
        } catch (TimeInvalidException e) {
            System.out.println("Error: Time invalid");
            return false;
        }
        System.out.println("Cancel Booking Success!");
        return true;
    }

    // EFFECTS:  Print options for user to select
    private void printOptions() {
        if (student.getStudyRooms().size() == 0) {
            System.out.println("Currently Booked Rooms: NONE");
        } else {
            System.out.println("Currently Booked Rooms: ");
            for (StudyRoom room : student.getStudyRooms()) {
                System.out.println(room.getRoomNumber());
            }
        }
        currentTime.updateData();
        System.out.println("\n========================================");
        System.out.println("|| 1 - Check Room Status              ||");
        System.out.println("|| 2 - Book Time                      ||");
        System.out.println("|| 3 - Cancel Time                    ||");
        System.out.println("|| 0 - Log Out                        ||");
        System.out.println("========================================");
        System.out.println("Current Time: " + currentTime.getYear() + "/" + currentTime.getMonth() + "/"
                + currentTime.getDay() + " " + currentTime.getCurrentTime());
    }

    // MODIFIES: This
    // EFFECTS:  Driver code for Booking Room
    private void run() {
        while (true) {
            printOptions();
            System.out.print("\nPlease choose an option to proceed: ");
            option = scanOption.nextInt();
            System.out.print("\n");
            if (option == 1 || option == 2 || option == 3) {
                bookingMain(option);
            } else {
                try {
                    room.saveData();
                } catch (FileNotFoundException e) {
                    System.out.println("ERROR: Cannot save file");
                }
                break;
            }
        }
    }

    // REQUIRES: Variable input must be either 1, 2, or 3
    // MODIFIES: This
    // EFFECTS:  Get status of room, book room, or cancel room depending on the input
    private void bookingMain(int input) {
        if (input == 1) {
            room.printStatus();
        } else if (input == 2) {
            System.out.print("Enter time to Book (in hour from 9-20): ");
            time = scanOption.nextInt();
            bookRoom(time);
        } else if (input == 3) {
            System.out.print("Enter time to Cancel Booking (in hour from 9-20): ");
            time = scanOption.nextInt();
            System.out.print("\n");
            cancelRoom(time);
        }
    }

    // EFFECTS: Main program to run
    public static void main(String[] args) {
        while (true) {
            System.out.println("===== Study Room Management System =====");
            System.out.println("Welcome to Study Room Booking Service!");
            System.out.print("\nPlease enter your name: ");
            input[0] = scan.nextLine();
            System.out.print("Please enter the room number (-1 to quit): ");
            input[1] = scan.nextLine();
            if (input[0].equals("-1") || input[1].equals("-1")) {
                break;
            } else {
                BookingMain roomService = new BookingMain(input[0], input[1]);
                roomService.run();
            }
        }
        System.out.println("Quitting Program");
    }
}
