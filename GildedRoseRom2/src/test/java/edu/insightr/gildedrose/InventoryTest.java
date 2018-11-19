package edu.insightr.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    private Item vest = new Item("+5 Dexterity Vest", 0, 0);
    private Item agedBrie =  new Item("Aged Brie", 0, 0);
    private Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 0);
    private Item passes = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 0);
    private Item conjured = new Item("Conjured Mana Cake", 0, 0);
    private Inventory testInventory = new Inventory(new Item[] {
            vest, agedBrie, sulfuras, passes, conjured
    });

    @Test
    void updateQuality() {

        //basic case
        vest.setQuality(20);
        vest.setSellIn(10);
        testInventory.updateQuality();
        assertEquals(19, vest.getQuality());
        assertEquals(9, vest.getSellIn());

        //sell by date passed
        vest.setQuality(10);
        vest.setSellIn(0);
        testInventory.updateQuality();
        assertEquals(8, vest.getQuality());

        //quality never negative
        vest.setQuality(0);
        vest.setSellIn(10);
        testInventory.updateQuality();
        assertFalse(vest.getQuality() < 0);

        //"Aged Brie" quality increases
        agedBrie.setQuality(4);
        agedBrie.setSellIn(5);
        testInventory.updateQuality();
        assertEquals(5, agedBrie.getQuality());
        agedBrie.setQuality(10);
        agedBrie.setSellIn(0);
        testInventory.updateQuality();
        assertEquals(12, agedBrie.getQuality());

        //quality never > 50
        agedBrie.setQuality(50);
        agedBrie.setSellIn(10);
        testInventory.updateQuality();
        assertEquals(50, agedBrie.getQuality());
        agedBrie.setQuality(49);
        agedBrie.setSellIn(0);
        assertEquals(49, agedBrie.getQuality()); //seems to me that it would be more logical with 50

        //"Sulfuras"
        sulfuras.setQuality(80);
        sulfuras.setSellIn(0);
        testInventory.updateQuality();
        assertEquals(80, sulfuras.getQuality());

        //"Backstages passes"
        passes.setQuality(20);
        passes.setSellIn(15);
        testInventory.updateQuality();
        assertEquals(21, passes.getQuality());
        passes.setQuality(10);
        passes.setSellIn(10);
        testInventory.updateQuality();
        assertEquals(12, passes.getQuality());
        passes.setQuality(10);
        passes.setSellIn(5);
        testInventory.updateQuality();
        assertEquals(13, passes.getQuality());
        passes.setQuality(10);
        passes.setSellIn(0);
        testInventory.updateQuality();
        assertEquals(0, passes.getQuality());
    }

    @Test
    void updateQualityConjuredItemsUpdate() {

        //basic case
        conjured.setQuality(20);
        conjured.setSellIn(10);
        testInventory.updateQuality();
        assertEquals(18, conjured.getQuality());
        assertEquals(9, conjured.getSellIn());

        //sell by date passed
        conjured.setQuality(20);
        conjured.setSellIn(-1);
        testInventory.updateQuality();
        assertEquals(16, conjured.getQuality());

        //quality never negative
        conjured.setQuality(0);
        conjured.setSellIn(10);
        testInventory.updateQuality();
        assertFalse(conjured.getQuality() < 0);
    }
}