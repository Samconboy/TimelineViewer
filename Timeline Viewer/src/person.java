

import java.time.LocalDate;

public class person {

    protected String name;
    protected LocalDate birthDate;
    protected LocalDate deathDate;
    protected String description;
    protected String hashCode;
    protected boolean alive;

    public void person(String name, LocalDate birthDate, LocalDate deathDate, String description) {
        this.name = name;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        this.description = description;
    }
    public void person(){}

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }



    public String getHashCode() {
        return hashCode;
    }
    public String getName() {
        return name;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public LocalDate getDeathDate() {
        return deathDate;
    }
    public String getDescription() {
        return description;
    }
    public boolean getAlive() {return alive;}


}