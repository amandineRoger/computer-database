package com.excilys.cdb.validators;

import java.time.LocalDate;
import java.util.List;

import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.util.Errors;
import com.excilys.cdb.util.UtilDate;

public class ComputerValidator {
    Errors errorsMap;

    public ComputerValidator(Errors errorsMap) {
        this.errorsMap = errorsMap;
    }

    /**
     * Check data compatibility and validity
     *
     * @param computer
     *            the computer to validate
     * @return an Errors object filled with all errors messages
     */
    public Errors validate(Computer computer) {
        validateBothDates(computer.getIntroduced(), computer.getDiscontinued());
        return errorsMap;
    }

    /**
     * Check if discontinued is after introduced and if both dates are
     * compatible with sql.Timestamp.
     *
     * @param introduced
     *            introduced date
     * @param discontinued
     *            discontinued date
     */
    private void validateBothDates(LocalDate introduced,
            LocalDate discontinued) {
        List<String> listIntro = errorsMap.getListForKey(Errors.INTRODUCED),
                listDisco = errorsMap.getListForKey(Errors.INTRODUCED);

        String s;
        if (introduced != null && discontinued != null
                && introduced.isAfter(discontinued)) {
            s = "Introduced date must be before discontinued date";
            listIntro.add(s);
            listDisco.add(s);
        }
        s = "Date must be include between 1970-01-01 and 2037-12-31";
        if (introduced != null && !UtilDate.checkDBCompat(introduced)) {
            listIntro.add(s);
        }
        if (discontinued != null && !UtilDate.checkDBCompat(discontinued)) {
            listDisco.add(s);
        }

    }

}
