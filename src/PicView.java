import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import java.util.List;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.io.*;
import javafx.geometry.Insets;
import javafx.stage.DirectoryChooser;
import java.net.*;

public class PicView extends Application {
  private File folder;

	public static List<String> list = new ArrayList<>();

    public static void main(String[] args) {
               Application.launch(args);
    }

    public static List<String> getSource(){
        
        return list;
    }       

  @Override
  public void start(Stage stage) {
    HBox root = new HBox();
    VBox left = new VBox();
    ImageView iv = new ImageView();
    ListView<String> sourceList = new ListView();
    sourceList.setOrientation(Orientation.VERTICAL);
    Button btnLoad = new Button("Load");

    btnLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                   DirectoryChooser dc = new DirectoryChooser();
                   folder=dc.showDialog(stage);

                   String[] files = folder.list(new FilenameFilter(){
                     @Override 
                     public boolean accept(File folder, String name) {
                        return name.endsWith(".jpg");
                     }
                   });
                   sourceList.getItems().clear();
                   for ( String fileName : files ) {
                      sourceList.getItems().add(fileName);
                   }
                }
                catch (NullPointerException e){
                   System.out.println("Ошибка" + e.getMessage());
                }
            }
        });

    sourceList.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> observable,
              String oldValue, String newValue) {
            String fullName=folder.toString()+"/"+newValue;
            File pic=new File(fullName);
            Image image=null;

            try {
              //image = new Image(fullName);
              image=new Image(pic.toURI().toURL().toString());
            }
            catch(MalformedURLException e) {
              System.out.println(e);
            }
            catch(IllegalArgumentException e) {
              System.out.println(e);
            }

            iv.setImage(image);
            iv.setFitHeight(500);
            iv.setFitWidth(625);

          }
        });

    
    root.setPadding(new Insets(15,15,15,15));
    root.setSpacing(20);
    left.setSpacing(20);
    left.getChildren().addAll(sourceList,btnLoad);
    root.getChildren().addAll(left,iv);
    Scene scene = new Scene(root, 800, 530);
    stage.setScene(scene);
    stage.setTitle("PicView, 2019");
    stage.show();
   }
}