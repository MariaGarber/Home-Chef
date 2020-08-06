/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.example.homechef;
import androidx.annotation.NonNull;

public class Recipe {

    private String id;
    private String Title;
    private String Thumbnail;
    private int amountOfDishes;
    private int readyInMins;

    public Recipe(String id, String title, String thumbnail, int amountOfDishes, int readyInMins) {
        this.id = id;
        Title = title;
        Thumbnail = thumbnail;
        this.amountOfDishes = amountOfDishes;
        this.readyInMins = readyInMins;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return Title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public int getAmountOfDishes() {
        return amountOfDishes;
    }

    public int getReadyInMins() {
        return readyInMins;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }
}
