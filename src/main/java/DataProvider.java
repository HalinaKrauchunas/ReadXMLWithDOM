import java.io.FileReader;
import java.io.IOException;
import java.math.*;
import java.text.*;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class DataProvider {

    private static final String XMLDATEFORMAT = "MM/dd/yyyy";
    public static final String DATADIR = "D:\\XMLWithDOM\\ReadXML\\src\\main\\resources\\";
    public static final int SMALL = 10;
    public static final int MEDIUM = 1000;
    public static final int LARGE = 50000;

    public static List<Customer> getData(int limit) {

//		Decide which file to read from.
//		Use 1000 record file for small or medium, 50,000 record file for large
        String filename;
        if (limit == LARGE) {
            filename = DATADIR + "NScustomers.xml";
        } else {
            filename = DATADIR + "customers.xml";
        }

//		Parse JSON file and get the data
        JSONArray inputData;
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj = (JSONObject) parser.parse(new FileReader(filename));
            inputData = (JSONArray) obj.get("result");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }

//		Create a List to contain typed objects
        List<Customer> outputData = new ArrayList<>();

//		Loop through the array up to requested limit,
//		copy JSON objects to ArrayList of POJO's
        for (int i = 0; i < limit; i++) {
            JSONObject item = (JSONObject) inputData.get(i);
            Customer cust = new Customer();

            cust.setId(Math.toIntExact((Long) item.get(Customer.ID)));
            cust.setName((String) item.get(Customer.NAME));
            cust.setPhone((String) item.get(Customer.PHONE));
            cust.setAbout((String) item.get(Customer.ABOUT));
            cust.setAge(Math.toIntExact((Long) item.get(Customer.AGE)));
            cust.setBalance((BigDecimal) item.get(Customer.BALANCE));
            cust.setActive((Boolean) item.get(Customer.ACTIVE));

            DateFormat df = new SimpleDateFormat(XMLDATEFORMAT);
            String joined = (String) item.get(Customer.JOINED);
            try {
                cust.setJoined(df.parse(joined));
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }

            outputData.add(cust);
        }

//		All done, return the data
        return outputData;
    }
}
