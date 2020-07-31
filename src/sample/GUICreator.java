package sample;


import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Klasa tworząca interfejs graficzny aplikacji - Singleton zawierający wszelkie zmienne i metody odpowiadające za GUI
 */
public class GUICreator {


    private Controller controller;
    private static GUICreator guiCreator;
    private TableColumn<Person, String> nameColumn, peselColumn, genderColumn, insuranceColumn;
    private TableColumn<Person, Boolean> examinationColumn;
    private DatePicker date;
    private TextField nameField, surnameField, peselField, tempField, pulseField, leukocyteField;
    private MenuItem menuItem;
    private MenuBar menuBar;
    private Menu menu;
    private AnchorPane pane, pane1, pane2, root;
    private RadioButton maleGender, femaleGender;
    private ChoiceBox<String> insurance;
    private Button saveButton, cancelButton, addButton, deleteButton, saveExaminationButton, cancelExaminationButton;
    private TableView<Person> tableView;


    public Button getCancelButton() {
        return cancelButton;
    }


    public Button getDeleteButton() {
        return deleteButton;
    }


    public TextField getNameField() {
        return nameField;
    }


    public TextField getSurnameField() {
        return surnameField;
    }

    public TextField getPeselField() {
        return peselField;
    }


    public static GUICreator getInstance() {
        if (guiCreator == null) {
            guiCreator = new GUICreator();
        }
        return guiCreator;
    }


    public DatePicker getDate() {
        return date;
    }

    public TextField getTempField() {
        return tempField;
    }

    public TextField getPulseField() {
        return pulseField;
    }

    public TextField getLeukocyteField() {
        return leukocyteField;
    }

    private GUICreator() {

        createPanes();
        setPane(320, 250, 10, 50, pane);
        setPane(450, 440, 340, 50, pane1);
        setPane(320, 180, 10, 310, pane2);
        createMenu();
        createText("Imię:", 15, 35, pane);
        createText("Nazwisko:", 15, 70, pane);
        createText("PESEL:", 15, 105, pane);
        createText("Płeć:", 15, 140, pane);
        nameField = addField(155, 20, pane);
        surnameField = addField(155, 55, pane);
        peselField = addField(155, 90, pane);
        createText("Ubezpieczenie:", 15, 175, pane);
        maleGender = addTick(155, 125, "Mężczyzna", pane);
        femaleGender = addTick(245, 125, "Kobieta", pane);
        addChoiceBox(pane);
        saveButton = addButton("Zapisz", 15, 195, pane);
        cancelButton = addButton("Anuluj", 75, 195, pane);
        addButton = addButton("Dodaj", 10, 390, pane1);
        deleteButton = addButton("Usuń", 60, 390, pane1);
        deleteButton.setDisable(true);
        pane.setDisable(true);
        date = addDate(pane2);
        createText("Data:", 15, 25, pane2);
        createText("Temperatura ciała:", 15, 60, pane2);
        createText("Tętno:", 15, 95, pane2);
        createText("Leukocyty/μl:", 15, 130, pane2);
        saveExaminationButton = addButton("Zapisz", 15, 150, pane2);
        cancelExaminationButton = addButton("Anuluj", 65, 150, pane2);
        tempField = addField(145, 45, pane2);
        pulseField = addField(145, 80, pane2);
        leukocyteField = addField(145, 115, pane2);
        addTable();
        setDefaultFieldColor(nameField);
        setDefaultFieldColor(surnameField);
        setDefaultFieldColor(peselField);
        setDefaultFieldColor(tempField);
        setDefaultFieldColor(pulseField);
        setDefaultFieldColor(leukocyteField);


    }


    /**
     * Metoda tworząca odpowiednie sektory na scenie glównej
     */

    public void createPanes() {
        root = new AnchorPane();
        pane = new AnchorPane();
        pane.setDisable(true);
        pane1 = new AnchorPane();
        pane2 = new AnchorPane();
        pane2.setDisable(true);
        pane1.getChildren().add(new Text("Lista pacjentów "));
        pane2.getChildren().add(new Text("Badanie"));
        pane.getChildren().add(new Text("Dane pacjenta"));
    }

