package com.excilys.cdb.validators;

import java.time.LocalDate;
import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Errors;
import com.excilys.cdb.mappers.UtilDate;

public class ComputerValidator {
    Errors errorsMap;

    /**
     * Constructor of ComputerValidator.
     *
     * @param errorsMap
     *            the map to fill with errors message
     */
    public ComputerValidator(Errors errorsMap) {
        this.errorsMap = errorsMap;
    }

    /**
     * Check data compatibility and validity.
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
        String s;
        if (introduced != null && discontinued != null
                && introduced.isAfter(discontinued)) {
            List<String> listIntro = errorsMap.getListForKey(Errors.INTRODUCED),
                    listDisco = errorsMap.getListForKey(Errors.DISCONTINUED);
            s = "Introduced date must be before discontinued date";
            listIntro.add(s);
            listDisco.add(s);
        }
        s = "Date must be include between 1970-01-01 and 2037-12-31";
        if (introduced != null && !UtilDate.checkDBCompat(introduced)) {
            List<String> listIntro = errorsMap.getListForKey(Errors.INTRODUCED);
            listIntro.add(s);
        }
        if (discontinued != null && !UtilDate.checkDBCompat(discontinued)) {
            List<String> listDisco = errorsMap
                    .getListForKey(Errors.DISCONTINUED);
            listDisco.add(s);
        }
    }
}
