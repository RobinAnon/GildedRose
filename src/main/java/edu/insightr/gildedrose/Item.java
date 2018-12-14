package edu.insightr.gildedrose;

import java.time.LocalDate;

public class Item {

    private String name;
    private int sellIn;
    private LocalDate date;
    private float buy;
    private float sell;
    private int quality;

    public Item(String name, int sellIn, int quality, LocalDate date) {
        super();
        this.name = name;
        this.sellIn = sellIn;
        this.quality = quality;
        this.date = date;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSellIn() {
        return sellIn;
    }

    public void setSellIn(int sellIn) {
        this.sellIn = sellIn;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public LocalDate getDate(){ return date;}

    public void setDate(LocalDate date){this.date=date;}

    public void accept(Visitor v) {
        v.visit(this);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", sellIn=" + sellIn +
                ", quality=" + quality +
                '}';
    }

    public Object clone()
    {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
    }
}