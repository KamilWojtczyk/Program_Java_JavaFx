package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;


/**
 * Klasa odpowiadająca za wszelkie interakcje użytkownika z GUI
 */

public class Controller {

    private static Controller controller;
    private GUICreator guiCreator;
    private String toogleGroupValue;
    private boolean peselDuplicate;
    private int index;
    private String PESEL;
    private Person person;
    private String name;
    private ObservableList<Person> patientList;

    private Controller() {

        patientList = FXCollections.observableArrayList();
        guiCreator = GUICreator.getInstance();


    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }


    /**
     * Metoda tworząca nowego pacjenta, umieszczająca go na liście i wyświetlająca w tabeli
     */
    public void addPatient() {


        if (GUICreator.getInstance().getName().equals("") || guiCreator.getSurname().equals("") || guiCreator.getPesel().equals("") || guiCreator.getPesel().length() < 11 || toogleGroupValue == null) {
            guiCreator.setFieldColor(guiCreator.getNameField());
            guiCreator.setFieldColor(guiCreator.getSurnameField());
            guiCreator.setFieldColor(guiCreator.getPeselField());
            guiCreator.setPeselLimit(guiCreator.getPeselField());
        } else {

            PESEL = guiCreator.getPesel();
            person = guiCreator.getPerson(); //pacjent zaznaczony w tabeli
            index = guiCreator.getIndex(); //indeks wybranego pacjenta

            if (toogleGroupValue.equals("Mężczyzna")) {
                name = "M";
            } else if (toogleGroupValue.equals("Kobieta")) {
                name = "K";
            }

            Person person1 = new Person(guiCreator.getName() + " " + guiCreator.getSurname(), PESEL, guiCreator.getSurname(), name, guiCreator.getSelectedInsurance()); //nowy pacjent


            peselDuplicate = false; //zmienna określająca czy wprowadzony przez użytkownika pesel jest już w systemie

            if (person == null) {


                browseList(); //przeszukanie listy w przypadku dodawania nowego pacjenta
                if (peselDuplicate) {
                    guiCreator.setPeselColorRed();
                } else {
                    person = person1;
                    patientList.add(person1);
                    guiCreator.disablePane();
                }


            } else {

                guiCreator.clearTableSelection();
                patientList.remove(person); //usunięcie zaznaczonego pacjenta

                browseList(); //przeszukanie listy bez pacjenta, którego modyfikujemy
                patientList.add(index, person);

                if (peselDuplicate) {
                    guiCreator.setPeselColorRed();
                } else {
                    person1.setExamination(person.getExamination());
                    patientList.set(index, person1);
                    guiCreator.selectTableIndex(index);//ponowne dodanie pacjenta
                    guiCreator.disablePane();

                }

            }
            guiCreator.setTableItems(patientList);
            guiCreator.enableAddButton();
            guiCreator.clearTableSelection();

        }


    }

    /**
     * Metoda ograniczająca możliwości wprowadzania znaków w polu tekstowym do cyfr
     *
     * @param field pole
     */
    public void correctDigitTyping(String t1, TextField field) {
        if (!t1.matches("0-9")) {

            field.setText(t1.replaceAll("[^0-9]", ""));

        }

    }


    public void limitTemp(String text, TextField field) {
        if (!text.matches("0-9.")) {
            field.setText(text.replaceAll("[^0-9.]", ""));
        }
    }

    /**
     * Metoda przeszukująca listę w poszukiwaniu duplikatu
     */
    public void browseList() {
        if (patientList.isEmpty()) {
            peselDuplicate = false;
        }
        for (int i = 0; i < patientList.size(); i++) {
            if (patientList.get(i).getPesel().equals(PESEL)) {

                peselDuplicate = true;
                break;


            }
        }
    }

