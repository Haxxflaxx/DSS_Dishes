package application;

/**
 * Created by haxxflaxx on 2015-11-09.
 */
public class Recipe {
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

    public Recipe(String id, String name, String type, String cuisine, String difficulty, String ratings, String diet, String time, String timeUnit, String description) {
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
}