import org.w3c.dom.*;
import org.xml.sax.*;

import javax.xml.parsers.*;
import javax.xml.xpath.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;

public class DOMReader {

    @SuppressWarnings("unused")
    private static final String XMLDATEFORMAT = "yyyy-MM-dd'T'HH:mm:ss";
//    private static final String NSURI = "http://www.example.org/customers";

    public List<Customer> getDataFromXML(String filename, String filter) throws ParseException,
        XPathExpressionException {

        List<Customer> data = new ArrayList<>();

        File xmlFile = new File(filename);
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder documentBuilder = factory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFile);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        XPathExpression xPathExpression = xPath.compile(filter);
        NodeList nodeList = (NodeList) xPathExpression.evaluate(document, XPathConstants.NODESET);

//        NodeList nodeList = document.getElementsByTagName("customer");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Customer customer = new Customer();
            data.add(customer);

            Element customerElement = (Element) nodeList.item(i);
            String idAsString = customerElement.getAttribute(Customer.ID);
            customer.setId(Integer.parseInt(idAsString));

            customer.setName(getTextFromElement(customerElement, Customer.NAME));
            customer.setAge(Integer.parseInt(getTextFromElement(customerElement, Customer.AGE)));
            customer.setBalance(new BigDecimal(getTextFromElement(customerElement, Customer.BALANCE)));
            customer.setActive(Boolean.parseBoolean(getTextFromElement(customerElement, Customer.ACTIVE)));
            customer.setAbout(getTextFromElement(customerElement, Customer.ABOUT));
            customer.setPhone(getTextFromElement(customerElement, Customer.PHONE));

            String joined = getTextFromElement(customerElement, Customer.JOINED);
            DateFormat dateFormat = new SimpleDateFormat(XMLDATEFORMAT);
            customer.setJoined(dateFormat.parse(joined));
        }
        return data;
    }

    private String getTextFromElement(Element customerElement, String elementName) {

        Element node = (Element) customerElement.getElementsByTagName(elementName).item(0);
        return node.getTextContent();
    }
}
