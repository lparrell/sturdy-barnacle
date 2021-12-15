package application;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class UIDialog {
	
	//static Boolean canClose = false;
	
	public UIDialog() {
		// Default no argument constructor
	}

    public void showAlerts(Alert.AlertType type, Window owner, String header, String headerMsg, Boolean shouldClose) {
        Alert alert = new Alert(type);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(headerMsg);
        alert.initOwner(owner);
        alert.show();
        
        if (shouldClose) {
	        Button okButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
	        
	        okButton.setOnAction(new EventHandler<ActionEvent>() {
	
				@Override
				public void handle(ActionEvent arg0) {
					Platform.exit();				
				}
	        	
	        });
        }
        
    }
    
    public void showCloseAlert(Alert.AlertType type, Window owner, String header, String headerMsg) {
        Alert alert = new Alert(type);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(headerMsg);
        alert.initOwner(owner);
        alert.show();
        

        
    }
    
    public Boolean showExportCloseDialog(Window owner, NetworkManager network, WindowEvent event) {
    	
    	event.consume();
    	
    	Dialog<String> buttonDialog = new Dialog<String>();
    	
	    ButtonType bChoice1 = new ButtonType("Save", ButtonData.OK_DONE);
	    ButtonType bChoice2 = new ButtonType("Exit without Save", ButtonData.FINISH);
	    
	    buttonDialog.setTitle("Exiting...");
	    buttonDialog.setHeaderText(null);
	    buttonDialog.setContentText("Would you like to save the file, or exit without saving?");
	    buttonDialog.initOwner(owner);
	    buttonDialog.getDialogPane().getButtonTypes().add(bChoice1);
	    buttonDialog.getDialogPane().getButtonTypes().add(bChoice2);
	    
	    buttonDialog.show();
    	
    	Button saveButton = (Button) buttonDialog.getDialogPane().lookupButton(bChoice1);
    	Button exitButton = (Button) buttonDialog.getDialogPane().lookupButton(bChoice2);
    	
    	saveButton.setOnAction(new EventHandler<ActionEvent>() {
    		
			@Override
			public void handle(ActionEvent arg0) {
				showImportExportBox(owner, false, network, true);
			}
    	});
    	
    	exitButton.setOnAction(new EventHandler<ActionEvent>() {
    		
			@Override
			public void handle(ActionEvent arg1) {
				buttonDialog.hide();
				Platform.exit();
			}
    	});
    	
    	return false;
    }
    
    public Boolean showImportExportBox(Window owner, Boolean isImport, NetworkManager network, Boolean shouldClose) {
    	
    	Boolean success = false;
    	
    	TextInputDialog dialog = new TextInputDialog();
    	
    	String text = "import";
    	if (!isImport) { text = "export"; }
    	
    	dialog.setHeaderText("Enter an " + text + " file path:");
    	dialog.initOwner(owner);
    	Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
    	
    	okButton.setOnAction(new EventHandler<ActionEvent>() {
    		
			@Override
			public void handle(ActionEvent arg0) {
				if (dialog.getResult().isEmpty()) {
					showAlerts(Alert.AlertType.ERROR, dialog.getOwner(),"Information Required.","Information required to continue: you need to enter a file path.", false);
				}
				
				if (!dialog.getResult().isEmpty()) {
					
					
					String filepath = dialog.getResult();

					if (isImport) {
						try {
							network.importGraph(dialog.getResult());
							showAlerts(Alert.AlertType.CONFIRMATION, dialog.getOwner(),"Success!","The specified file, " + filepath + ", was successfully imported.", false);
						} catch (IOException e) {
							showAlerts(Alert.AlertType.ERROR, dialog.getOwner(),"Invalid File Input","The specified file, " + filepath + ", was not found.", false);
						} catch (ImportFormatException e) {
							showAlerts(Alert.AlertType.ERROR, dialog.getOwner(),"Invalid File Format","The specified file, " + filepath + ", had an invalid file format.", false);
						}
						//
					}

					if (!isImport) { // if it's not import, it's export
						try {
							network.exportGraph(dialog.getResult());
							showAlerts(Alert.AlertType.CONFIRMATION, dialog.getOwner(),"Success!","The specified file, " + filepath + ", was successfully exported.", shouldClose);
						} catch (FileNotFoundException e) {
							showAlerts(Alert.AlertType.ERROR, dialog.getOwner(),"File not found","The specified file, " + filepath + ", was not found.", false);
						}
					}

				}
			}
    	});

    	dialog.showAndWait();
    	return success;
    }
	
    public String returnHelpText() {
    	
    	String helptext =
    			"TO ADD A USER: Toggle the 'Add' radio button and enter a name\n"
    			+ "TO ADD A FRIENDSHIP: Toggle the 'Add' radio button and enter in TWO names.\n"
    			+ "TO REMOVE A USER: Toggle the 'Remove' radio button and enter a name.\n"
    			+ "TO REMOVE A FRIENDSHIP: Toggle the 'Remove' radio button and enter in TWO names.";
    	
    	return helptext;

    }
}