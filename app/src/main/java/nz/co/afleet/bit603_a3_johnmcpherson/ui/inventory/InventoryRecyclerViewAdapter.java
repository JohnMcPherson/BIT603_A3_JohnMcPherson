/*
    ASSUMPTIONS
    - The inventory id is not to be displayed

    COMMENTS
    -   Changed AndroidStudio provided code to use InventoryItem
*/

package nz.co.afleet.bit603_a3_johnmcpherson.ui.inventory;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedHashMap;

import nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.FragmentInventoryBinding;

/**
 * {@link RecyclerView.Adapter} that can display an {@link InventoryItem}.
 */
public class InventoryRecyclerViewAdapter extends RecyclerView.Adapter<InventoryRecyclerViewAdapter.ViewHolder> {
/*
*********IMPORTANT NOTE********
        - the core code was copied from BIT603 Assignment 2 and modified to meet the new requirements
*/

    // Using LinkedHashMap because we can get a more reliable definition of entry positions than HashMap. [onBindViewHolder() uses position]
    private final LinkedHashMap<String, Double> inventoryHashMap;

    public InventoryRecyclerViewAdapter(LinkedHashMap<String, Double> items) {
        inventoryHashMap = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentInventoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Object key = inventoryHashMap.keySet().toArray()[position];
        // test for String before casting to String, to protect from a crash in the case of incorrect usage
        if (key.getClass() == String.class) {
            String itemName = (String) key;

            Double doubleQuantity = inventoryHashMap.get(key);
            String stringValue = String.valueOf(doubleQuantity);
            holder.mContentView.setText(itemName);
            holder.mQuantity.setText(stringValue);
        }
    }

    @Override
    public int getItemCount() {
        return inventoryHashMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         public final TextView mContentView;
        public final TextView mQuantity;
        public InventoryItem mItem;

        public ViewHolder(FragmentInventoryBinding binding) {
            super(binding.getRoot());
            mContentView = binding.textName;
            mQuantity = binding.textQuantity;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}