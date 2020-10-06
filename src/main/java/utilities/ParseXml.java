package utilities;

import java.io.*;


public class ParseXml {
    public static void parseXml(File f) throws IOException {
        String patientXmlFile = "C:\\Dev\\AMx\\DataEntry\\src\\convertedFormatPatientInfo.json";
        try {
            File file = f;
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            StringBuilder sb = new StringBuilder();
            int ptr = 0;
            while ((ptr = inputStream.read()) != -1) {
                sb.append((char) ptr);
                //System.out.println(ptr);
            }

            String xml = sb.toString();
            System.out.println("Printing XML:  ");
            System.out.println(xml);

            String xmlData = replaceXmlKeys(xml);
            System.out.println("Printing XMLData:  ");
            System.out.println(xmlData);

            JSONObject jsonObj = XML.toJSONObject(xmlData);
            jsonObj.get("sex");

            //use a getter and setter to replace state
            //use a getter and setter to replace sex
            //use a getter and setter to replace birthdate with age


            System.out.println("Printing JSON Object:  ");
            System.out.println(jsonObj.toString());
            //System.out.println(jsonObj.toString().split(",").length);

            FileWriter fileWriter = new FileWriter(patientXmlFile);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0; i < jsonObj.toString().split(",").length; i++) {
                System.out.println(jsonObj.toString().split(",")[i]);
                bufferedWriter.write(jsonObj.toString().split(",")[i]);
                bufferedWriter.write("\n");
            }

            bufferedWriter.close();
        } catch (IOException io) {
            System.out.println("Error writing to file " + patientXmlFile);
            io.printStackTrace();
        }
    }
}
