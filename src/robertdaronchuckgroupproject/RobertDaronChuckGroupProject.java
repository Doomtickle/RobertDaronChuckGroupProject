package robertdaronchuckgroupproject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
/**
 *Rough Group project
 *Robert's version
 * Robert Chuck & Daron
 */

public class RobertDaronChuckGroupProject extends Application {
    
    
    /**
    * @param args the command line arguments
    */
    public static void main(String[] args) {
        launch(args);
    }
        //Screen position references
        private double xPos;
        private double yPos;
        //FadeTransition reference
        private FadeTransition ft;
        
        //Stage reference 
        private Stage pStage;
        
        //create Time Selectors
        RobertChuckDaronTimeSelector returnHomeTime = new RobertChuckDaronTimeSelector();
        RobertChuckDaronTimeSelector departTime = new RobertChuckDaronTimeSelector();
        RobertChuckDaronTimeSelector returnTime = new RobertChuckDaronTimeSelector();
        
        //Business trip data
        SimpleStringProperty name = new SimpleStringProperty();
        long tripDays;//THIS MUST BE LONG
        ArrayList<LocalDate> travelDates = new ArrayList<>();
        LocalDateTime departDT;
        LocalDateTime returnDT;
        LocalTime hTime;
        boolean breakfastD_Day = false;
        boolean lunchD_Day = false;
        boolean dinnerD_Day = false;
        boolean breakfastR_Day = false;
        boolean lunchR_Day = false;
        boolean dinnerR_Day = false;
        ArrayList<Double> breakfastArray = new ArrayList<>();
        ArrayList<Double> lunchArray = new ArrayList<>();
        ArrayList<Double> dinnerArray = new ArrayList<>();
        double totalCabFare = 0.0;
        double totalPersonalMiles = 0.0;
        double totalRentalFees = 0.0;
        double totalParkingFees = 0.0;
        double valueAirfare = 0.0;
        double totalHotelFee = 0.0;
        double totalSeminarFee = 0.0;

        
        
