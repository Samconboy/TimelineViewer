import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


/** timelineViewerGUI
 * @author sconboy
 * @version 1.0
 *
 * controller class for timelineViewerGUI.fxml
 */

public class timelineViewerGUI {

    //declaration of javafx items
    public MenuItem mItem_open;
    public MenuItem mItem_new;
    public MenuItem mItem_newEvent;
    public MenuItem mItem_editEvent;
    public MenuItem mItem_deleteEvent;
    public MenuItem mItem_newPerson;
    public MenuItem mItem_editPerson;
    public MenuItem mItem_deletePerson;
    public Label lbl_eventName;
    public Label lbl_eventDate;
    public Label lbl_eventDesc;
    public TextArea txtA_eventDesc;
    public Canvas canvas_timeline;
    public TextField txtF_eventName;
    public TextField txtF_eventDate;
    public Label lbl_linkedPerson;
    public TextField txtF_eventLinkedPerson;
    public Button btn_LinkedPerson;
    public TextField txtF_eventPos;
    public Button btn_firstEvent;
    public Button btn_previousEvent;
    public Button btn_nextEvent;
    public Button btn_lastEvent;
    public MenuItem mItem_save;
    public Menu menu_event;
    public Menu menu_person;


    //declaration of private variables
    static private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static private int arrayPosition = 0;

    /** fillData
     * fills the TextFields and TextArea on the window
     * handles the enable/disable of the navigation buttons depending on location in array
     * @param arrayPosition the current event to display to the user
     */
    private void fillData(int arrayPosition) {
        txtF_eventName.setText(dataHandler.events.get(arrayPosition).getName());
        txtF_eventDate.setText(dataHandler.events.get(arrayPosition).getDate().format(dtf));
        txtA_eventDesc.setText(dataHandler.events.get(arrayPosition).getDescription());
        txtF_eventLinkedPerson.setText(dataHandler.events.get(arrayPosition).getLinkedPerson());


        if (dataHandler.events.size() == 1) {
            btn_firstEvent.setDisable(true);
            btn_previousEvent.setDisable(true);
            btn_nextEvent.setDisable(true);
            btn_lastEvent.setDisable(true);
        } else if (arrayPosition == 0) {
            btn_firstEvent.setDisable(true);
            btn_previousEvent.setDisable(true);
            btn_nextEvent.setDisable(false);
            btn_lastEvent.setDisable(false);
        } else if (arrayPosition == dataHandler.events.size()-1) {
            btn_firstEvent.setDisable(false);
            btn_previousEvent.setDisable(false);
            btn_nextEvent.setDisable(true);
            btn_lastEvent.setDisable(true);
        } else {
            btn_firstEvent.setDisable(false);
            btn_previousEvent.setDisable(false);
            btn_nextEvent.setDisable(false);
            btn_lastEvent.setDisable(false);
        }



    }

