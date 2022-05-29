package nz.co.afleet.bit603_a3_johnmcpherson;

import android.content.Context;

public class Utilities {
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

        // We are missing userName, password, or both
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
