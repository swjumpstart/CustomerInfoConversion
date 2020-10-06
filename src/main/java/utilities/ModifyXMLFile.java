package utilities;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import StateList.StateList;
import org.json.JSONObject;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModifyXMLFile {

    public static void modifyXmlFile(String xmlFilePath) {
        try {
            File inputFile = new File(xmlFilePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("patient");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String gender = eElement.getElementsByTagName("sex").item(0).getTextContent();
                    if (gender.equalsIgnoreCase("f")){
                        eElement.getElementsByTagName("sex").item(0).setTextContent("female");
                    }
                    if (gender.equalsIgnoreCase("m")){
                        eElement.getElementsByTagName("sex").item(0).setTextContent("male");
                    }

                    String fullStateName = eElement.getElementsByTagName("state").item(0).getTextContent();
                    String stateAbbr = StateList.StateAbbrevEnum.getStateName(fullStateName).name();
                    eElement.getElementsByTagName("state").item(0).setTextContent(stateAbbr);

                    String birthdate = eElement.getElementsByTagName("age").item(0).getTextContent();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate date = LocalDate.parse(birthdate, formatter);
                    eElement.getElementsByTagName("age").item(0).setTextContent(String.valueOf(calculateAge(date)));

                }
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int calculateAge(LocalDate bDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(bDate, currentDate).getYears();
    }
}
