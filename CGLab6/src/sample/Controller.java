package sample;

import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Controller {

    public File imageFile;
    private BufferedImage bufferedImageBefore;
    private BufferedImage bufferedImageAfter;

    @FXML
    private Button button;

    @FXML
    private TextField paramField;

    @FXML
    private ImageView imageBefore;

    @FXML
    private ImageView imageAfter;

    @FXML
    private ChoiceBox<String> processChoiceBox;

    @FXML
    private Label paramLabel;

    @FXML
    private ChoiceBox<String> methodChoiceBox;

    @FXML
    public void initialize() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files", "*.jpg", "*.png", "*.bmp"));
        fileChooser.setTitle("Open image");
        imageFile = fileChooser.showOpenDialog(new Stage());

        try {
            bufferedImageBefore = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image imageFx = SwingFXUtils.toFXImage(bufferedImageBefore, null);
        imageAfter.setImage(imageFx);
        imageBefore.setImage(imageFx);


        processChoiceBox.setItems(FXCollections.observableArrayList("Filter", "Morphological process"));
        processChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() == 0) {
                methodChoiceBox.setItems(FXCollections.observableArrayList("Median", "Gaussian"));
                paramField.setVisible(true);
                paramLabel.setVisible(true);
            }
            else if(newValue.intValue() == 1) {
                methodChoiceBox.setItems(FXCollections.observableArrayList("Erosion", "Dilation",
                        "Opening", "Closing"));
                paramField.setVisible(false);
                paramLabel.setVisible(false);
            }
        });
    }

    public void transformImage(ActionEvent actionEvent) {
        try {
            bufferedImageAfter = ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int selectedProcess = processChoiceBox.getSelectionModel().getSelectedIndex();
        int selectedMethod = methodChoiceBox.getSelectionModel().getSelectedIndex();

        if(selectedProcess == 0) {
            int param;
            try {
                param = Integer.parseInt(paramField.getText());
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Parameter must be a number");
                alert.show();
                return;
            }
            List<Integer> affordableParams;
            switch(selectedMethod) {
                case 0:
                    affordableParams = Arrays.asList(0, 1, 2, 4, 12);
                    if(!affordableParams.contains(param)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Parameter must be in {0, 1, 2, 4, 12}");
                        alert.show();
                        return;
                    }
                    bufferedImageAfter = ImageProcessor.filter(bufferedImageBefore, FilterType.MEDIAN, param);
                    break;
                case 1:
                    affordableParams = Arrays.asList(1, 2, 3, 4);
                    if(!affordableParams.contains(param)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Parameter must be in {1, 2, 3, 4}");
                        alert.show();
                        return;
                    }
                    bufferedImageAfter = ImageProcessor.filter(bufferedImageBefore, FilterType.GAUSSIAN, param);
                    break;
            }
        }
        else if(selectedProcess == 1) {
            switch(selectedMethod) {
                case 0:
                    bufferedImageAfter = ImageProcessor.erose(bufferedImageBefore);
                    break;
                case 1:
                    bufferedImageAfter = ImageProcessor.dilate(bufferedImageBefore);
                    break;
                case 2:
                    bufferedImageAfter = ImageProcessor.dilate(ImageProcessor.erose(bufferedImageBefore));
                    break;
                case 3:
                    bufferedImageAfter = ImageProcessor.erose(ImageProcessor.dilate(bufferedImageBefore));
                    break;
            }
        }

        Image imageFx = SwingFXUtils.toFXImage(bufferedImageAfter, null);
        imageAfter.setImage(imageFx);
    }
}
