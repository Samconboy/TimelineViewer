import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;

/** editPersonGUI
 * @author sconboy
 * @version 1.0
 *
 * controller class for editPersonGUI.fxml
 */
public class editPersonGUI {

    //declare javafx items
    public Label lbl_EditPerson;
    public Label lbl_PersonName;
    public Label lbl_birthDate;
    public Label lbl_PersonDesc;
    public TextField txtF_personName;
    public TextArea txtA_PersonDesc;
    public Button btn_save;
    public Button btn_cancel;
    public DatePicker DateP_birthDate;
    public DatePicker DateP_deathDate;
    public Label lbl_deathDate;
    public CheckBox ckbox_isAlive;
    public Label lbl_isAlive;

    //declare variables
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private boolean isAlive;
    private String desc;

    /** initialize
     * called on start
     * populates fields with data
     */
    public void initialize() {
        int index = 0;

        //find person in arraylist
        for (int i = 0; i < dataHandler.persons.size(); i++) {
            if (dataHandler.getTmp().equals(dataHandler.persons.get(i).getName())) {
                index = i;
                break;
            }
        }

        //set local variables
        name = dataHandler.persons.get(index).getName();
        birthDate = dataHandler.persons.get(index).getBirthDate();
        isAlive = dataHandler.persons.get(index).getAlive();
        deathDate = dataHandler.persons.get(index).getDeathDate();
        desc = dataHandler.persons.get(index).getDescription();

        //send data to display
        txtF_personName.setText(name);
        DateP_birthDate.setValue(birthDate);
        if (isAlive) {
            DateP_deathDate.setDisable(true);
        }  else {
            DateP_deathDate.setValue(deathDate);
        }
        ckbox_isAlive.setSelected(isAlive);
        txtA_PersonDesc.setText(desc);
    }

    /** act_cancel
     * cancels person edit
     * @param actionEvent for btn_cancel
     */
    public void act_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();
    }

    /** act_save
     * saves person edit
     * @param actionEvent for btn_save
     */
    public void act_save(ActionEvent actionEvent) {
        //if contents == null get string from txt area in date picker
        if (DateP_birthDate.getValue() != DateP_birthDate.getConverter().fromString(DateP_birthDate.getEditor().getText())) {
            DateP_birthDate.setValue(DateP_birthDate.getConverter().fromString(DateP_birthDate.getEditor().getText()));
        }
        //if contents == null get string from txt area in date picker
        if (DateP_deathDate.getValue() != DateP_deathDate.getConverter().fromString(DateP_deathDate.getEditor().getText())) {
            DateP_deathDate.setValue(DateP_deathDate.getConverter().fromString(DateP_deathDate.getEditor().getText()));
        }

        //check to see if birth date has been provided and alert user if not
        if (DateP_birthDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error editing person");
            alert.setHeaderText("Birth Date");
            alert.setContentText("Please provide a date");
            alert.showAndWait();
        }

        //check to see if death date is provided if needed and alert user if not
        if (DateP_deathDate == null && !ckbox_isAlive.isSelected()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error editing person");
            alert.setHeaderText("Death Date");
            alert.setContentText("Please provide a date or select Is Alive");
            alert.showAndWait();
        }


        //check for changes and update where needed
        if (DateP_birthDate.getValue() != birthDate) {
            birthDate = DateP_birthDate.getValue();
        } else {
            birthDate = null;
        }
        if (ckbox_isAlive.isSelected() != isAlive) {
            isAlive = ckbox_isAlive.isSelected();
        }
        if (DateP_deathDate.getValue() != deathDate || !isAlive) {
            deathDate = DateP_deathDate.getValue();
        } else {
            deathDate = null;
        }
        if (!txtA_PersonDesc.getText().equals(desc)) {
            desc = txtA_PersonDesc.getText();
        } else {
            desc = null;
        }

        //send edited data to the data handler
        dataHandler.editPerson(name, birthDate, deathDate, desc, isAlive);

        //close window
        Stage stage = (Stage) btn_save.getScene().getWindow();
        stage.close();
    }

    /** ckbox_isAlive
     * enable/disable death date picker
     *
     * @param actionEvent for ckbox_isAlive
     */
    public void ckbox_isAlive(ActionEvent actionEvent) {
        if (ckbox_isAlive.isSelected()) {
            DateP_deathDate.setDisable(true);
        } else if (!ckbox_isAlive.isSelected()) {
            DateP_deathDate.setDisable(false);
        }
    }
}
