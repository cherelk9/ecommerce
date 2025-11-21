package cm.backend.ecommerce.utils;

public class UserUtils {

    public static final Object EXIST_By_NAME_OR_EMAIL = null;

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        boolean hasUpper = false, hasLower = false, hasDigit = false, hasSpecial = false;
        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch))
                hasUpper = true;
            else if (Character.isLowerCase(ch))
                hasLower = true;
            else if (Character.isDigit(ch))
                hasDigit = true;
            else
                hasSpecial = true;
        }
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }

    public static boolean isValidAge(int age) {
        return age >= 13 && age <= 120;
    }

    public static String USER_NOT_FOUND = "User not found.";
    public static String INVALIDE_EMAIL_FORMAT = "Invalid email format.";
    public static String USER_EMAIL_ALREADY_EXISTS = "User email already exists.";

}
