/*
********IMPORTANT NOTE********
        - The majority of this code was copied from BIT603 Assessment 2, and modified to meet the new requirements
*/

package nz.co.afleet.bit603_a3_johnmcpherson.database;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

import nz.co.afleet.bit603_a3_johnmcpherson.database.ApplicationDatabase;
import nz.co.afleet.bit603_a3_johnmcpherson.database.DaoInventory;
import nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class Integration_InventoryItem_DatabaseTesting {
    private Application application;
    ApplicationDatabase applicationDatabase;

    private final String SUGAR = "Sugar";
    private final String SUGAR_QUANTITY_STRING = "4";
    final double SUGAR_QUANTITY_DOUBLE = 4;
    private final String FLOUR = "Flour";
    private final double FLOUR_QUANTITY = 6.6;



    @Before
    public void initialiseApplicationAndDatabase() {
        application = ApplicationProvider.getApplicationContext();
        applicationDatabase = ApplicationDatabase.getInstance(application);
    }

    @Test
    public void testCreateInventoryItem() {
        // test with whole number quantity
        InventoryItem sugarInventory = InventoryItem.create(SUGAR, SUGAR_QUANTITY_DOUBLE);
        testInventoryItemContent(sugarInventory, SUGAR, SUGAR_QUANTITY_DOUBLE);

        // and test with decimal number quantity
        InventoryItem flourInventory = InventoryItem.create(FLOUR, FLOUR_QUANTITY);
        testInventoryItemContent(flourInventory, FLOUR, FLOUR_QUANTITY);
    }

    @Test
    public void testAddInventoryItemsToDatabase() {
        InventoryItem sugarInventory = InventoryItem.create(SUGAR, SUGAR_QUANTITY_DOUBLE);
        InventoryItem flourInventory = InventoryItem.create(FLOUR, FLOUR_QUANTITY);

        // add items to the database
        getDaoInventory().addInventoryItem(sugarInventory);
        getDaoInventory().addInventoryItem(flourInventory);

        List<InventoryItem> inventoryItems = getDaoInventory().getInventoryItems();
        // confirm the number of items in the database
        assertEquals(inventoryItems.size(), 2);
        // and the contents
        testInventoryItemContent(inventoryItems.get(0), SUGAR, SUGAR_QUANTITY_DOUBLE);
        testInventoryItemContent(inventoryItems.get(1), FLOUR, FLOUR_QUANTITY);
        // note: we are not interested in the ID, which was added for consistency with good database practice

        // check identification of duplicates
        assertTrue(InventoryItem.isDuplicateOfInventoryItem(application, SUGAR));
        assertFalse(InventoryItem.isDuplicateOfInventoryItem(application,"New Item"));

    }

    @Test
    public void testAddInventoryItemsToDatabaseViaInventoryItem() {
        // confirm that item with junk quantity will not be added to the database
        InventoryItem.addInventoryItemToDatabase(application, "Item with junk quantity", "Junk quantity");
        // confirm item not added to database the database
        List<InventoryItem> inventoryItems = getDaoInventory().getInventoryItems();
        assertEquals(inventoryItems.size(), 0);

        // confirm that item with negative quantity will not be added to the database
        InventoryItem.addInventoryItemToDatabase(application, "Item with negative quantity", "-6.0");
        // confirm item not added to database the database
        inventoryItems = getDaoInventory().getInventoryItems();
        assertEquals(inventoryItems.size(), 0);

        // confirm addition of Sugar
        InventoryItem.addInventoryItemToDatabase(application, SUGAR, SUGAR_QUANTITY_STRING);

        // confirm the number of items in the database
        inventoryItems = getDaoInventory().getInventoryItems();
        assertEquals(inventoryItems.size(), 1);
        // check the contents (including conversion of string to double)
        testInventoryItemContent(inventoryItems.get(0), SUGAR, SUGAR_QUANTITY_DOUBLE);
    }
    private void testInventoryItemContent(InventoryItem item, String expectedName, double expectedQuantity) {
        assertEquals(item.getName(), expectedName);
        assertEquals(item.getQuantity(), expectedQuantity, 0);
    }

    private DaoInventory getDaoInventory() {
        return applicationDatabase.daoInventory();
    }

    @After
    public void tearDown() {
        ApplicationDatabase.TESTING_tearDown();
    }
}
