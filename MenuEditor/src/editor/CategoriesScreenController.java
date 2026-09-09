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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

import java.awt.*;
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
    private AnchorPane rootPane;
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
    private Label descriptionTitle;
    private Label imageTitle;
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
        rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane,descriptionTitle);
        contentPane.getChildren().remove(imageView);
        this.initializeCategoryScreen();

        this.selectedCategoryShow();



    }

    private void onClickRemove(ActionEvent event) {
        eb.removeCategory(selectedCategory.getIdcategories());
        rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane,descriptionTitle);
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

   private void createLabels(List<Category> categories) {


       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       double width = screenSize.getWidth();
       double height = screenSize.getHeight();
       int x = 0, y = 0;
       for (Category category : categories) {
           Label ca = new Label();
           ca.setText(category.getName());
           ca.setFont(Font.font("DIN Alternate Bold", 18));
           ca.setAlignment(Pos.CENTER);
           ca.setPrefWidth(width / 5);
           ca.setPrefHeight(60);
           ca.setMaxHeight(ca.getPrefHeight());
           ca.setMaxWidth(ca.getPrefWidth());
           ca.setLayoutX(x);
           ca.setLayoutY(y);
           ca.setStyle("-fx-background-color: black");
           ca.setTextFill(Paint.valueOf("white"));
           y = y + 60;
           ca.setOnMouseEntered(e -> {

               rigthPane.getChildren().removeAll(imageView, description, imageTitle);
               String name;
               String[] list = e.getSource().toString().split("'");
               name = list[list.length - 1];
               Category hovered = eb.getCategory(name);
               System.out.println(hovered.getImage());
               Image preview = new Image(FTPURL.getImageURL()+"/CategoriesImages/"+hovered.getImage());
               this.selectedImage = preview;
               imageView.setImage(preview);
               description.setText(hovered.getDescription());
               imageTitle.setText(name);
               rigthPane.getChildren().addAll(imageView, description, imageTitle);
               new Pulse(ca).setSpeed(1).play();
               ca.setStyle("-fx-background-color: #ffc040");
               this.selectedCategory = hovered;

           });

           ca.setOnMouseExited(e -> {
               ca.setStyle("-fx-background-color: black");

           });

           this.leftPane.getChildren().add(ca);
       }

   }

   public void onClickEdit(ActionEvent editEvent) {

       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       double width = screenSize.getWidth();
       double height = screenSize.getHeight();

       rigthPane.getChildren().removeAll(imageView, description, edit, imageTitle);
       topPane.getChildren().removeAll(addButton,generateXML);
       saveButton = new JFXButton();
       removeButton = new JFXButton();
       cancelButton = new JFXButton();
       nameEnter = new TextField();
       descriptionEnter = new TextArea();
       contentPane = new StackPane();

       this.setButton(saveButton, 40, 60, (float)(width-width/5)-100, 50, "save","#f4c542","white",16);
       this.setButton(removeButton, 40, 80, (float)(width-width/5)-270, 50, "remove","red","white",16);
       this.setButton(cancelButton, 40, 80, (float)(width-width/5)-185,50,"cancel","#f4c542","white",16);
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

       descriptionTitle.setLayoutY(520);

       descriptionEnter.setLayoutX(50);
       descriptionEnter.setLayoutY(590);
       descriptionEnter.setPrefHeight(200);

       contentPane.getChildren().add(imageView);
       rigthPane.getChildren().addAll(contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
       leftPane.setDisable(true);


   }

   public void onClickCancel(ActionEvent event){
       rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane, descriptionTitle);
       leftPane.setDisable(false);
       contentPane.getChildren().remove(imageView);
       this.initializeCategoryScreen();
       this.selectedCategoryShow();

   }

   @Override
   public void initialize(URL url, ResourceBundle rb) {

        this.initializeCategoryScreen();
        ImageView logo = new ImageView(new Image("/Resources/welcome.png"));
        Label categoriesTitle = new Label();
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

        categoriesTitle.setText("Categories");
        categoriesTitle.setFont(Font.font("DIN Alternate Bold", 30));
        categoriesTitle.setTextFill(Paint.valueOf("white"));
        categoriesTitle.setLayoutX(400);

       topPane.getChildren().addAll(categoriesTitle, logo, back);
    }

    private void initializeCategoryScreen() {

        eb = new EditorBusiness();
        imageView = new ImageView();
        description = new Label();
        descriptionTitle = new Label();
        imageTitle = new Label();
        edit = new JFXButton();
        addButton = new JFXButton();
        generateXML = new JFXButton();


        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        scrollPane.setPrefWidth(width / 5);
        scrollPane.setMaxWidth(scrollPane.getPrefWidth());
        scrollPane.setPrefHeight(height-40);
        scrollPane.setMaxHeight(scrollPane.getPrefHeight());

        scrollPane.setStyle("-fx-background-color: black");


        AnchorPane.setLeftAnchor( rigthPane,width / 5);
        rigthPane.setPrefWidth(width - width / 5);
        rigthPane.setPrefHeight(height-40);
        rigthPane.setMaxHeight(rigthPane.getPrefHeight());
        rigthPane.setLayoutX(width / 5);


        topPane.setPrefHeight(40);
        topPane.setMaxHeight(topPane.getPrefHeight());
        topPane.setStyle("-fx-background-color: black");

        AnchorPane.setLeftAnchor(imageView,50.0);
        AnchorPane.setTopAnchor(imageView,150.0);
        /*remove only for testing
        Image image = new Image("/Resources/CategoriesImages/first-course.png");
        imageView.setFitWidth(image.getWidth());
        imageView.setFitHeight(image.getHeight());


        imageView.setImage(image);*/

        imageView.setFitWidth(500);
        imageView.setFitHeight(400);
        descriptionTitle.setLayoutY(imageView.getFitHeight()+200);
        AnchorPane.setTopAnchor(description,descriptionTitle.getLayoutY()+ 105);

        imageTitle.setLayoutX(50);
        imageTitle.setLayoutY(60);
        imageTitle.setFont(Font.font("DIN Alternate Bold", 50));
        imageTitle.setText("Test 1");

        descriptionTitle.setLayoutX(50);
        descriptionTitle.setLayoutY(imageView.getFitHeight()+200);
        descriptionTitle.setText("DESCRIPTION:");
        descriptionTitle.setFont(Font.font("DIN Alternate Bold", 50));

        AnchorPane.setLeftAnchor(description,50.0);
        AnchorPane.setTopAnchor(description,descriptionTitle.getLayoutY()+ 105);
        description.prefHeight(100);
        description.prefWidth(500);
        description.maxHeight(100);
        description.maxWidth(500);
        description.setWrapText(true);
        description.setFont(Font.font("DIN Alternate Bold", 20));
        description.setText("This is a test");

        AnchorPane.setRightAnchor(addButton,50.0);
        AnchorPane.setTopAnchor(addButton,7.0);
        addButton.setPrefWidth(30);
        addButton.setPrefHeight(30);
        addButton.maxHeight(30);
        addButton.maxWidth(30);
        addButton.setId("addButton");


        addButton.setOnMouseClicked(e -> {
            onClickAdd();

        });

        generateXML.setText("GENERATE MENU");
        AnchorPane.setTopAnchor(generateXML,5.0);
        AnchorPane.setRightAnchor(generateXML,200.0);

        generateXML.setFont(Font.font("DIN Alternate Bold", 15));
        generateXML.setStyle("-fx-background-color: white;");

       generateXML.setOnAction(e->{
            eb.getMenu();
        });


        this.setButton(edit, 40, 60, (float)(width - width / 5) - 150, 50, "edit", "#f4c542", "white", 16);
        edit.setVisible(true);

        List<Category> categories = eb.getCategories();
        if(categories.size()!=0) {
            Image image = new Image(FTPURL.getImageURL()+"/CategoriesImages/" + categories.get(0).getImage());
            this.selectedImage = image;

            imageView.setImage(image);

            description.setText(categories.get(0).getDescription());
            imageTitle.setText(categories.get(0).getName());

            if ((categories.size()) * 60 > height-40) {
                leftPane.setPrefHeight((categories.size()) * 60);
            } else {
                leftPane.setPrefHeight(height-40);
                scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            }
            rigthPane.getChildren().addAll(edit, descriptionTitle, imageView, description, imageTitle);
            selectedCategory = categories.get(0);
        }


            leftPane.setPrefWidth(width / 5);
            leftPane.setDisable(false);
            leftPane.getChildren().clear();
            leftPane.setStyle("-fx-background-color: black");


           topPane.getChildren().addAll(addButton,generateXML);

            this.createLabels(categories);
            scrollPane.setContent(leftPane);

            edit.setOnAction((ActionEvent event) -> {
                this.onClickEdit(event);
            });
    }

    private void onClickAdd() {
        rigthPane.getChildren().removeAll(imageView, description, edit, imageTitle);
        topPane.getChildren().removeAll(addButton,generateXML);
        saveButton = new JFXButton();
        cancelButton = new JFXButton();
        nameEnter = new TextField();
        descriptionEnter = new TextArea();
        contentPane = new StackPane();
        imageView = new ImageView(new Image("/Resources/draganddrop.png"));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        this.setButton(saveButton, 40, 60, (float)(width-width/5)-150, 50, "save","#f4c542","white",16);
        this.setButton(cancelButton, 40, 80, (float)(width-width/5)-240,50,"cancel","red","white",16);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);

        cancelButton.setOnAction((ActionEvent cancel)->{
            rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane, descriptionTitle);
            leftPane.setDisable(false);
            contentPane.getChildren().remove(imageView);
            this.initializeCategoryScreen();
        });

        saveButton.setOnAction((ActionEvent save)->{
            addCategory();
        });



        nameEnter.setLayoutX(50);
        nameEnter.setLayoutY(30);
        nameEnter.setPromptText("NAME");



        contentPane.setLayoutX(50);
        contentPane.setLayoutY(80);
        contentPane.setPrefHeight(80);
        contentPane.setPrefWidth(80);
        contentPane.setMaxWidth(contentPane.getPrefWidth());
        contentPane.setMaxHeight(contentPane.getPrefHeight());
        contentPane.setStyle("-fx-border-color: black");

        imageView.setFitHeight(400);
        imageView.setFitWidth(500);

        descriptionTitle.setLayoutY(530);

        descriptionEnter.setLayoutX(50);
        descriptionEnter.setLayoutY(600);
        descriptionEnter.setPrefHeight(200);
        descriptionEnter.setPromptText("Add description of the category");

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
            descriptionTitle.setVisible(true);
            rigthPane.getChildren().removeAll(imageView, description, imageTitle);
            edit.setVisible(true);
            String name = selectedCategory.getName();
            Category category = eb.getCategory(name);
            Image image = new Image(FTPURL.getImageURL()+"/CategoriesImages/"+category.getImage());
            this.selectedImage = image;
            imageView.setImage(image);
            description.setText(category.getDescription());
            imageTitle.setText(name);
            rigthPane.getChildren().addAll(imageView, description, imageTitle);
            this.selectedCategory = category;
        }

    void addImage(Image i, StackPane pane){

        imageView = new ImageView();
        imageView.setImage(i);
        imageView.setFitWidth(500);
        imageView.setFitHeight(400);

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
            Path dest = Paths.get(FTPURL.getImgDest()+"/CategoriesImages/" + uploadedImage.getName());
            try {
                Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            eb.addCategory(category);
            rigthPane.getChildren().removeAll(saveButton,removeButton,cancelButton,nameEnter,descriptionEnter,contentPane,descriptionTitle);
            contentPane.getChildren().remove(imageView);
            this.initializeCategoryScreen();

    }
}
