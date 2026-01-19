package summative;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;


public class PrimaryController {

    private Stage stage;
    private Image originalImage; // Use this to keep track of the original image

    @FXML
    private MenuItem brightness;

    @FXML
    private MenuItem bulge;

    @FXML
    private MenuItem colorOverlay;

    @FXML
    private MenuItem grayScale;

    @FXML
    private MenuItem horizontalFlip;

    @FXML
    private ImageView imageView;

    @FXML
    private MenuItem invertColor;

    @FXML
    private MenuItem openImage;

    @FXML
    private MenuItem restoreOriginal;

    @FXML
    private MenuItem saveImage;

    @FXML
    private MenuItem sepiaTone;

    @FXML
    private MenuItem verticalFlip;

    @FXML
    private MenuItem rotateClockwise;

    @FXML
    private MenuItem rotateCounterClock;

    @FXML
    private MenuItem vignette;

    @FXML
    private MenuItem pixelation;

    @FXML
    private MenuItem exit;

    @FXML
    void onOpenImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"));

        try {
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                Image image = new Image(file.toURI().toString());
                originalImage = image;
                imageView.setImage(image);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Image Load Failed");
            alert.setContentText("There was a problem opening your image");
            alert.showAndWait();
        }
    }

    @FXML
    public void onSaveImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
        File file = fileChooser.showSaveDialog(imageView.getScene().getWindow());

        if (file != null) {
            WritableImage writableImage = imageView.snapshot(null, null);
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Image Save Failed");
                alert.setContentText("There was a problem saving your image");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void onRestoreOriginal(ActionEvent event) {
        Image newImage = originalImage;
        imageView.setImage(newImage);
    }

    @FXML
    void onExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onHorizontalFlip(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                writer.setColor(width - i - 1, j, reader.getColor(i, j));
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onVerticalFlip(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                writer.setColor(i, height - j - 1, reader.getColor(i, j));
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onRotateClockwise(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        int cx = width / 2;
        int cy = height / 2;
        double degree = Math.toRadians(90);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double dx = i - cx;
                double dy = j - cy;
                int xPrime = (int) Math.round(dx * Math.cos(degree) - dy * Math.sin(degree) + cx);
                int yPrime = (int) Math.round(dx * Math.sin(degree) + dy * Math.cos(degree) + cy);
                if (xPrime > 0 && xPrime < width && yPrime < height && yPrime > 0) {
                    writer.setColor(xPrime, yPrime, reader.getColor(i, j));
                }
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onRotateCounterClockwise(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        int cx = width / 2;
        int cy = height / 2;
        double degree = Math.toRadians(-90);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double dx = i - cx;
                double dy = j - cy;
                int xPrime = (int) Math.round(dx * Math.cos(degree) - dy * Math.sin(degree) + cx);
                int yPrime = (int) Math.round(dx * Math.sin(degree) + dy * Math.cos(degree) + cy);
                if (xPrime > 0 && xPrime < width && yPrime < height && yPrime > 0) {
                    writer.setColor(xPrime, yPrime, reader.getColor(i, j));
                }
            }
        }
        imageView.setImage(writableImage);
    }

    /*
     * Accessing a pixels colors
     * 
     * Color color = reader.getColor(x, y);
     * double red = color.getRed();
     * double green = color.getGreen();
     * double blue = color.getBlue();
     */

    /*
     * Modifying a pixels colors
     * 
     * Color newColor = new Color(1.0 - red, 1.0 - green, 1.0 - blue,
     * color.getOpacity());
     */

    // DO NOT REMOVE THIS METHOD!

    @FXML
    void onGreyScale(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double red = color.getRed() * 0.21;
                double green = color.getGreen() * 0.71;
                double blue = color.getBlue() * 0.07;

                Color greyScaleColor = new Color(red + blue + green, red + blue + green, red + blue + green,
                        color.getOpacity());
                writer.setColor(i, j, greyScaleColor);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onSepiaTone(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();

                Color sepiaToneColor = new Color(Math.min(0.393 * red + 0.769 * green + 0.189, 1),
                        Math.min(0.349 * red + 0.686 * green + 0.168 * blue, 1),
                        Math.min(0.272 * red + 0.534 * green + 0.131 * blue, 1),
                        color.getOpacity());
                writer.setColor(i, j, sepiaToneColor);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onInvertColor(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();

                Color greyScaleColor = new Color(1.0 - red, 1.0 - green, 1.0 - blue,
                        color.getOpacity());
                writer.setColor(i, j, greyScaleColor);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onBrightness(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = reader.getColor(i, j);
                double red = Math.min(color.getRed() + 0.2, 1);
                double green = Math.min(color.getGreen() + 0.2, 1);
                double blue = Math.min(color.getBlue() + 0.2, 1);

                Color greyScaleColor = new Color(red, green, blue,
                        color.getOpacity());
                writer.setColor(i, j, greyScaleColor);
            }
        }
        imageView.setImage(writableImage);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void onBuldge(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        int cx = width / 2;
        int cy = height / 2;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double dx = i - cx;
                double dy = j - cy;
                double radius = Math.sqrt(dx * dx + dy * dy);
                double degree = Math.atan2(dy, dx);
                double rPrime = Math.pow(radius, 1.6) / 30;

                int xPrime = (int) (cx + rPrime * Math.cos(degree));
                int yPrime = (int) (cy + rPrime * Math.sin(degree));

                if (xPrime > 0 && xPrime < width && yPrime < height && yPrime > 0) {
                    writer.setColor(i, j, reader.getColor(xPrime, yPrime));
                }
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onColorOverlay(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                Color color = reader.getColor(i, j);
                double red = color.getRed();
                double green = color.getGreen();
                double blue = color.getBlue();
                Color overlay = new Color(red * 0.80, green * 0.21, blue * 0.07,
                        0.50);
                Color newOverlay = color.interpolate(overlay, 0.50);
                writer.setColor(i, j, newOverlay);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onVignette(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        int cx = width / 2;
        int cy = height / 2;

        double maxDistance = Math.sqrt(cx * cx + cy * cy);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                double distance = Math.sqrt(Math.pow(i - cx, 2) + Math.pow(j - cy, 2));
                double bright = Math.clamp(1 - distance / maxDistance, 0.3, 1);
                Color color = reader.getColor(i, j);
                Color derive = color.deriveColor(0, 0, 1, bright);
                writer.setColor(i, j, derive);
            }
        }
        imageView.setImage(writableImage);
    }

    @FXML
    void onPixelation(ActionEvent event) {
        int width = (int) imageView.getImage().getWidth();
        int height = (int) imageView.getImage().getHeight();

        WritableImage writableImage = new WritableImage(width, height);
        PixelReader reader = imageView.getImage().getPixelReader();
        PixelWriter writer = writableImage.getPixelWriter();

        int blockSize = 4;

        for (int x = 0; x < width; x += blockSize) {
            for (int y = 0; y < height; y += blockSize) {
                Color color = reader.getColor(x, y);
                writer.setColor(x, y, color);
                for (int i = 0; i < blockSize; i++) {
                    for (int j = 0; j < blockSize; j++) {
                        int xAxis = x + i;
                        int yAxis = y + j;
                        if (xAxis >= 0 && xAxis < width && yAxis < height && yAxis >= 0) {
                            writer.setColor(xAxis, yAxis, color);
                        }
                    }
                }
            }
        }
        imageView.setImage(writableImage);
    }
}