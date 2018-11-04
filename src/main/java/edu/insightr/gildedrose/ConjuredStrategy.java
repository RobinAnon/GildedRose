package edu.insightr.gildedrose;

public class ConjuredStrategy implements IStrategy {
    public void updateQuality(Item item)
    {
        if (item.getQuality() > 0) {
            item.setQuality(item.getQuality() - 1);
            if (item.getQuality() > 0)
                item.setQuality(item.getQuality() - 1);
        }
        if (item.getSellIn() <= 0 && item.getQuality() > 0) {
            item.setQuality(item.getQuality() - 1);
            if (item.getQuality() > 0)
                item.setQuality(item.getQuality() - 1);
        }
        item.setSellIn(item.getSellIn() - 1);
    }
}
