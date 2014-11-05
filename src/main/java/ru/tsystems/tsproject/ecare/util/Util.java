package ru.tsystems.tsproject.ecare.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.tsystems.tsproject.ecare.ECareException;
import ru.tsystems.tsproject.ecare.service.IClientService;

import java.util.Date;

/**
 * This class contains static util methods for checking format of input parameters.
 *
 * @author Starostin Konstantin
 */

@Component
public class Util {

    /*Client service instance for method of checking telephone number on existing*/
    @Autowired
    private static IClientService clientService;

    /*Logger for util operations*/
    private static Logger logger = Logger.getLogger(Util.class);

    /**
     * Method checks string on length. Must be less than 60 symbols (in this app).
     *
     * @param s input string.
     * @return string if it satisfies the requirements.
     * @throws ru.tsystems.tsproject.ecare.ECareException if string not satisfies the requirements.
     */
    public static String checkStringLength(String s) throws ECareException {
        if (s.length() <= 60) {
            return s;
        }
        else {
            ECareException ecx = new ECareException("Too big length of the input string.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    /**
     * Method checks string on empty. Must be more than 0 symbols.
     *
     * @param s input string.
     * @return string if it satisfies the requirements.
     * @throws ru.tsystems.tsproject.ecare.ECareException ECareException if string not satisfies the requirements.
     */
    public static String checkStringOnEmpty(String s) throws ECareException {
        if(s.length() != 0) {
            return s;
        }
        else {
            ECareException ecx = new ECareException("Required string is empty.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    /**
     * Method checks string on right format of integer variable.
     *
     * @param s input string.
     * @return converted integer variable.
     * @throws ru.tsystems.tsproject.ecare.ECareException if string not satisfies the requirements.
     */
    public static Integer checkInt(String s) throws ECareException {
        try {
            int intDigit = Integer.valueOf(s);
            if (intDigit > 0) return intDigit;
            else {
                ECareException ecx = new ECareException("Entered the wrong data format.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
        }catch (NumberFormatException nfx) {
            ECareException ecx = new ECareException("Entered the wrong data format.", nfx);
            logger.warn(ecx.getMessage(), nfx);
            throw ecx;
        }
    }

    /**
     * Method checks string on right format of long variable.
     *
     * @param s input string.
     * @return converted long variable.
     * @throws ru.tsystems.tsproject.ecare.ECareException if string not satisfies the requirements.
     */
    public static Long checkLong(String s) throws ECareException {
        try {
            long longDigit = Long.valueOf(s);
            if (longDigit > 0) return longDigit;
            else {
                ECareException ecx = new ECareException("Entered the wrong data format.");
                logger.warn(ecx.getMessage(), ecx);
                throw ecx;
            }
        }catch (NumberFormatException nfx) {
            ECareException ecx = new ECareException("Entered the wrong data format.", nfx);
            logger.warn(ecx.getMessage(), nfx);
            throw ecx;
        }
    }

    /**
     * Method checks string on right format of java.sql.Date variable.
     *
     * @param s input string.
     * @return converted java.sql.Date variable.
     * @throws ru.tsystems.tsproject.ecare.ECareException if string not satisfies the requirements.
     */
    public static Date checkDate(String s) throws ECareException {
        try {
            return java.sql.Date.valueOf(s);
        } catch (IllegalArgumentException iax) {
            ECareException ecx = new ECareException("Entered the wrong format of Date.", iax);
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    /**
     * Method checks two input passwords on equals.
     *
     * @param password1 first password.
     * @param password2 second password.
     * @return first password if they are equal.
     * @throws ru.tsystems.tsproject.ecare.ECareException if passwords are not equal.
     */
    public static String checkPassword(String password1, String password2) throws ECareException {
        if(password1.equals(password2)) return password1;
        else {
            ECareException ecx = new ECareException("Entered passwords do not match.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    /**
     * Method checks telephone number on existing in database.
     *
     * @param s input string with telephone number.
     * @return telephone number in format of long variable if it does not exist in database.
     * @throws ru.tsystems.tsproject.ecare.ECareException if number already exist in database.
     */
    public static Long checkNumberOnExisting(String s) throws ECareException {
        try {
            long number = checkLong(s);
            try {
                clientService.findClientByNumber(number);
            } catch (ECareException ecx) {
                return number;
            }
            ECareException ecx = new ECareException("Entered telephone number already exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        } catch (NumberFormatException nfx) {
            ECareException ecx = new ECareException("Wrong format of entered telephone number.", nfx);
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
    }

    /**
     * Method checks client login(e-mail) on existing in database.
     *
     * @param s input string with login.
     * @return client login if it does not exist in database.
     * @throws ru.tsystems.tsproject.ecare.ECareException if login already exist in database.
     */
    public static String checkLoginOnExisting(String s) throws ECareException {
        if(clientService.existLogin(s)) {
            ECareException ecx = new ECareException("Entered e-mail (login) already exist.");
            logger.warn(ecx.getMessage(), ecx);
            throw ecx;
        }
        else return s;
    }
}
