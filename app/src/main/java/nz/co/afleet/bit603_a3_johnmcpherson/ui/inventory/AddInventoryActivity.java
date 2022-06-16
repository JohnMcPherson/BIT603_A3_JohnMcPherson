package nz.co.afleet.bit603_a3_johnmcpherson.ui.inventory;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import nz.co.afleet.bit603_a3_johnmcpherson.R;
import nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem;
import nz.co.afleet.bit603_a3_johnmcpherson.databinding.ActivityAddInventoryBinding;

public class AddInventoryActivity extends AppCompatActivity {

    private EditText editTextItemName;
    private RadioGroup radioGroupItemType;
    private EditText editTextQuantity;
    private TextView textViewErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // use View Binding to set the root view
        ActivityAddInventoryBinding binding = ActivityAddInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // show the "Home" menu item (to return user to the calling screen)
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        // use binding to find and initialise the required views
        editTextItemName = binding.editTextItemName;
        radioGroupItemType = binding.radioGroupItemType;
        editTextQuantity = binding.editTextQuantity;
        textViewErrorMessage = binding.textErrorMessageAdd;
        Button buttonAdd = binding.buttonAdd;
        Button buttonCancel = binding.buttonCancel;

        buttonAdd.setOnClickListener(v -> {
            String stringItemName = editTextItemName.getText().toString();
            String stringQuantity = editTextQuantity.getText().toString();
            boolean hasItemName = !stringItemName.isEmpty();
            boolean hasPassword = !stringQuantity.isEmpty();
            boolean additionSuccessful = false; // we have not yet tried to add the item, so initialise as false

            if (hasItemName && hasPassword) {
                if (!InventoryItem.isDuplicateOfInventoryItem(getApplication(), stringItemName)) {
                    int selectedItemTypeId = radioGroupItemType.getCheckedRadioButtonId();
                    RadioButton selectedItemTypeButton = findViewById(selectedItemTypeId);
                    String stringItemType = selectedItemTypeButton.getText().toString();
                   InventoryItem.addInventoryItemToDatabase(getApplication(), stringItemName, stringItemType, stringQuantity);
                   additionSuccessful = true;
                }
            }
            // determine the error message and set it. (Even if the login is successful, we want to clear the error message)
            String errorMessage = determineErrorMessage(hasItemName, hasPassword, additionSuccessful);
            textViewErrorMessage.setText(errorMessage);

            // launch the Main Activity (if we have a successful login)
            if (additionSuccessful) {
                Toast.makeText(this,stringItemName + " " + getString(R.string.added_to_inventory), Toast.LENGTH_LONG).show();
                finish();
            }
        });

        buttonCancel.setOnClickListener(v -> {
            manageCancellationAction();
        });
    }

    private void manageCancellationAction() {
        if (!editTextItemName.getText().toString().isEmpty() || !editTextQuantity.getText().toString().isEmpty()) {
            // work to lose, so warn the user of the consequences, and get confirmation
            AlertDialog.Builder builder = new AlertDialog.Builder(AddInventoryActivity.this);
            builder.setTitle(R.string.discarding_title);
            builder.setMessage(R.string.discard_warning_message);
            // We are cancelling a cancel, so we need to be careful with the wording
            builder.setNegativeButton(R.string.keep_draft, (dialog, which) -> {
                // will just return to the editing screen
            });
            builder.setPositiveButton(R.string.discard_draft, (dialog, which) -> {
                Toast.makeText(AddInventoryActivity.this, R.string.inventory_add_cancelled, Toast.LENGTH_LONG).show();
                finish();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // determine what to do if someone clicks the home (back arrow) button
        switch (item.getItemId()) {
            // act on the "Home" menu item
            case android.R.id.home:
                manageCancellationAction(); // same as hitting the Cancel button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String determineErrorMessage(boolean hasItemName, boolean hasQuantity, boolean additionSuccessful) {
        if (additionSuccessful) return ""; // clear the error message

        // addition successful still false. If we have both item name and quantity, we must have a duplicate
        // (for now) that is the only reason tested for not adding the item
        if (hasItemName && hasQuantity) return getString(R.string.duplicate_inventory_item);

        // We are missing itemName, quantity, or both
        String errorMessage = getString(R.string.missing_details_header) + " ";
        if (hasItemName) {
            // we have an item name. So, it must be just the quantity that is missing
            errorMessage += (getString(R.string.missing_inventory_item_quantity));
        } else {
            // we are missing the item name
            errorMessage += getString(R.string.missing_inventory_item_name);
            // are we also missing the quantity?
            if (!hasQuantity) {
                errorMessage += (" " + getString(R.string.and) + " " + getString(R.string.missing_inventory_item_quantity));
            }
        }
        return errorMessage;
    }


}
