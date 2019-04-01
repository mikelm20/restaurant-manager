package editor;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import flow.Transition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;
import java.util.ResourceBundle;

public class CategoriesScreenController implements Initializable {

    private EditorBusiness eb;
    @FXML
    private StackPane rootPane;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private AnchorPane leftPane;
    @FXML
    private AnchorPane rigthPane;
    @FXML
    private AnchorPane topPane;
    private ImageView imageView;
    private JFXButton edit;
    private Label description;
    private Label tituloDescripcion;
    private Label tituloImagen;
    private JFXButton saveButton;
    private JFXButton removeButton;
    private JFXButton cancelButton;
    private TextArea descriptionEnter;
    private TextField nameEnter;
    private Category selectedCategory;
    private Image selectedImage;
    private StackPane contentPane;
    private File uploadedImage;
    private JFXButton addButton;
    private JFXButton generateXML;



    private void onClickSave(ActionEvent event) {
        Category updateCategory = new Category();

        updateCategory.setName(nameEnter.getText());
        updateCategory.setDescription(descriptionEnter.getText());

        if(uploadedImage != null)
        {
            updateCategory.setImage(uploadedImage.getName());
            Path source = Paths.get(uploadedImage.getAbsolutePath());
            Path dest = Paths.get(FTPURL.getImageURL()+"/CategoriesImages/"+uploadedImage.getName());
            try {
                Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else
        {
            updateCategory.setImage(selectedCategory.getImage());
        }

        eb.updateCategory(updateCategory,selectedCategory.getIdcategories());
        rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane,tituloDescripcion);
        contentPane.getChildren().remove(imageView);
        this.initializeCategoryScreen();

        this.selectedCategoryShow();



    }

    private void onClickRemove(ActionEvent event) {
        eb.removeCategory(selectedCategory.getIdcategories());
        rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane,tituloDescripcion);
        contentPane.getChildren().remove(imageView);

        this.initializeCategoryScreen();


    }
   private void setButton (JFXButton button, float height, float width, float X, float Y, String text, String color,String fontColor, float size) {
      button.setLayoutX(X);
       button.setLayoutY(Y);
       button.setPrefHeight(height);
       button.setPrefWidth(width);

      button.setMaxWidth(button.getPrefWidth());
       button.setMaxHeight(button.getPrefHeight());

       button.setText(text);
      button.setFont(Font.font("DIN Alternate Bold", size));
      button.setTextFill(Paint.valueOf(fontColor));
       button.setStyle("-jfx-button-type:RAISED");
       button.setStyle("-fx-background-color:"+color);

       button.setVisible(false);


   }

   private void createLabels(List<Category> categorias) {
       int x = 0, y = 0;
       for (Category category : categorias) {
           Label ca = new Label();
           ca.setText(category.getName());
           ca.setFont(Font.font("DIN Alternate Bold", 18));
           ca.setAlignment(Pos.CENTER);
           ca.setPrefWidth(900 / 5);
           ca.setPrefHeight(60);
           ca.setMaxHeight(ca.getPrefHeight());
           ca.setMaxWidth(ca.getPrefWidth());
           ca.setLayoutX(x);
           ca.setLayoutY(y);
           ca.setStyle("-fx-background-color: black");
           ca.setTextFill(Paint.valueOf("white"));
           y = y + 60;
           ca.setOnMouseEntered(e -> {

               rigthPane.getChildren().removeAll(imageView, description, tituloImagen);
               String name;
               String[] lista = e.getSource().toString().split("'");
               name = lista[lista.length - 1];
               Category categoria = eb.getCategory(name);
               System.out.println(categoria.getImage());
               Image imagen = new Image(FTPURL.getImageURL()+"/CategoriesImages/"+categoria.getImage());
               this.selectedImage = imagen;
               imageView.setImage(imagen);
               description.setText(categoria.getDescription());
               tituloImagen.setText(name);
               rigthPane.getChildren().addAll(imageView, description, tituloImagen);
               new Pulse(ca).setSpeed(1).play();
               ca.setStyle("-fx-background-color: #ffc040");
               this.selectedCategory = categoria;

           });

           ca.setOnMouseExited(e -> {
               ca.setStyle("-fx-background-color: black");

           });

           this.leftPane.getChildren().add(ca);
       }

   }

