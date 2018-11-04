package edu.insightr.gildedrose;

public class BrieStrategy implements IStrategy {

    public void updateQuality(Item item)
    {
        if (item.getQuality() < 50)
            item.setQuality(item.getQuality() + 1);
        if (item.getSellIn() <= 0 && item.getQuality() < 50)
            item.setQuality(item.getQuality() + 1);
        item.setSellIn(item.getSellIn() - 1);
    }
}