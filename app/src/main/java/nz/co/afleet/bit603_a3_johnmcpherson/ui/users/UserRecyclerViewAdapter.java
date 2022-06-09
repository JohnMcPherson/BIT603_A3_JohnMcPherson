package nz.co.afleet.bit603_a3_johnmcpherson.ui.users;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.database.User;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.UserDetailsBinding;

public class UserRecyclerViewAdapter
        extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

    private final List<User> mUserList;
    private final View mUserDetailFragmentContainer;

    UserRecyclerViewAdapter(List<User> users,
                            View userDetailFragmentContainer) {
        mUserList = users;
        mUserDetailFragmentContainer = userDetailFragmentContainer;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        UserDetailsBinding binding =
                UserDetailsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewUserName.setText(mUserList.get(position).getName());

        holder.itemView.setTag(mUserList.get(position));
        holder.itemView.setOnClickListener(itemView -> {
            User item = (User) itemView.getTag();
            Bundle arguments = new Bundle();
            arguments.putString(UserDetailFragment.ARG_ITEM_ID, item.getId().toString());
            if (mUserDetailFragmentContainer != null) {
                NavController navController = Navigation.findNavController(mUserDetailFragmentContainer);
                navController
                        .navigate(R.id.sw600_fragment_item_detail, arguments);
            } else {
                Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewUserName;

        ViewHolder(UserDetailsBinding binding) {
            super(binding.getRoot());
            textViewUserName = binding.userName;
        }

    }


}
