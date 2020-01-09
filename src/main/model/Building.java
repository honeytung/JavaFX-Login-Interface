package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Building {
    protected String name;
    protected List<Building> buildings;

    // MODIFIES: This
    // EFFECTS:  Constructor for initialize name and list of buildings
    public Building(String name) {
        this.name = name;
        this.buildings = new ArrayList<>();
    }

    // MODIFIES: This
    // EFFECTS:  Add building to list of buildings
    public void add(Building building) {
        buildings.add(building);
    }

    // EFFECTS:  Prints building name and buildings it connects to
    protected abstract void print(int indent);

    // EFFECTS:  Prints the building name with university name
    public void print(String universityName) {
        System.out.println(universityName + " has the following buildings: ");
        for (Building i : buildings) {
            i.print(3);
        }
    }

    // EFFECTS:  Generates string of indentations
    protected String getIndent(int indent) {
        String indentation = "";
        for (int i = 0; i < indent; i++) {
            indentation += " ";
        }

        return indentation;
    }
}
