/*
ASSUMPTIONS
    -   Only non-admin users are added on this screen (for now)
        To provide the option of adding an admin user, we could add a checkbox to the screen,
        and map that to the User.isAdmin field
*/

package nz.co.afleet.bit603_a3_johnmcpherson;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import java.util.LinkedHashMap;

import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentAddUserBinding;

import static nz.co.afleet.bit603_a3_johnmcpherson.ErrorMessageGenerator.allFieldsFilledIn;
import static nz.co.afleet.bit603_a3_johnmcpherson.ErrorMessageGenerator.determineErrorMessage;

public class AddUserFragment extends Fragment {


    private FragmentAddUserBinding binding;

    public AddUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddUserBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        binding.buttonCancel.setOnClickListener(view -> {
            // navigate to UserListFragment
            returnToUserListFragment(view);
        });

        binding.buttonAdd.setOnClickListener(view -> {
            LinkedHashMap<Integer, Boolean> statusOfEachEntryField = getMandatoryFieldPopulatedDetails();
            if (allFieldsFilledIn(statusOfEachEntryField)) {
                returnToUserListFragment(view);
           } else {
                String errorMessage = determineErrorMessage(getContext(),
                        null,
                        R.string.missing_details_header,
                        statusOfEachEntryField,
                        false);
            }
        });

        return rootView;
    }

    private void returnToUserListFragment(View view) {
        // navigate to UserListFragment
        Navigation.findNavController(view).navigate(R.id.action_nav_add_user_to_users,null);
    }

    private LinkedHashMap<Integer, Boolean> getMandatoryFieldPopulatedDetails() {
        LinkedHashMap<Integer, Boolean> returnMap = new LinkedHashMap<>();
        addFieldStatusToMap(returnMap, binding.textUserName, R.id.textUserName);
        return returnMap;
    }

    private void addFieldStatusToMap(LinkedHashMap<Integer, Boolean> fieldStatuses, EditText fieldToBeTestedForPopulated, Integer stringResourceDisplayField) {
        boolean isPopulated = !fieldToBeTestedForPopulated.getText().toString().isEmpty();
        fieldStatuses.put(stringResourceDisplayField, isPopulated);
    }
}