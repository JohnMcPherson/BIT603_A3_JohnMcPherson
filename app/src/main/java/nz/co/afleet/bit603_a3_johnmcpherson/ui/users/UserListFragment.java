package nz.co.afleet.bit603_a3_johnmcpherson.ui.users;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.database.User;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentUserListBinding;

/**
 * A fragment representing a list of users. This fragment
 * has different presentations for phone and larger screen devices. On
 * handsets, the fragment presents a list of users, which when touched,
 * lead to a {@link UserDetailFragment} representing
 * user details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class UserListFragment extends Fragment {

    private FragmentUserListBinding binding;
    private final ArrayList<User> mUsers = new ArrayList<>();
    private UserRecyclerViewAdapter userRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentUserListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        // refresh the user list (that the adapter has access to)
        refreshUserList();
        // and tell the adapter that the list has changed
        // due to the small amount of data, performance should be fine, and we do not need to respond to specific change events
        userRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void refreshUserList() {
        mUsers.clear();
        mUsers.addAll(User.getUsers(getActivity()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.userList;

/*
        R.id.user_detail_frag_container is for the fragment that shows user detail on the same
        screen (layout) as the user list. It is used in the tablet (sw600dpi) version of fragment_user_list.
        If the current layout does not show details, alongside the user list, there will be no view found
        and userDetailFragmentContainer will be null.
        This will be used to control navigation between the list and the detail (whether or not to show a different screen)
*/
        View userDetailFragmentContainer = view.findViewById(R.id.user_detail_frag_container);

        setupRecyclerView(recyclerView, userDetailFragmentContainer);
    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View userDetailFragmentContainer
    ) {
        userRecyclerViewAdapter = new UserRecyclerViewAdapter(
                mUsers,
                userDetailFragmentContainer
        );

        recyclerView.setAdapter(userRecyclerViewAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}