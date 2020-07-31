package sample;

import javafx.scene.control.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Klasa odpowiadająca za obiekt badania i jego elementy
 */
public class Examination {

    private Date date;
    private double temperature;
    private SimpleDateFormat formatter;
    private int pulse, leukocyte;

    public SimpleDateFormat getFormatter() {
        return formatter;
    }


    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public void setLeukocyte(int leukocyte) {
        this.leukocyte = leukocyte;
    }


    public Date getDate() {
        return date;
    }

    /**
     * Metoda modyfikująca format daty pobranej z pola w GUI
     *
     * @param datePicker pole do wyboru daty
     */
    public void setDate(DatePicker datePicker) {
        try {
            formatter = new SimpleDateFormat("dd.mm.yyyy");
            date = formatter.parse(datePicker.getEditor().getText());
        } catch (ParseException pe) {
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Examination)) {
            return false;
        }
        Examination exam = (Examination) o;
        return exam.getDate() == getDate() && exam.getPulse() == getPulse() && exam.getTemperature() == getTemperature() && exam.getLeukocyte() == getLeukocyte() && exam.getFormatter() == getFormatter();
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, leukocyte, pulse, date, formatter);
    }

    public String toString() {
        return getTemperature() + "," + getLeukocyte() + "," + getPulse() + "," + getDate();
    }


    public double getTemperature() {
        return temperature;
    }

    public int getPulse() {
        return pulse;
    }

    public int getLeukocyte() {
        return leukocyte;
    }
}
