package utilities;

import java.io.*;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.json.XML;

import static utilities.ModifyXMLFile.modifyXmlFile;


public class ParseXml {
    public static void parseXml(File f) throws IOException {
        try {
            File file = f;
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            int ptr = 0;
            while ((ptr = inputStream.read()) != -1) {
                sb.append((char) ptr);
            }

            String xml = sb.toString();
            //replace node names
            String xmlString = replaceXmlKeys(xml);

            //write this out to a new file
            File xmlfile = new File ("updatePatientXml.txt");
            FileOutputStream fos = new FileOutputStream(xmlfile);
            byte [] contentInBytes = xmlString.getBytes();
            fos.write(contentInBytes);
            fos.flush();
            fos.close();

            //modify the node values
            modifyXmlFile(xmlfile.getPath());

            //convert to JSON object and write to a JSON file
            Reader xmlSource = new FileReader ("updatePatientXml.txt");
            JSONObject json = XML.toJSONObject(xmlSource);
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
            writer.writeValue(new File("patientInfoJsonFormat.json"), json.toString());

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private static String replaceXmlKeys(String xml) {
        String xmlData = xml;
        xmlData = xmlData.replace("id", "patientid");
        xmlData = xmlData.replace("gender", "sex");
        xmlData = xmlData.replace("dateOfBirth", "age");
        return xmlData;
    }
}
