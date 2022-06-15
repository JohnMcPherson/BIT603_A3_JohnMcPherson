/*
********IMPORTANT NOTE********
        - The majority of this code was copied from BIT603 Assessment 2, and modified to meet the new requirements
*/

package nz.co.afleet.bit603_a3_johnmcpherson.database;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Test that InventoryItem can be created and gets are correct
 */

public class UnitTest_InventoryItem {
    @Test
    public void createItemConfirmGets() {
        String NAME = "Sugar";
        double DOUBLE_QUANTITY = 6;
        String STRING_QUANTITY = "6.0";

        InventoryItem inventoryItem = InventoryItem.create(NAME, DOUBLE_QUANTITY);
        assertEquals(NAME, inventoryItem.getName());
        assertEquals(DOUBLE_QUANTITY, inventoryItem.getQuantity(), 0);
        assertEquals(STRING_QUANTITY, inventoryItem.getStringQuantity());
    }

   @Test
    public void confirmDecimalQuantity() {
        String NAME = "Sugar";
        double DOUBLE_QUANTITY = 1.24;
        String STRING_QUANTITY = "1.24";

        InventoryItem inventoryItem = InventoryItem.create(NAME, DOUBLE_QUANTITY);
        assertEquals(NAME, inventoryItem.getName());
        assertEquals(DOUBLE_QUANTITY, inventoryItem.getQuantity(), 0);
        assertEquals(STRING_QUANTITY, inventoryItem.getStringQuantity());
    }
}
