package abcode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;


public class PrimaryController {
    Font currentFont;
    Double fontSize;

    @FXML
    private Toggle times, arial, open;

    @FXML
    private Toggle small, medium, big;

    @FXML
    private ToggleGroup fontGroup;

    @FXML
    private ToggleGroup sizeGroup;

    @FXML
    private MenuBar menuBar;

    @FXML
    private TextArea textArea;

    @FXML
    void closeApp(ActionEvent event) {

        if (textArea.getText().toString().isEmpty()){
            App.close();
        } else{
            showAlertBox(event);
        }

    }

    public void initialize(){
        textArea.setFont(Font.font("Verdana", 14));
        textArea.setWrapText(true);
        currentFont = Font.font("Verdana");
        fontSize = 14.0;
    }


    /**
     * When you press the Open menu Item, a FileChooser gets displayed and you can pick the txt file that you want to
     * display inside your TextArea. loading of the data is done via Data.java class's loadData method and a handy stream
     *
     * @param event registered when the user presses the Open button.
     */
    @FXML
    void openFile(ActionEvent event) {
        Data data = new Data();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File selectedFile = fileChooser.showOpenDialog(App.stage);

        if (selectedFile != null){
            try{
                data.loadData(selectedFile);
            }catch (IOException e){
                System.out.println("Cannot Open File");
                e.printStackTrace();
            }
        }

        //using java streams to load content inside the TextArea
        textArea.appendText(data.text.stream()
                . map(e -> e.toString())
                .collect(Collectors.joining(" "))
        );
    }

    @FXML
    void saveFile(ActionEvent event) {
        Data data = new Data();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Specify location and name");

        File fileName = fileChooser.showSaveDialog(App.stage);
        if (fileName != null){
            try{
                data.saveData(textArea.getText(), fileName);
                System.out.println(textArea);

            }catch (IOException e){
                System.out.println("could not save the file sorry");
            }
        }
    }


    @FXML
    void setFont(ActionEvent event) {
        Toggle actualFont = fontGroup.getSelectedToggle();

        if (actualFont.getToggleGroup().getSelectedToggle().equals(arial)){
            textArea.setFont(Font.font("Serif", fontSize));
            currentFont = Font.font("Serif");
        } else if(actualFont.getToggleGroup().getSelectedToggle().equals(times)){
            textArea.setFont(Font.font("Verdana", fontSize));
            currentFont = Font.font("Verdana");
        } else{
            textArea.setFont(Font.font("Times", fontSize));
            currentFont = Font.font("Times");
        }
    }

    @FXML
    void setFontSize(ActionEvent event) {
        Toggle actualFontSize = sizeGroup.getSelectedToggle();

        if (actualFontSize.getToggleGroup().getSelectedToggle().equals(small)){
            textArea.setFont(Font.font(currentFont.getName(), 12));
            fontSize = 12.0;
        }else if(actualFontSize.getToggleGroup().getSelectedToggle().equals(medium)){
            textArea.setFont(Font.font(currentFont.getName(), 14));
            fontSize = 14.0;
        } else {
            textArea.setFont(Font.font(currentFont.getName(), 22));
            fontSize = 22.0;
        }
    }

    public void showAlertBox(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Save or Not?");
        alert.setHeaderText("Are you sure you want to exit without saving?");
        alert.setContentText("Choose your option.");

        ButtonType buttonTypeOne = new ButtonType("Save");
        ButtonType buttonTypeTwo = new ButtonType("No saving");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            saveFile(event);
        } else if (result.get() == buttonTypeTwo) {
            App.close();
        } else {
            alert.close();
        }
    }

}