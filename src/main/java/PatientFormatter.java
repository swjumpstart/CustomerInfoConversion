import utilities.ParseXml;

import java.io.*;

public class PatientFormatter {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File (ClassLoader.getSystemResource("input/patientInfo.xml").getFile());

        try {
            ParseXml.parseXml(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
