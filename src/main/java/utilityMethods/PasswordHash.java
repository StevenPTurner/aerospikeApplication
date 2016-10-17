package utilityMethods;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {

    public static String genHash(String password, String salt) {
        return BCrypt.hashpw(password, salt);
    }

    public static String genSalt() {
        return BCrypt.gensalt();
    }

    public static String genSalt(int workFactor) {
        return BCrypt.gensalt(workFactor);
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        if (BCrypt.checkpw(password, hashedPassword))
            return true;
        else
            return false;
    }
}