    @Override
    public void start(Stage primaryStage) {
        //set stage Reference and style
        pStage = primaryStage;
        pStage.initStyle(StageStyle.TRANSPARENT);
        
        //call animated intro scene
        getIntroScene();
        
        //Listen for fade transition to end then call scene1
        ft.setOnFinished(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event)
        {
            getScene1();
        }
        });
    

        
    }//end @override
    /**
     * Intro Scene displays team designation
     */
    public void getIntroScene()
    {
        
        Group root = new Group();
        
        //format prompt label
        Text logo = new Text();
        logo.setText("CODE_HARD");
        logo.setTextOrigin(VPos.TOP);
        logo.setFont(Font.font("Comic Sans MS",FontWeight.BOLD,FontPosture.ITALIC,150));
        //format dropshadow & add to prompt
        DropShadow ds = new DropShadow();
        ds.setColor(Color.CHARTREUSE);
        ds.setSpread(0);
        ds.setRadius(15);
        logo.setEffect(ds);
        //Create Spread Animation
        Timeline timeline = new Timeline();
        timeline.setCycleCount(16);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add
                (new KeyFrame(Duration.seconds(3),
                new KeyValue(ds.spreadProperty(),.80,Interpolator.EASE_BOTH)));        
        timeline.play();
        //Create FadeTransition
        ft = new FadeTransition(Duration.seconds(3.5),logo);
        ft.setFromValue(1);
        ft.setToValue(.0);
        ft.play();
        //add prompt to group
        root.getChildren().add(logo);
        //Postion prompt on to scene & format scene
        Scene introScene = new Scene(root);
        introScene.setFill(null);
        
        manageStage(root,introScene);
        pStage.setScene(introScene);
        pStage.centerOnScreen();
        pStage.show();
        
        
        
        ft.setOnFinished(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
           getScene1();
           
        }
        });
        
        
        
    }
   /**
    * Manage stage allows the window to be moved smoothly
    */
    void manageStage(Group root, Scene scene)
    {   
        
        
        //when mouse button is pressed, save the initial position of screen
        root.setOnMousePressed(e->
        {
            xPos = e.getScreenX() - pStage.getX();
            yPos = e.getScreenY() - pStage.getY();
        });
        
        //when screen is dragged, translate it accordingly
        root.setOnMouseDragged(e ->
        {
            pStage.setX(e.getScreenX() - xPos);
            pStage.setY(e.getScreenY() - yPos);
        });
        
        //set the stage
        pStage.setScene(scene);

    }
    /**
     * Next button provides forward navigation
     */
    Button nextButton(){
        
        ImageView rArrow = new ImageView("rightArrow.png");
        Button nextButton = new Button("",rArrow);
        nextButton.setStyle("-fx-background-color: transparent;");
        nextButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        nextButton.setTranslateX(792);
        nextButton.setTranslateY(75);
        
        nextButton.setOnMouseEntered(me ->
        {
            nextButton.setEffect(new GaussianBlur(3));
        });
        nextButton.setOnMouseExited(me ->{
            nextButton.setEffect(null);
        });
        nextButton.setOnMousePressed(mp ->{
            nextButton.setTranslateX(795);
        });
        nextButton.setOnMouseReleased(mr->{
            nextButton.setTranslateX(792);
        });
        
        
        
        return nextButton;
        
    }
    
    /**
     * Back Button provides backward Navigation
     */
    Button backButton(){
        
        ImageView lArrow = new ImageView("leftArrow.png");
        Button backButton = new Button("",lArrow);
        backButton.setStyle("-fx-background-color: transparent;");
        backButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        backButton.setTranslateX(792);
        backButton.setTranslateY(175);
        
        backButton.setOnMouseEntered(me ->
        {
            backButton.setEffect(new GaussianBlur(3));
        });
        backButton.setOnMouseExited(me ->{
            backButton.setEffect(null);
        });
        backButton.setOnMousePressed(mp ->{
            backButton.setTranslateX(795);
        });
        backButton.setOnMouseReleased(mr->{
            backButton.setTranslateX(792);
        });
        
        
        return backButton;
        
    }
    
    /**
     * Exit Button provides APP exiting
     */
    Button exitButton(){
        
        ImageView exit = new ImageView("exit.png");
        Button exitButton = new Button("",exit);
        exitButton.setStyle("-fx-background-color: transparent;");
        exitButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        exitButton.setTranslateX(792);
        exitButton.setTranslateY(275);
        
        exitButton.setOnMouseEntered(me ->
        {
            exitButton.setEffect(new GaussianBlur(3));
        });
        exitButton.setOnMouseExited(me ->{
            exitButton.setEffect(null);
        });
        exitButton.setOnMousePressed(mp ->{
            exitButton.setTranslateX(795);
        });
        exitButton.setOnMouseReleased(mr->{
            exitButton.setTranslateX(792);
        });
        
        exitButton.setOnAction(a->{
        	
            boolean answer =  ConfirmBox.confirm("Exit the program?", " Are you sure you want "
					+ "to exit the program? Your progress will not be saved. ");
            if (answer == true)
                pStage.close();
        });
        
        
        return exitButton;
        
    }
    
    /**
     * Scene 1 is the Entry point and title screen of the APP
     */
    void getScene1(){
      
        Group root = new Group();
        Image picture = new Image("scene1.png");
        ImageView iv = new ImageView(picture);
        iv.setEffect(new GaussianBlur(3));
        Text prompt = new Text();
        prompt.setText("Trip Reporter Express\n Use the menu on the right to begin.");
        
        prompt.setFill(Color.WHITE);
        prompt.setStroke(Color.BLACK);
        prompt.setFont(Font.font("Helvetica",FontWeight.BOLD,FontPosture.ITALIC,40));
        prompt.setStyle("-fx-stroke: black");
        StackPane spScene1 = new StackPane();
        Button nextButton = nextButton();
        Button backButton = backButton();
        Button exitButton = exitButton();
        spScene1.getChildren().addAll(iv,prompt);
        root.getChildren().addAll(spScene1,nextButton,backButton,exitButton);
        Scene scene = new Scene(root);
        scene.setFill(null);
        manageStage(root,scene);
        pStage.setScene(scene);
        pStage.show();
        
        backButton.setOnAction(a->
        {
            getIntroScene();
        
        });
        
        nextButton.setOnAction(a->
        {
            getName();
        });
        
        
        
    }
    /**
     *GetName() gets travelers name
     */
    void getName()
    {
        
        
        Button nextButton = nextButton();
        Button backButton = backButton();
        Button exitButton = exitButton();
        
        
        //set a group to hold nodes
        Group root = new Group();
        
        //load image set to imageview
        Image picture = new Image("nameTag800x400.png");
        ImageView iv = new ImageView(picture);
            
        StackPane spScene2 = new StackPane(iv);
        spScene2.setBackground(Background.EMPTY);
        
        //text prompt
        Text prompt = new Text("Click The Sceen to enter your name");
        prompt.setFont(Font.font("Helvetica",FontWeight.NORMAL, FontPosture.REGULAR,29));
        prompt.setTextOrigin(VPos.BASELINE);
        spScene2.getChildren().add(prompt);
        
        Text name = new Text("");
        name.setFont(Font.font("Helvetica",FontWeight.NORMAL, FontPosture.REGULAR,29));
        name.setTextOrigin(VPos.BASELINE);
        spScene2.getChildren().addAll(name);
        this.name.bindBidirectional(name.textProperty());
        
        
        //add to group
        root.getChildren().addAll(spScene2,nextButton,backButton,exitButton);
        
        Scene scene = new Scene(root);
        scene.setFill(null);
        
        spScene2.setOnMouseReleased(new EventHandler<MouseEvent>()
        {
            @Override public void handle(MouseEvent me)
            {
                prompt.setVisible(false);
                name.setVisible(true);
                spScene2.requestFocus();
                me.consume();
            }
        });
        spScene2.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override public void handle(KeyEvent ke)
            {
                String swap = "";
                
              if(ke.getCode().equals(KeyCode.BACK_SPACE))
              {
                  if (!name.getText().isEmpty())
                  {
                    swap = name.getText();
                    swap = swap.substring(0,swap.length()-1);
                  }
                  name.setText(swap);
                  swap = "";
                  ke.consume();
              }
              
              if(ke.getCode().equals(KeyCode.SPACE) 
                && name.getText().isEmpty() == false
                && name.getText().contains(" ") == false)
              {
                  swap = name.getText();
                  swap += " ";
                  name.setText(swap);
                  swap = "";
                  ke.consume();
              }
                  
                
              if (ke.getText().matches("[a-zA-z]") && name.getText().length() < 31)
              { 
                swap = name.getText();
                swap += ke.getText().toUpperCase().trim();
                name.setText(swap);
                swap = "";
                ke.consume();    
              };
              
           if(ke.getCode().equals(KeyCode.ENTER)){ 
              
              if (name.getText().isEmpty())
            {
                prompt.setVisible(true);
                name.setVisible(false);
            }
              
              if(name.getText().isEmpty())
            {
                prompt.setVisible(true);
                prompt.setFill(Paint.valueOf("RED"));
            }
            else 
               getDateTime();
           }
              
            }
        });
       
        
        //handle on mouse removed
        root.setOnMouseExited(a->
        {
            if (name.getText().isEmpty())
            {
                prompt.setVisible(true);
                name.setVisible(false);
            }
        });
        
        nextButton.setOnAction(a->{
            if(name.getText().isEmpty())
            {
                prompt.setVisible(true);
                prompt.setFill(Paint.valueOf("RED"));
            }
            else
                getDateTime();
        
        });
        
        backButton.setOnAction(a->{
            getScene1();
        });

        manageStage(root,scene);
        pStage.setScene(scene);
        pStage.show();
         
    }
    
    /**
     *getDateTime() gets travel dates and times
     */
    void getDateTime()
    {
    
        //setting up departDT inputs
        Text departAt = new Text("Pick Your Depart Date and Time: ");
        departAt.setFont(Font.font("Helvetica", FontWeight.BLACK, FontPosture.REGULAR,15));
        
        DatePicker departDate = new DatePicker();
        
       
        
        //add departDT inputs to a hbox
        HBox departBox = new HBox();
        departBox.setSpacing(8);
        departBox.getChildren().addAll(departAt,departDate,departTime.timeBox);
        
        
        
        //setting up return inputs 
        Text returnAt = new Text("Pick Your Return Date and Time: ");
        returnAt.setFont(Font.font("Helvetica",FontWeight.BLACK,FontPosture.REGULAR,15));
        
        
        DatePicker returnDate = new DatePicker();
        
        
        //add return inputs to a hbox
        HBox returnBox = new HBox();
        returnBox.setSpacing(8);
        returnBox.getChildren().addAll(returnAt,returnDate,returnTime.timeBox);
        
        //setting up return home inputs
        Text returnHome = new Text("Enter your return home time.");
        returnHome.setFont(Font.font("Helvetica", FontWeight.BLACK, FontPosture.REGULAR,15));
        
        
        
        //add returnhome to a hbox
        HBox returnHomeBox = new HBox();
        returnHomeBox.setSpacing(8);
        returnHomeBox.getChildren().addAll(returnHome,returnHomeTime.timeBox);
    
        StackPane stackpane = new StackPane();
        VBox vbScene3 = new VBox(45);
        vbScene3.setPadding(new Insets(22));
        vbScene3.setBackground(Background.EMPTY);
        stackpane.setBackground(Background.EMPTY);
        Image calendarImage = new Image("calendar800x400.jpg");
        ImageView calendarIV = new ImageView(calendarImage);
        stackpane.getChildren().add(calendarIV);
        vbScene3.getChildren().addAll(departBox,returnBox,returnHomeBox);
        stackpane.getChildren().add(vbScene3);
        
        Button nextButton = nextButton();
        Button backButton = backButton();
        Button exitButton = exitButton();
       
        Group root = new Group();
        root.getChildren().addAll(stackpane, nextButton, backButton,exitButton);
       
        Scene scene3 = new Scene(root); 
        scene3.setFill(null);
        manageStage(root,scene3);
        pStage.setScene(scene3);
        
        
        nextButton.setOnAction(a ->{
        	
        	if (!dateTimeValid(departDate,returnDate))
                {
                    AlertBox.alert("Error",
                    "Be sure to enter a value in each field.\n"
                  + "Return date and time must be after depart date and time.\n"
                  + "Home time must be after return time.");
        	}else
                {
                    getAirfare();
                }
            
        });
        
        backButton.setOnAction(a ->{
            getName();
        });
        
    }
   
    /**
     *getAirfare() gets any airfare costs
     */
    void getAirfare()
    {
        //nav buttons
        Button nextButton = nextButton();
        Button backButton = backButton();
        Button exitButton = exitButton();
        
        
        //set a group to hold nodes
        Group root = new Group();
        
        //load image set to imageview
        Image picture = new Image("airplane800x400.png");
        ImageView iv = new ImageView(picture);
            
        StackPane spScene4 = new StackPane(iv);
        spScene4.setBackground(Background.EMPTY);
        
        VBox vb = new VBox(40);
        vb.setPadding(new Insets(10));
        Text prompt = new Text(" Enter any airfare incurred on you trip.");
        prompt.setFocusTraversable(true);
        prompt.requestFocus();
        prompt.setFont(Font.font("Helvetica",FontWeight.NORMAL, FontPosture.REGULAR,29));
        
        
        
        vb.getChildren().addAll(prompt);
        
        spScene4.getChildren().addAll(vb);
        
        //airfare validation
        TextField airfare = new NumberTextField();  //made a new class to restrict the type of input (only positive intege
        airfare.setText("0.00");
        airfare.setMaxWidth(100);
        airfare.setPrefColumnCount(8);
        vb.getChildren().addAll(airfare);
        
        
        
        root.getChildren().addAll(spScene4,nextButton,backButton,exitButton);
        
        Scene scene4 = new Scene(root); 
        scene4.setFill(null);
        manageStage(root,scene4);
        pStage.setScene(scene4);
        
        nextButton.setOnAction(a->{
        	
        	valueAirfare = Double.parseDouble(airfare.getText());
        	
            getCarFees();
        });
        
        backButton.setOnAction(a->{
            getDateTime();
        });
    }
    
    /**
     *getCarFees() gets car associated costs
     */
    void getCarFees()
    {
    	//nav buttons
        Button nextButton = nextButton();
        Button backButton = backButton();
        Button exitButton = exitButton();
        

        //set a group to hold nodes
        Group root = new Group();

        
        //load image set to imageview
        Image picture = new Image("taxi800x400.png");
        ImageView iv = new ImageView(picture);

        
        VBox vb = new VBox(15);
        vb.setPadding(new Insets(10));

        
        Text text = new Text("Please enter all of your car fees");
        text.setFont(Font.font("Helvetica",FontWeight.NORMAL, FontPosture.REGULAR,29));
        text.setFill(Color.ANTIQUEWHITE);
        NumberTextField cabfare = new NumberTextField();  //made a new class to restrict the type of input (only positive integers)
        cabfare.setText("0.00");
        cabfare.setMaxWidth(100);
        cabfare.setPrefColumnCount(8);
        NumberTextField personalMiles = new NumberTextField();  //made a new class to restrict the type of input (only positive integers)
        personalMiles.setText("0.00");
        personalMiles.setMaxWidth(100);
        personalMiles.setPrefColumnCount(8);
        NumberTextField rentalFees = new NumberTextField();  //made a new class to restrict the type of input (only positive integers)
        rentalFees.setText("0.00");
        rentalFees.setMaxWidth(100);
        rentalFees.setPrefColumnCount(8);
        NumberTextField parkingFees = new NumberTextField();  //made a new class to restrict the type of input (only positive integers)
        parkingFees.setText("0.00");
        parkingFees.setMaxWidth(100);
        parkingFees.setPrefColumnCount(8);

        
        vb.getChildren().addAll(text, cabfare, personalMiles, rentalFees, parkingFees);

        
        VBox labels = new VBox(15);
        labels.setPadding(new Insets(10));
        labels.setTranslateX(150);
        labels.setTranslateY(45);
        labels.setMaxWidth(600);
        Label cabLabel = new Label("Total Cab Fares:");
        cabLabel.setTextFill(Color.ANTIQUEWHITE);
        cabLabel.setFont(Font.font("Helvetica",FontWeight.NORMAL, FontPosture.REGULAR,29));

       
        Label personalLabel = new Label("Total Personal Miles");
        personalLabel.setFont(Font.font("Helvetica",FontWeight.NORMAL, FontPosture.REGULAR,29));

        
        personalLabel.setTextFill(Color.ANTIQUEWHITE);
        Label rentalLabel = new Label("Total Rental Fees");
        rentalLabel.setFont(Font.font("Helvetica",FontWeight.NORMAL, FontPosture.REGULAR,29));

        
        rentalLabel.setTextFill(Color.ANTIQUEWHITE);
        Label parkingLabel = new Label("Total Parking Fees");

        
        parkingLabel.setFont(Font.font("Helvetica",FontWeight.NORMAL, FontPosture.REGULAR,29));
        parkingLabel.setTextFill(Color.ANTIQUEWHITE);
        labels.getChildren().addAll(cabLabel, personalLabel, rentalLabel, parkingLabel);

       
        VBox buttons = new VBox(15);
        buttons.setPadding(new Insets(10));
        buttons.setTranslateX(100);
        buttons.setTranslateY(45);
        Button addCabFare = new Button("Add Cab Fare");

        

        
        addCabFare.setOnAction(a->{

        	
        totalCabFare += (Double.parseDouble(cabfare.getText()));
        cabLabel.setText("Total Cab Fares: " + totalCabFare);
        cabfare.clear();
        });

        

        
         Button addPersonalMiles = new Button("Add Personal Miles");
        addPersonalMiles.setOnAction(a->{

        	
        	totalPersonalMiles += (Double.parseDouble(personalMiles.getText()));
        	personalLabel.setText("Total Personal Miles: " + totalPersonalMiles);
        	personalMiles.clear();
        });

        
        Button addRentalFees = new Button("Add Rental Fees");
        addRentalFees.setOnAction(a->{

        	
        	totalRentalFees += (Double.parseDouble(rentalFees.getText()));
        	rentalLabel.setText("Total Rental Fees: " + totalRentalFees);
        	rentalFees.clear();
        });

        
        Button addParkingFees = new Button("Add Parking Fees");
        addParkingFees.setOnAction(a->{

        	
        	totalParkingFees += (Double.parseDouble(parkingFees.getText()));
        	parkingLabel.setText("Total Parking Fees: " + totalParkingFees);
        	parkingFees.clear();
        });

        
        buttons.getChildren().addAll(addCabFare, addPersonalMiles, addRentalFees, addParkingFees);

        

        

        
        StackPane spScene5 = new StackPane(iv);
        spScene5.setBackground(Background.EMPTY);
        spScene5.getChildren().addAll(vb,labels, buttons);

        

       
        root.getChildren().addAll(spScene5,nextButton,backButton,exitButton);

        
        Scene scene5 = new Scene(root); 
        scene5.setFill(null);
        manageStage(root,scene5);
        pStage.setScene(scene5);
        
        nextButton.setOnAction(a->{

        	getHotelRegFees();
        });

        
        backButton.setOnAction(a->{
            totalCabFare = 0;
            totalRentalFees = 0;
            totalPersonalMiles = 0;
            totalParkingFees = 0;
            getAirfare();
        });
        
    }//end getCarFees
    
    
    /**
     *getHotelRegFees() gets hotel/registration costs
     */
    void getHotelRegFees()
    {

    	
    	DropShadow ds = new DropShadow();
    	ds.setOffsetY(3.0f);
    	ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
         //nav buttons
    	//nav buttons
        Button nextButton = nextButton();
        Button backButton = backButton();
        Button exitButton = exitButton();

        

        
        //set a group to hold nodes
        Group root = new Group();

        
        //load image set to imageview
        Image picture = new Image("bellagiohotel800x400.png");
        ImageView iv = new ImageView(picture);

        
        VBox vb = new VBox(15);
        vb.setPadding(new Insets(10));

        
        Text text = new Text("Please enter all of your Hotel and Seminar registration fees");
        text.setFont(Font.font("Helvetica",FontWeight.NORMAL, FontPosture.REGULAR,29));
        text.setFill(Color.BLACK);
        NumberTextField hotelFee = new NumberTextField();  //made a new class to restrict the type of input (only positive integers)
        hotelFee.setText("0.00");
        hotelFee.setMaxWidth(100);
        hotelFee.setPrefColumnCount(8);
        NumberTextField seminarFee = new NumberTextField();  //made a new class to restrict the type of input (only positive integers)
        seminarFee.setText("0.00");
        seminarFee.setMaxWidth(100);
        seminarFee.setPrefColumnCount(8);

        

        
        vb.getChildren().addAll(text);
        
        Label hotelLabel = new Label("Total Hotel Fee: ");
        hotelLabel.setTextFill(Color.BLACK);
        hotelLabel.setFont(Font.font("Helvetica",FontWeight.EXTRA_BOLD, FontPosture.REGULAR,20));
        hotelLabel.setEffect(ds);

        

       
        Label seminarLabel = new Label("Total Seminar Fees: ");
        seminarLabel.setFont(Font.font("Helvetica",FontWeight.EXTRA_BOLD, FontPosture.REGULAR,20));
        seminarLabel.setTextFill(Color.BLACK);
        seminarLabel.setEffect(ds);

        
        
        Button addHotelFee = new Button("Add Hotel Fee");
        Button addSeminarFee = new Button("Add Seminar Registration Fee");

        HBox hotelBox = new HBox();
        hotelBox.setSpacing(10);
        hotelBox.setPadding(new Insets(40));
        hotelBox.getChildren().addAll(hotelFee,addHotelFee,hotelLabel);
        
        HBox regFeeBox = new HBox();
        regFeeBox = new HBox();
        regFeeBox.setSpacing(10);
        regFeeBox.setPadding(new Insets(40));
        regFeeBox.getChildren().addAll(seminarFee,addSeminarFee,seminarLabel);
        
        vb.getChildren().addAll(hotelBox,regFeeBox);

        
        addHotelFee.setOnAction(a->{

        	
        totalHotelFee += (Float.parseFloat(hotelFee.getText()));
        hotelLabel.setText("Total Hotel Fee: " + totalHotelFee);
        hotelFee.clear();
        });
        
         
        addSeminarFee.setOnAction(a->{

        	
        	totalSeminarFee += (Float.parseFloat(seminarFee.getText()));
        	seminarLabel.setText("Total Seminar Fees: " + totalSeminarFee);
        	seminarFee.clear();
        });


        StackPane spScene5 = new StackPane(iv);
        spScene5.setBackground(Background.EMPTY);
        spScene5.getChildren().addAll(vb);

        

       
        root.getChildren().addAll(spScene5,nextButton,backButton,exitButton);

        
        Scene scene5 = new Scene(root); 
        scene5.setFill(null);
        manageStage(root,scene5);
        pStage.setScene(scene5);

        

        
        nextButton.setOnAction(a->{
                getMeals();
        });

        
        backButton.setOnAction(a->{
        	totalHotelFee = 0;
        	totalSeminarFee = 0;
                getCarFees();
        });
    }
    
    /**
     *getMeals() gets food costs
     */
    void getMeals()
    {
         //nav buttons
        Button nextButton = nextButton();
        Button backButton = backButton();
        Button exitButton = exitButton();
        //disable focus nextButton until all meal values confirmed
        nextButton.setDisable(true);
        
        //set a group to hold nodes
        Group root = new Group();
        
        //load image set to imageview
        Image picture = new Image("meal800x400.png");
        ImageView iv = new ImageView(picture);
        StackPane spScene7 = new StackPane(iv);
        
        VBox outerVB = new VBox(40);
        outerVB.setPadding(new Insets(10));
        Text prompt = new Text(" Enter meal costs incurred on you trip.");
        prompt.setFont(Font.font("Helvetica",FontWeight.BOLD, FontPosture.REGULAR,29));
        prompt.setFill(Color.BLACK);
        
        outerVB.getChildren().addAll(prompt);
        
        
        
        //array position
        SimpleIntegerProperty index = new SimpleIntegerProperty();
        index.set(0);
        
        
        //charge frame
        GridPane chargeGrid = new GridPane();
        chargeGrid.setHgap(15);
        chargeGrid.setVgap(12);
        ObservableList<Node> content = chargeGrid.getChildren();
        
        VBox mainBox = new VBox();
        mainBox.setMaxWidth(250);
        mainBox.setPadding(new Insets(5));
        Color color = Color.FLORALWHITE;
        mainBox.setBackground(new Background(new BackgroundFill(color,null,null)));
        
           
        Label date = new Label();
        date.setText(travelDates.get(index.intValue()).toString());
        
        
        Label label_1 = new Label("Breakfast");
        GridPane.setConstraints(label_1, 0,0);
        content.add(label_1);
        
        TextField cost1 = new NumberTextField();
        GridPane.setConstraints(cost1, 1,0);
        content.add(cost1);
        
        
        Label label_2 = new Label("Lunch");
        GridPane.setConstraints(label_2, 0,1);
        content.add(label_2);
        
        TextField cost2 = new NumberTextField();
        GridPane.setConstraints(cost2, 1,1);
        content.add(cost2);
        
    
        Label label_3 = new Label("Dinner");
        GridPane.setConstraints(label_3, 0,2);
        content.add(label_3);
        
        TextField cost3 = new NumberTextField();
        GridPane.setConstraints(cost3, 1,2);
        content.add(cost3);
        
        HBox navBox = new HBox();
        navBox.setSpacing(20);
        Button nextDay = new Button("Next Day");
        Button backDay = new Button("Previous Day");
        if (tripDays == 1)
            backDay.setDisable(true);
        
        navBox.getChildren().addAll(backDay,nextDay);
        
        mainBox.setSpacing(10);
        mainBox.getChildren().addAll(date,chargeGrid,navBox);
        
        //end charge frame
        
        //costs setTextfield values to 00.00
        cost1.setText("0.0");
        cost2.setText("0.0");
        cost3.setText("0.0");
        
        
        //add values to the array
        nextDay.setOnAction(a->{
           //confirm boolean
           boolean answer = false;
           //Check bounds are safe before doing stuff
           if(index.intValue() < travelDates.size()-1)
           {//do stuff first on nextDay
                /*Safe to do stuff Here */
                if (cost1.getText().isEmpty())
                    breakfastArray.add(index.intValue(),0.00);
                else
                   breakfastArray.add(index.intValue(),Double.parseDouble(cost1.textProperty().getValueSafe()));
                if (cost2.getText().isEmpty())
                    lunchArray.add(index.intValue(),0.00);
                else
                lunchArray.add(index.intValue(),Double.parseDouble(cost2.textProperty().getValueSafe()));
                if  (cost3.getText().isEmpty())
                    dinnerArray.add(index.intValue(),0.00);
                else
                dinnerArray.add(index.intValue(),Double.parseDouble(cost1.textProperty().getValueSafe()));
                cost1.setText("0.0");
                cost2.setText("0.0");
                cost3.setText("0.0");
               
               
                /*Stop doing stuff Here */
                /*   Then Increment     */   
                index.set((index.intValue() + 1));
                date.setText(travelDates.get(index.intValue()).toString());
                //change button label
                if(index.intValue() == travelDates.size()-1)
                {
                    nextDay.setText("Confirm");
                }
                
           }
           else
           {
            
                answer =  ConfirmBox.confirm("Meals","Are you done entering your meal costs?");
                if (answer)
                {
                    if (cost1.getText().isEmpty())
                        breakfastArray.add(index.intValue(),0.00);
                    else
                        breakfastArray.add(index.intValue(),Double.parseDouble(cost1.textProperty().getValueSafe()));
                    if (cost2.getText().isEmpty())
                        lunchArray.add(index.intValue(),0.00);
                    else
                        lunchArray.add(index.intValue(),Double.parseDouble(cost2.textProperty().getValueSafe()));
                    if  (cost3.getText().isEmpty())
                        dinnerArray.add(index.intValue(),0.00);
                    else
                        dinnerArray.add(index.intValue(),Double.parseDouble(cost1.textProperty().getValueSafe())); 

                    //enable nextButton
                    nextButton.setDisable(false);
                    Image picture2 = new Image("flag800x400.png");
                    ImageView iv2 = new ImageView(picture2);
                    spScene7.getChildren().add(iv2);
                    

                }
               
           }
        });
        
        
        //change values on the array
        backDay.setOnAction(a->{
           //check bounds are safe before doing stuff
           if (index.intValue() != 0)
           {//decrement first on backDay
               /*   Then decrement     */
                index.set((index.intValue() - 1));
                date.setText(travelDates.get(index.intValue()).toString());
                /*Safe to do stuff Here */
                cost1.setText(String.valueOf(breakfastArray.get(index.intValue())));
                cost2.setText(String.valueOf(lunchArray.get(index.intValue())));
                cost3.setText(String.valueOf(dinnerArray.get(index.intValue())));
                //set/revert next day button
                nextDay.setText("Next Day");
               
               /*Stop doing stuff Here */
           }
           else
               AlertBox.alert("Hey","This is the first day of your trip!");
   
        });
        
        
        //add to outervbox
        outerVB.getChildren().addAll(mainBox);
        
   /////////////////////////////////////////////////////////////////
            
        
        spScene7.getChildren().addAll(outerVB);
        spScene7.setBackground(Background.EMPTY);
        
       
        root.getChildren().addAll(spScene7,nextButton,backButton,exitButton);
        
        Scene scene7 = new Scene(root); 
        scene7.setFill(null);
        manageStage(root,scene7);
        pStage.setScene(scene7);
        
        
        nextButton.setOnAction(a->{
            writeToCommand();
        });
        
        backButton.setOnAction(a->{
            if (nextButton.isDisable())
                getHotelRegFees();
            else
                getMeals();
        });
    }
    
    /**
     * dateTimeValid takes all date time information and validates and
     * sets vars. This method also a sets meal boolean vars.
     * @return returns true is dates/times are valid false otherwise.
     */
    boolean dateTimeValid(DatePicker departD, DatePicker returnD)
    {
        //return flag for method
        boolean valid;
        //Holds times
        LocalTime dTime = null;
        LocalTime rTime = null;
        LocalTime homeTime = null;
        //holds dates
        LocalDate dDate = null;
        LocalDate rDate = null;
        //holds dateTime
        LocalDateTime dDateTime = null;
        LocalDateTime rDateTime = null;
        
        
        // First Validate INPUT
        if (departD.getValue() == null || returnD.getValue() == null)
            valid = false;
        else{
            dDate = departD.getValue();
            rDate = returnD.getValue();  
        }
        
        //check use inputed times by using Methods in the class RobertChuckDarTimeSelector.java class
        if (departTime.IsTimeReady()&&returnTime.IsTimeReady()&&returnHomeTime.IsTimeReady())
        {
            dTime = departTime.getMilitaryTime();
            rTime = returnTime.getMilitaryTime();
            homeTime = returnHomeTime.getMilitaryTime();
            valid = true;
        }
        else{
            valid = false;
        }
        
       //Now Validate Chronological order
       if (valid)
       {
           dDateTime = LocalDateTime.of(dDate, dTime);
           rDateTime = LocalDateTime.of(rDate, rTime);
           //check return date time is after departDT date time
           valid = rDateTime.isAfter(dDateTime);
       }
       
      //check that home time is after return time
      if (valid)
      {
          valid = homeTime.isAfter(rTime);
      }
      
      //Prior to returning valid set all vars to hold validated input
      if (valid)
      {
        tripDays = ChronoUnit.DAYS.between(dDate,rDate);
        tripDays++;//add 1 to mean 1 day for to count the first day of trip
        departDT = dDateTime;
        returnDT = rDateTime;
        hTime = homeTime;
        
        //set depart meal booleans
        if (dTime.isBefore(LocalTime.of(07,00)))
        {
            breakfastD_Day = true;
        }
        if(dTime.isBefore(LocalTime.of(12,00)))
        {
            lunchD_Day = true;
        }
        if(dTime.isBefore(LocalTime.of(18, 00)))
        {
           dinnerD_Day = true;   
        }
        
        //set return meal booleans
        if (rTime.isAfter(LocalTime.of(8,00)))
        {
            breakfastR_Day = true;
        }
        if(rTime.isAfter(LocalTime.of(13,00)))
        {
            lunchR_Day = true;
        }
        if(homeTime.isAfter(LocalTime.of(19, 00)))
        {
           dinnerR_Day = true;   
        }
        
        
        //populate traveldates array
        //travelDates.add(dDate);
        int index = 0;
        while(index < (tripDays) )
        {
            travelDates.add(dDate.plusDays(index));
            index++;
        }
        
        
        //DONT DELETE ROBERT WILL DELETE
//        for (int i = 0; i < travelDates.size(); i++)
//        {
//            System.out.println(travelDates.get(i).toString());
//        }
//        System.out.println(tripDays);
        
      }

      //All date time information should now be validated chronologically
        return valid;
        
    }//end dateTimeValid
        
    
    //temp method to test data
    void writeToCommand()
    {
        System.out.println("Name: " + name.getValueSafe());
        System.out.println("Today Days of Trip: " + tripDays);
        System.out.println("DepartDate/Time: " + departDT);
        System.out.println("DepartTime: "+departTime.getConcatTime() + " Military Equiv: " + departTime.getMilitaryTime() );
        System.out.println("ReturnDate/Time: " + returnDT);
        System.out.println("ReturnTime: "+returnTime.getConcatTime() + " Military Equiv: " + returnTime.getMilitaryTime() );
        System.out.println("Home Time: " + hTime);
        System.out.println("Depart Meals breakfast, lunch, dinner: " + breakfastD_Day + lunchD_Day +dinnerD_Day);
        System.out.println("Return Meals breakfast, lunch, dinner: " + breakfastR_Day + lunchR_Day +dinnerR_Day);
        System.out.println("Cab Fees: "+totalCabFare);
        System.out.println("Peronal Miles: "+totalPersonalMiles);
        System.out.println("Rental Car: "+ totalRentalFees);
        System.out.println("Parking cost: "+ totalParkingFees);
        System.out.println("Airfare: " + valueAirfare);
        System.out.println("Hotel costs: "+totalHotelFee);
        System.out.println("Seminar costs: "+ totalSeminarFee);
        System.out.println("DISPLAY THE ARRAYS BELOW");
        System.out.println("Travel Dates Array");
        for (int i = 0; i < travelDates.size(); i++)
        {
            System.out.println(travelDates.get(i).toString());
        }
        System.out.println("Note day zero is the index postion and represents depart day or day 1 of trip");
        System.out.println("Breakfast Array");
        for (int i = 0; i < breakfastArray.size(); i++)
        {
            System.out.println("Day " + i + ": " + breakfastArray.get(i).toString());
        }
        
        System.out.println("Lunch Array");
        for (int i = 0; i < lunchArray.size(); i++)
        {
            System.out.println("Day " + i + ": " + lunchArray.get(i).toString());
        }
        
        System.out.println("Dinner Array");
        for (int i = 0; i < dinnerArray.size(); i++)
        {
            System.out.println("Day " + i + ": " +dinnerArray.get(i).toString());
        }
    }

    
}//end class
