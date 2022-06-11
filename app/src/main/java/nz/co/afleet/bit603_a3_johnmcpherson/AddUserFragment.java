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

import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentNewUserBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentNewUserBinding binding;

    public AddUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddUserFragment newInstance(String param1, String param2) {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewUserBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        binding.buttonCancel.setOnClickListener(view -> {
            // navigate to UserListFragment
            returnToUserListFragment(view);
        });

        binding.buttonAdd.setOnClickListener(view -> {
            returnToUserListFragment(view);
        });

        return rootView;
    }

    private void returnToUserListFragment(View view) {
        // navigate to UserListFragment
        Navigation.findNavController(view).navigate(R.id.action_nav_add_user_to_users,null);
    }
}