package utilities;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.sun.xml.internal.bind.v2.runtime.XMLSerializer;
import org.json.JSONObject;
import org.json.XML;

import static utilities.ModifyXMLFile.modifyXmlFile;


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
            //replace node names
            String xmlString = replaceXmlKeys(xml);

            String xmlData = xmlString.substring(xmlString.indexOf("?>")+2);
            System.out.println("Printing XMLData:  ");
            System.out.println(xmlData);

            //write this out to a new file
            File xmlfile = new File ("updatePatientXml.xml");
            FileOutputStream fos = new FileOutputStream(xmlfile);
            if (xmlfile.exists() && !xmlfile.isDirectory()){
                xmlfile.delete();
            }
            byte [] contentInBytes = xmlString.getBytes();
            fos.write(contentInBytes);
            fos.flush();
            fos.close();

            //modify the nodes within the document
            modifyXmlFile(xmlfile.getAbsolutePath());

            XmlMapper xmlMapper = new XmlMapper();
            JsonNode node = xmlMapper.readTree(xmlData.getBytes());

            System.out.println("this is the JSON:  "  + node);

            ObjectMapper jsonMapper = new ObjectMapper();
            JsonNode jsonNodeRoot = jsonMapper.readTree(node.toPrettyString());
            System.out.println("HERE IS THE jsonNodeRoot!!!!");
            System.out.println(jsonNodeRoot.asText());
            JsonNode jsonNodeSex = jsonNodeRoot.get("sex");
            String gender = jsonNodeSex.asText();
            System.out.println("HERE IS THE gender!!!!");
            System.out.println(gender);

            String json = jsonMapper.writeValueAsString(node);

            System.out.println("HERE IS THE jackson JSON!!!!");
            System.out.println(json);

            //String jsonString = jsonObj.toString();

            //jsonObj.get("sex");

            //use a getter and setter to replace state
            //use a getter and setter to replace sex
            //use a getter and setter to replace birthdate with age


           // System.out.println("Printing JSON Object:  ");
           // System.out.println(jsonObj.toString());
            //System.out.println(jsonObj.toString().split(",").length);

//            JSONObject jsonObj = XML.toJSONObject(json);
//            Path path = Paths.get(patientJsonFile);
//            Files.deleteIfExists(path);
//            System.out.println("Printing JSON Object:  ");
//            System.out.println(path.toString());
//            Path filePath = Files.createFile(path);
//
//            Files.write(filePath, jsonObj.toString().getBytes());

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
//            System.out.println("Error writing to file " + patientJsonFile);
//            io.printStackTrace();
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
