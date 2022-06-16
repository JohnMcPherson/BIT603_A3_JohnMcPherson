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

import static org.junit.Assert.assertEquals;

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
    public void testRecyclerView() {
        InventoryItem.createTestItems(application);
        ArrayList<InventoryItem> inventoryItems = new ArrayList<>();
        inventoryItems.addAll(getDaoInventory().getInventoryItems());
        InventoryRecyclerViewAdapter inventoryRecyclerViewAdapter = new InventoryRecyclerViewAdapter(inventoryItems);
        assertEquals(0, inventoryRecyclerViewAdapter.getPositionOfFirstItemToDisplay());
        assertEquals(5, inventoryRecyclerViewAdapter.getItemCount());
     }

    private DaoInventory getDaoInventory() {
        return applicationDatabase.daoInventory();
    }

    @After
    public void tearDown() {
        ApplicationDatabase.TESTING_tearDown();
    }
}