    /**
     * Metoda wyświetlająca dane pacjenta z tabeli w odpowiednich polach
     */
    public void getDataFromTable(Person actualPerson) {


        guiCreator.disableExaminationPane();

        if (actualPerson != null) {
            guiCreator.enableDeleteButton();
            guiCreator.disableAddButton();
            guiCreator.enablePane();
            getExaminationDataFromTable(actualPerson);
            guiCreator.enableExaminationPane();
            guiCreator.setName(actualPerson.getName().split(" ")[0]);
            guiCreator.setSurname(actualPerson.getSurname());
            guiCreator.setPesel(actualPerson.getPesel());


            if (actualPerson.getGender().equals("Mężczyzna")) {
                guiCreator.selectMaleGender();
            } else if (actualPerson.getGender().equals("Kobieta")) {
                guiCreator.selectFemaleGender();
            }


            if (actualPerson.getInsurance().equals("NFZ")) {
                guiCreator.selectInsurance(0);
            } else if (actualPerson.getInsurance().equals("Prywatne")) {
                guiCreator.selectInsurance(1);
            } else
                guiCreator.selectInsurance(2);
        } else guiCreator.disableDeleteButton();
    }


    /**
     * Metoda ustawiająca domyślny kolor pola tekstowego po naciśnięciu na niego myszką
     *
     * @param field pole tekstowe
     */
    public void setDefaultFieldColor(TextField field) {

        field.setStyle("-fx-background-color: white ;-fx-border-color: black");

    }

    /**
     * Metoda ograniczająca ilość znaków wpisanych w polu PESEL
     *
     * @param pesel pesel
     * @param field pole
     */

    public void limitPesel(String pesel, TextField field) {
        if (pesel.length() > 11) {
            field.setText(pesel.substring(0, 11));
        }
    }


    /**
     * Metoda zapewniająca poprawne wpisanie imienia/nazwiska - wyklucza znaki specjalne i cyfry
     *
     * @param textField pole tekstowe
     */
    public void correctNameTyping(String t1, TextField textField) {


        if (!t1.matches("[A-Za-zĄąŁłÓóŻżŹźŃń]*")) {

            textField.setText(t1.replaceAll("[^A-Za-zĄąŁłÓóŻżŹźŃń]", ""));
        }


    }


    /**
     * Metoda usuwająca pacjenta z listy po naciśnięciu na przycisk 'Usuń' oraz czyszcząca formularze
     */
    public void deletePatient(Person t1) {


        guiCreator.removeTableItem(t1);
        guiCreator.disablePane();
        guiCreator.clearTableSelection();
        guiCreator.enableAddButton();
        cancelPatientData();
        cancelExaminationData();


    }


    /**
     * Metoda przypisująca przyciski wyboru płci do grupy - naciśnięcie przycisku powoduje pobranie jego nazwy(mężczyzna/kobieta)
     */
    public void setToggle(ToggleGroup group) {

        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle(); //naciśnięcie na przycisk powoduje przypisanie parametrów tego przycisku do zmiennej
        try {
            toogleGroupValue = selectedRadioButton.getText();
        } catch (NullPointerException noe) {
        }


    }

    /**
     * Metoda umożliwiająca zamknięcie aplikacji po wybraniu odpowiedniej opcji z Menu
     */
    public void closeApp() {

        System.exit(0);

    }

    /**
     * Metoda odblokowująca formularz pacjenta
     */

    public void enableNewPatientData() {

        guiCreator.clearTableSelection();
        guiCreator.enablePane();
        setDefaultFieldColor(guiCreator.getPeselField());
        guiCreator.disableAddButton();
        cancelPatientData();
        cancelExaminationData();

    }


    /**
     * Metoda czyszcząca formularz badania po naciśnięciu na przycisk Anuluj
     */

    public void cancelExamination() {

        cancelExaminationData();
        guiCreator.disablePane();


    }

