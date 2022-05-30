package nz.co.afleet.bit603_a3_johnmcpherson;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FormChecker {

    private final Context context; // supports retrieval of strings from the string resources
    private final int stringResourceInvalidCombination; //the message for when the user has filled in both fields, but the action failed. e.g. incorrect user/password combination
    private final int stringResourceDetailsRequiredHeader; //the beginning of the message to indicate missing field(s) e.g. "Please enter your"

    private final boolean actionSucceeded;
    // true if the form has been submitted successfully, based on the supplied user data. e.g. User name and password have been validated
        // false: if the combination is invalid; or the combination has not been tried (because of missing field[s])

    private ArrayList<Integer> mandatoryFieldsNotPopulated = new ArrayList<>();

    /**
     *
     * @param context
     * @param stringResourceInvalidCombination
     *@param stringResourceDetailsRequiredHeader
     *@param fieldsFilledOrNotFilled
     *     // the key refers to the name of the mandatory field, as defined in the string resource matching the key
     *     // the value defines whether that field has been filled or not
     *
     *@param actionSucceeded
     */
    public FormChecker(
                        Context context,
                        int stringResourceInvalidCombination,
                       int stringResourceDetailsRequiredHeader,
                       LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled,
                       boolean actionSucceeded) {
        this.context = context;
        this.stringResourceInvalidCombination = stringResourceInvalidCombination;
        this.stringResourceDetailsRequiredHeader = stringResourceDetailsRequiredHeader;
        this.actionSucceeded = actionSucceeded;
        initialiseFieldsNotPopulated(fieldsFilledOrNotFilled);
    }

    public static String determineErrorMessage(Context context,
                                               int stringResourceInvalidCombination,
                                               int stringResourceDetailsRequiredHeader,
                                               LinkedHashMap<Integer, Boolean> fieldsFilledOrNotFilled,
                                               boolean actionSucceeded) {
        FormChecker formChecker = new FormChecker(
                context,
                stringResourceInvalidCombination,
                stringResourceDetailsRequiredHeader,
                fieldsFilledOrNotFilled,
                actionSucceeded);

        return formChecker.determineErrorMessage();
    }

    /**
     * Generic method for returning an error message for a form with two mandatory fields.
     *
     * actionSucceeded should not be true if any of the mandatory fields are false;
     * but this method does not check for that. The method will not fail, but the returned "" would not make sense
     *
     * @return an error message
     */
    private String determineErrorMessage() {
        if (actionSucceeded) return ""; // clear the error message

        // we don't have a successful action. If we have all fields filled in, there was a problem using them
        if (allMandatoryFieldsPopulated()) return context.getString(stringResourceInvalidCombination);

        // We are missing at least one field, so populate the error message with the "header" and the first missing field name
        String nameOfFirstMissingField = getNameOfMissingField(context, 0);
        String errorMessage = context.getString(stringResourceDetailsRequiredHeader)
                + " " + nameOfFirstMissingField;

        // add any fields between the first field and the last field (there may not be any)
        // use commas as separators
        for (int index = 1; index < mandatoryFieldsNotPopulated.size() - 1; index++) {
            String nameOfNextField = getNameOfMissingField(context, index);
            errorMessage += ", " + nameOfNextField;
        }

        if (mandatoryFieldsNotPopulated.size() > 1) { // we need to finish with "and [last field name]"
            // add the name of the last field
            String nameOfLastField = getNameOfMissingField(context, mandatoryFieldsNotPopulated.size() - 1);
            errorMessage += " " + context.getString(R.string.and) + " " + nameOfLastField;
        }
        return errorMessage;
    }

    private String getNameOfMissingField(Context context, int index) {
        Integer stringResourceIdentifier = mandatoryFieldsNotPopulated.get(index);
        return context.getString(stringResourceIdentifier);
    }

    private void initialiseFieldsNotPopulated(HashMap<Integer, Boolean> fieldsFilledOrNotFilled) {
        mandatoryFieldsNotPopulated = new ArrayList<>();
        // I used a LinkedHashMap because I understand that the order in which entries are added is retained
        // when iterating over it (with a for loop)
        // The order is important because we want control over the order in which the field names are added to the error message
        for (Map.Entry<Integer, Boolean> fieldFilledOrNotFilled : fieldsFilledOrNotFilled.entrySet()) {
            if (!fieldFilledOrNotFilled.getValue()) mandatoryFieldsNotPopulated.add(fieldFilledOrNotFilled.getKey());
        }
    }

    private boolean allMandatoryFieldsPopulated() {
        return (mandatoryFieldsNotPopulated.isEmpty());
    }
}
