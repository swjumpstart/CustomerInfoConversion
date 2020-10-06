package utilities;

import java.time.LocalDate;
import java.time.Period;

public class ConvertBirthDateToAge {
    public int calculateAge (LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate,  currentDate).getYears();
    }
}