    /**
     * Metoda czyszcząca formularz badania
     */
    public void cancelExaminationData() {

        guiCreator.clearExamination();
        guiCreator.setDefaultFieldColor(guiCreator.getDate());
        guiCreator.setDefaultFieldColor(guiCreator.getTempField());
        guiCreator.setDefaultFieldColor(guiCreator.getPulseField());
        guiCreator.setDefaultFieldColor(guiCreator.getLeukocyteField());

    }

    /**
     * Metoda czyszcząca formularz pacjenta
     */
    public void cancelPatientData() {
        guiCreator.clearPatientFormula();
        guiCreator.unselectMaleGender();
        guiCreator.unselectFemaleGender();
        guiCreator.selectInsurance(2);
        guiCreator.setDefaultFieldColor(guiCreator.getNameField());
        guiCreator.setDefaultFieldColor(guiCreator.getSurnameField());
        guiCreator.setDefaultFieldColor(guiCreator.getPeselField());


    }

    /**
     * Metoda definiująca domyślny kolor pola z datą po naciśnięciu na pole
     */
    public void setDefaultDateColor(DatePicker date) {

        date.setStyle("-fx-background-color: white ;-fx-border-color: black");

    }

    /**
     * Metoda dodająca badanie - pomyślny zapis zaznacza checkBox w tabeli i aktualizuje dane pacjenta
     */
    public void addExamination() {

        if (guiCreator.getDateField().equals("") || guiCreator.getTemp().equals("") || guiCreator.getPulse().equals("") || guiCreator.getLeukocyte().equals("") || Double.parseDouble(guiCreator.getTemp()) < 36 || Double.parseDouble(guiCreator.getTemp()) > 42 || Integer.parseInt(guiCreator.getPulse()) < 30 || Integer.parseInt(guiCreator.getPulse()) > 200) {
            guiCreator.setFieldColor(guiCreator.getTempField());
            guiCreator.setFieldColor(guiCreator.getPulseField());
            guiCreator.setFieldColor(guiCreator.getLeukocyteField());
            if (guiCreator.getDateField().equals("")) {
                guiCreator.paintRed(guiCreator.getDate());
            }

            try {
                if (Double.parseDouble(guiCreator.getTemp()) < 36 || Double.parseDouble(guiCreator.getTemp()) > 42) {
                    guiCreator.paintRed(guiCreator.getTempField());
                }
                if (Integer.parseInt(guiCreator.getPulse()) < 30 || Integer.parseInt(guiCreator.getPulse()) > 200) {
                    guiCreator.paintRed(guiCreator.getPulseField());
                }
                if (Integer.parseInt(guiCreator.getLeukocyte()) < 2000 || Integer.parseInt(guiCreator.getLeukocyte()) > 40000) {
                    guiCreator.paintRed(guiCreator.getLeukocyteField());
                }

            } catch (NumberFormatException nfe) {

            }
        } else {

            person = guiCreator.getPerson();
            index = guiCreator.getIndex();
            person.createExamination();
            person.getExamination().setDate(guiCreator.getDate());
            person.getExamination().setTemperature(Double.parseDouble(guiCreator.getTemp()));
            person.getExamination().setPulse(Integer.parseInt(guiCreator.getPulse()));
            person.getExamination().setLeukocyte(Integer.parseInt(guiCreator.getLeukocyte()));

            patientList.set(index, person);
            guiCreator.disablePane();
            guiCreator.clearTableSelection();
            guiCreator.enableAddButton();


        }
    }


    /**
     * Metoda pobierająca dane pacjenta z formularza badania
     *
     * @param person pacjent
     */
    public void getExaminationDataFromTable(Person person) {
        if (person.getExamination() != null) {
            cancelExaminationData();
            guiCreator.setDate(person.getExamination().getFormatter().format(person.getExamination().getDate()));
            guiCreator.setTemp(String.valueOf(person.getExamination().getTemperature()));
            guiCreator.setPulse(String.valueOf(person.getExamination().getPulse()));
            guiCreator.setLeukocyte(String.valueOf(person.getExamination().getLeukocyte()));
        } else {
            cancelExaminationData();
        }
    }


}

