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

import java.util.ArrayList;
import nz.co.afleet.bit603_a3_johnmcpherson.ui.inventory.InventoryRecyclerViewAdapter;

import static nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem.addInventoryItemToDatabase;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem.createTestItems;
import static nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem.deleteAllInventoryItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class Integration_InventoryItemDisplay {
    private Application application;
    ApplicationDatabase applicationDatabase;

    @Before
    public void initialiseApplicationAndDatabase() {
        application = ApplicationProvider.getApplicationContext();
        applicationDatabase = ApplicationDatabase.getInstance(application);
    }

    @Test
    public void testRecyclerViewPageIncrement() {
        createTestItems(application);
        addInventoryItemToDatabase(application, "Test", "Cookie", "2" );
        ArrayList<InventoryItem> inventoryItems = new ArrayList<>();
        inventoryItems.addAll(getDaoInventory().getInventoryItems());
        InventoryRecyclerViewAdapter inventoryRecyclerViewAdapter = new InventoryRecyclerViewAdapter(inventoryItems);
        assertEquals(0, inventoryRecyclerViewAdapter.getPositionOfFirstItemToDisplay());
        assertEquals(5, inventoryRecyclerViewAdapter.getItemCount());
        assertTrue(inventoryRecyclerViewAdapter.canIncrementPage());
        inventoryRecyclerViewAdapter.incrementPage();
        inventoryRecyclerViewAdapter.incrementPage();
        inventoryRecyclerViewAdapter.incrementPage();
        assertTrue(inventoryRecyclerViewAdapter.canIncrementPage());
        inventoryRecyclerViewAdapter.incrementPage();
        assertEquals(1, inventoryRecyclerViewAdapter.getItemCount());
        assertFalse(inventoryRecyclerViewAdapter.canIncrementPage());
    }

    @Test
    public void testRecyclerViewAutoPageDecrement() {
        createTestItems(application);
        addInventoryItemToDatabase(application, "Test", "Cookie", "2" );
        ArrayList<InventoryItem> inventoryItems = new ArrayList<>();
        inventoryItems.addAll(getDaoInventory().getInventoryItems());
        InventoryRecyclerViewAdapter inventoryRecyclerViewAdapter = new InventoryRecyclerViewAdapter(inventoryItems);
        inventoryRecyclerViewAdapter.incrementPage();
        inventoryRecyclerViewAdapter.incrementPage();
        inventoryRecyclerViewAdapter.incrementPage();
        assertTrue(inventoryRecyclerViewAdapter.canIncrementPage());
        inventoryRecyclerViewAdapter.incrementPage();
        assertFalse(inventoryRecyclerViewAdapter.canIncrementPage());
        assertEquals(1, inventoryRecyclerViewAdapter.getItemCount());
        deleteAllInventoryItems(application);
        inventoryItems.clear();
        inventoryItems.addAll(getDaoInventory().getInventoryItems());
        inventoryRecyclerViewAdapter.notifyDataSetChanged();
        assertEquals(0, inventoryRecyclerViewAdapter.getPositionOfFirstItemToDisplay());
        assertFalse(inventoryRecyclerViewAdapter.canDecrementPage());
        assertFalse(inventoryRecyclerViewAdapter.canIncrementPage());
    }

    private DaoInventory getDaoInventory() {
        return applicationDatabase.daoInventory();
    }

    @After
    public void tearDown() {
        ApplicationDatabase.TESTING_tearDown();
    }
}