    /**
     * Metoda tworząca główną scenę aplikacji
     *
     * @param primaryStage główne okienko programu
     */
    public void createScene(Stage primaryStage) {

        Scene scene = new Scene(root, 800, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("Rejestracja wyników badań");
    }

    /**
     * Metoda rozmieszczająca sektory i określająca ich rozmiary
     *
     * @param width   długość
     * @param height  wysokość
     * @param layoutX współrzędna X
     * @param layoutY współrzędna Y
     * @param pane    sektor
     */

    public void setPane(int width, int height, int layoutX, int layoutY, AnchorPane pane) {
        pane.setPrefWidth(width);
        pane.setPrefHeight(height);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        pane.setLayoutX(layoutX);
        pane.setLayoutY(layoutY);
        root.getChildren().add(pane);

    }

    /**
     * Metoda tworząca napis i przydzielająca mu odpowiednie miejsce na scenie
     *
     * @param text napis
     * @param x    współrzędna X położenia
     * @param y    współrzędna Y położenia
     * @param pane sektor do którego przypisany jest napis
     */
    public void createText(String text, int x, int y, AnchorPane pane) {
        Text text1 = new Text(text);
        text1.setX(x);
        text1.setY(y);
        pane.getChildren().add(text1);


    }

    public void disableAddButton() {
        addButton.setDisable(true);
    }

    /**
     * Metoda tworząca pole tekstowe i przydzielająca mu odpowiednie miejsce na scenie
     *
     * @param x    współrzędna X polożenia
     * @param y    współrzędna Y położenia
     * @param pane sektor do którego przypisane jest pole
     * @return utworzone pole tekstowe
     */
    public TextField addField(int x, int y, AnchorPane pane) {
        TextField field = new TextField();
        field.setLayoutX(x);
        field.setLayoutY(y);
        pane.getChildren().add(field);
        field.setStyle("-fx-background-color: white ;-fx-border-color: black");
        return field;

    }

    /**
     * Metoda definiująca kolor pola tekstowego
     *
     * @param field pole tekstowe
     */
    public void setFieldColor(TextField field) {
        if (field.getText().equals("")) {
            field.setStyle("-fx-background-color: red;-fx-border-color: black");
        }
    }

    /**
     * Metoda ustalająca limit znaków jakie można wpisać w polu PESEL
     *
     * @param peselField pole PESEL
     */
    public void setPeselLimit(TextField peselField) {
        if (peselField.getText().length() < 11) {
            peselField.setStyle("-fx-background-color: red;-fx-border-color: black");
        }
    }

    /**
     * Metoda tworząca rozwijane menu w lewym górnym rogu aplikacji
     */
    public void createMenu() {
        menuBar = new MenuBar();
        menuBar.setPrefWidth(800);
        menu = new Menu("Aplikacja");
        menuBar.getMenus().add(menu);
        root.getChildren().add(menuBar);
        menuItem = new MenuItem("Zamknij ALT+F4");
        menu.getItems().add(menuItem);

    }

    /**
     * Metoda tworząca przycisk do zaznaczania - mężczyzna/kobieta
     *
     * @param x    współrzędna X
     * @param y    współrzędna Y
     * @param name nazwa przycisku
     * @param pane sektor
     * @return przycisk o odpowiedniej nazwie, położeniu i rozmiarach
     */
    public RadioButton addTick(int x, int y, String name, AnchorPane pane) {
        RadioButton button = new RadioButton();
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setText(name);
        pane.getChildren().add(button);


        return button;
    }

    /**
     * Metoda tworząca listę do wyboru rodzaju ubezpieczenia
     *
     * @param pane sektor
     * @return lista
     */

    public ChoiceBox<String> addChoiceBox(AnchorPane pane) {
        insurance = new ChoiceBox<>();
        insurance.getItems().add("NFZ");
        insurance.getItems().add("Prywatne");
        insurance.getItems().add("Brak");
        insurance.setLayoutX(155);
        insurance.setLayoutY(160);
        insurance.setPrefWidth(150);
        insurance.getSelectionModel().selectLast();
        pane.getChildren().add(insurance);
        return insurance;
    }

    /**
     * Metoda tworząca przyciski i określająca ich położenie
     *
     * @param name nazwa przycisku
     * @param x    współrzędna  X
     * @param y    współrzędna Y
     * @param pane sektor
     * @return przycisk
     */

    public Button addButton(String name, int x, int y, AnchorPane pane) {
        Button button = new Button();
        button.setText(name);
        button.setLayoutX(x);
        button.setLayoutY(y);
        pane.getChildren().add(button);
        return button;
    }


    /**
     * Metoda tworząca pole do wyboru daty
     *
     * @param pane sektor
     * @return pole
     */
    public DatePicker addDate(AnchorPane pane) {
        date = new DatePicker();
        date.setLayoutX(145);
        date.setLayoutY(10);
        date.setStyle("-fx-background-color: white ;-fx-border-color: black");
        pane.getChildren().add(date);
        date.getEditor().setDisable(true);
        return date;

    }

    /**
     * Metoda tworząca tabelę zawierającą listę pacjentów
     *
     * @return tabela
     */
    public TableView<Person> addTable() {

        tableView = new TableView<Person>();
        nameColumn = new TableColumn<>("Imię i nazwisko");
        genderColumn = new TableColumn<>("Płeć");
        peselColumn = new TableColumn<>("PESEL");
        insuranceColumn = new TableColumn<>("Ubezpieczenie");
        examinationColumn = new TableColumn<>("Badanie");
        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(genderColumn);
        tableView.getColumns().add(peselColumn);
        tableView.getColumns().add(insuranceColumn);
        tableView.getColumns().add(examinationColumn);
        tableView.setLayoutX(10);
        tableView.setPrefWidth(420);
        tableView.setPrefHeight(360);
        tableView.setLayoutY(10);
        pane1.getChildren().add(tableView);
        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("pesel"));
        insuranceColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("insurance"));
        CheckBoxTableCell<Person, Boolean> checkBoxTableCell = new CheckBoxTableCell<>(); //sposob ukazania danych - w postaci checkboxa
        examinationColumn.setCellFactory(checkBoxTableCell.forTableColumn(examinationColumn));
        examinationColumn.setCellValueFactory(e -> new SimpleBooleanProperty(e.getValue().getExamined()));
        return tableView;
    }

    public String getName() {
        return nameField.getText();
    }

    public String getSurname() {
        return surnameField.getText();
    }

    public String getPesel() {
        return peselField.getText();
    }

    public Person getPerson() {
        return tableView.getSelectionModel().getSelectedItem();
    }

    public int getIndex() {
        return tableView.getSelectionModel().getSelectedIndex();
    }

    public String getSelectedInsurance() {
        return insurance.getSelectionModel().getSelectedItem();
    }

    public void selectTableIndex(int index) {
        tableView.getSelectionModel().select(index);
    }

    public void disablePane() {
        pane.setDisable(true);
    }

    public void setTableItems(ObservableList<Person> patientList) {
        tableView.setItems(patientList);
    }

    public void enableAddButton() {
        addButton.setDisable(false);
    }

    public void clearTableSelection() {
        tableView.getSelectionModel().clearSelection();
    }

    public void disableExaminationPane() {
        pane2.setDisable(true);
    }

    public void enableExaminationPane() {
        pane2.setDisable(false);
    }

    public void enablePane() {
        pane.setDisable(false);
    }

    public void setName(String name) {
        nameField.setText(name);
    }

    public void setSurname(String surname) {
        surnameField.setText(surname);
    }

    public void setPesel(String pesel) {
        peselField.setText(pesel);
    }

    public void selectMaleGender() {
        maleGender.setSelected(true);
    }

    public void unselectMaleGender() {
        maleGender.setSelected(false);
    }

    public void unselectFemaleGender() {
        femaleGender.setSelected(false);
    }

    public void selectFemaleGender() {
        femaleGender.setSelected(true);
    }

    public void selectInsurance(int index) {
        insurance.getSelectionModel().select(index);
    }

    public void setPeselColorRed() {
        peselField.setStyle("-fx-background-color: red;-fx-border-color: black");
    }

    public void setDefaultFieldColor(TextField field) {
        field.setStyle("-fx-background-color: white ;-fx-border-color: black");

    }

    public void setTemp(String temp) {
        tempField.setText(temp);
    }

    public void setLeukocyte(String leukocyte) {
        leukocyteField.setText(leukocyte);
    }

    public void setPulse(String pulse) {
        pulseField.setText(pulse);
    }

    public void setDate(String time) {
        date.getEditor().setText(time);
    }

    public void setControls(Controller controller) {
        this.controller = controller;

        saveButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.addPatient();
            }
        });

        tempField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                controller.limitTemp(t1, tempField);
            }
        });

        peselField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                controller.limitPesel(t1, peselField);
            }
        });

        pulseField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                controller.correctDigitTyping(t1, pulseField);
            }
        });

        leukocyteField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                controller.correctDigitTyping(t1, leukocyteField);
            }
        });

        nameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                controller.correctNameTyping(t1, nameField);
            }
        });

        surnameField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                controller.correctNameTyping(t1, surnameField);
            }
        });

        nameField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.setDefaultFieldColor(nameField);
            }
        });

        surnameField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.setDefaultFieldColor(surnameField);
            }
        });

        peselField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.setDefaultFieldColor(peselField);
            }
        });

        tempField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.setDefaultFieldColor(tempField);
            }
        });

        pulseField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.setDefaultFieldColor(pulseField);
            }
        });

        leukocyteField.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.setDefaultFieldColor(leukocyteField);
            }
        });

        date.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.setDefaultDateColor(date);
            }
        });

        menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.closeApp();
            }
        });

        setToggle();

        addButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.enableNewPatientData();
            }
        });

        cancelButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.cancelPatientData();
            }
        });

        cancelExaminationButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.cancelExamination();
            }
        });

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Person>() {
            @Override
            public void changed(ObservableValue<? extends Person> observableValue, Person person, Person t1) {
                deleteButton.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        controller.deletePatient(t1);
                    }
                });
                controller.getDataFromTable(t1);
            }
        });

        saveExaminationButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.addExamination();
            }
        });

    }

    public void setToggle() {
        final ToggleGroup group = new ToggleGroup();
        maleGender.setToggleGroup(group);
        femaleGender.setToggleGroup(group);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                controller.setToggle(group);

            }
        });
    }

    public void enableDeleteButton() {
        deleteButton.setDisable(false);
    }

    public void disableDeleteButton() {
        deleteButton.setDisable(true);
    }

    public void removeTableItem(Person t1) {
        tableView.getItems().remove(t1);
    }

    public String getDateField() {
        return date.getEditor().getText();
    }

    public String getTemp() {
        return tempField.getText();
    }

    public String getPulse() {
        return pulseField.getText();
    }

    public String getLeukocyte() {
        return leukocyteField.getText();
    }

    public void paintRed(TextField field) {
        field.setStyle("-fx-background-color: red ;-fx-border-color: black");
    }

    public void paintRed(DatePicker date) {
        date.setStyle("-fx-background-color: red ;-fx-border-color: black");
    }

    public void clearExamination() {
        date.getEditor().clear();
        tempField.clear();
        pulseField.clear();
        leukocyteField.clear();
    }

    public void clearPatientFormula() {
        nameField.clear();
        surnameField.clear();
        peselField.clear();


    }

    public void setDefaultFieldColor(DatePicker date) {
        date.setStyle("-fx-background-color: white ;-fx-border-color: black");
    }
}

