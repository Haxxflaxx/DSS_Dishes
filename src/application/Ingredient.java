package application;

/**
 * Created by Fredrik
 * Responsible programmer Fredrik Rissanen
 * Container for holding a ingredient.
 */
public class Ingredient {
    String name;
    String amount;
    String unit;
    String id;
    String type;

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

    public Ingredient(String id, String name, String type, String unit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.unit = unit;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
};