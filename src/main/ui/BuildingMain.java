package ui;

import model.Building;
import model.LectureBuilding;
import model.StudentBuilding;

public class BuildingMain {
    public static void main(String[] args) {
        Building lectureBuilding1 = new LectureBuilding("Kaiser");
        Building lectureBuilding2 = new LectureBuilding("Macleod");
        Building lectureBuilding3 = new LectureBuilding("ICICS");
        Building lectureBuilding4 = new LectureBuilding("Engineering Design Centre");

        Building studentBuilding1 = new StudentBuilding("Life");
        Building studentBuilding2 = new StudentBuilding("Nest");

        lectureBuilding1.add(lectureBuilding2);
        lectureBuilding2.add(lectureBuilding3);
        lectureBuilding2.add(lectureBuilding4);
        lectureBuilding1.add(studentBuilding1);
        lectureBuilding1.add(studentBuilding2);

        lectureBuilding1.print("UBC");
    }
}