    /** act_openTimeline
     * calls open timeline dialog
     * @param actionEvent for the openTimeline menuitem
     */
    public void act_openTimeline(ActionEvent actionEvent) {

        //check for an open timeline, if so alert user and save timeline first
        if (dataHandler.openTimeline) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("New timeline");
            alert.setContentText("save current timeline?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    dataHandler.saveTimeline();
                    dataHandler.newTimeline();
                    clearData();
                    menuState(false);
                } else if (type == ButtonType.NO) {
                    dataHandler.newTimeline();
                    clearData();
                    menuState(false);
                }
            });
        } else {
            menuState(false);
        }

        dataHandler.openTimeline();
        arrayPosition = 0;
        fillData(arrayPosition);
    }

    /** act_saveTimeline
     * calls save timeline dialog
     * @param actionEvent for the saveTimeline menuitem
     */
    public void act_saveTimeline(ActionEvent actionEvent) {

        dataHandler.saveTimeline();
    }

    /** act_newTimeline
     * checks whether a timeline is open and asks user to save before clearing arrays for new timeline
     * @param actionEvent for the newTimeline menuitem
     */
    public void act_newTimeline(ActionEvent actionEvent) {

        if (dataHandler.openTimeline) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("New timeline");
            alert.setContentText("save current timeline?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, noButton, cancelButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == ButtonType.YES) {
                    dataHandler.saveTimeline();
                    dataHandler.newTimeline();
                    clearData();
                    menuState(false);
                } else if (type == ButtonType.NO) {
                    dataHandler.newTimeline();
                    clearData();
                    menuState(false);
                }
            });

        } else {
            menuState(false);
        }

    }

    /** act_newEvent
     * opens dialog to create new event
     * @param actionEvent for the newEvent menuitem
     */
    public void act_newEvent(ActionEvent actionEvent) {

        //open window to create event
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("newEventGUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("New Event");
            stage.setScene(new Scene(root, 400, 300));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                    if (dataHandler.events.size() != 0) {
                        fillData(arrayPosition);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //COMP


    /** act_editEvent
     * opens dialog to edit an event
     * @param actionEvent for the editEvent menuitem
     */
    public void act_editEvent(ActionEvent actionEvent) {

        //set accessible variable with event name
        dataHandler.setTmp(txtF_eventName.getText());

        //open window to edit event
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("editEventGUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Edit Person");
            stage.setScene(new Scene(root, 400, 300));
            stage.setResizable(false);
            stage.show();

            //on close refresh data
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                    fillData(arrayPosition);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    } //COMP

    /** act_deleteEvent
     * opens conformation for deleting current event
     * @param actionEvent for the deleteEvent menuitem
     */
    public void act_deleteEvent(ActionEvent actionEvent) {

        //set local variable with event name
        String eventName = dataHandler.events.get(arrayPosition).getName();

        //get conformation of delete from user
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Event");
        alert.setHeaderText("Delete " + eventName);
        alert.setContentText("Confirm delete of event");

        //if confirmed, delete event and check array position
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            dataHandler.removeEvent(eventName);

            //if arrayposition is out of bounds, bring back into
            if(arrayPosition == dataHandler.events.size() && dataHandler.events.size() > 0) {
                arrayPosition--;
                fillData(arrayPosition);
            } else if (dataHandler.events.size() == 0) {
                clearData();
            }else {
                fillData(arrayPosition);
            }
        } else {

        }

        System.out.println(dataHandler.events.size());
    } //COMP

    /** act_newPerson
     * opens dialog to create new person
     * @param actionEvent
     */
    public void act_newPerson(ActionEvent actionEvent) {

        //open window to create person
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("newPersonGUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 400, 300));
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }



    } //COMP

    /** act_editPerson
     * opens dialog to edit person
     * @param actionEvent for the editPerson menuitem
     */
    public void act_editPerson(ActionEvent actionEvent) {

        //open window to select person to edit
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("editPersonSelectGUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 400, 300));
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    } //COMP


    /** act_deletePerson
     * opens dialog to delete person
     * @param actionEvent
     */
    public void act_deletePerson(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("deletePersonSelectGUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("select Person");
            stage.setScene(new Scene(root, 400, 300));
            stage.setResizable(false);
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    fillData(arrayPosition);

                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    } //DONE

    /** act_firstEvent
     * goes to array position 0 for first event in timeline
     * @param actionEvent for the btn_firstEvent button
     */
    public void act_firstEvent(ActionEvent actionEvent) {

        arrayPosition = 0;
        fillData(arrayPosition);


    } //DONE

    /** act_previousEvent
     * array position -1 for previous event
     * @param actionEvent for the btn_previousEvent button
     */
    public void act_previousEvent(ActionEvent actionEvent) {
        arrayPosition--;
        fillData(arrayPosition);
    } //DONE

    /** act_nextEvent
     * array position +1 for next event
     * @param actionEvent for the btn_nextEvent button
     */
    public void act_nextEvent(ActionEvent actionEvent) {
        arrayPosition++;
        fillData(arrayPosition);
    } //DONE

    /** act_lastEvent
     * array position = array size -1 for last event
     * @param actionEvent for the btn_lastEvent button
     */
    public void act_lastEvent(ActionEvent actionEvent) {
        arrayPosition = dataHandler.events.size()-1;
        fillData(arrayPosition);
    } //DONE

    /** menuState
     * sets enable/disable for the menus
     * @param state boolean true = disabled  false = enable
     */
    public void menuState(boolean state) {
        menu_event.setDisable(state);
        menu_person.setDisable(state);
        mItem_save.setDisable(state);

    } //DONE


    /** clearData
     * clears the data from the window
     */
    public void clearData() {
        txtF_eventName.setText(null);
        txtF_eventDate.setText(null);
        txtA_eventDesc.setText(null);
        txtF_eventLinkedPerson.setText(null);
    } //DONE

    public void act_linkedPerson(ActionEvent actionEvent) {
        dataHandler.setTmp(dataHandler.events.get(arrayPosition).getLinkedPerson());
        try {
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("viewPersonGUI.fxml"));
            Stage stage = new Stage();
            stage.setTitle("view Person");
            stage.setScene(new Scene(root, 400, 300));
            stage.setResizable(false);
            stage.show();
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    event.consume();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
