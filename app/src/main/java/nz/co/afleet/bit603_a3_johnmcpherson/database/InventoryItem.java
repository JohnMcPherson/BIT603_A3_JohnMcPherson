/*
ASSUMPTIONS
    - we need decimals (double) to record quantities. this will allow recording of both:
        - integer quantities
        - measurement in parts (e.g. kg)
    - the unit of measure (items, packets, kg) is not required. There is no mention of them
        in the requirements. In the real world, we would check

ABOUT InventoryItem
    -   InventoryItem is the UI facing interface to the inventory database.
        Activities (and other UI components) should use InventoryItem for all database facing (inventory) services.
        This:
        -   makes it simpler to build UI components
        -   protects the database from illegal calls (such as attempting to add a duplicate item, which would cause a crash)

********IMPORTANT NOTE********
    - The majority of this code was copied from BIT603 Assessment 2, and modified to meet the new requirements

*/

package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.app.Application;

import androidx.room.ColumnInfo;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.List;

// This is a room database entity.
// Make the Name unique by indexing and annotating as unique. Also enforced at higher levels in the app, to reduce risk of crashes
@Entity(indices = {@Index(value = {"Name"},
        unique = true)})
public class InventoryItem {

    // Good practice to add an ID, although we do not expect to need it (yet)
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "Name")
    private String Name;

    @ColumnInfo(name = "Quantity")
    private double quantity;

    public int getId() {
        return id;
    }

    // to provide text string for display
    public String getIdString() {
        return String.valueOf(getId());
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getStringQuantity() {
        return String.valueOf(getQuantity());
    }

    // This is the method that the UI developer should use to create and add to inventory
    // If (when, for a production app) we added update and delete functionality, we would create appropriate UI facing methods in this class
    // Uses a string for quantity, so we can provide a service directly to the UI, without the UI developer needing to know
    // how the quantity is stored
    public static void addInventoryItemToDatabase(Application application, String name, String quantity) {
        // initial check on data quality
        if (name == null) return;
        double doubleQuantity;
        try {
            doubleQuantity = Double.parseDouble(quantity);
        } catch (NumberFormatException e) {
            // doesn't crash, but does not indicate error type
            return;
        }

        if (doubleQuantity < 0) {
            // negatives prevented from getting into the database, but does not indicate error type
            return;
        }

        // don't allow duplicates. The database doesn't allow duplicates, so this is important to prevent a crash,
        // (in case the UI developer does not check for duplicates)
        // A duplicate will not return any error (from this method), but we have provided isDuplicateOfInventoryItem().
        // The UI developer is expected to use that method, prior to attempting to add inventory to the database
        if (!isDuplicateOfInventoryItem(application, name)) {
            // if all OK, create and save the InventoryItem
            InventoryItem newInventoryItem = InventoryItem.create(name, doubleQuantity);
            getDaoInventory(application).addInventoryItem(newInventoryItem);
        }
    }

    // Make it quicker and less error prone to create a new inventory item
    // Default visibility hides this function from the ui (and other) packages, so the UI developer does not create an InventoryItem,
    // and (inadvertently) omit adding it to the database.
    static InventoryItem create(String name, double quantity) {
        InventoryItem item = new InventoryItem();
        item.setName(name);
        item.setQuantity(quantity);
        return item;
    }


    // provides access to DaoInventory.getInventoryItems(). (All access to Inventory items in the Database to be done through the InventoryItem class)
    public static HashMap<String, Double> getInventoryItems(Application application) {
        ApplicationDatabase applicationDatabase = ApplicationDatabase.getInstance(application);
        DaoInventory daoInventory = applicationDatabase.daoInventory();
        List<InventoryItem> inventoryItems = daoInventory.getInventoryItems();
        HashMap<String, Double> returnMap = new HashMap<>();
        for (InventoryItem inventoryItem : inventoryItems) {
            returnMap.put(inventoryItem.getName(), inventoryItem.getQuantity());
        }
        return returnMap;
    }

    private static DaoInventory getDaoInventory(Application application) {
        ApplicationDatabase inventoryDatabase = ApplicationDatabase.getInstance(application);
        return inventoryDatabase.daoInventory();
    }

    public static boolean isDuplicateOfInventoryItem(Application application, String candidateName) {
        List<InventoryItem> currentInventoryItems = getDaoInventory(application).getInventoryItems();
        // check each current inventory item for the candidate name
        for (InventoryItem inventoryItem : currentInventoryItems) {
            if (inventoryItem.getName().equals(candidateName)) {
                // a match means there is a duplicate
                return true;
            }
        }
        return false;
    }

    /*
        The name says it. Only use for testing, or if you are SURE you want to clear the databse!
    */
    public static void FOR_TEST_USE_ONLY_deleteAllInventoryItems(Application application) {
        getDaoInventory(application).FOR_TEST_USE_ONLY_deleteAllInventoryItems();
    }
}
