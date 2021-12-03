package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Main extends Application {
	
	private static final String APP_TITLE = "Friend Maker";
	protected ArrayList<Friend> friendList;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	this.friendList = new ArrayList<Friend>();
    	
    	GridPane gridPane = newGridPane();
    	
    	addLabels(gridPane);
    	
        Scene scene = new Scene(gridPane, 1000, 500);

        primaryStage.setScene(scene);
        
        primaryStage.setTitle(APP_TITLE);
        primaryStage.setFullScreen(true);
        
        primaryStage.show();
    }
    
    private GridPane newGridPane() {
    	
    	GridPane gridPane = new GridPane();
    	
    	gridPane.setBackground(new Background (new BackgroundFill(Color.ALICEBLUE, null, null)));
    	
    	gridPane.setAlignment(Pos.TOP_LEFT);
    	
    	gridPane.setPadding(new Insets(10, 10, 10, 10));
    	
    	gridPane.setHgap(10);
    	gridPane.setVgap(20);
    	
    	return gridPane;
    	
    }
    
    private void addLabels(GridPane gridPane) {
    	
    	
    	Label headerLabel = new Label(APP_TITLE);
    	headerLabel.setFont(Font.font("Courier New", FontWeight.EXTRA_BOLD, 22));
    	
    	Label subtext = new Label("Let's make friends");
    	subtext.setFont(Font.font("Courier New", FontWeight.EXTRA_LIGHT, 18));
    	
    	
    	Label name = new Label("Friend Name: ");
    	TextField nameField = new TextField();
    	Label cUserLabel = new Label("Central User? ");
    	CheckBox centralCheckBox = new CheckBox();
    	Button submitButton = new Button("Make me a friend");
    	
    	gridPane.add(headerLabel, 0, 0);
    	
    	gridPane.add(subtext, 0, 1);
    	
    	gridPane.add(name, 0, 2);
    	gridPane.add(nameField, 1, 2);
    	gridPane.add(cUserLabel, 2, 2);
    	gridPane.add(centralCheckBox, 3, 2);
    	gridPane.add(submitButton, 4, 2);
    	
    	submitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if (nameField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Friend Name Required.", "Information required to continue: Your friend needs a name.");
				
				}
				
				if ((!nameField.getText().isEmpty())&&(getFriendWithName(nameField.getText()) != null)) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "No duplicate friends allowed.", "We already have a friend that's named " + nameField.getText() + ". Find a new friend.");
				}
				
				if ((!nameField.getText().isEmpty())&&(getFriendWithName(nameField.getText()) == null)) {
					Friend friend;
					String friendName = nameField.getText();
					
					showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Friend Created!", 
							"Success! Your new friend " + friendName + " has been created.");
					
					if (centralCheckBox.isSelected()) {
						friend = new Friend(friendName, true);
					}
					else {
						friend = new Friend(friendName, false);
					}
					
					friendList.add(friend);
					friend.addToGridPane(gridPane);
				}
    		
    	};
    	})  ;	
    	
    	
    	Label remove = new Label("Remove a friend: ");
    	TextField removeField = new TextField();
    	Button removeButton = new Button("Remove my friend>:(");
    	
    	gridPane.add(remove, 0, 3);
    	gridPane.add(removeField, 1, 3);
    	gridPane.add(removeButton, 2, 3);
    	
    	removeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if (removeField.getText().isEmpty()) {
					showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Friend Name Required.", "Information required to continue: you need to enter a name.");
				
				}
				
				if (!removeField.getText().isEmpty()) {
					String removeName = removeField.getText();
					if (getFriendWithName(removeName) == null) {
						
						
						showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "No Friend Found!", 
								"No friend found with name " + removeName + ". Please enter a friend that exists.");
						
					}
				}
				
				if (!removeField.getText().isEmpty()) {
					String removeName = removeField.getText();
					Friend removeFriend = getFriendWithName(removeName);
					if (removeFriend != null) {

						showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "Success! Friend deleted.", 
								"Guess you don't like " + removeName + " very much. They have been removed.");
						friendList.remove(removeFriend);
						removeFriend.removeFromGridPane(gridPane);
						
					}
				}
    		
    	};
    	})  ;
    	
    	
    	
    }
    
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText(null);
        alert.initOwner(owner);
        alert.show();
    }

    
    private Friend getFriendWithName(String friend) {
    	
    	for (Friend f : this.friendList) {
    		if (f.getName().equals(friend)) { return f;}
    	}
    	
    	return null;
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}


	