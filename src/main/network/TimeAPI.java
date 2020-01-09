package network;

import org.json.JSONObject;

import java.net.URL;
import java.util.Scanner;


public class TimeAPI {
    private static final TimeAPI instance = new TimeAPI();

    private String dateTime;
    private String jsonData;
    private JSONObject json;
    private String year;
    private String month;
    private String day;
    private String currentTime;

    public static TimeAPI getInstance() {
        return instance;
    }

    private TimeAPI() {
        updateData();
    }

    // EFFECTS:  Returns year String
    public String getYear() {
        return year;
    }

    // EFFECTS:  Returns month String
    public String getMonth() {
        return month;
    }

    // EFFECTS:  Returns day String
    public String getDay() {
        return day;
    }

    // EFFECTS:  Returns currentTime String
    public String getCurrentTime() {
        return currentTime;
    }

    // MODIFIES: this
    // EFFECTS:  Receive current json time and parse into variables
    public void updateData() {
        jsonData = "";
        try {
            String address = "http://worldtimeapi.org/api/ip";
            URL url = new URL(address);

            Scanner scan = new Scanner(url.openStream());
            while (scan.hasNext()) {
                jsonData += scan.nextLine();
            }
            scan.close();
        } catch (Exception e) {
            System.out.println("ERROR: Cannot grab data from URL");
        }

        if (jsonData != null) {
            json = new JSONObject(jsonData);
        }

        dateTime = json.getString("utc_datetime");
        parseData();
    }

    // REQUIRES: dataTime already has data and dataTime follow correct format
    // MODIFIES: this
    // EFFECTS:  Parse data into variables
    private void parseData() {
        int i = 0;

        // Parse data into Year, Month, Date
        year = dateTime.substring(0, 4);
        month = dateTime.substring(5, 7);
        day = dateTime.substring(8, 10);
        currentTime = dateTime.substring(11, 19);
    }
}
