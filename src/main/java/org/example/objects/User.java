package org.example.objects;

// Запись пользователя
public class User {
    private String id;
    private long user_id;
    private String text;
    private long time;

    public User(String id, long user_id, String text, long time) {
        this.id = id;
        this.user_id = user_id;
        this.text = text;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
