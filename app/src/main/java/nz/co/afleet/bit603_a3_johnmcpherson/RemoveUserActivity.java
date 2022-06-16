/*
ASSUMPTIONS
    -   Only non-admin users are added on this screen (for now)
        To provide the option of adding an admin user, we could add a checkbox to the screen,
        and map that to the User.isAdmin field
*/

package nz.co.afleet.bit603_a3_johnmcpherson;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import nz.co.afleet.bit603_a3_johnmcpherson.database.User;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.ActivityRemoveUserBinding;

public class RemoveUserActivity extends AppCompatActivity {


    private ActivityRemoveUserBinding binding;

   @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // use View Binding to set the root view
        binding = ActivityRemoveUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonRemoveUser.setOnClickListener(view -> {
            String userName = binding.editTextTextPersonName.getText().toString();
            User userToDelete = User.find(this, userName);
            if (userToDelete != null) {
                User.removeUser(this, userName);
            }
            finish();
        });

        binding.buttonCancel.setOnClickListener(view -> {
            finish();
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.remove_user_label);
    }
}