   public void onClickEdit(ActionEvent editEvent) {
       rigthPane.getChildren().removeAll(imageView, description, edit, tituloImagen);
       topPane.getChildren().removeAll(addButton,generateXML);
       saveButton = new JFXButton();
       removeButton = new JFXButton();
       cancelButton = new JFXButton();
       nameEnter = new TextField();
       descriptionEnter = new TextArea();
       contentPane = new StackPane();

       this.setButton(saveButton, 40, 60, (900-900/5)-100, 50, "save","#f4c542","white",16);
       this.setButton(removeButton, 40, 80, (900-900/5)-270, 50, "remove","red","white",16);
       this.setButton(cancelButton, 40, 80, (900-900/5)-185,50,"cancel","#f4c542","white",16);
       saveButton.setVisible(true);
       removeButton.setVisible(true);
       cancelButton.setVisible(true);

       cancelButton.setOnAction((ActionEvent cancel)->{
           this.onClickCancel(cancel);
       });

       saveButton.setOnAction((ActionEvent save)->{
           this.onClickSave(save);
       });

       removeButton.setOnAction((ActionEvent remove)->{
           this.onClickRemove(remove);
       });


       nameEnter.setLayoutX(50);
       nameEnter.setLayoutY(30);
       nameEnter.setText(selectedCategory.getName());

       descriptionEnter.setLayoutX(50);
       descriptionEnter.setLayoutY(350);
       descriptionEnter.setText(selectedCategory.getDescription());

       contentPane.setLayoutX(50);
       contentPane.setLayoutY(80);
       contentPane.setPrefHeight(80);
       contentPane.setPrefWidth(80);
       contentPane.setMaxWidth(contentPane.getPrefWidth());
       contentPane.setMaxHeight(contentPane.getPrefHeight());

       contentPane.setOnDragOver(new EventHandler<DragEvent>() {
           @Override
           public void handle(final DragEvent event) {
               mouseDragOver(event);
           }
       });

       contentPane.setOnDragDropped(new EventHandler<DragEvent>() {
           @Override
           public void handle(final DragEvent event) {
               mouseDragDropped(event);
           }
       });

       contentPane.setOnDragExited(new EventHandler<DragEvent>() {
           @Override
           public void handle(final DragEvent event) {
               contentPane.setStyle("-fx-border-color: #C6C6C6;");
           }
       });

       contentPane.getChildren().add(imageView);
       rigthPane.getChildren().addAll(contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
       leftPane.setDisable(true);


   }

   public void onClickCancel(ActionEvent event){
       rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane, tituloDescripcion);
       leftPane.setDisable(false);
       contentPane.getChildren().remove(imageView);
       this.initializeCategoryScreen();
       this.selectedCategoryShow();

   }

   @Override
   public void initialize(URL url, ResourceBundle rb) {

        this.initializeCategoryScreen();
        ImageView logo = new ImageView(new Image("/Resources/welcome.png"));
        Label tituloCategorias = new Label();
        logo.maxHeight(40);
        logo.maxWidth(900/5);
        logo.setFitHeight(40);
        logo.setFitWidth(900/5);
        logo.setLayoutX(70);

       JFXSpinner spinner = new JFXSpinner();
       spinner.setPrefHeight(150);
       spinner.setPrefWidth(150);
       spinner.setMaxHeight(spinner.getPrefHeight());
       spinner.setMaxWidth(spinner.getPrefWidth());
       spinner.setLayoutX(450);
       spinner.setLayoutY(300);
       spinner.setVisible(false);
       rootPane.getChildren().add(spinner);

        JFXButton back = new JFXButton();
        back.setStyle("-fx-background-color: black");
        back.setPrefWidth(40);
        back.setPrefHeight(40);
        back.setLayoutX(10);
        back.setMaxHeight(back.getPrefHeight());
        back.setMaxWidth(back.getPrefWidth());
        back.setId("backButton");
        back.setOnAction((ActionEvent e)->{
            spinner.setVisible(true);
            Transition transition = new Transition();
            transition.changeScene(rootPane, 0, "/editor/SelectionMenu.fxml");
            new FadeOutRightBig(topPane).play();
            new FadeOutRightBig(rigthPane).play();
            new SlideOutDown(scrollPane).setSpeed(1).play();
            new SlideOutDown(leftPane).play();


        });

        tituloCategorias.setText("Categories");
        tituloCategorias.setFont(Font.font("DIN Alternate Bold", 30));
        tituloCategorias.setTextFill(Paint.valueOf("white"));
        tituloCategorias.setLayoutX(400);

       topPane.getChildren().addAll(tituloCategorias, logo, back);
    }

