package robertdaronchuckgroupproject;

import java.time.LocalTime;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.ListSpinnerValueFactory;
import javafx.scene.layout.HBox;

/**
 *This class constructs the timeBox and provides
 *get methods to retrieve a concatenated string of the selected time or
 *of the individual parts of the selected time.
 */
public class RobertChuckDaronTimeSelector {
    
    private SimpleStringProperty hour = new SimpleStringProperty();
    private SimpleStringProperty minute = new SimpleStringProperty();
    private SimpleStringProperty meridiem = new SimpleStringProperty();
    private LocalTime militaryTime;
    private String time;
   
    
    private final ObservableList hoursList = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12");
    private final ChoiceBox hourBox = new ChoiceBox(hoursList);
    
    private final ObservableList minuteList = FXCollections.observableArrayList("00","05","10","15","20","25","30","35","40","45","50","55");
    private final ChoiceBox minuteBox = new ChoiceBox(minuteList);
            
    private final ObservableList meridiemList = FXCollections.observableArrayList("","AM","PM");
    private final ListSpinnerValueFactory meridiemFactory = new ListSpinnerValueFactory(meridiemList);
    private final Spinner meridiemSpin = new Spinner(meridiemFactory);
    
    //timebox is public so it can be referenced in gui
    public  HBox timeBox = new HBox();
    
    
    //The constructor for the class formats the nodes used
    public RobertChuckDaronTimeSelector()
    {
        hour.bind(hourBox.valueProperty());
        minute.bind(minuteBox.valueProperty());
        meridiem.bind(meridiemSpin.valueProperty());
        

        // Format am/pm Spinner
        meridiemFactory.setWrapAround(true);
        meridiemSpin.setMaxWidth(58);
        
        //add nodes to timeBox
        timeBox.getChildren().addAll(hourBox,minuteBox,meridiemSpin);
       
    }
    
    
    
    /**Public method IsTimeReady signals that the the time is ready to pass
     * @return Returns true if hour minutes and meridiem is ready to return
     * false otherwise.
     */
    public boolean IsTimeReady()
    {
        Boolean flag;
        
        if ((hour.getValueSafe().isEmpty() || minute.getValueSafe().isEmpty() || meridiem.getValueSafe().isEmpty()))
        {
            flag = false;
        }
        else
        {
            flag = true;
            setTime();
        }
        
        return flag;
    }
    
    
    /**Private setTime method sets the strings
     * hour minute and meridiem
     * 
     */
    
    private void setTime()
    {

        time = hour.getValueSafe();
        time += ":";
        time += minute.getValueSafe();
        time += " ";
        time += meridiem.getValueSafe();
        setMilitaryTime();
    }
    
    private void setMilitaryTime()
    {   
        
        //Convert for AM
        if(meridiem.getValueSafe().equals("AM") && hour.getValueSafe().equals("12") == false)
        {
           militaryTime = LocalTime.of(Integer.parseInt(hour.getValueSafe()),
                                    Integer.parseInt(minute.getValueSafe()));
           
        }
        else if (meridiem.getValueSafe().equals("AM")&& hour.getValueSafe().equals("12") == true)
        {
            militaryTime = LocalTime.of(00,Integer.parseInt(minute.getValueSafe()));
           
        }
        
        //Convert for PM
        if(meridiem.getValueSafe().equals("PM") && hour.getValueSafe().equals("12") == false)
        {
           militaryTime = LocalTime.of(((Integer.parseInt(hour.getValueSafe()) + 12)),
                                    Integer.parseInt(minute.getValueSafe()));
           
        }
        else if (meridiem.getValueSafe().equals("PM")&& hour.getValueSafe().equals("12") == true)
        {
            militaryTime = LocalTime.of(Integer.parseInt(hour.getValueSafe()),
                                     Integer.parseInt(minute.getValueSafe()));
           
        }
  
    }
    
   public String getConcatTime()
   {
       return time;
   }
   
   public LocalTime getMilitaryTime()
   {
       return militaryTime;
   }

 
    
}//end class