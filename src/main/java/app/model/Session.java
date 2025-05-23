package app.model;

import java.util.Date;

public class Session {
    private int id;
    private String title;
    private String description;
    private Date date;

    public Session(int id) {
        this.id = id;
    }

    public Session(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Session(int sesId, String sesTitle, String sesDesc, Date sesDate) {
        this.id = sesId;
        this.title = sesTitle;
        this.description = sesDesc;
        this.date = sesDate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}
