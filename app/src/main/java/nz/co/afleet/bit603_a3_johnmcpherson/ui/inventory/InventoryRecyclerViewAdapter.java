/*
    ASSUMPTIONS
    - The inventory id is not to be displayed

    COMMENTS
    -   Changed AndroidStudio provided code to use InventoryItem

*********IMPORTANT NOTE********
        - the core code was copied from BIT603 Assignment 2 and modified to meet the new requirements
*/

package nz.co.afleet.bit603_a3_johnmcpherson.ui.inventory;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentInventoryBinding;

/**
 * {@link RecyclerView.Adapter} that can display an {@link InventoryItem}.
 */
public class InventoryRecyclerViewAdapter extends RecyclerView.Adapter<InventoryRecyclerViewAdapter.ViewHolder> {

    private final ArrayList<InventoryItem> inventoryList;

    public InventoryRecyclerViewAdapter(ArrayList<InventoryItem> items) {
        inventoryList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentInventoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        InventoryItem inventoryItem = inventoryList.get(position);
             holder.mName.setText(inventoryItem.getName());
             holder.mType.setText(inventoryItem.getItemType());
             holder.mQuantity.setText(String.valueOf(inventoryItem.getQuantity()));
    }

    public int getPositionOfFirstItemToDisplay() {
        return 0;
    }

    public int getPositionOfLastItemToDisplay() {
        return getItemCount() - 1;
    }

    @Override
    public int getItemCount() {
        return inventoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mName;
        public final TextView mType;
        public final TextView mQuantity;

        public ViewHolder(FragmentInventoryBinding binding) {
            super(binding.getRoot());
            mName = binding.textName;
            mType = binding.textType;
            mQuantity = binding.textQuantity;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mName.getText() + "'";
        }
    }
}