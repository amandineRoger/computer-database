package validators;

import java.util.List;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.util.Errors;

/**
 * Validate data format only
 *
 * @author Amandine Roger
 *
 */
public class DTOValidator {
    private Errors errorsMap;

    public DTOValidator(Errors errorsMap) {
        this.errorsMap = errorsMap;
    }

    /**
     * Check the format of data in DTO.
     *
     * @param dto
     *            the ComputerDTO object to validate
     * @return an Errors object filled with all errors messages
     */
    public Errors validate(ComputerDTO dto) {
        validateID(dto.getId());
        validateName(dto.getName());
        validateDate(Errors.INTRODUCED, dto.getIntroduced());
        validateDate(Errors.DISCONTINUED, dto.getDiscontinued());
        validateCompanyID(dto.getCompanyId());
        return errorsMap;
    }

    /**
     * validate the computerID (for edition only).
     *
     * @param id
     *            the String id coming from front-end
     */
    private void validateID(String id) {
        if (id != null && !id.equals("")) {
            List<String> list = errorsMap.getListForKey(Errors.ID);

            if (!id.matches(Constants.REGEX_INT)) {
                list.add("This computer ID is not an integer number !");
            } else if (Integer.parseInt(id) < 1) {
                list.add("This computer ID is negative !");
            }
        }
    }

    /**
     * Check if a name is not null.
     *
     * @param name
     *            computer name
     */
    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            List<String> list = errorsMap.getListForKey(Errors.NAME);
            list.add("A computer must have a name !");
        }
    }

    /**
     * Check date format (must match COnstant.REGEX_DATE).
     *
     * @param type
     *            type of date (Errors.INTRODUCED or Errors.DISCONTINUED)
     * @param date
     *            the string date to test
     */
    private void validateDate(String type, String date) {
        if (date != null && !date.matches(Constants.REGEX_DATE)) {
            List<String> list = errorsMap.getListForKey(type);
            list.add("The date format is invalid !");
        }
    }

    /**
     * Check that the company id is a number and if it is positive.
     *
     * @param id
     *            the company id to check
     */
    private void validateCompanyID(String id) {
        List<String> list = errorsMap.getListForKey(Errors.COMPANY);
        if (!id.matches(Constants.REGEX_INT)) {
            list.add("Company ID is not a number !");
        } else {
            long tmp = Long.parseLong(id);
            if (tmp < 1) {
                list.add("Company ID can't be negative !");
            }
        }
    }

}
