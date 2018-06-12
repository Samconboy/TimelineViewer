import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** editPersonSelectGUI
 * @author sconboy
 * @version 1.0
 *
 * controller class for editPersonSelectGUI.fxml
 */
public class editPersonSelectGUI {

    //declare javafx items
    public Label lbl_EditPerson;
    public Button btn_select;
    public Button btn_cancel;
    public ListView listV_persons;

    /** initialize
     * called on start
     * populates list view with persons
     */
    public void initialize() {
        List<String> list = new ArrayList<String>();

        //checks persons arraylist and populates listView
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

    /** act_select
     * selects the
     *
     * @param actionEvent for btn_save
     */
    public void act_select(ActionEvent actionEvent) {

        //set tmp with person name
        dataHandler.setTmp(listV_persons.getSelectionModel().getSelectedItem().toString());

        //open edit person dialog
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("editPersonGUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit Person");
            stage.setScene(new Scene(root, 400, 300));
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) btn_select.getScene().getWindow();
        stage.close();
    }

    /** act_cancel
     * cancel edit person
     *
     * @param actionEvent for btn_cancel
     */
    public void act_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_select.getScene().getWindow();
        stage.close();
    }

}

