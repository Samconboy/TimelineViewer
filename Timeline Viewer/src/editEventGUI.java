import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** editEventGUI
 * @author sconboy
 * @version 1.0
 *
 * controller class for editEventGUI.fxml
 */
public class editEventGUI {

    //declare javafx items
    public Label lbl_eventName;
    public Label lbl_EventDate;
    public Label lbl_EventDesc;
    public Label lbl_LinkedPerson;
    public TextField txtF_eventName;
    public ComboBox cmb_linkedPerson;
    public TextArea txtA_eventDesc;
    public Button btn_newPerson;
    public Button btn_save;
    public Button btn_cancel;
    public DatePicker DateP_eventDate;

    //declare variables
    private String eventName;
    private LocalDate eventDate;
    private String linkedPerson;
    private String eventDescription;


    /** initialize
     * called on start
     * fills fields on the dialog
     */
    public void initialize() {

        int arrayPosition = -1;

        //find event in arraylist
        for (int i = 0; i < dataHandler.events.size(); i++) {
            if (dataHandler.getTmp().equals(dataHandler.events.get(i).getName())) {
                arrayPosition = i;
            }
        }

        //set local variables
        eventName = dataHandler.events.get(arrayPosition).getName();
        eventDate = dataHandler.events.get(arrayPosition).getDate();
        eventDescription = dataHandler.events.get(arrayPosition).getDescription();
        linkedPerson = dataHandler.events.get(arrayPosition).getLinkedPerson();


        //if no linked person pass null to method
        if (linkedPerson == null) {
            fillCombobox(null);
        } else {
            fillCombobox(linkedPerson);
        }

        //fill fields
        txtF_eventName.setText(eventName);
        txtA_eventDesc.setText(eventDescription);
        DateP_eventDate.setValue(eventDate);
    }

    /** act_newPerson
     * opens dialog for adding a new person
     * @param actionEvent for btn_newPerson
     */
    public void act_newPerson(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("newPersonGUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New Person");
            stage.setScene(new Scene(root, 400, 300));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                    fillCombobox(linkedPerson);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** act_save
     * saves the edited event information
     * @param actionEvent for btn_save
     */
    public void act_save(ActionEvent actionEvent) {

        //if contents == null get string from txt area in date picker
        if (DateP_eventDate.getValue() != DateP_eventDate.getConverter().fromString(DateP_eventDate.getEditor().getText())) {
            DateP_eventDate.setValue(DateP_eventDate.getConverter().fromString(DateP_eventDate.getEditor().getText()));
        }

        //check event date has been provided, if not alert user
        if (eventDate == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding Event");
            alert.setHeaderText(null);
            alert.setContentText("Please provide a date for the event");
            alert.showAndWait();
        }

        //check for changes, and update variables
        if (DateP_eventDate.getValue() != eventDate) {
            eventDate = DateP_eventDate.getValue();
        } else {
            eventDate = null;
        }
        if (txtA_eventDesc.getText() != eventDescription) {
            eventDescription = txtA_eventDesc.getText();
        } else {
            eventDescription = null;
        }
        if (cmb_linkedPerson.getSelectionModel().getSelectedItem().toString().equals("don't link")||
                cmb_linkedPerson.getSelectionModel().getSelectedItem().toString().equals("No Persons")) {
            linkedPerson = "NOLINK";
        } else if (cmb_linkedPerson.getSelectionModel().getSelectedItem().toString() != linkedPerson) {
            linkedPerson = cmb_linkedPerson.getSelectionModel().getSelectedItem().toString();
        } else {
            linkedPerson = null;
        }

        //call function to update event
        dataHandler.editEvent(eventName, eventDate, eventDescription, linkedPerson);
        System.out.println(eventName+ eventDate+ eventDescription+ linkedPerson);

        Stage stage = (Stage) btn_save.getScene().getWindow();
        stage.close();
    }


    /** act_cancel
     * cancels the edit
     * @param actionEvent for btn_cancel
     */
    public void act_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }


    /** fillCombobox
     * fills combobox with persons to link
     * @param currentLinkedPerson - String linked person on event - null if no linked person
     */
    public void fillCombobox(String currentLinkedPerson) {

        List<String> list = new ArrayList<String>();
        list.add("don't link");

        //fill combobox with persons
        if (dataHandler.persons.size() == 0) {
            list.add("No Persons");
            ObservableList obList = FXCollections.observableList(list);
            cmb_linkedPerson.setItems(obList);
            cmb_linkedPerson.getSelectionModel().selectFirst();
        } else {
            for (int i = 0; i < dataHandler.persons.size(); i++) {
                list.add(dataHandler.persons.get(i).getName());
                ObservableList obList = FXCollections.observableList(list);
                cmb_linkedPerson.setItems(obList);

                //if event has linked person, select it
                if (currentLinkedPerson == null){
                    cmb_linkedPerson.getSelectionModel().selectFirst();
                } else {
                    cmb_linkedPerson.getSelectionModel().select(currentLinkedPerson);
                }
            }
        }
    }
}
