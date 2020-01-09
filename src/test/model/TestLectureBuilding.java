package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLectureBuilding {
    private Building lectureBuilding1;
    private Building lectureBuilding2;
    private Building lectureBuilding3;
    private Building lectureBuilding4;

    private Building studentBuilding1;
    private Building studentBuilding2;

    @BeforeEach
    public void runBefore() {
        lectureBuilding1 = new LectureBuilding("Kaiser");
        lectureBuilding2 = new LectureBuilding("Macleod");
        lectureBuilding3 = new LectureBuilding("ICICS");
        lectureBuilding4 = new LectureBuilding("Engineering Design Centre");

        studentBuilding1 = new StudentBuilding("Life");
        studentBuilding2 = new StudentBuilding("Nest");
    }

    @Test
    public void testAdd() {
        lectureBuilding1.add(lectureBuilding2);
        lectureBuilding2.add(lectureBuilding3);
        lectureBuilding2.add(lectureBuilding4);
        lectureBuilding1.add(studentBuilding1);
        lectureBuilding1.add(studentBuilding2);
    }
}
