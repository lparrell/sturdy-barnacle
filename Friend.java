package application;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
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
import javafx.scene.image.ImageView;
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


public class Friend {
	
	final static String IMAGE_FILE_NAME = "File:images/StickManFront.png";
	
	String name;
	Label label;
	Image image;
	ImageView imageView;
	int column;
	int row;
	

	public Friend(String name, Boolean centralUser) {
		this.name = name;
		this.label = new Label(name);
		
		if (centralUser != true){
			this.column = (int) (15 + (10 * Math.random()));
			this.row = (int) (15 + (10 * Math.random()));
		}
		
		
		else {
			this.column = 15;
			this.row = 15;
		}
		
		this.image = new Image(IMAGE_FILE_NAME);
		this.imageView = new ImageView(this.image);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addToGridPane(GridPane gridPane) {
		
		gridPane.add(this.label, column, row); 
		gridPane.add(imageView, column, row + 1);
		
	}
	
	public void removeFromGridPane(GridPane gridPane) {
		
		gridPane.getChildren().remove(this.label);
		gridPane.getChildren().remove(this.imageView);
		
	}

}
