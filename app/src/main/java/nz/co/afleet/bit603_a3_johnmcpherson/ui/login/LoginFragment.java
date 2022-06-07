package nz.co.afleet.bit603_a3_johnmcpherson.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedHashMap;

import nz.co.afleet.bit603_a3_johnmcpherson.ErrorMessageGenerator;
import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.database.User;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    TextView editTextUserName;
    TextView editTextPassword;
    TextView textViewErrorMessage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Button loginButton = binding.buttonLogin;
        editTextUserName = binding.editTextUserName;
        editTextPassword = binding.editTextPassword;
        textViewErrorMessage = binding.textErrorMessage;
        loginButton.setOnClickListener(v -> {
            boolean hasUserName = !editTextUserName.getText().toString().isEmpty();
            boolean hasPassword = !editTextPassword.getText().toString().isEmpty();
            boolean loginSuccessful = false; // we have not yet tried to login, so initialise as false

            if (hasUserName && hasPassword) {
                // try to login
                loginSuccessful = User.loginUser(getContext(), editTextUserName.getText().toString(), editTextPassword.getText().toString());
            }
            // determine the error message and set it. (Even if the login is successful, we want to clear the error message)
            LinkedHashMap<Integer, Boolean> fieldNamesWithIsFilled = new LinkedHashMap<>();
            fieldNamesWithIsFilled.put(R.string.login_user_field_name, hasUserName);
            fieldNamesWithIsFilled.put(R.string.login_password_field_name, hasPassword);
            String errorMessage = ErrorMessageGenerator.determineErrorMessage(getContext(), R.string.error_incorrect_login, R.string.login_error_header, fieldNamesWithIsFilled, loginSuccessful);
            textViewErrorMessage.setText(errorMessage);

            // launch the Main Activity (if we have a successful login)
            if (loginSuccessful) {
//                launchMainActivity();
            }

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}