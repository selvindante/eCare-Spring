package ru.tsystems.tsproject.ecare;

/**
 * This class contains the client session parameters in the application and
 * is transmitted through the web pages. Class Session is a singleton.
 *
 * @author Starostin Konstantin
 */
public class Session {

    /*Instance of the singleton class*/
    private static Session instance;

    /**
     * Field contains role of user in application. In context of eCare application
     * can be "client" or "admin"
     */
    private static String role;

    /*Field contains boolean status of user session*/
    private static boolean isOn = false;

    /*Private constructor of singleton class*/
    private Session() {
    }

    /**
     * This method return instance of singleton class Session.
     * @return instance of class.
     */
    public static Session getInstance() {
        if (instance == null)
        {
            instance = new Session();
        }
        return instance;
    }

    // Getters and setters:

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }
}
