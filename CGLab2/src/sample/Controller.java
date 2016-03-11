package sample;

import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.apache.commons.imaging.color.ColorCieLuv;
import org.apache.commons.imaging.color.ColorConversions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

public class Controller {

    public static final String IMAGE_URL = "xp.jpg";

    private int rBefore = 0;
    private int gBefore = 0;
    private int bBefore = 0;
    private int rAfter = 0;
    private int gAfter = 0;
    private int bAfter = 0;

    @FXML
    private Slider bAfterSlider;

    @FXML
    private Slider rBeforeSlider;

    @FXML
    private ToggleButton hueToggle;

    @FXML
    private ImageView imageBefore;

    @FXML
    private Button transformButton;

    @FXML
    private Slider gAfterSlider;

    @FXML
    private ImageView imageAfter;

    @FXML
    private Slider gBeforeSlider;

    @FXML
    private TextField faultTextField;

    @FXML
    private ColorPicker colorPicker;

    @FXML
    private Slider bBeforeSlider;

    @FXML
    private Slider rAfterSlider;

    @FXML
    private Rectangle rectAfter;

    @FXML
    private Rectangle rectBefore;

    @FXML
    private ProgressBar progressBar;


    private BufferedImage bufferedImageBefore;
    private BufferedImage bufferedImageAfter;

    @FXML
    void transformImage(ActionEvent event) throws InterruptedException {
        progressBar.setStyle("-fx-accent: #0093ff;");

        try {
            bufferedImageAfter = ImageIO.read(new File(IMAGE_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int fault;
        try {
            fault = Integer.parseInt(faultTextField.getText());
            if(fault < 0 || fault > 100) {
                throw new IllegalArgumentException("Fault must be between 0 and 100.");
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.show();
            return;
        }

        int width = bufferedImageAfter.getWidth();
        int height = bufferedImageAfter.getHeight();


        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for(int x = 0; x < width; x++) {
                    for(int y = 0; y < height; y++) {
                        updateProgress((double) x, (double) width);

                        ColorCieLuv actColor =
                                ColorConversions.convertXYZtoCIELuv(ColorConversions.convertRGBtoXYZ(bufferedImageAfter.getRGB(x, y)));
                        java.awt.Color colorRGBBefore = new java.awt.Color(rBefore, gBefore, bBefore);
                        ColorCieLuv colorBefore =
                                ColorConversions.convertXYZtoCIELuv(ColorConversions.convertRGBtoXYZ(colorRGBBefore.getRGB()));
                        if(Math.sqrt(Math.pow((actColor.u - colorBefore.u), 2) + Math.pow((actColor.v - colorBefore.v), 2)) < fault) {
                            ColorCieLuv colorAfter = ColorConversions.convertXYZtoCIELuv(ColorConversions.convertRGBtoXYZ(new java.awt.Color(rAfter, gAfter, bAfter).getRGB()));
                            ColorCieLuv finalColorLuv = new ColorCieLuv(actColor.L, colorAfter.u, colorAfter.v);
                            java.awt.Color finalColor = new java.awt.Color(ColorConversions.convertXYZtoRGB(ColorConversions.convertCIELuvtoXYZ(finalColorLuv)));
                            bufferedImageAfter.setRGB(x, y, finalColor.getRGB());
                        }
                    }
                }
                return null;
            }
        };

        progressBar.progressProperty().bind(task.progressProperty());

        progressBar.progressProperty().addListener(observable -> {
            if (progressBar.getProgress() >= 1 - 0.005) {
                progressBar.setStyle("-fx-accent: forestgreen;");
                Image imageFx = SwingFXUtils.toFXImage(bufferedImageAfter, null);
                imageAfter.setImage(imageFx);
            }
        });

        Thread thread = new Thread(task);
        thread.start();
    }

    @FXML
    void setSliders(ActionEvent event) {
        Color color = colorPicker.getValue();
        rBeforeSlider.setValue(color.getRed() * 255);
        gBeforeSlider.setValue(color.getGreen() * 255);
        bBeforeSlider.setValue(color.getBlue() * 255);
    }

    private void setSliderListeners() {
        ChangeListener<Number> changeBeforeValuesListener = (observable, oldValue, newValue) -> {
            rBefore = (int) rBeforeSlider.getValue();
            gBefore = (int) gBeforeSlider.getValue();
            bBefore = (int) bBeforeSlider.getValue();
            rectBefore.setFill(Color.rgb(rBefore, gBefore, bBefore));
        };

        ChangeListener<Number> changeAfterValuesListener = (observable, oldValue, newValue) -> {
            rAfter = (int) rAfterSlider.getValue();
            gAfter = (int) gAfterSlider.getValue();
            bAfter = (int) bAfterSlider.getValue();
            rectAfter.setFill(Color.rgb(rAfter, gAfter, bAfter));
        };

        rBeforeSlider.valueProperty().addListener(changeBeforeValuesListener);
        gBeforeSlider.valueProperty().addListener(changeBeforeValuesListener);
        bBeforeSlider.valueProperty().addListener(changeBeforeValuesListener);

        rAfterSlider.valueProperty().addListener(changeAfterValuesListener);
        gAfterSlider.valueProperty().addListener(changeAfterValuesListener);
        bAfterSlider.valueProperty().addListener(changeAfterValuesListener);
    }

    @FXML
    void initialize() {
        try {
            bufferedImageBefore = ImageIO.read(new File(IMAGE_URL));
            Image imageFx = SwingFXUtils.toFXImage(bufferedImageBefore, null);
            imageAfter.setImage(imageFx);
            imageBefore.setImage(imageFx);

            setSliderListeners();

            imageBefore.setOnMouseClicked(event -> {
                Point point = MouseInfo.getPointerInfo().getLocation();
                try {
                    java.awt.Color color = new Robot().getPixelColor(point.x, point.y);
                    rBeforeSlider.setValue(color.getRed());
                    gBeforeSlider.setValue(color.getGreen());
                    bBeforeSlider.setValue(color.getBlue());
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
