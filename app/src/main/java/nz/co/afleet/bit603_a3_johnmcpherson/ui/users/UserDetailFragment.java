package nz.co.afleet.bit603_a3_johnmcpherson.ui.users;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.database.User;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentUserDetailBinding;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link UserListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class UserDetailFragment extends Fragment {

    /**
     * The fragment argument representing the user ID that this fragment
     * represents.
     */
    public static final String ARG_USER_ID = "item_id";

    /**
     * The placeholder content this fragment is presenting.
     */
    private User mItem;
    private TextView mTextView;

    private FragmentUserDetailBinding binding;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments.containsKey(ARG_USER_ID)) {
            // Load the placeholder content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            Context context = getContext();
            mItem = User.getById(context, arguments.getString(ARG_USER_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        mTextView = binding.textUserName;

        updateContent();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if (mItem != null) {
            mTextView.setText(mItem.getName());
        }
    }
}