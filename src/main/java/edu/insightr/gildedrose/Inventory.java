package edu.insightr.gildedrose;

public class Inventory {

    private Item[] items;
    private Visitor visitor = new Visitor();

    public Inventory(Item[] items) {
        super();
        this.items = items;
    }

    public Inventory() {
        super();
        items = new Item[]{
                new Item("+5 Dexterity Vest", 10, 20),
                new Item("Aged Brie", 2, 0),
                new Item("Elixir of the Mongoose", 5, 7),
                new Item("Sulfuras, Hand of Ragnaros", 0, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Conjured Mana Cake", 3, 6)
        };
    }

    public Item[] getItems() {
        return this.items;
    }

    public void printInventory() {
        System.out.println("***************");
        for (Item item : items) {
            System.out.println(item);
        }
        System.out.println("***************");
        System.out.println("\n");
    }

    public void updateQuality() {
        for (Item item : items) {
            item.accept(visitor);
        }
    }

    public void AddItem(String item, int sellin, int quality)
    {
        try
        {
            if(items==null)
            {
                items = new Item[]{
                        new Item(item,sellin,quality)
                };
            }
            else
            {
                Item tabItem[]=new Item[items.length+1];
                for (int i=0; i<items.length;i++)
                {
                    tabItem[i]=items[i];
                }
                tabItem[items.length]=new Item(item,sellin,quality);
                items=tabItem;
            }
        }
        catch (Exception e){}
    }

    public void RemoveItem(Item i)
    {
        Item tabItem[]=new Item[items.length-1];
        int indice_i=-1;
        for (int k=0; k<items.length;k++)
        {
            if(i!=items[k])
            {
                tabItem[k]=items[k];
            }
            else
            {
                indice_i=k;
                k=items.length;
            }
        }
        if(indice_i!=-1)
        {
            for(int k=indice_i;k<items.length-1;k++)
            {
                tabItem[k]=items[k+1];
            }
            items=tabItem;
        }
	}
}
