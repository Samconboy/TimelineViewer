import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class newPersonGUI {

    //declare javafx variables
    public Label lbl_newPerson;
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

    //save button action
    public void act_save(ActionEvent actionEvent) {

        //if contents == null get string from txt area in date picker
        if (DateP_birthDate.getValue() != DateP_birthDate.getConverter().fromString(DateP_birthDate.getEditor().getText())) {
            DateP_birthDate.setValue(DateP_birthDate.getConverter().fromString(DateP_birthDate.getEditor().getText()));
        }



        //if contents == null get string from txt area in date picker
        if (DateP_deathDate.getValue() != DateP_deathDate.getConverter().fromString(DateP_deathDate.getEditor().getText())) {
            DateP_deathDate.setValue(DateP_deathDate.getConverter().fromString(DateP_deathDate.getEditor().getText()));
        }

        if (!ckbox_isAlive.isSelected() && DateP_deathDate.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Adding Event");
            alert.setHeaderText(null);
            alert.setContentText("Please provide a death date for the person");
            alert.showAndWait();
        }
            

        //try to save person
        int result = dataHandler.addPerson(txtF_personName.getText(), DateP_birthDate.getValue(), DateP_deathDate.getValue(), txtA_PersonDesc.getText(), ckbox_isAlive.isSelected());

        //check save completes correctly, alert user if error
        switch (result) {
            case 1: Stage stage = (Stage) btn_save.getScene().getWindow();
                    stage.close();
                    break;
            case -1: Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Adding Person");
                alert.setHeaderText(null);
                alert.setContentText("Name of person in use");
                alert.showAndWait();
                break;
        }
    }

    //cancel button action
    public void act_cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

    //enable/disable death date picker with checkbox
    public void ckbox_isAlive(ActionEvent actionEvent) {
        if (ckbox_isAlive.isSelected()) {
            DateP_deathDate.setDisable(true);
        } else if (!ckbox_isAlive.isSelected()) {
            DateP_deathDate.setDisable(false);
        }
    }
}
