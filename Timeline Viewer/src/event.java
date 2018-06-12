

import java.time.LocalDate;

public class event {

    protected String name;
    protected LocalDate date;
    protected String description;
    protected String linkedPerson;
    protected String webLink;


    public void event(){}
    public void event(String name) {
        this.name = name;
    }
    public void event(String name, LocalDate date, String description) {
        this.name = name;
        this.description = description;
        this.date = date;
    }



    public LocalDate getDate() {
        return date;
    }
    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public void setLinkedPerson(String linkedPerson) {
        this.linkedPerson = linkedPerson;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLinkedPerson() {
        return linkedPerson;
    }
}