/*
*********IMPORTANT NOTE********
        - the core code was copied from BIT603 Assignment 2 and modified to meet the new requirements
*/

package nz.co.afleet.bit603_a3_johnmcpherson.ui.inventory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem;

/**
 * A fragment representing a list of Items.
 */
public class InventoryFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    private final ArrayList<InventoryItem> inventoryList = new ArrayList<>();
    private InventoryRecyclerViewAdapter inventoryItemRecyclerViewAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InventoryFragment() {
    }

    // TODO: Customize parameter initialization (IGNORE for now)
    @SuppressWarnings("unused")
    public static InventoryFragment newInstance(int columnCount) {
        InventoryFragment fragment = new InventoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // refresh the inventory list (that the adapter has access to)
        refreshInventoryDisplay();
        // and tell the adapter that the list has changed
        // If performance became a problem we could
        //  -  only update when an inventory item is added (using startActivityForResult, and onActivityResult)
        //  -  use inventoryItemRecyclerViewAdapter.notifyItemInserted(insertIndex)
        // performance is not a problem. So, no need to do the above yet
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.inventory_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (super.onOptionsItemSelected(item)) {
            return true;
        };

        switch (item.getItemId()) {
            case R.id.action_clear_items: {
                Toast.makeText(getContext(), "Clear Items", Toast.LENGTH_LONG).show();
                return true;
            }

            case R.id.action_add_test_items: {
                addTestItemsOnConfirmation();

                return true;
            }
        }

        return false;

    }

    private void addTestItemsOnConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.adding_test_items);
        builder.setMessage(R.string.check_add_test_items);
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> {
            // do nothing
        });
        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            InventoryItem.createTestItems(requireActivity().getApplication());
            refreshInventoryDisplay();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void refreshInventoryDisplay() {
        inventoryList.clear();
        List<InventoryItem> refreshedInventoryList = InventoryItem.getInventoryItems(requireActivity().getApplication());
        inventoryList.addAll(refreshedInventoryList);
        inventoryItemRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inventory_list, container, false);
        View listView = view.findViewById(R.id.list);

        // Set the adapter
        if (listView instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // create adapter with reference to a list that we will periodically update
            inventoryItemRecyclerViewAdapter = new InventoryRecyclerViewAdapter(inventoryList);
            // add the adapter to the recycler view
            recyclerView.setAdapter(inventoryItemRecyclerViewAdapter);
        }

        FloatingActionButton buttonAddInventory = view.findViewById(R.id.fabAddInventoryItem);
        buttonAddInventory.setOnClickListener(v -> {
            Intent addItemIntent = new Intent(view.getContext(), AddInventoryActivity.class);
            startActivity(addItemIntent);
        });

        return view;
    }
}