package application;

/**
 * Created by Fredrik on 2015-11-15.
 *
 * Container for holding a ingredient.
 */
public class Ingredient {
    String name;
    String amount;
    String unit;
    String id;

    public Ingredient(String name, String amount, String unit){
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public Ingredient(String name){
        this.name = name;
    }

    public Ingredient(String name, String id){
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


};