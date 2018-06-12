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
import java.util.*;

public class newEventGUI {

    //declare Javafx items
    public Label lbl_newEvent;
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

    //fill combobox on initialize
    public void initialize() {
        fillCombobox();
    }


    //opens the new person dialog to add a new person
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
                    fillCombobox();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //save button action
    public void act_save(ActionEvent actionEvent) {

        //if contents == null get string from txt area in date picker
        if (DateP_eventDate.getValue() != DateP_eventDate.getConverter().fromString(DateP_eventDate.getEditor().getText())) {
            DateP_eventDate.setValue(DateP_eventDate.getConverter().fromString(DateP_eventDate.getEditor().getText()));
        }

        System.out.println(DateP_eventDate.getValue());
        System.out.println(txtF_eventName.getText());

        //check to see if the name and date have been provided
        if (DateP_eventDate.getValue() == null || txtF_eventName.getText() == "") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding Event");
            alert.setHeaderText(null);
            alert.setContentText("Please provide a name and a date for the event");
            alert.showAndWait();
        } else {


            //check for errors when adding the event

            int result;

            //determine whether to link person
            if (cmb_linkedPerson.getSelectionModel().getSelectedItem().toString().equals("don't link") ||
                    cmb_linkedPerson.getSelectionModel().getSelectedItem().toString().equals("No Persons")) {
                result = dataHandler.addEvent(txtF_eventName.getText(), DateP_eventDate.getValue(), txtA_eventDesc.getText(), null);
            } else {
                result = dataHandler.addEvent(txtF_eventName.getText(), DateP_eventDate.getValue(), txtA_eventDesc.getText(), cmb_linkedPerson.getSelectionModel().getSelectedItem().toString());
            }

            //present error to user if adding event fails
            switch (result) {
                case 1:
                    Stage stage = (Stage) btn_save.getScene().getWindow();
                    stage.close();
                    break;
                case -1:
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Adding Event");
                    alert.setHeaderText(null);
                    alert.setContentText("Name of event in use");
                    alert.showAndWait();
                    break;
            }
        }
    }

    //cancel button action
    public void act_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    //fill combobox with persons
    public void fillCombobox() {
        List<String> list = new ArrayList<String>();
        list.add("don't link");
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
                cmb_linkedPerson.getSelectionModel().selectFirst();
            }
        }
    }

}
