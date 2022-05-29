package nz.co.afleet.bit603_a3_johnmcpherson;

import android.content.Context;

public class Utilities {
    /**
     * Generic method for returning an error message for a form with two mandatory fields.
     *
     * actionSucceeded should not be true if either isFilledFirstField or isFilledSecondField is false;
     * but this method does not check for that. The method will not fail, but the returned "" would not make sense
     *
     * @param context supports retrieval of strings from the string resources
     * @param isFilledFirstField the user has/has not filled in the first field
     * @param isFilledSecondField the user has/has not filled in the first field
     * @param stringResourceInvalidCombination the message for when the user has filled in both fields, but the action failed. e.g. incorrect user/password combination
     * @param stringResourceDetailsRequiredHeader the beginning of the message to indicate missing field(s) e.g. "Please enter your"
     * @param stringResourceNameOfFirstField used to indicate the first missing field (where applicable) e.g. "User Name"
     * @param stringResourceNameOfSecondField used to indicate the second missing field (where applicable) e.g. "Password"
     * @param actionSucceeded
     *          true if a valid combination has been accepted. e.g. User name and password have been validated
     *          false: if the combination is invalid; or the combination has not been tried (because of missing field[s])
     * @return an error message
     */
    public static String determineErrorMessage(   Context context,
                                                  boolean isFilledFirstField,
                                                  boolean isFilledSecondField,
                                                  int stringResourceInvalidCombination,
                                                  int stringResourceDetailsRequiredHeader,
                                                  int stringResourceNameOfFirstField,
                                                  int stringResourceNameOfSecondField,
                                                  boolean actionSucceeded) {
        if (actionSucceeded) return ""; // clear the error message

        // we don't have a successful login. If we have both user name and password, we must have a bad combination
        if (isFilledFirstField && actionSucceeded) return context.getString(stringResourceInvalidCombination);

        // We are missing one or both input fields
        String errorMessage = context.getString(stringResourceDetailsRequiredHeader) + " ";
        if (isFilledFirstField) {
            // we have a user name. So, it must be just the password that is missing
            errorMessage += (context.getString(stringResourceNameOfSecondField));
        } else {
            // we are missing the first field
            errorMessage += context.getString(stringResourceNameOfFirstField);
            // are we also missing the password?
            if (!isFilledSecondField) {
                errorMessage += (" " + context.getString(R.string.and) + " " + context.getString(stringResourceNameOfSecondField));
            }
        }
        return errorMessage;
    }

}
