package app.model;

import java.time.LocalDate;

public class Publication {
    private int id;
    private Session session;
    private LocalDate publicationDate;
    private String note;
    private String publishedTo;

    public Publication() {
    }

    public Publication(int id, Session session, LocalDate publicationDate, String note, String publishedTo) {
        this.id = id;
        this.session = session;
        this.publicationDate = publicationDate;
        this.note = note;
        this.publishedTo = publishedTo;
    }

    public String getPublishedTo() {
        return publishedTo;
    }

    public void setPublishedTo(String publishedTo) {
        this.publishedTo = publishedTo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
