package application;

//import java.awt.Color;
import java.io.File;
import javafx.application.Application;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import application.GrayScale;  

public class Main extends Application {
    private Image originalImage;

    @Override
    public void start(Stage primaryStage) {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // UI STUFF
        Label label = new Label("Choose an Image");
        Button uploadBTN = new Button("Browse...");
        ComboBox<String> options = new ComboBox<String>();
        options.getItems().addAll("Grayscale", "Blur");
        options.setValue("Grayscale");
        Button convertBTN = new Button("Convert");
        ImageView imageView = new ImageView();

        // set image size
        imageView.setFitWidth(300);
        imageView.setPreserveRatio(true);

        // AKSES FILE-> AMBIL GAMBAR YANG MAU DI CONVERT
        FileChooser filechooser = new FileChooser();
        filechooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        uploadBTN.setOnAction(e -> {
            File selectedFile = filechooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                label.setText("Selected File: " + selectedFile.getName());
                originalImage = new Image(selectedFile.toURI().toString());
                imageView.setImage(originalImage);
            } else {
                label.setText("No file selected sad,,,, T^T");
            }
        });

        // convert 
        convertBTN.setOnAction(e -> {
            if (originalImage == null) {
                label.setText("No image to convert");
                return;
            }
            String selectedOption = options.getValue();
            if (selectedOption.equals("Grayscale")) {
            	imageView.setImage(GrayScale.convertGrayScale(originalImage));
            } else if (selectedOption.equals("Blur")) {
                imageView.setImage(blur(originalImage));
            }
        });

        // layout VBOX, UI DAN kawan2 -> scene
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, uploadBTN, options, convertBTN, imageView);

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setTitle("Upload and Transform Your Image");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Image blur(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage blurImage = new WritableImage(width, height);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = blurImage.getPixelWriter();

        int radius = 5; // Radius untuk efek blur

        // Loop untuk setiap piksel 
        for (int y = radius; y < height - radius; y++) {
            for (int x = radius; x < width - radius; x++) {
                double r = 0, g = 0, b = 0;
                int count = 0;

                // Hitung rata-rata warna dari piksel di radius tertentu
                for (int a = -radius; a <= radius; a++) {
                    for (int c = -radius; c <= radius; c++) {
                        Color color = pixelReader.getColor(x + a, y + c);
                        r += color.getRed();
                        g += color.getGreen();
                        b += color.getBlue();
                        count++;
                    }
                }
                r /= count;
                g /= count;
                b /= count;
                pixelWriter.setColor(x, y, new Color(r, g, b, 1));
            }
        }

        return blurImage;
        
        
        
        
    }

    
    
    

    public static void main(String[] args) {
        launch(args);
    }
}
