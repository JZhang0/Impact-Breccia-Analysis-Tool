package utils.File;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageDragDropApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Label label = new Label("Drag an image here");
        root.getChildren().add(label);

        root.setOnDragOver(event -> {
            if (event.getGestureSource() != root && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        root.setOnDragDropped(event -> {
            var db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                File file = db.getFiles().get(0);
                try {
                    saveImageToSampleFolder(file);
                    label.setText("Image saved successfully!");
                } catch (Exception e) {
                    label.setText("Failed to save image.");
                    e.printStackTrace();
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Drag and Drop Image Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveImageToSampleFolder(File file) throws Exception {
        Path sampleDir = Paths.get("sample");
        if (!Files.exists(sampleDir)) {
            Files.createDirectory(sampleDir);
        }

        Path targetPath = sampleDir.resolve(file.getName());
        try (InputStream is = new FileInputStream(file);
             OutputStream os = new FileOutputStream(targetPath.toString())) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
