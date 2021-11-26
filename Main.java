package application;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {
	// store any command-line arguments that were entered.
	// NOTE: this.getParameters().getRaw() will get these also
	private List<String> args;

	private static final int WINDOW_WIDTH = 1000;
	private static final int WINDOW_HEIGHT = 800;
	private static final int CIRCLE_RADIUS = 30;
	private static final String APP_TITLE = "Pawstagram";

	@Override
	public void start(Stage primaryStage) throws Exception {
		// save args example
		args = this.getParameters().getRaw();

		// Create a vertical box with Hello labels for each args
		VBox vbox = new VBox();
		
		// Creates a canvas that can draw shapes and text
		Canvas canvas = new Canvas(WINDOW_WIDTH, WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		// Write some text
		// Text is filled with the fill color
		gc.setFill(Color.BLUE);
		gc.setFont(new Font(40));
		gc.fillText("Pawstagram", 425, 40);
		gc.setFill(Color.ORANGE);
		gc.fillText("Pawstagram", 427, 42);

		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		gc.strokeLine(0, 65, WINDOW_WIDTH, 65);
		gc.strokeLine(0, 66, WINDOW_WIDTH, 66);
		
		gc.setFill(Color.BLACK);
		gc.setFont(new Font(20));
		gc.fillText("McKenna's Social Network", 390, 90);
		gc.strokeLine(0, 105, WINDOW_WIDTH, 105);
		gc.strokeLine(0, 106, WINDOW_WIDTH, 106);
		
		// Main box:
		gc.strokeLine(50, 115, 700, 115); // Top of box
		gc.strokeLine(50, 115, 50, 615);  // Left of box
		gc.strokeLine(700, 115, 700, 615);  // Right of box
		gc.strokeLine(50, 615, 700, 615); // Bottom of box
		
		gc.strokeRect(51, 116, 651, 501);
		
		// Side box:
		gc.strokeLine(710, 115, 985, 115); // Top of box
		gc.strokeLine(710, 115, 710, 715);  // Left of box
		gc.strokeLine(985, 115, 985, 715);  // Right of box
		gc.strokeLine(710, 715, 985, 715); // Bottom of box
		
		// Bottom box:
		gc.strokeLine(100, 700, 670, 700); // Top of box
		gc.strokeLine(100, 700, 100, 750);  // Left of box
		gc.strokeLine(670, 700, 670, 750);  // Right of box

		// Bottom:
		gc.strokeLine(0, 750, WINDOW_WIDTH, 750); 
		gc.setFont(new Font(15));
		gc.fillText("An A10 Production * GitHub Repo", 390, 780);
		
		gc.drawImage(new Image("https://uploads.dailydot.com/2018/03/Dog-Phone.jpg",200,180,true,false), 750, 600);
		
		// Draw a few circles
		gc.setFill(Color.BLACK);
		// The circles draw from the top left, so to center them, subtract the radius from each coordinate
		drawCircle(300,300,gc,"Laura");
		drawCircle(400,400,gc,"McKenna");
		drawCircle(200,400,gc,"Ross");
		drawCircle(500,250,gc,"Daniel");
		drawCircle(200,500,gc, "Joy(Dog)");
		drawCircle(500,400,gc,"Board Game Fans");
		drawCircle(450,500,gc,"Fun Food Friends");

		vbox.getChildren().add(canvas);

		// Main layout is Border Pane example (top,left,center,right,bottom)
		BorderPane root = new BorderPane();

		// Add the vertical box to the center of the root pane
		root.setCenter(vbox);
		Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

		// Add the stuff and set the primary stage
		primaryStage.setTitle(APP_TITLE);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
	
	/**
	 * Draws a circle at the specified coordinates
	 * */
	private void drawCircle(int x, int y, GraphicsContext gc, String label) {
		gc.strokeOval(x-CIRCLE_RADIUS, y-CIRCLE_RADIUS, CIRCLE_RADIUS*2 + (label.length() * 3), CIRCLE_RADIUS*2);	
		gc.setFont(new Font(10));
		gc.fillText(label, x - (label.length()), y);
		
	}
	
}

	