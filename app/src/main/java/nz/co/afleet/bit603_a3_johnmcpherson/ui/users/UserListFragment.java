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
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentItemListBinding;

/**
 * A fragment representing a list of users. This fragment
 * has different presentations for phone and larger screen devices. On
 * handsets, the fragment presents a list of users, which when touched,
 * lead to a {@link UserDetailFragment} representing
 * user details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class UserListFragment extends Fragment {

    private FragmentItemListBinding binding;
    private final ArrayList<User> mUsers = new ArrayList<>();
    private UserRecyclerViewAdapter userRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);

        userRecyclerViewAdapter = new UserRecyclerViewAdapter(mUsers, binding.userList);
        return binding.getRoot();

    }

    @Override
    public void onResume() {
        super.onResume();
        // refresh the user list (that the adapter has access to)
        refreshUserList();
        // and tell the adapter that the list has changed
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

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        View itemDetailFragmentContainer = view.findViewById(R.id.user_detail_nav_container);

        setupRecyclerView(recyclerView, itemDetailFragmentContainer);
    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View userDetailFragmentContainer
    ) {

        recyclerView.setAdapter(userRecyclerViewAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}