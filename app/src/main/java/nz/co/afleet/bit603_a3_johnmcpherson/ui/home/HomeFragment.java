/*
ASSUMPTIONS
    -   we are doing this for the same client, so I assume the client will want this screen to look similar
        to the equivalent screen we built for assignment 2
        (But, there is no requirement for favourite colour, so I have not replicated that)
*/

package nz.co.afleet.bit603_a3_johnmcpherson.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.database.User;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        setWelcomeMessage();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setWelcomeMessage() {
        User loggedInUser = User.getLoggedInUser();
        if (loggedInUser != null) { // We shouldn't be here without a logged in user, but just in case

            // set the welcome message text
            String stringWelcomeMessage = getContext().getString(R.string.welcome_message_header) + " " + loggedInUser.getName();
            binding.textViewWelcomeMessage.setText(stringWelcomeMessage);

            // no requirement for text colour
        }
    }
}