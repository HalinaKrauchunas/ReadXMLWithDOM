import javax.xml.parsers.*;
import java.text.*;
import java.util.*;

public class ReadXMLWithDOM {

    public static void main(String[] args) throws ParseException {
        DOMReader domReader = new DOMReader();

        Stopwatch stopwatch = new Stopwatch().start("Parsing XML");
        List<Customer> data = domReader.getDataFromXML(DataProvider.DATADIR + "NScustomers.xml");
        stopwatch.stop();

        for (Customer customer : data){
            System.out.println(customer);
        }
    }
}
