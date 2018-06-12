import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/** dataHandler
 * @author sconboy
 * @version 1.0
 *
 * data handler for the events and figures data
 *
 */

public class dataHandler {


    //declare variables
    static private String timelineName;
    public static boolean openTimeline;
    static private String tmp;

    //declare arraylists
    static protected ArrayList<event> events = new ArrayList<event>();
    static protected ArrayList<person> persons = new ArrayList<person>();

    //declare date time formatter
    static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** addEvent
     * takes data provided, and adds to an object into the events arraylist
     * @param initname - String name of the event
     * @param initdate - LocalDate date of the event in localDate
     * @param initdescription - String description of the event
     * @param linkedPerson - String linked person of the event
     * @return 1 - add completed successfully
     * @return -1 - name of event already in use
     */
    public static int addEvent(String initname, LocalDate initdate, String initdescription, String linkedPerson) {

        //check to see if name is taken
        for (int i = 0; i < events.size(); i++) {
            if (initname.equals(events.get(i).getName())) {
                System.out.println("Name Taken");
                return -1;
            }
        }

        //create new event, add data, and add to arraylist
        event event = new event();
        event.setName(initname);
        event.setDate(initdate);
        event.setDescription(initdescription);

        if (linkedPerson != null){
            event.setLinkedPerson(linkedPerson);
        }
        events.add(event);

        //sort events into chronological order
        sortEvents();

        //return 1 as completed successfully
        return 1;
    }

    /** removeEvent
     * removes event with matching name from arraylist
     * @param removeName - String name of the event to remove
     * @return 1 - removed event successfully
     * @return -1 - unable to fine event to remove
     */
    public static int removeEvent(String removeName) {

        int index = -1;

        //loop through arraylist to find event
        for (int i = 0; i < events.size(); i++) {
            if (removeName.equals(events.get(i).getName())) {
                index = i;
                break;
            }
        }

        // return -1 if event is not found
        if (index == -1) {
            return -1;
        } else {

            //remove event  and sort into chronological order
            events.remove(index);
            sortEvents();
        }

        //return 1 as successful removal of event
        return 1;
    }

    /** editEvent
     * edits event with info provided
     * @param name - String name of event to be edited
     * @param newDate - LocalDate new date for the event - null if no change
     * @param newDescription - String new description for the event  - null if no change
     * @param linkedPerson - String linked person for the event - "NOLINK" for no linked person and null for no change
     * @return 1 - edit completed successfully
     * @return -1 - unable to find event to edit
     */
    public static int editEvent(String name, LocalDate newDate, String newDescription, String linkedPerson) {

        int index = -1;

        //loop thorugh arraylist to find event
        for (int i = 0; i < events.size(); i++) {
            if (name.equals(events.get(i).getName())) {
                index = i;
                break;
            }
        }

        //return -1 if event not found
        if (index == -1) {
            return -1;
        }

        //check changes and apply where necessary
        if (newDate != null) {
            events.get(index).setDate(newDate);
        }
        if (newDescription != null) {
            events.get(index).setDescription(newDescription);
        }
        if (linkedPerson == null) {
        } else if (linkedPerson.equals("NOLINK")) {
            events.get(index).setLinkedPerson(null);
        } else {
            events.get(index).setLinkedPerson(linkedPerson);
        }

        //sort events and return 1 as completed successfully
        sortEvents();
        return 1;
    }

    /** addPerson
     * adds new person to the arraylist
     * @param initname - string name of person
     * @param birthDate - LocalDate birthdate of person
     * @param deathDate - localDate deathdate of person
     * @param initdescription - String description of person
     * @param alive - boolean is alive
     * @return 1 - new person added successfully
     * @return -1 - name of person in use
     */
    public static int addPerson(String initname, LocalDate birthDate, LocalDate deathDate, String initdescription, boolean alive) {

        //check for name already in use
        for (int i = 0; i < persons.size(); i++) {
            if (initname.equals(persons.get(i).getName())) {
                System.out.println("Name Taken");
                return -1;
            }
        }


        //create new object, add information and add to persons array
        person person = new person();
        person.setBirthDate(birthDate);
        if (!alive) {
            person.setDeathDate(deathDate);
        }
        person.setName(initname);
        person.setDescription(initdescription);
        person.setAlive(alive);
        persons.add(person);

        //return 1 as completed add successfully
        return 1;
    }

