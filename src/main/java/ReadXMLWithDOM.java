import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.text.*;
import java.util.*;

public class ReadXMLWithDOM {

    public static void main(String[] args) throws ParseException, XPathExpressionException {
        DOMReader domReader = new DOMReader();

        List<Customer> data = domReader.getDataFromXML(DataProvider.DATADIR + "customers.xml", "//customer[age >= 65]");

        for (Customer customer : data){
            System.out.println(customer);
        }
    }
}
