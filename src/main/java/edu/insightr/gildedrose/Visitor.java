package edu.insightr.gildedrose;

public class Visitor {
    private IStrategy strategy;
    
    public void visit(Item item) {
        setStrategy(item);
        strategy.updateQuality(item);
    }

    private void setStrategy(Item item) {
        switch (item.getName()) {
            case "+5 Dexterity Vest":
                strategy = new VestStrategy();
                break;
            case "Aged Brie":
                strategy = new BrieStrategy();
                break;
            case "Elixir of the Mongoose":
                strategy = new ElixirStrategy();
                break;
            case "Sulfuras, Hand of Ragnaros":
                strategy = new SulfurasStrategy();
                break;
            case "Conjured Mana Cake":
                strategy = new ConjuredStrategy();
                break;
            case "Backstage passes to a TAFKAL80ETC concert":
                strategy = new BackstagesStrategy();
                break;
            default:
                strategy = new VestStrategy();
        }
    }
}
