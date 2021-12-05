/**
 * Course: 			CS400 - Fall 2021
 * Program:			Term Project - Social Network
 * Names: 			<Ross Volkmann, McKenna Stillings, Laura Parella>
 * Wisc Email: 		<rvolkmann@wisc.edu, mstillings@wisc.edu, lparella@wisc.edu>
 * Web Sources: 	<none>
 * Personal Help: 	<none>
 */

package application;

import java.util.List;

//CS400 Final Project
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	private List<String> args;
	
	private static final int WINDOW_WIDTH = 1024;
	private static final int WINDOW_HEIGHT = 600;
	private static final String APP_TITLE = "BadgerNet";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		args = this.getParameters().getRaw();//save the args

		//Values and Constants used for the layout
		int hGap = 10; //horizontal gap between columns
		int vGap = 6; //vertical gap between rows
		int inset = 10; //padding around outside of grid
		double width1 = (WINDOW_WIDTH / 4) * 3; //Width of column 0
		double width2 = WINDOW_WIDTH - width1 - hGap - 2*inset; //Width of column 1
		int smallMargin = 15;
		
		//Declare the top level controls and containers
		Label titleLabel = new Label("BadgerNet");
		Label subTitle = new Label("A Social Network");
		ScrollPane graphControl = new ScrollPane();
		VBox sideBar = new VBox();//new VBox();
		HBox bottomBox = new HBox();//new HBox();
		HBox footer = new HBox();
		
	
		//Add labels to HBoxes and set position
		Label footerLabel = new Label("An A10 Production");
		footer.getChildren().addAll(footerLabel);
		footer.setAlignment(Pos.CENTER);
		
		//Declare controls for the sideBar
		String labelModifier1 = "Add";
		Label sideLabel1 = new Label(labelModifier1+ " to Network");
		TextField user1Text = new TextField();
		TextField user2Text = new TextField();
		Label sideLabel2 = new Label("View friends of:");
		TextField userFocusText = new TextField();
		user1Text.setPromptText("person 1");
		user2Text.setPromptText("person 2");
		userFocusText.setPromptText("See friends of...");
		
		//Create the radio buttons and contain them in an HBox
		HBox radioButtons = new HBox();
		ToggleGroup selectorGroup = new ToggleGroup();
		RadioButton rb1 = new RadioButton("Add");
		rb1.setToggleGroup(selectorGroup);
		RadioButton rb2 = new RadioButton("Remove");
		rb2.setToggleGroup(selectorGroup);
		radioButtons.getChildren().addAll(rb1, rb2);
		radioButtons.setAlignment(Pos.CENTER);
		radioButtons.setSpacing(smallMargin);
		//Configure the sideBar
		sideBar.setAlignment(Pos.TOP_CENTER);
		sideBar.setSpacing(smallMargin);
		//Add controls to the sideBar
		sideBar.getChildren().addAll(sideLabel1, user1Text, user2Text, radioButtons, sideLabel2, userFocusText);
		
		//Declare controls for the bottomBox
		Button importButton = new Button("Import Network");
		Button exportButton = new Button("Export Network");
		Button clearButton = new Button("Clear Network");
		//Configure bottomBox
		bottomBox.setSpacing(55);
		bottomBox.setAlignment(Pos.CENTER);
		//Add controls to the bottomBox
		bottomBox.getChildren().addAll(importButton, exportButton,clearButton);
		
		//The root layout pane will be a grid
		GridPane grid = new GridPane(); 
		grid.setHgap(hGap);
		grid.setVgap(vGap);
		grid.setPadding(new Insets(inset,inset,inset,inset)); //JavaFX geometry import
		//grid.setGridLinesVisible(true); //TODO: remove later, used for debugging
		
		//Set the two main column widths -- revise this later if necessary
		grid.getColumnConstraints().add(new ColumnConstraints(width1));
		grid.getColumnConstraints().add(new ColumnConstraints(width2));
		
		//Assign Grid Positions -- reminder: setConstraints(control, col, row)
		GridPane.setConstraints(titleLabel, 0, 0);
		GridPane.setConstraints(subTitle, 0, 1);
		GridPane.setConstraints(graphControl, 0, 2);
		GridPane.setConstraints(sideBar,1, 2);
		GridPane.setConstraints(bottomBox, 0, 3);
		GridPane.setConstraints(footer, 0, 4);
		
		//Title Position
		GridPane.setColumnSpan(titleLabel, 2);
		GridPane.setHalignment(titleLabel, HPos.CENTER);
		//Subtitle Position
		GridPane.setColumnSpan(subTitle, 2);
		GridPane.setHalignment(subTitle, HPos.CENTER);
		//SideBox Position 
		GridPane.setRowSpan(sideBar, 2);
		GridPane.setHalignment(sideBar, HPos.CENTER);
		GridPane.setValignment(sideBar, VPos.CENTER);
		//Graph Position
		GridPane.setHalignment(graphControl, HPos.CENTER);
		GridPane.setValignment(graphControl, VPos.CENTER);
		//TODO
		//BottomBox Position
		GridPane.setHalignment(bottomBox, HPos.CENTER);
		//Footer Position
		GridPane.setColumnSpan(footer, 2);
		GridPane.setHalignment(footer, HPos.CENTER);
		
		//Configure the graphControl
		graphControl.setPrefHeight(600);
		graphControl.setPrefWidth(width1);
		Image image1 = new Image("images/SocialNetwork.png"); //placeholder
		ImageView iv1 = new ImageView(); //placeholder
		iv1.setImage(image1); //placeholder
		graphControl.setContent(iv1);
		
		//Add children to grid object
		grid.getChildren().addAll(titleLabel, subTitle, graphControl, sideBar, bottomBox, footer);

		//Declare the Scene
		Scene mainScene = new Scene(grid, WINDOW_WIDTH, WINDOW_HEIGHT);
		
		//Apply css ids and classes
		titleLabel.setId("titleLabel");
		subTitle.setId("subTitle");
		bottomBox.setId("bottomBox");
		footer.setId("footer");
		footerLabel.setId("footerLabel");
		
		
		//Apply css to the scene
		String css = this.getClass().getResource("badger.css").toExternalForm();
		mainScene.getStylesheets().add(css);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