    private void initializeCategoryScreen() {

        eb = new EditorBusiness();
        imageView = new ImageView();
        description = new Label();
        tituloDescripcion = new Label();
        tituloImagen = new Label();
        edit = new JFXButton();
        addButton = new JFXButton();
        generateXML = new JFXButton();


        rootPane.setPrefWidth(900);
        rootPane.setPrefHeight(600);

        scrollPane.setPrefWidth(900 / 5);
        scrollPane.setMaxWidth(scrollPane.getPrefWidth());
        scrollPane.setPrefHeight(560);
        scrollPane.setMaxHeight(scrollPane.getPrefHeight());
        scrollPane.setLayoutY(40);
        scrollPane.setLayoutX(0);
        scrollPane.setStyle("-fx-background-color: black");


        rigthPane.setPrefWidth(900 - 900 / 5);
        rigthPane.setPrefHeight(560);
        rigthPane.setMaxHeight(rigthPane.getPrefHeight());
        rigthPane.setLayoutX(900 / 5);
        rigthPane.setLayoutY(40);

        topPane.setPrefWidth(900);
        topPane.setPrefHeight(40);
        topPane.setMaxHeight(topPane.getPrefHeight());
        topPane.setLayoutX(0);
        topPane.setLayoutY(0);
        topPane.setStyle("-fx-background-color: black");

        imageView.setLayoutX(50);
        imageView.setLayoutY(80);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        tituloImagen.setLayoutX(50);
        tituloImagen.setLayoutY(30);
        tituloImagen.setFont(Font.font("DIN Alternate Bold", 30));

        tituloDescripcion.setLayoutX(50);
        tituloDescripcion.setLayoutY(310);
        tituloDescripcion.setText("DESCRIPTION:");
        tituloDescripcion.setFont(Font.font("DIN Alternate Bold", 20));

        description.setLayoutX(50);
        description.setLayoutY(350);
        description.prefHeight(100);
        description.prefWidth(500);
        description.maxHeight(100);
        description.maxWidth(500);
        description.setWrapText(true);
        description.setFont(Font.font("DIN Alternate Bold", 16));

        addButton.setLayoutX(850);
        addButton.setLayoutY(7);
        addButton.setPrefWidth(30);
        addButton.setPrefHeight(30);
        addButton.maxHeight(30);
        addButton.maxWidth(30);
        addButton.setId("addButton");


        addButton.setOnMouseClicked(e -> {
            onClickAdd();

        });

        generateXML.setText("GENERATE MENU");
        generateXML.setLayoutY(2);
        generateXML.setLayoutX(580);
        generateXML.setMaxHeight(80);
        generateXML.setMaxWidth(200);
        generateXML.setFont(Font.font("DIN Alternate Bold", 20));
        generateXML.setStyle("-fx-background-color: white;");

        generateXML.setOnAction(e->{
            eb.getMenu();
        });


        this.setButton(edit, 40, 60, (900 - 900 / 5) - 100, 50, "edit", "#f4c542", "white", 16);
        edit.setVisible(true);

        List<Category> categorias = eb.getCategories();
        if(categorias.size()!=0) {
            Image imagen = new Image(FTPURL.getImageURL()+"/CategoriesImages/" + categorias.get(0).getImage());
            this.selectedImage = imagen;
            imageView.setImage(imagen);
            description.setText(categorias.get(0).getDescription());
            tituloImagen.setText(categorias.get(0).getName());

            if ((categorias.size()) * 60 > 560) {
                leftPane.setPrefHeight((categorias.size()) * 60);
            } else {
                leftPane.setPrefHeight(560);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
            rigthPane.getChildren().addAll(edit, tituloDescripcion, imageView, description, tituloImagen);
            selectedCategory = categorias.get(0);
        }

            leftPane.setPrefWidth(900 / 5);
            leftPane.setDisable(false);
            leftPane.getChildren().clear();
            leftPane.setStyle("-fx-background-color: black");


            topPane.getChildren().addAll(addButton,generateXML);

            this.createLabels(categorias);
            scrollPane.setContent(leftPane);

            edit.setOnAction((ActionEvent event) -> {
                this.onClickEdit(event);
            });
    }

    private void onClickAdd() {
        rigthPane.getChildren().removeAll(imageView, description, edit, tituloImagen);
        topPane.getChildren().removeAll(addButton,generateXML);
        saveButton = new JFXButton();
        cancelButton = new JFXButton();
        nameEnter = new TextField();
        descriptionEnter = new TextArea();
        contentPane = new StackPane();
        imageView = new ImageView(new Image("/Resources/draganddrop.png"));

        this.setButton(saveButton, 40, 60, (900-900/5)-100, 50, "save","#f4c542","white",16);
        this.setButton(cancelButton, 40, 80, (900-900/5)-185,50,"cancel","red","white",16);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);

        cancelButton.setOnAction((ActionEvent cancel)->{
            rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane, tituloDescripcion);
            leftPane.setDisable(false);
            contentPane.getChildren().remove(imageView);
            this.initializeCategoryScreen();
        });

