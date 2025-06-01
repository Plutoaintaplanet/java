package memlog2;

import java.time.LocalDate;

public class Memo2 {
    private int id;
    private String title;
    private String content;
    private LocalDate date;

    // Constructor used when creating a new memo (id is not yet known)
    public Memo2(String title, String content, LocalDate date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    // Constructor used when retrieving a memo from the database (id is known)
    public Memo2(int id, String title, String content, LocalDate date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getDate() {
        return date;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // toString override (optional, useful for debugging or JList display)
    @Override
    public String toString() {
        return title + " (" + date + ")";
    }
}
