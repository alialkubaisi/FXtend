package io.fxtend.password;

public class PasswordUtil
{
    /**
     * calculate how strong is the password by checking if the password met all requirement
     *
     * @param password password as plain text to check
     * @param minLength the minimal length that the password must be
     * @return return a score as integer (the score need to be equals to the number of factories)
     */
    public static int getPasswordScore(String password, int minLength)
    {
        int score = 0;

        // predicate to check
        final boolean isPasswordLong = password.length() >= minLength;
        final boolean hasUppercase = password.matches("(?=.*[A-Z]).*");
        final boolean hasLowercase = password.matches("(?=.*[a-z]).*");
        final boolean hasNumber = password.matches("(?=.*[0-9]).*");
        final boolean hasSymbol = password.matches("(?=.*[\\W_]).*");

        // Check for password length
        if (isPasswordLong)
        {
            score++;
        }

        // Check for uppercase letters
        if (hasUppercase)
        {
            score++;
        }

        // Check for lowercase letters
        if (hasLowercase)
        {
            score++;
        }

        // Check for Numbers
        if (hasNumber)
        {
            score++;
        }

        // Check for symbols
        if (hasSymbol)
        {
            score++;
        }

        return score;
    }

    /**
     * check that the password requirements are met
     *
     * @param password password as plain text to check
     * @param minLength the minimal length that the password must be
     * @param numberOfFactors number of factors (requirements)
     * @return return is the password met all requirements
     */
    public static boolean isPasswordValid(String password, int minLength, double numberOfFactors)
    {
        return getPasswordScore(password, minLength) / numberOfFactors == 1.0;
    }
}
