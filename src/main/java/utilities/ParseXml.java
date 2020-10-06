package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.json.JSONObject;
import org.json.XML;


public class ParseXml {
    public static void parseXml(File f) throws IOException {
        String patientJsonFile = "patientJson.json";
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
            //System.out.println("Printing XML:  ");
            //System.out.println(xml);
            String xmlData = replaceXmlKeys(xml);
            //System.out.println("Printing XMLData:  ");
            //System.out.println(xmlData);

            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(xmlData.getBytes());
            ObjectMapper jsonMapper = new ObjectMapper();
            String json = jsonMapper.writeValueAsString(node);
            //System.out.println("HERE IS THE jackson JSON!!!!");
            //System.out.println(json);



            JSONObject jsonObj = XML.toJSONObject(json);
            //String jsonString = jsonObj.toString();

            //jsonObj.get("sex");

            //use a getter and setter to replace state
            //use a getter and setter to replace sex
            //use a getter and setter to replace birthdate with age


           // System.out.println("Printing JSON Object:  ");
           // System.out.println(jsonObj.toString());
            //System.out.println(jsonObj.toString().split(",").length);

            Path path = Paths.get(patientJsonFile);
            Files.deleteIfExists(path);
            System.out.println("Printing JSON Object:  ");
            System.out.println(path.toString());
            Path filePath = Files.createFile(path);

            Files.write(filePath, jsonObj.toString().getBytes());

//            FileWriter fileWriter = new FileWriter(filePath.toFile());

//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//
//            for (int i = 0; i < jsonObj.toString().split(",").length; i++) {
//                System.out.println(jsonObj.toString().split(",")[i]);
//                bufferedWriter.write(jsonObj.toString().split(",")[i]);
//                bufferedWriter.write("\n");
//            }
//
//            bufferedWriter.close();
        } catch (IOException io) {
            System.out.println("Error writing to file " + patientJsonFile);
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
