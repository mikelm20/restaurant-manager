package editor;

import animatefx.animation.FadeOutRightBig;
import animatefx.animation.Pulse;
import animatefx.animation.SlideOutDown;
import com.jfoenix.controls.*;
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
import javafx.scene.text.TextAlignment;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DishesScreenController implements Initializable {

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
    private JFXButton editAllergens;
    private JFXButton editDish;
    private Text description;
    private Label descriptionTitle;
    private Label imageTitle;
    private Label categoriesTitle;
    private Label category;
    private Label nutritionTitle;
    private Label priceTitle;
    private JFXButton saveButton;
    private JFXButton removeButton;
    private JFXButton cancelButton;
    private TextArea descriptionEnter;
    private TextField nameEnter;
    private Dish selectedDish;
    private Image selectedImage;
    private StackPane contentPane;
    private File uploadedImage;
    private JFXButton addButton;
    private Label nutritionInfo;
    private List<Allergens> allergens;
    private List<JFXCheckBox> allergensCheck;
    private boolean allerChecked[];
    private List<Allergens> allergensByDish;
    private TextField priceValue;
    private AnchorPane iconsHolder;
    private Label allergensTitle;
    private TextField energy;
    private TextField carbohydrates;
    private TextField salt;
    private TextField saturated;
    private TextField fat;
    private TextField sugar;
    private TextField weight;
    private ChoiceBox selector;
    private Dish selectedShowDish;
    private TextField proteins;
    private JFXButton generateXML;



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

    private String nutritionInfoText(Dish dish) {
        return "Energy: "+dish.getEnergy().toString()+"\n\n"+
                "Carbohydrates: "+dish.getCarboHydrates().toString()+"\n\n"+
                "Salt: " + dish.getSalt().toString() + "\n\n"+
                "Saturated Fat: " +dish.getSaturedFat().toString()+"\n\n"+
                "Proteins: " +dish.getProteins().toString()+"\n\n"+
                "Fat: "+dish.getFat().toString()+"\n\n"+
                "Sugar: "+dish.getSugars().toString()+"\n\n"+
                "Weight: "+dish.getWeight().toString();
    }

    private void createLabels(List<Dish> dishes) {

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        int x = 0, y = 0;
        for (Dish dish : dishes) {
            Label di = new Label();
            di.setText(dish.getName());
            di.setFont(Font.font("DIN Alternate Bold", 18));
            di.setAlignment(Pos.CENTER);
            di.setPrefWidth(width / 5);
            di.setPrefHeight(60);
            di.setMaxHeight(di.getPrefHeight());
            di.setMaxWidth(di.getPrefWidth());
            di.setLayoutX(x);
            di.setLayoutY(y);
            di.setStyle("-fx-background-color: black");
            di.setTextFill(Paint.valueOf("white"));
            y = y + 60;
            di.setOnMouseEntered(e -> {


                rigthPane.getChildren().removeAll(priceTitle,nutritionInfo, imageView, description, imageTitle);
                String name;
                String[] list = e.getSource().toString().split("'");
                name = list[list.length - 1];
                Dish hovered = eb.getDish(name);
                System.out.println(hovered.getImage());
                Image preview = new Image(FTPURL.getImageURL()+"/DishesImages/"+hovered.getImage());
                this.selectedImage = preview;
                imageView.setImage(preview);
                description.setText(hovered.getDescription());
                imageTitle.setText(name);
                nutritionInfo.setText(nutritionInfoText(hovered));
                priceTitle.setText("- PRICE: "+hovered.getPrice() +"€");
                category.setText(eb.getCategoryByDish(hovered.getIdDishes()));
                rigthPane.getChildren().addAll(priceTitle,nutritionInfo, imageView, description, imageTitle);
                new Pulse(di).setSpeed(1).play();
                di.setStyle("-fx-background-color: #ffc040");
                this.selectedDish = hovered;
                selectedShowDish = selectedDish;
                allergensByDish = eb.getAllergensbyDish(selectedDish.getIdDishes());
                int numImage = 1;
                iconsHolder.getChildren().clear();

                for(Allergens allergen:allergensByDish)
                {
                    ImageView image = new ImageView(new Image("Resources/AWIcons/"+allergen.getImage()));
                    image.setFitHeight(50);
                    image.setFitWidth(40);
                    if(numImage<8) {
                        image.setLayoutY(35);
                        image.setLayoutX(50*(numImage-1));
                    }

                    else {

                        image.setLayoutY(85);
                        image.setLayoutX(50*(numImage-8));
                    }

                    numImage+=1;

                    iconsHolder.getChildren().add(image);

                }






            });

            di.setOnMouseExited(e -> {
                di.setStyle("-fx-background-color: black");

            });

            this.leftPane.getChildren().add(di);
        }

    }

    private void onClickSaveAllergens(ActionEvent event){
        int index = 0;
        allerChecked = new boolean[14];
        while(index<14){
            allerChecked[Integer.parseInt(allergensCheck.get(index).getId())-1]=allergensCheck.get(index).isSelected();
            index+=1;
        }

        eb.addAllergens(allerChecked,selectedShowDish.getIdDishes());

        rigthPane.getChildren().clear();
        initializeDishesScreen();
        this.selectedDishShow();




    }

    private void onClickEditAllergens(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        rigthPane.getChildren().clear();
        leftPane.setDisable(true);
        topPane.getChildren().removeAll(addButton,generateXML);
        Label allergensTitle = new Label();
        allergensTitle.setLayoutX(50);
        allergensTitle.setLayoutY(80);
        allergensTitle.setText("SELECT THE ALLERGENS: ");
        allergensTitle.setFont(Font.font("DIN Alternate Bold", 30));

        allergens = eb.getAllergens();
        saveButton = new JFXButton();
        this.setButton(saveButton, 40, 60, (float)(width-width/5)-100, 30, "save","#f4c542","white",16);
        saveButton.setVisible(true);
        cancelButton = new JFXButton();
        this.setButton(cancelButton, 40, 80, (float)(width-width/5)-185,30,"cancel","red","white",16);
        cancelButton.setVisible(true);

        cancelButton.setOnAction((ActionEvent back)->{
            rigthPane.getChildren().clear();
            this.initializeDishesScreen();

            this.selectedDishShow();
        });
        saveButton.setOnAction((ActionEvent save)->{
            this.onClickSaveAllergens(save);
        });

        rigthPane.getChildren().addAll(saveButton,cancelButton, allergensTitle);
        int y=200;
        JFXCheckBox checkBox;
        ImageView image;
        allergensCheck = new ArrayList<JFXCheckBox>();
        allergensByDish = eb.getAllergensbyDish(selectedShowDish.getIdDishes());

        for(Allergens allergen : allergens) {

            checkBox = new JFXCheckBox();
            image = new ImageView(new Image("Resources/AWIcons/" + allergen.getImage()));
            image.setFitWidth(80);
            image.setFitHeight(100);
            checkBox.setId(Integer.toString(allergen.getId()));
            if(allergensByDish!=null) {
                if (allergensByDish.contains(allergen)) {
                    checkBox.setSelected(true);
                }
            }

            if (allergen.getId()<4 ) {

                image.setLayoutX(100);
                checkBox.setLayoutX(80);
                image.setLayoutY((y*allergen.getId()));
                checkBox.setLayoutY(10+(y*allergen.getId()));

            }
            else if(allergen.getId()<7) {

                image.setLayoutX(280);
                checkBox.setLayoutX(260);
                image.setLayoutY((y*(allergen.getId()-3)));
                checkBox.setLayoutY(10+(y*(allergen.getId()-3)));

            }
            else if(allergen.getId()<10) {

                image.setLayoutX(460);
                checkBox.setLayoutX(440);
                image.setLayoutY((y*(allergen.getId()-6)));
                checkBox.setLayoutY(10+(y*(allergen.getId()-6)));

            }
             else if(allergen.getId()<13){
                image.setLayoutX(640);
                checkBox.setLayoutX(620);
                image.setLayoutY((y*(allergen.getId()-9)));
                checkBox.setLayoutY(10+(y*(allergen.getId()-9)));
            }
             else {
                image.setLayoutX(820);
                checkBox.setLayoutX(800);
                image.setLayoutY((y*(allergen.getId()-12)));
                checkBox.setLayoutY(10+(y*(allergen.getId()-12)));

            }



            allergensCheck.add(checkBox);
            rigthPane.getChildren().addAll(checkBox, image);

        }
    }

    private void selectedDishShow() {
        descriptionTitle.setVisible(true);
        rigthPane.getChildren().removeAll(imageView, description, imageTitle);
        editDish.setVisible(true);
        String name = selectedShowDish.getName();
        Dish dish = eb.getDish(name);
        Image preview = new Image(FTPURL.getImageURL()+"/DishesImages/"+dish.getImage());
        this.selectedImage = preview;
        imageView.setImage(preview);
        description.setText(dish.getDescription());
        imageTitle.setText(name);
        rigthPane.getChildren().addAll(imageView, description, imageTitle);
        allergensByDish = eb.getAllergensbyDish(selectedShowDish.getIdDishes());
        int numImage = 1;
        iconsHolder.getChildren().clear();

        for(Allergens allergen:allergensByDish)
        {
            ImageView image = new ImageView(new Image("Resources/AWIcons/"+allergen.getImage()));
            image.setFitHeight(50);
            image.setFitWidth(40);
            if(numImage<8) {
                image.setLayoutY(35);
                image.setLayoutX(50*(numImage-1));
            }

            else {

                image.setLayoutY(85);
                image.setLayoutX(50*(numImage-8));
            }

            numImage+=1;

            iconsHolder.getChildren().add(image);

        }



        this.selectedDish = dish;
    }

    public void onClickAdd(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        rigthPane.getChildren().removeAll(category, categoriesTitle, imageView, description, editDish,editAllergens, imageTitle,iconsHolder,nutritionInfo);
        rigthPane.getChildren().clear();
        topPane.getChildren().removeAll(addButton,generateXML);
        saveButton = new JFXButton();
        cancelButton = new JFXButton();
        nameEnter = new TextField();
        descriptionEnter = new TextArea();
        contentPane = new StackPane();
        priceValue = new TextField();
        allergensTitle = new Label();
        imageView = new ImageView(new Image("/Resources/draganddrop.png"));
        selectedShowDish = selectedDish;

        this.setButton(saveButton, 20, 60, (float)(width-width/5)-100, 20, "save","#f4c542","white",16);
        this.setButton(cancelButton, 20, 80, (float)(width-width/5)-185,20,"cancel","red","white",16);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);

        cancelButton.setOnAction((ActionEvent cancel)->{
            rigthPane.getChildren().clear();
            this.initializeDishesScreen();
        });

        saveButton.setOnAction((ActionEvent save)->{

            Dish newDish = new Dish();

            newDish.setName(nameEnter.getText());
            newDish.setDescription(descriptionEnter.getText());

            if(uploadedImage != null)
            {
                newDish.setImage(uploadedImage.getName());
                Path source = Paths.get(uploadedImage.getAbsolutePath());
                Path dest = Paths.get(FTPURL.getImgDest()+"/DishesImages/"+uploadedImage.getName());
                try {
                    Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else
            {
                newDish.setImage("null");
            }

            newDish.setPrice(Double.parseDouble(priceValue.getText()));
           newDish.setEnergy(Double.parseDouble(energy.getText()));
            newDish.setSaturedFat(Double.parseDouble(saturated.getText()));
            newDish.setProteins(Double.parseDouble(proteins.getText()));
            newDish.setSugars(Double.parseDouble(sugar.getText()));
            newDish.setSalt(Double.parseDouble(salt.getText()));
            newDish.setFat(Double.parseDouble(fat.getText()));
            newDish.setWeight(Double.parseDouble(weight.getText()));
            newDish.setCarboHydrates(Double.parseDouble(carbohydrates.getText()));


            rigthPane.getChildren().removeAll(priceTitle,selector,weight,sugar, fat, saturated,proteins, salt, carbohydrates, categoriesTitle, nutritionInfo, energy, priceValue, contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
            contentPane.getChildren().remove(imageView);
            eb.addDish(newDish, selector.getValue().toString());
            selectedShowDish = eb.getDish(newDish.getName());
            this.onClickEditAllergens();

        });

        priceTitle.setText("- PRICE: ");
        priceValue.setLayoutY(priceTitle.getLayoutY()+5);
        priceValue.setLayoutX(priceTitle.getLayoutX()+150);
        priceValue.setPrefSize(60,10);
        priceValue.setMaxSize(60,10);
        priceValue.setPromptText(Double.toString(0)+"€");


        nameEnter.setLayoutX(50);
        nameEnter.setLayoutY(30);
        nameEnter.setPromptText("NAME");

       // descriptionTitle.setLayoutX(360);
        //descriptionTitle.setLayoutY(400);

        descriptionEnter.setLayoutX(600);
        descriptionEnter.setLayoutY(650);
        descriptionEnter.setMaxHeight(100);
        descriptionEnter.setMaxWidth(350);
        descriptionEnter.setWrapText(true);
        descriptionEnter.setPromptText("WRITE THE DESCRIPTION OF THE NEW DISH");

        contentPane.setLayoutX(50);
        contentPane.setLayoutY(80);
        contentPane.setPrefHeight(80);
        contentPane.setPrefWidth(80);
        contentPane.setMaxWidth(contentPane.getPrefWidth());
        contentPane.setMaxHeight(contentPane.getPrefHeight());
        contentPane.setStyle("-fx-border-color: black");

        imageView.setFitHeight(400);
        imageView.setFitWidth(500);

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


        //nutritionTitle.setLayoutX(360);
       // nutritionInfo.setLayoutX(410);
        //nutritionInfo.setLayoutY(130);
        nutritionInfo.setText("Energy: \n\n"+
                "Carbohydrates: \n\n"+
                "Salt: \n\n"+
                "Saturated Fat: \n\n"+
                "Fat: \n\n"+
                "Sugar: \n\n"+
                "Weight: \n\n"+
                "Proteins: ");

        energy = new TextField();
        energy.setLayoutY(nutritionInfo.getLayoutY());
        energy.setLayoutX(nutritionInfo.getLayoutX()+200);
        energy.setPromptText("0.0");
        energy.setPrefSize(70,10);

        carbohydrates = new TextField();
        carbohydrates.setLayoutY(nutritionInfo.getLayoutY()+53);
        carbohydrates.setLayoutX(nutritionInfo.getLayoutX()+200);
        carbohydrates.setPromptText("0.0");
        carbohydrates.setPrefSize(70,10);

        salt = new TextField();
        salt.setLayoutY(nutritionInfo.getLayoutY()+103);
        salt.setLayoutX(nutritionInfo.getLayoutX()+200);
        salt.setPromptText("0.0");
        salt.setPrefSize(70,10);

        saturated = new TextField();
        saturated.setLayoutY(nutritionInfo.getLayoutY()+153);
        saturated.setLayoutX(nutritionInfo.getLayoutX()+200);
        saturated.setPromptText("0.0");
        saturated.setPrefSize(70,10);

        fat = new TextField();
        fat.setLayoutY(nutritionInfo.getLayoutY()+206);
        fat.setLayoutX(nutritionInfo.getLayoutX()+200);
        fat.setPromptText("0.0");
        fat.setPrefSize(70,10);

        sugar = new TextField();
        sugar.setLayoutY(nutritionInfo.getLayoutY()+260);
        sugar.setLayoutX(nutritionInfo.getLayoutX()+200);
        sugar.setPromptText("0.0");
        sugar.setPrefSize(70,10);

        weight = new TextField();
        weight.setLayoutY(nutritionInfo.getLayoutY()+310);
        weight.setLayoutX(nutritionInfo.getLayoutX()+200);
        weight.setPromptText("0.0");
        weight.setPrefSize(70,10);

        proteins = new TextField();
        proteins.setLayoutY(nutritionInfo.getLayoutY()+360);
        proteins.setLayoutX(nutritionInfo.getLayoutX()+200);
        proteins.setPromptText("0.0");
        proteins.setPrefSize(70,10);



        List<Category> categories = eb.getCategories();

        selector = new ChoiceBox();
        for (Category category:categories) {

            selector.getItems().add(category.getName());

        }

        selector.setValue(selector.getItems().get(0));
        selector.setLayoutX(categoriesTitle.getLayoutX()+230);
        selector.setLayoutY(categoriesTitle.getLayoutY()+3);

        contentPane.getChildren().add(imageView);
        rigthPane.getChildren().addAll(selector,weight,sugar, fat,proteins, saturated, salt, carbohydrates, nutritionInfo, energy, priceValue, contentPane,descriptionEnter, cancelButton, saveButton,nameEnter);

        rigthPane.getChildren().addAll(allergensTitle,categoriesTitle,nutritionTitle,descriptionTitle,priceTitle);
        leftPane.setDisable(true);






    }

    public void onClickEdit(ActionEvent editEvent) {
        rigthPane.getChildren().removeAll(descriptionTitle,category, categoriesTitle, imageView, description, editDish,editAllergens, imageTitle,iconsHolder,nutritionInfo);
        topPane.getChildren().removeAll(addButton,generateXML);
        saveButton = new JFXButton();
        removeButton = new JFXButton();
        cancelButton = new JFXButton();
        nameEnter = new TextField();
        descriptionEnter = new TextArea();
        contentPane = new StackPane();
        priceValue = new TextField();
        allergensTitle = new Label();
        selectedShowDish = selectedDish;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();



        /*allergensTitle.setLayoutX(360);
        allergensTitle.setLayoutY(400);
        allergensTitle.setText("ALLERGENS & WARNINGS:");
        allergensTitle.setFont(Font.font("DIN Alternate Bold", 20));*/

        this.setButton(saveButton, 20, 60, (float)(width-width/5)-100, 20, "save","#f4c542","white",16);
        this.setButton(removeButton, 20, 80, (float)(width-width/5)-270, 20, "remove","red","white",16);
        this.setButton(cancelButton, 20, 80, (float)(width-width/5)-185,20,"cancel","#f4c542","white",16);
        saveButton.setVisible(true);
        removeButton.setVisible(true);
        cancelButton.setVisible(true);

        cancelButton.setOnAction((ActionEvent cancel)->{
            rigthPane.getChildren().clear();
            this.initializeDishesScreen();
            this.selectedDishShow();
        });

        saveButton.setOnAction((ActionEvent save)->{

            Dish updateDish = new Dish();

            updateDish.setName(nameEnter.getText());
            updateDish.setDescription(descriptionEnter.getText());

            if(uploadedImage != null)
            {
                updateDish.setImage(uploadedImage.getName());
                Path source = Paths.get(uploadedImage.getAbsolutePath());
                Path dest = Paths.get(FTPURL.getImageURL()+"/DishesImages/"+uploadedImage.getName());
                try {
                    Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            else
            {
                updateDish.setImage(selectedDish.getImage());
            }

            updateDish.setPrice(Double.parseDouble(priceValue.getText()));
            updateDish.setEnergy(Double.parseDouble(energy.getText()));
            updateDish.setSaturedFat(Double.parseDouble(saturated.getText()));
            updateDish.setProteins(Double.parseDouble(proteins.getText()));
            updateDish.setSugars(Double.parseDouble(sugar.getText()));
            updateDish.setSalt(Double.parseDouble(salt.getText()));
            updateDish.setFat(Double.parseDouble(fat.getText()));
            updateDish.setWeight(Double.parseDouble(weight.getText()));
            updateDish.setCarboHydrates(Double.parseDouble(carbohydrates.getText()));

            eb.updateDish(updateDish,selectedDish.getIdDishes(),selector.getValue().toString());
            rigthPane.getChildren().removeAll(priceTitle,selector,weight,sugar, fat, saturated,proteins, salt, carbohydrates, categoriesTitle, nutritionInfo, energy, priceValue, contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
            contentPane.getChildren().remove(imageView);
            this.initializeDishesScreen();

            selectedDish = eb.getDish(updateDish.getName());
            allergensByDish = eb.getAllergensbyDish(selectedDish.getIdDishes());
            int numImage = 1;
            iconsHolder.getChildren().clear();

            for(Allergens allergen:allergensByDish)
            {
                ImageView image = new ImageView(new Image("Resources/AWIcons/"+allergen.getImage()));
                image.setFitHeight(50);
                image.setFitWidth(40);
                if(numImage<8) {
                    image.setLayoutY(35);
                    image.setLayoutX(50*(numImage-1));
                }

                else {

                    image.setLayoutY(85);
                    image.setLayoutX(50*(numImage-8));
                }

                numImage+=1;

                iconsHolder.getChildren().add(image);

            }

            this.selectedDishShow();



        });

        removeButton.setOnAction((ActionEvent remove)->{

            eb.removeDish(selectedDish.getIdDishes());
            rigthPane.getChildren().removeAll(selector,weight,sugar, fat,proteins,saturated, salt, carbohydrates, categoriesTitle, nutritionInfo, energy, priceValue, contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
            contentPane.getChildren().remove(imageView);
            this.initializeDishesScreen();

        });


        //priceTitle.setLayoutX(priceTitle.getLayoutX()-40);
        priceTitle.setText("- PRICE: ");
        priceValue.setLayoutY(priceTitle.getLayoutY()+5);
        priceValue.setLayoutX(priceTitle.getLayoutX()+150);
        priceValue.setPrefSize(60,10);
        priceValue.setMaxSize(60,10);
        priceValue.setText(Double.toString(selectedDish.getPrice()));


        nameEnter.setLayoutX(50);
        nameEnter.setLayoutY(30);
        nameEnter.setText(selectedDish.getName());

        /*descriptionTitle.setLayoutX(360);
        descriptionTitle.setLayoutY(400);*/

        descriptionEnter.setLayoutX(600);
        descriptionEnter.setLayoutY(630);
        descriptionEnter.setMaxHeight(100);
        descriptionEnter.setMaxWidth(350);
        descriptionEnter.setWrapText(true);
        descriptionEnter.setText(selectedDish.getDescription());

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


        //nutritionTitle.setLayoutX(360);
       // nutritionInfo.setLayoutX(360);
        nutritionInfo.setText("Energy: \n\n"+
                "Carbohydrates: \n\n"+
                "Salt: \n\n"+
                "Saturated Fat: \n\n"+
                "Fat: \n\n"+
                "Sugar: \n\n"+
                "Weight: \n\n"+
                "Proteins: ");
        energy = new TextField();
        energy.setLayoutY(nutritionInfo.getLayoutY());
        energy.setLayoutX(nutritionInfo.getLayoutX()+200);
        energy.setText(selectedDish.getEnergy().toString());
        energy.setPrefSize(70,10);

        carbohydrates = new TextField();
        carbohydrates.setLayoutY(nutritionInfo.getLayoutY()+53);
        carbohydrates.setLayoutX(nutritionInfo.getLayoutX()+200);
        carbohydrates.setText(selectedDish.getCarboHydrates().toString());
        carbohydrates.setPrefSize(70,10);

        salt = new TextField();
       salt.setLayoutY(nutritionInfo.getLayoutY()+103);
        salt.setLayoutX(nutritionInfo.getLayoutX()+200);
        salt.setText(selectedDish.getSalt().toString());
        salt.setPrefSize(70,10);

        saturated = new TextField();
        saturated.setLayoutY(nutritionInfo.getLayoutY()+153);
        saturated.setLayoutX(nutritionInfo.getLayoutX()+200);
        saturated.setText(selectedDish.getSaturedFat().toString());
        saturated.setPrefSize(70,10);

        fat = new TextField();
        fat.setLayoutY(nutritionInfo.getLayoutY()+206);
        fat.setLayoutX(nutritionInfo.getLayoutX()+200);
        fat.setText(selectedDish.getFat().toString());
        fat.setPrefSize(70,10);

        sugar = new TextField();
        sugar.setLayoutY(nutritionInfo.getLayoutY()+260);
        sugar.setLayoutX(nutritionInfo.getLayoutX()+200);
        sugar.setText(selectedDish.getSugars().toString());
        sugar.setPrefSize(70,10);

        weight = new TextField();
        weight.setLayoutY(nutritionInfo.getLayoutY()+310);
        weight.setLayoutX(nutritionInfo.getLayoutX()+200);
        weight.setText(selectedDish.getWeight().toString());
        weight.setPrefSize(70,10);

        proteins = new TextField();
        proteins.setLayoutY(nutritionInfo.getLayoutY()+360);
        proteins.setLayoutX(nutritionInfo.getLayoutX()+200);
        proteins.setText(selectedDish.getProteins().toString());
        proteins.setPrefSize(70,10);

        List<Category> categories = eb.getCategories();

        selector = new ChoiceBox();
        String assignCategory = eb.getCategoryByDish(selectedDish.getIdDishes());
        for (Category category:categories) {
            if(assignCategory.equals(category.getName()))
                selector.setValue(category.getName());

            selector.getItems().add(category.getName());

        }


        selector.setLayoutX(categoriesTitle.getLayoutX()+230);
        selector.setLayoutY(categoriesTitle.getLayoutY()+3);

        contentPane.getChildren().add(imageView);
        rigthPane.getChildren().addAll(descriptionTitle, selector,weight,sugar, fat, saturated,proteins, salt, carbohydrates, categoriesTitle, nutritionInfo, energy, priceValue, contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
        leftPane.setDisable(true);


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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDishesScreen();
        ImageView logo = new ImageView(new Image("/Resources/welcome.png"));
        Label dishesTitle = new Label();
        logo.maxHeight(40);
        logo.maxWidth(900 / 5);
        logo.setFitHeight(40);
        logo.setFitWidth(900 / 5);
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
        back.setOnAction((ActionEvent e) -> {
            spinner.setVisible(true);
            Transition transition = new Transition();
            transition.changeScene(rootPane, 0, "/editor/SelectionMenu.fxml");
            new FadeOutRightBig(topPane).play();
            new FadeOutRightBig(rigthPane).play();
            new SlideOutDown(scrollPane).setSpeed(1).play();
            new SlideOutDown(leftPane).play();
        });
        dishesTitle.setText("Dish");
        dishesTitle.setFont(Font.font("DIN Alternate Bold", 30));
        dishesTitle.setTextFill(Paint.valueOf("white"));
        dishesTitle.setLayoutX(400);

        topPane.getChildren().addAll(dishesTitle, logo, back);
    }

    private void initializeDishesScreen(){

        rigthPane.getChildren().clear();
        eb = new EditorBusiness();
        imageView = new ImageView();
        description = new Text();
        descriptionTitle = new Label();
        imageTitle = new Label();
        categoriesTitle = new Label();
        nutritionTitle = new Label();
        priceTitle = new Label();
        addButton = new JFXButton();
        nutritionInfo = new Label();
        editDish = new JFXButton();
        editAllergens = new JFXButton();
        iconsHolder = new AnchorPane();
        category = new Label();
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


        imageView.setLayoutX(50);
        imageView.setLayoutY(80);
        imageView.setFitWidth(500);
        imageView.setFitHeight(400);
        imageTitle.setLayoutX(50);
        imageTitle.setLayoutY(30);
        imageTitle.setFont(Font.font("DIN Alternate Bold", 30));

        descriptionTitle.setLayoutX(600);
        descriptionTitle.setLayoutY(580);
        descriptionTitle.setText("- DESCRIPTION:");
        descriptionTitle.setFont(Font.font("DIN Alternate Bold", 30));

        categoriesTitle.setLayoutX(80);
        categoriesTitle.setLayoutY(500);
        categoriesTitle.setText("- CATEGORY:");
        categoriesTitle.setFont(Font.font("DIN Alternate Bold", 30));



        nutritionTitle.setLayoutX(600);
        nutritionTitle.setLayoutY(100);
        nutritionTitle.setText("- NUTRITION FACTS:");
        nutritionTitle.setFont(Font.font("DIN Alternate Bold", 30));



        priceTitle.setLayoutX(80);
        priceTitle.setLayoutY(560);
        priceTitle.setFont(Font.font("DIN Alternate Bold", 30));


       description.setLayoutX(620);
        description.setLayoutY(650);
        description.setFont(Font.font(16));

        description.setWrappingWidth(300);
        description.maxHeight(100);
        description.maxWidth(300);



        nutritionInfo.setLayoutX(620);
        nutritionInfo.setLayoutY(160);
        nutritionInfo.setFont(Font.font(20));

        category.setLayoutX(categoriesTitle.getLayoutX()+230);
        category.setLayoutY(categoriesTitle.getLayoutY()+3);
        category.setFont(Font.font("DIN Alternate Bold", 25));


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



        this.setButton(editDish, 20, 60, (float)(width - width / 5) - 130, 20, "edit", "#f4c542", "white", 16);
        editDish.setVisible(true);
        editDish.setOnAction((ActionEvent event)->{
            this.onClickEdit(event);
        });


        this.setButton(editAllergens, 10, 300, 80, 630, "WARNING & ALLERGENS (click to edit):", "green", "white", 16);
        editAllergens.setVisible(true);
        editAllergens.setTextAlignment(TextAlignment.LEFT);

        editAllergens.setOnAction(e -> {
            this.onClickEditAllergens();
        });


        List<Dish> dishes = eb.getDishes();
        if(dishes.size() >= 1 ) {
            Image preview = new Image(FTPURL.getImageURL()+"/DishesImages/" + dishes.get(0).getImage());
            this.selectedImage = preview;
            imageView.setImage(preview);
            description.setText(dishes.get(0).getDescription());
            imageTitle.setText(dishes.get(0).getName());
            nutritionInfo.setText(nutritionInfoText(dishes.get(0)));
            priceTitle.setText("- PRICE: " + dishes.get(0).getPrice() + "€");
            category.setText(eb.getCategoryByDish(dishes.get(0).getIdDishes()));
            selectedDish=dishes.get(0);
            allergensByDish = eb.getAllergensbyDish(dishes.get(0).getIdDishes());
            int numImage = 1;
            iconsHolder.setLayoutX(80);
            iconsHolder.setLayoutY(650);
            iconsHolder.getChildren().add(editAllergens);
            iconsHolder.setOnMouseClicked(e->{
                this.onClickEditAllergens();
            });

            for(Allergens allergen:allergensByDish)
            {
                ImageView image = new ImageView(new Image("Resources/AWIcons/"+allergen.getImage()));
                image.setFitHeight(50);
                image.setFitWidth(40);
                if(numImage<8) {
                    image.setLayoutY(35);
                    image.setLayoutX(50*(numImage-1));
                }

                else {

                    image.setLayoutY(85);
                    image.setLayoutX(50*(numImage-8));
                }

                numImage+=1;

                iconsHolder.getChildren().add(image);

            }
        }

        if ((dishes.size()) * 60 > height-40) {
            leftPane.setPrefHeight((dishes.size()) * 60);
        } else {
            leftPane.setPrefHeight(height-40);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }

        leftPane.setPrefWidth(width / 5);
        leftPane.setDisable(false);
        leftPane.getChildren().clear();
        leftPane.setStyle("-fx-background-color: black");

        topPane.getChildren().addAll(addButton,generateXML);

        if(dishes.size()>0) {
            this.createLabels(dishes);
            scrollPane.setContent(leftPane);
            rigthPane.getChildren().addAll(category, iconsHolder, editDish,editAllergens, nutritionInfo, descriptionTitle,categoriesTitle,nutritionTitle,priceTitle, imageView, description, imageTitle);

        }


    }


}
