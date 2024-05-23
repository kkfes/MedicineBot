package org.example.objects;

//Объект доктора
public class Doctor {
    private long id;
    private long hospital_id;
    private long specialist_type;
    private String name;
    private int cost;
    private String[] selectedTimes = new String[]{};

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
}