    /** removePerson
     * removes person with matching name from arraylist
     * @param removeName - String name of person to remove
     * @return 1 - person removed successfully
     * @return -1 - unable to find person to remove
     */
    public static int removePerson(String removeName) {

        int index = -1;

        //find person to remove
        for (int i = 0; i < persons.size(); i++) {
            if (removeName == persons.get(i).getName()) {
                index = i;
                break;
            }
        }

        //return -1 if person not found
        if (index == -1) {
            return -1;
        }
        //remove person from arraylist
        persons.remove(index);

        //return 1 for successful removal
        return 1;
    }

    /** editPerson
     * edits person with info provided
     * @param name - String name of person to edit
     * @param newBirthDate - localDate birth date of person - null if no change
     * @param newDeathDate - localDate death date of person - null if no change
     * @param newDescription - String description of person - null if no change
     * @param isAlive - boolean is person alive - null if no change
     * @return 1 - person edited successfully
     * @return -1 - unable to find person to edit
     */
    public static int editPerson(String name, LocalDate newBirthDate, LocalDate newDeathDate, String newDescription, boolean isAlive) {

        int index = -1;

        //find person in array
        for (int i = 0; i < persons.size(); i++) {
            if (name == persons.get(i).getName()) {
                index = i;
                break;
            }
        }

        //return -1 if person not found
        if (index == -1) {
            return -1;
        }

        //check for changes and apply them
        if (newBirthDate != null) {
            persons.get(index).setBirthDate(newBirthDate);
        }
        if (newDeathDate != null) {
            persons.get(index).setDeathDate(newDeathDate);
        }
        if (newDescription != null) {
            persons.get(index).setDescription(newDescription);
        }
        if (isAlive != persons.get(index).getAlive()) {
            persons.get(index).setAlive(isAlive);
        }
        if (isAlive) {
            persons.get(index).setDeathDate(null);
        }

        //return 1 as edited successfully
        return 1;
    }

    /** newTimeline
     * clears the arrays
     */
    public static void newTimeline() {
        events.clear();
        persons.clear();
        openTimeline = true;

    }

