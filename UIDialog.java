package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Window;

public class UIDialog {
	
	public UIDialog() {
		// Default no argument constructor
	}

    public void showAlerts(Alert.AlertType type, Window owner, String header, String headerMsg) {
        Alert alert = new Alert(type);
        alert.setTitle(header);
        alert.setHeaderText(null);
        alert.setContentText(headerMsg);
        alert.initOwner(owner);
        alert.show();
    }
    
    public void showExportCloseDialog(Window owner) {
    	
    	ButtonType bChoice1 = new ButtonType("Save", ButtonData.OK_DONE);
    	ButtonType bChoice2 = new ButtonType("Exit without Save", ButtonData.FINISH);
    	
    	Dialog<String> buttonDialog = new Dialog<String>();
    	
    	buttonDialog.setTitle("File processed successfully!");
    	buttonDialog.setHeaderText(null);
    	buttonDialog.setContentText("Would you like to save the file, or exit without saving?");
    	buttonDialog.initOwner(owner);
    	buttonDialog.getDialogPane().getButtonTypes().add(bChoice2);
    	buttonDialog.getDialogPane().getButtonTypes().add(bChoice1);
    	buttonDialog.setResultConverter(dialogButton -> {
    		  if (dialogButton == bChoice1) {
    		   return "SAVE";
    		  }
    		  if (dialogButton == bChoice2) {
       		   return "EXIT";
       		  }
    		  return null;
    		 });
    	
    	buttonDialog.show();
    	
    	Button saveButton = (Button) buttonDialog.getDialogPane().lookupButton(bChoice1);
    	Button exitButton = (Button) buttonDialog.getDialogPane().lookupButton(bChoice2);
    	
    	saveButton.setOnAction(new EventHandler<ActionEvent>() {
    		
			@Override
			public void handle(ActionEvent arg0) {
				if (buttonDialog.getResult() == "SAVE") {
					//TODO: Save the file. 
					System.out.println("File saved.");
				}
				else if (buttonDialog.getResult().equals("EXIT")) {
					System.out.println("exit");
				}
			}
    	});
    	
    }
    
    public String showImportExportBox(Window owner, Boolean isImport, NetworkManager network) {
    	
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
					showAlerts(Alert.AlertType.ERROR, dialog.getOwner(),"Information Required.","Information required to continue: you need to enter a file path.");
				}
				
				if (!dialog.getResult().isEmpty()) {
					
					//@Mckenna:
					//TODO: Gracefully catch ImportFormatException and display message to user
					// if file path is invalid or unable to be interpreted by system
					// then load or export file based on user selection
					
					// if (importFormatException):
					// showAlerts(Alert.AlertType.ERROR, dialog.getOwner(),"Error","Error");
					
					String filepath = dialog.getResult();
					
					// TODO: Test the below for import:
					if (isImport) {
						try {
							network.importGraph(dialog.getResult());
						} catch (IOException e) {
							showAlerts(Alert.AlertType.CONFIRMATION, dialog.getOwner(),"Invalid File Input","The specified file, " + filepath + " was not found.");
						} catch (ImportFormatException e) {
							showAlerts(Alert.AlertType.CONFIRMATION, dialog.getOwner(),"Invalid File Format","The specified file, " + filepath + " had an invalid file format.");
						}
						//
					}
					// TODO: Test the below for export:
					if (!isImport) { // if it's not import, it's export
						network.exportGraph(dialog.getResult());
						showExportCloseDialog(dialog.getOwner());
					}

				}
			}
    	});

    	dialog.showAndWait();
    	return dialog.getContentText();
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
