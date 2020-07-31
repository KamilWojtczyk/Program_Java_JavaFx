package sample;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

/**
 * Klasa tworząca pacjenta i opisująca jego cechy
 */

public class Person {
    private String name, pesel, insurance, surname, gender;
    private Examination examination;


    /**
     * Konstruktor przetwarzający podane argumenty w wartości możliwe do wyświetlenia przez TableView
     *
     * @param name      imię
     * @param pesel     pesel
     * @param surname   nazwisko
     * @param gender    płeć
     * @param insurance ubezpieczenie
     */
    public Person(String name, String pesel, String surname, String gender, String insurance) {
        this.name = name;
        this.surname = surname;
        this.pesel = pesel;
        this.insurance = insurance;
        this.gender = gender;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public void createExamination() {
        examination = new Examination();
    }


    public String getName() {
        return name;
    }


    public String getPesel() {
        return pesel;
    }

    public String getInsurance() {
        return insurance;
    }

    public String getGender() {
        return gender;
    }

    public String toString() {
        return getName() + "," + getSurname() + "," + getPesel() + "," + getGender() + "," + getExamination() + "," + getInsurance();
    }

    public String getSurname() {
        return surname;
    }

    public boolean getExamined() {
        return (this.examination != null);
    }


    public Examination getExamination() {
        return examination;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return person.getGender().equals(getGender()) && person.getName().equals(getName()) && person.getInsurance().equals(getInsurance()) && person.getSurname().equals(getSurname()) && person.getPesel().equals(getPesel()) && person.getExamination() == getExamination();
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, name, surname, pesel, insurance, examination);
    }


}
