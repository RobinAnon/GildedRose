package edu.insightr.gildedrose;

import java.time.LocalDate;

public class Inventory {

    private Item[] items;
    private Visitor visitor = new Visitor();
    private Item[] itemsSell;

    public Inventory(Item[] items) {
        super();
        this.items = items;
    }

    public Inventory() {
        super();
        items = new Item[]{
                new Item("+5 Dexterity Vest", 10, 20, LocalDate.of(2018,12,12)),
                new Item("Aged Brie", 2, 0,LocalDate.of(2018,12,12)),
                new Item("Elixir of the Mongoose", 5, 7, LocalDate.of(2018,12,12)),
                new Item("Sulfuras, Hand of Ragnaros", 0, 80,LocalDate.of(2018,12,13)),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20,LocalDate.of(2018,12,13)),
                new Item("Conjured Mana Cake", 3, 6,LocalDate.of(2018,12,14))
        };
    }

    public Item[] getItems() {
        return this.items;
    }

    public Item[] getItemsSell() {
        return this.itemsSell;
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

    public void AddItem(String item, int sellin, int quality, LocalDate date)
    {
        try
        {
            if(items==null)
            {
                items = new Item[]{
                        new Item(item,sellin,quality,date)
                };
            }
            else
            {
                Item tabItem[]=new Item[items.length+1];
                for (int i=0; i<items.length;i++)
                {
                    tabItem[i]=items[i];
                }
                tabItem[items.length]=new Item(item,sellin,quality, date);
                items=tabItem;
            }
        }
        catch (Exception e){}
    }

    public void RemoveItem(Item i)
    {

        if(itemsSell==null)
        {
            itemsSell= new Item[1];
            itemsSell[0]=i;
        }
        else
        {
            Item[] newItemSell = new Item[itemsSell.length+1];
            for(int k=0;k<itemsSell.length;k++)
            {
                newItemSell[k]=itemsSell[k];
            }
            newItemSell[newItemSell.length-1]=i;
            itemsSell=newItemSell;
        }

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

    public Number NumberElementsDatei(LocalDate date)
    {
        int res=0;
        for(int i=0; i<items.length;i++)
        {
            if(date.equals(items[i].getDate()))
            {
                res++;
            }
        }
        return res;
    }

    public Number NumberElementsSellIn(Number SellIn)
    {
        int res=0;
        for(int i=0; i<items.length;i++)
        {
            if(SellIn.equals(items[i].getSellIn()))
            {
                res++;
            }
        }
        return res;
    }

    public Number NumberElementsBuyDated(LocalDate d)
    {
        int res=0;
        for(int i=0; i<items.length;i++)
        {
            if(items[i]!=null)
            {
                if(d.equals(items[i].getDate()))
                {
                    res++;
                }
            }
        }
        return res;
    }

    public Number NumberElementsSellDated(LocalDate d)
    {
        int res=0;
        for(int i=0; i<itemsSell.length;i++)
        {
            if(itemsSell[i]!=null)
            {
                if(d.equals(itemsSell[i].getDate()))
                {
                    res++;
                }
            }
        }
        return res;
    }
}