        saveButton.setOnAction((ActionEvent save)->{
            addCategory();
        });



        nameEnter.setLayoutX(50);
        nameEnter.setLayoutY(30);
        nameEnter.setText("Name");

        descriptionEnter.setLayoutX(50);
        descriptionEnter.setLayoutY(350);
        descriptionEnter.setText("Add description of the category");

        contentPane.setLayoutX(50);
        contentPane.setLayoutY(80);
        contentPane.setPrefHeight(80);
        contentPane.setPrefWidth(80);
        contentPane.setMaxWidth(contentPane.getPrefWidth());
        contentPane.setMaxHeight(contentPane.getPrefHeight());
        contentPane.setStyle("-fx-border-color: black");

        imageView.setFitHeight(200);
        imageView.setFitWidth(300);

        contentPane.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(final DragEvent event) {
                mouseDragOver(event);
            }
        });

        contentPane.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(final DragEvent event) {
                mouseDragDropped(event);
            }
        });

        contentPane.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(final DragEvent event) {
                contentPane.setStyle("-fx-border-color: #C6C6C6;");
            }
        });

        contentPane.getChildren().add(imageView);
        rigthPane.getChildren().addAll(contentPane,descriptionEnter, cancelButton, saveButton,nameEnter);
        leftPane.setDisable(true);

    }

    private void selectedCategoryShow() {
            tituloDescripcion.setVisible(true);
            rigthPane.getChildren().removeAll(imageView, description, tituloImagen);
            edit.setVisible(true);
            String name = selectedCategory.getName();
            Category categoria = eb.getCategory(name);
            Image imagen = new Image(FTPURL.getImageURL()+"/CategoriesImages/"+categoria.getImage());
            this.selectedImage = imagen;
            imageView.setImage(imagen);
            description.setText(categoria.getDescription());
            tituloImagen.setText(name);
            rigthPane.getChildren().addAll(imageView, description, tituloImagen);
            this.selectedCategory = categoria;
        }

    void addImage(Image i, StackPane pane){

        imageView = new ImageView();
        imageView.setImage(i);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        contentPane.getChildren().add(imageView);

    }

    private void mouseDragDropped(final DragEvent e) {
        final Dragboard db = e.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            success = true;
            // Only get the first file from the list
            final File file = db.getFiles().get(0);
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    System.out.println(file.getAbsolutePath());
                    uploadedImage = file;

                    try {
                        if(!contentPane.getChildren().isEmpty()){
                            contentPane.getChildren().remove(0);
                        }
                        Image img = new Image(new FileInputStream(file.getAbsolutePath()));

                        addImage(img, contentPane);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                }
            });
        }
        e.setDropCompleted(success);
        e.consume();
    }

    private  void mouseDragOver(final DragEvent e) {
        final Dragboard db = e.getDragboard();

        final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".png")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpeg")
                || db.getFiles().get(0).getName().toLowerCase().endsWith(".jpg");

        if (db.hasFiles()) {
            if (isAccepted) {
                contentPane.setStyle("-fx-border-color: red;"
                        + "-fx-border-width: 5;"
                        + "-fx-background-color: #C6C6C6;"
                        + "-fx-border-style: solid;");
                e.acceptTransferModes(TransferMode.COPY);
            }
        } else {
            e.consume();
        }
    }

    private void addCategory(){

        Category category = new Category();
        category.setName(nameEnter.getText());
        category.setDescription(descriptionEnter.getText());
        category.setImage("null");


        if(uploadedImage != null) {
            category.setImage(uploadedImage.getName());
            Path source = Paths.get(uploadedImage.getAbsolutePath());
            Path dest = Paths.get(FTPURL.getImageURL()+"/CategoriesImages/" + uploadedImage.getName());
            try {
                Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            eb.addCategory(category);
            rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane,tituloDescripcion);
            contentPane.getChildren().remove(imageView);
            this.initializeCategoryScreen();

    }
}
