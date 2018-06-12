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
public class viewPersonGUI {

    //declare javafx items
    public Label lbl_PersonName;
    public Label lbl_birthDate;
    public Label lbl_PersonDesc;
    public TextField txtF_personName;
    public TextArea txtA_PersonDesc;
    public DatePicker DateP_birthDate;
    public DatePicker DateP_deathDate;
    public Label lbl_deathDate;
    public CheckBox ckbox_isAlive;
    public Label lbl_isAlive;
    public Label lbl_viewPerson;
    public Button btn_close;

    //declare variables
    private String name;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private boolean isAlive;
    private String desc;

    /**
     * initialize
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
        } else {
            DateP_deathDate.setValue(deathDate);
        }
        ckbox_isAlive.setSelected(isAlive);
        txtA_PersonDesc.setText(desc);
    }

    public void act_close(ActionEvent actionEvent) {
        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();
    }
}