    /** saveTimeline
     * saves current timeline to file
     * @return 1 - save completed successfully
     * @return -1 - error saving file
     * @return -2 - JSON Exception
     * @return -3 - user cancelled save
     */
    public static int saveTimeline(){

        try {
            //set current working directory
            String cwd = System.getProperty("user.dir");
            String directory = cwd + "/timelines";

            String saveFilename;

            //set up file chooser
            JFileChooser fileChooser = new JFileChooser(new File(directory));
            fileChooser.setDialogTitle("Save Timeline");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("json files","json");
            fileChooser.setFileFilter(filter);

            //show file chooser
            int returnValue = fileChooser.showDialog(null, "Save");
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                saveFilename = fileChooser.getSelectedFile().getPath();
            } else {
                //user cancelled save return 3
                return -3;
            }

            //decalre JSON objects and arrays
            JSONObject allEvents = new JSONObject();
            JSONObject allPersons = new JSONObject();
            JSONObject all = new JSONObject();
            JSONArray eventsArray = new JSONArray();
            JSONArray personsArray = new JSONArray();
            JSONObject eventObj;
            JSONObject personObj;

            //loop through events adding them to the JSON event array
            for (int i = 0; i < events.size(); i++) {
                eventObj = new JSONObject();
                eventObj.put("name", events.get(i).getName());
                eventObj.put("date", events.get(i).getDate().format(dtf));
                eventObj.put("description", events.get(i).getDescription());
                eventObj.put("linkedPerson", events.get(i).getLinkedPerson());
                eventsArray.put(eventObj);
            }

            //put JSON events array into allEvents object
            allEvents.put("events", eventsArray);

            //loop through persons adding them to the JSON persons array
            for (int i = 0; i < persons.size(); i++) {
                personObj = new JSONObject();
                personObj.put("name", persons.get(i).getName());
                personObj.put("birthDate", persons.get(i).getBirthDate().format(dtf));
                personObj.put("alive", persons.get(i).getAlive());
                if (persons.get(i).getAlive()) {
                    personObj.put("deathDate", "");
                } else {
                    personObj.put("deathDate", persons.get(i).getDeathDate().format(dtf));
                }
                personObj.put("description", persons.get(i).getDescription());
                personsArray.put(personObj);
            }


            //put JSON persons array into allPersons object
            allPersons.put("persons", personsArray);

            //put allEvents and allPersons into all object
            all.put("allEvents", allEvents);
            all.put("allPersons", allPersons);

            //write all object to file
            try {
                FileWriter file = new FileWriter(saveFilename);
                file.write(all.toString());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }

            //return 1 as successfully completed save
            return 1;

            //catch JSON exception and return -2
        } catch (JSONException e) {
            return -2;
        }
    }

    /** openTimeline
     * opens timeline from file and loads into arraylists
     * @return
     */
    public static int openTimeline(){

        try {
            //set current working directory for default location
            String cwd = System.getProperty("user.dir");
            String directory = cwd + "/timelines";

            String openFilename;

            try {

                //set up file chooser
                JFileChooser fileChooser = new JFileChooser(new File(directory));
                fileChooser.setDialogTitle("Open Timeline");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("json files","json");
                fileChooser.setFileFilter(filter);

                //display file chooser anf get path of file to open
                int returnValue = fileChooser.showDialog(null, "Open");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    openFilename = fileChooser.getSelectedFile().getPath();
                } else {
                    //user cancelled open
                    return -3;
                }

                //open file and get timeline name
                File openFile = new File(openFilename);
                if (openFile.getName().indexOf(".") > 0) {
                    timelineName = openFile.getName().substring(0, openFile.getName().lastIndexOf("."));
                }

                //scan file into jsonString
                Scanner sc = new Scanner(openFile);
                String jsonString = sc.useDelimiter("\\A").next();
                sc.close();

                //decalre JSON objects
                JSONObject eventObj;
                JSONObject personObj;
                JSONObject jsonObject = new JSONObject(jsonString);
                JSONObject allevents = jsonObject.getJSONObject("allEvents");
                JSONObject allpersons = jsonObject.getJSONObject(("allPersons"));
                JSONArray eventsArray = allevents.getJSONArray("events");

                //loop through all events in array and add them to the events arraylist
                for (int j = 0; j < eventsArray.length(); j++) {
                    eventObj = eventsArray.getJSONObject(j);
                    try {
                        addEvent( eventObj.get("name").toString(), LocalDate.parse(eventObj.get("date").toString(), dtf),eventObj.get("description").toString(), eventObj.get("linkedPerson").toString());
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                    }
                }

                //loop through all persons in array and add them to the persons arraylist
                JSONArray personsArray = allpersons.getJSONArray("persons");
                for (int j = 0; j < personsArray.length(); j++) {
                    personObj = personsArray.getJSONObject(j);
                    LocalDate birthdate = LocalDate.parse(personObj.get("birthDate").toString(), dtf);
                    LocalDate deathDate;
                    if (!Boolean.valueOf(personObj.get("alive").toString())) {
                        deathDate = LocalDate.parse(personObj.get("deathDate").toString(), dtf);
                    } else {
                        deathDate = LocalDate.parse(personObj.get("birthDate").toString(), dtf);
                    }
                    addPerson(personObj.get("name").toString(), birthdate, deathDate, personObj.get("description").toString(), Boolean.valueOf(personObj.get("alive").toString()));

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return -1;
            }

            //sort events into chronoligical order
            sortEvents();

            //set opentimeline true
            openTimeline = true;

            return 1;

        } catch (JSONException e) {
            return -2;
        }
    }

    /** printEvents
     *  prints events to console
     */
    public static void printEvents() {

        System.out.format("%n%n");
        System.out.format("%16s%16s%32s%16s%n", "Name", "Date", "description", "Linked Person");
        System.out.format("%64s%n", "-----------------------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < events.size(); i++) {

            System.out.format("%16s%16s%32s%16s%n", events.get(i).getName(), events.get(i).getDate().format(dtf), events.get(i).getDescription(), events.get(i).getLinkedPerson());
        }
    }

    /** printPersons
     * prints persons to console
     */
    public static void printPersons() {

        System.out.format("%n%n");
        System.out.format("%16s%16s%16s%16s%16s%n", "Name", "Birth Date", "Death Date", "description", "is alive");
        System.out.format("%64s%n", "-----------------------------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < persons.size(); i++) {


                System.out.format("%16s%16s%16s%32s%16s%n", persons.get(i).getName(), persons.get(i).getBirthDate(), persons.get(i).getDeathDate(), persons.get(i).getDescription(), persons.get(i).getAlive());

        }

    }


    public static void setTimelineName(String inittimelineName) {
        timelineName = inittimelineName;
    }

    public static String getTimelineName(){
        return timelineName;
    }


    /** sortEvents
     *  sort events into chronological order
     */
    public static void sortEvents() {
        events.sort(Comparator.comparing(event::getDate));
    }


    /** setTmp
     * tmp string setter
     * @param settmp
     */
    public static void setTmp(String settmp) {
        tmp = settmp;
    }

    /** getTmp
     * tmp string getter
     * @return
     */
    public static String getTmp() {
        return tmp;
    }
}