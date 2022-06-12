/*
ASSUMPTIONS
    -   Only non-admin users are added on this screen (for now)
        To provide the option of adding an admin user, we could add a checkbox to the screen,
        and map that to the User.isAdmin field
*/

package nz.co.afleet.bit603_a3_johnmcpherson;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedHashMap;

import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentAddUserBinding;

import static nz.co.afleet.bit603_a3_johnmcpherson.ErrorMessageGenerator.allFieldsFilledIn;
import static nz.co.afleet.bit603_a3_johnmcpherson.ErrorMessageGenerator.determineErrorMessage;

public class AddUserActivity extends AppCompatActivity {


    private FragmentAddUserBinding binding;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // use View Binding to set the root view
        binding = FragmentAddUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonCancel.setOnClickListener(view -> {
            // navigate to UserListFragment
            finish();
//            returnToUserListFragment(view);
        });

        binding.buttonAdd.setOnClickListener(view -> {
            LinkedHashMap<Integer, Boolean> statusOfEachEntryField = getMandatoryFieldPopulatedDetails();
            if (allFieldsFilledIn(statusOfEachEntryField)) {
//                Toast.makeText(this,stringItemName + " " + getString(R.string.added_to_inventory), Toast.LENGTH_LONG).show();
                finish();
            } else {
                String errorMessage = determineErrorMessage(this,
                        null,
                        R.string.missing_details_header,
                        statusOfEachEntryField,
                        false);
                binding.textErrorMessageAdd.setText(errorMessage);
            }
        });
    }

    private void returnToUserListFragment(View view) {
        // navigate to UserListFragment
//        Navigation.findNavController(view).navigate(R.id.action_nav_add_user_to_users,null);
    }

    private LinkedHashMap<Integer, Boolean> getMandatoryFieldPopulatedDetails() {
        LinkedHashMap<Integer, Boolean> returnMap = new LinkedHashMap<>();
        addFieldStatusToMap(returnMap, binding.editTextUserName, R.string.user_field_name);
        addFieldStatusToMap(returnMap, binding.editTextPassword, R.string.password_field_name);
        addFieldStatusToMap(returnMap, binding.editTextDateOfBirth, R.string.date_of_birth_field_name);
        addFieldStatusToMap(returnMap, binding.editTextEmployeeNumber, R.string.employee_number_field_name);
        addFieldStatusToMap(returnMap, binding.editTextPhoneNumber, R.string.user_phone_number_field_name);
        addFieldStatusToMap(returnMap, binding.editTextAddress, R.string.user_address_field_name);
        return returnMap;
    }

    private void addFieldStatusToMap(LinkedHashMap<Integer, Boolean> fieldStatuses, EditText fieldToBeTestedForPopulated, Integer stringResourceDisplayField) {
        boolean isPopulated = !fieldToBeTestedForPopulated.getText().toString().isEmpty();
        fieldStatuses.put(stringResourceDisplayField, isPopulated);
    }
}