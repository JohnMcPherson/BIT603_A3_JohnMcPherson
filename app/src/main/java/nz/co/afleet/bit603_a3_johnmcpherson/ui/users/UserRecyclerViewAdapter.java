package nz.co.afleet.bit603_a3_johnmcpherson.ui.users;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.UserDetailsBinding;
import nz.co.afleet.bit603_a3_johnmcpherson.placeholder.PlaceholderContent;

public class UserRecyclerViewAdapter
        extends RecyclerView.Adapter<UserRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderContent.PlaceholderItem> mUserList;
    private final View mUserDetailFragmentContainer;

    UserRecyclerViewAdapter(List<PlaceholderContent.PlaceholderItem> users,
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
        holder.textViewUserName.setText(mUserList.get(position).content);

        holder.itemView.setTag(mUserList.get(position));
        holder.itemView.setOnClickListener(itemView -> {
            PlaceholderContent.PlaceholderItem item =
                    (PlaceholderContent.PlaceholderItem) itemView.getTag();
            Bundle arguments = new Bundle();
            arguments.putString(UserDetailFragment.ARG_ITEM_ID, item.id);
            if (mUserDetailFragmentContainer != null) {
                Navigation.findNavController(mUserDetailFragmentContainer)
                        .navigate(R.id.sw600_fragment_user_detail, arguments);
            } else {
                Navigation.findNavController(itemView).navigate(R.id.show_item_detail, arguments);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            /*
             * Context click listener to handle Right click events
             * from mice and trackpad input to provide a more native
             * experience on larger screen devices
             */
            holder.itemView.setOnContextClickListener(v -> {
                PlaceholderContent.PlaceholderItem item =
                        (PlaceholderContent.PlaceholderItem) holder.itemView.getTag();
                Toast.makeText(
                        holder.itemView.getContext(),
                        "Context click of item " + item.id,
                        Toast.LENGTH_LONG
                ).show();
                return true;
            });
        }
        holder.itemView.setOnLongClickListener(v -> {
            // Setting the item id as the clip data so that the drop target is able to
            // identify the id of the content
            ClipData.Item clipItem = new ClipData.Item(mUserList.get(position).id);
            ClipData dragData = new ClipData(
                    ((PlaceholderContent.PlaceholderItem) v.getTag()).content,
                    new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                    clipItem
            );

            if (Build.VERSION.SDK_INT >= 24) {
                v.startDragAndDrop(
                        dragData,
                        new View.DragShadowBuilder(v),
                        null,
                        0
                );
            } else {
                v.startDrag(
                        dragData,
                        new View.DragShadowBuilder(v),
                        null,
                        0
                );
            }
            return true;
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
