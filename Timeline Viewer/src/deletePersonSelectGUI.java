import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** deletePersonSelectGUI
 * @author sconboy
 * @version 1.0
 *
 * controller class for timelineViewerGUI.fxml
 */

public class deletePersonSelectGUI {

    //declare javafx items
    public Label lbl_selectPerson;
    public Button btn_select;
    public Button btn_cancel;
    public ListView listV_persons;

    /** initialize
     * called on start
     * populates listView with persons
     */
    public void initialize() {
        List<String> list = new ArrayList<String>();

        //check array and fill listview

        //if no persons, add "No Persons" and disable select button
        if (dataHandler.persons.size() == 0) {
            list.add("No Persons");
            ObservableList obList = FXCollections.observableList(list);
            listV_persons.setItems(obList);
            btn_select.setDisable(true);

        } else {
            for (int i = 0; i < dataHandler.persons.size(); i++) {
                list.add(dataHandler.persons.get(i).getName());
                ObservableList obList = FXCollections.observableList(list);
                listV_persons.setItems(obList);
            }
        }
    }

    /** actSelect
     * selects person to delete
     * @param actionEvent for btn_select
     */
    public void act_select(ActionEvent actionEvent) {

        //local variable of person name
        String personName = listV_persons.getSelectionModel().getSelectedItem().toString();

        //ask user to confirm delete of person
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Person");
        alert.setHeaderText("Delete " + personName);
        alert.setContentText("Confirm delete of person. This will also remove the person from all linked events");

        //if confirmed delete person and remove person from all linked events
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dataHandler.removePerson(personName);
            for (int i = 0; i < dataHandler.events.size(); i++) {
                if (dataHandler.events.get(i).getLinkedPerson().equals(personName)) {
                    dataHandler.events.get(i).setLinkedPerson(null);
                }
            }
            Stage stage = (Stage) btn_select.getScene().getWindow();
            stage.close();
        }
    }

    /** act_cancel
     * cancels delete function
     * @param actionEvent for btn_cancel
     */
    public void act_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }
}

