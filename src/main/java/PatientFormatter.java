import  utilities.*;

import java.io.*;

public class PatientFormatter {
    public static void main(String[] args) throws FileNotFoundException {
        String patientXmlFile = "C:\\Dev\\AMx\\DataEntry\\src\\convertedFormatPatientInfo.json";
        File file = new File("C:\\Dev\\AMx\\DataEntry\\src\\patientInfo.xml");
        try {
            ParseXml.parseXml(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
