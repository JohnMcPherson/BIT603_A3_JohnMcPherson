/*
IMPORTANT NOTE
        - The majority of this code was copied from BIT603 Assessment 2, and modified to meet the new requirements

*/


        package nz.co.afleet.bit603_a3_johnmcpherson.database;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

// this interface and its methods are limited to default visibility so that only InventoryItem can use it

@androidx.room.Dao
interface DaoInventory {
    @Insert
    void addInventoryItem(nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem inventoryItem);

    @Query("SELECT * FROM InventoryItem")
    List<nz.co.afleet.bit603_a3_johnmcpherson.database.InventoryItem> getInventoryItems();

    @Query ("DELETE FROM InventoryItem")
    public void deleteAllInventoryItems();
}
