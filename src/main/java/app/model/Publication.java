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

    public Publication(Session session, LocalDate publicationDate, String note, String publishedTo) {
        this.session = session;
        this.publicationDate = publicationDate;
        this.note = note;
        this.publishedTo = publishedTo;
    }

    public int getId() {
        return id;
    }

    public Session getSession() {
        return session;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public String getNote() {
        return note;
    }

    public String getPublishedTo() {
        return publishedTo;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPublishedTo(String publishedTo) {
        this.publishedTo = publishedTo;
    }
}
