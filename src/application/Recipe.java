package application;

import application.dbTools.Query;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by haxxflaxx on 2015-11-09.
 *
 * Container for holding a recipe.
 */
public class Recipe {
    private static Recipe selected;

    String id;
    String name;
    String type;
    String cuisine;
    String difficulty;
    String ratings;
    String diet;
    String time;
    String timeUnit;
    String description;
    String date;
    String creator;

    public Recipe(String id, String name, String type, String cuisine, String difficulty, String ratings, String diet, String time, String timeUnit, String description, String date, String creator) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.cuisine = cuisine;
        this.difficulty = difficulty;
        this.ratings = ratings;
        this.diet = diet;
        this.time = time;
        this.timeUnit = timeUnit;
        this.description = description;
        this.date = date;
        this.creator = creator;
    }

    public static Recipe getSelected() {
        return selected;
    }

    public static void setSelected(Recipe selected) {
        Recipe.selected = selected;
    }

    /**
     * Updates the info in selected with a recipe based on ID.
     * @param ID The ID of the chosen recipe.
     */
    public static void setSelectedById(String ID) {
        try {
            String condition = "ID='" + ID + "'";
            ArrayList<ArrayList<String>> dataSet = Query.fetchData("recipes", "*", condition);

            selected = new Recipe(
                    dataSet.get(0).get(0),
                    dataSet.get(0).get(1),
                    dataSet.get(0).get(2),
                    dataSet.get(0).get(3),
                    dataSet.get(0).get(4),
                    dataSet.get(0).get(5),
                    dataSet.get(0).get(6),
                    dataSet.get(0).get(7),
                    dataSet.get(0).get(8),
                    dataSet.get(0).get(9),
                    dataSet.get(0).get(10),
                    dataSet.get(0).get(11)
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getRatings() {
        return ratings;
    }

    public String getDiet() {
        return diet;
    }

    public String getTime() {
        return time;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {return date;}

    public String getCreator() {return creator;}

    public void setCreator(String creator) {this.creator = creator;}
}