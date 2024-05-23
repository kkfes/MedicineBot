package org.example.objects;

import java.util.ArrayList;

//Объект доктора
public class Doctor {
    private long id;
    private long hospital_id;
    private long specialist_type;
    private String name;
    private int cost;
    private String[] selectedTimes = new String[]{};
    private ArrayList<Rating> ratings = new ArrayList<>();

    public static class Rating{
        int rating;
        int comment;
        String owner;

        public Rating(int rating, String owner) {
            this.rating = rating;
            this.comment = comment;
            this.owner = owner;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public int getComment() {
            return comment;
        }

        public void setComment(int comment) {
            this.comment = comment;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }
    }

    public int getRating(){
        if(ratings.isEmpty()){
            return 5;
        }
        int all = 0;
        for (Rating r:ratings){
            all+=r.rating;
        }
        return all/ratings.size();
    }

    public Doctor(long id, long hospital_id, long specialist_type, String name, int cost) {
        this.id = id;
        this.hospital_id = hospital_id;
        this.specialist_type = specialist_type;
        this.name = name;
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(long hospital_id) {
        this.hospital_id = hospital_id;
    }

    public long getSpecialist_type() {
        return specialist_type;
    }

    public void setSpecialist_type(long specialist_type) {
        this.specialist_type = specialist_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String[] getSelectedTimes() {
        return selectedTimes;
    }

    public void setSelectedTimes(String[] selectedTimes) {
        this.selectedTimes = selectedTimes;
    }

    public ArrayList<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(ArrayList<Rating> ratings) {
        this.ratings = ratings;
    }
}
