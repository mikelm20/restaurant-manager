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
    private JFXButton editAllergens;
    private JFXButton editDish;
    private Text description;
    private Label tituloDescripcion;
    private Label tituloImagen;
    private Label tituloCategorias;
    private Label categoria;
    private Label tituloNutricion;
    private Label tituloPrecio;
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
    private List<Allergens> alergenos;
    private List<JFXCheckBox> allergensCheck;
    private boolean allerChecked[];
    private List<Allergens> allergensByDish;
    private TextField priceValue;
    private AnchorPane iconsHolder;
    private Label tituloAlergenos;
    private TextField energy;
    private TextField carbohydrates;
    private TextField salt;
    private TextField saturated;
    private TextField fat;
    private TextField sugar;
    private TextField weight;
    private ChoiceBox selector;
    private Dish selectedShowDish;



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

    private String nutritionInfoText(Dish plato) {
        return "Energy: "+plato.getEnergy().toString()+"\n"+
                "Carbohydrates: "+plato.getCarboHydrates().toString()+"\n"+
                "Salt: " + plato.getSalt().toString() + "\n"+
                "Saturated Fat: " +plato.getSaturedFat().toString()+"\n"+
                "Fat: "+plato.getFat().toString()+"\n"+
                "Sugar: "+plato.getSugars().toString()+"\n"+
                "Weight: "+plato.getWeight().toString();
    }

    private void createLabels(List<Dish> platos) {
        int x = 0, y = 0;
        for (Dish dish : platos) {
            Label di = new Label();
            di.setText(dish.getName());
            di.setFont(Font.font("DIN Alternate Bold", 18));
            di.setAlignment(Pos.CENTER);
            di.setPrefWidth(900 / 5);
            di.setPrefHeight(60);
            di.setMaxHeight(di.getPrefHeight());
            di.setMaxWidth(di.getPrefWidth());
            di.setLayoutX(x);
            di.setLayoutY(y);
            di.setStyle("-fx-background-color: black");
            di.setTextFill(Paint.valueOf("white"));
            y = y + 60;
            di.setOnMouseEntered(e -> {


                rigthPane.getChildren().removeAll(tituloPrecio,nutritionInfo, imageView, description, tituloImagen);
                String name;
                String[] lista = e.getSource().toString().split("'");
                name = lista[lista.length - 1];
                Dish plato = eb.getDish(name);
                System.out.println(plato.getImage());
                Image imagen = new Image("/Resources/DishesImages/"+plato.getImage());
                this.selectedImage = imagen;
                imageView.setImage(imagen);
                description.setText(plato.getDescription());
                tituloImagen.setText(name);
                nutritionInfo.setText(nutritionInfoText(plato));
                tituloPrecio.setText("PRECIO: "+plato.getPrice() +"€");
                rigthPane.getChildren().addAll(tituloPrecio,nutritionInfo, imageView, description, tituloImagen);
                new Pulse(di).setSpeed(1).play();
                di.setStyle("-fx-background-color: #ffc040");
                this.selectedDish = plato;
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
        rigthPane.getChildren().clear();
        leftPane.setDisable(true);
        topPane.getChildren().remove(addButton);
        Label tituloAlergenos = new Label();
        tituloAlergenos.setLayoutX(50);
        tituloAlergenos.setLayoutY(80);
        tituloAlergenos.setText("SELECT THE ALLERGENS: ");
        tituloAlergenos.setFont(Font.font("DIN Alternate Bold", 30));

        alergenos = eb.getAllergens();
        saveButton = new JFXButton();
        this.setButton(saveButton, 40, 60, (900-900/5)-100, 30, "save","#f4c542","white",16);
        saveButton.setVisible(true);
        cancelButton = new JFXButton();
        this.setButton(cancelButton, 40, 80, (900-900/5)-185,30,"cancel","red","white",16);
        cancelButton.setVisible(true);

        cancelButton.setOnAction((ActionEvent back)->{
            rigthPane.getChildren().clear();
            this.initializeDishesScreen();

            this.selectedDishShow();
        });
        saveButton.setOnAction((ActionEvent save)->{
            this.onClickSaveAllergens(save);
        });

        rigthPane.getChildren().addAll(saveButton,cancelButton, tituloAlergenos);
        int y=100;
        JFXCheckBox checkBox;
        ImageView imagen;
        allergensCheck = new ArrayList<JFXCheckBox>();
        allergensByDish = eb.getAllergensbyDish(selectedShowDish.getIdDishes());

        for(Allergens alergeno : alergenos) {

            checkBox = new JFXCheckBox();
            imagen = new ImageView(new Image("Resources/AWIcons/" + alergeno.getImage()));
            imagen.setFitWidth(40);
            imagen.setFitHeight(60);
            checkBox.setId(Integer.toString(alergeno.getId()));
            if(allergensByDish!=null) {
                if (allergensByDish.contains(alergeno)) {
                    checkBox.setSelected(true);
                }
            }

            if (alergeno.getId()<4 ) {

                imagen.setLayoutX(70);
                checkBox.setLayoutX(50);
                imagen.setLayoutY(40+(y*alergeno.getId()));
                checkBox.setLayoutY(50+(y*alergeno.getId()));

            }
            else if(alergeno.getId()<7) {

                imagen.setLayoutX(200);
                checkBox.setLayoutX(180);
                imagen.setLayoutY(40+(y*(alergeno.getId()-3)));
                checkBox.setLayoutY(50+(y*(alergeno.getId()-3)));

            }
            else if(alergeno.getId()<10) {

                imagen.setLayoutX(330);
                checkBox.setLayoutX(310);
                imagen.setLayoutY(40+(y*(alergeno.getId()-6)));
                checkBox.setLayoutY(50+(y*(alergeno.getId()-6)));

            }
             else if(alergeno.getId()<13){
                imagen.setLayoutX(460);
                checkBox.setLayoutX(440);
                imagen.setLayoutY(40+(y*(alergeno.getId()-9)));
                checkBox.setLayoutY(50+(y*(alergeno.getId()-9)));
            }
             else {
                imagen.setLayoutX(590);
                checkBox.setLayoutX(570);
                imagen.setLayoutY(40+(y*(alergeno.getId()-12)));
                checkBox.setLayoutY(50+(y*(alergeno.getId()-12)));

            }



            allergensCheck.add(checkBox);
            rigthPane.getChildren().addAll(checkBox, imagen);

        }
    }

    private void selectedDishShow() {
        tituloDescripcion.setVisible(true);
        rigthPane.getChildren().removeAll(imageView, description, tituloImagen);
        editDish.setVisible(true);
        String name = selectedShowDish.getName();
        Dish dish = eb.getDish(name);
        Image imagen = new Image("/Resources/DishesImages/"+dish.getImage());
        this.selectedImage = imagen;
        imageView.setImage(imagen);
        description.setText(dish.getDescription());
        tituloImagen.setText(name);
        rigthPane.getChildren().addAll(imageView, description, tituloImagen);
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

        rigthPane.getChildren().removeAll(categoria, tituloCategorias, imageView, description, editDish,editAllergens, tituloImagen,iconsHolder,nutritionInfo);
        rigthPane.getChildren().clear();
        topPane.getChildren().removeAll(addButton);
        saveButton = new JFXButton();
        cancelButton = new JFXButton();
        nameEnter = new TextField();
        descriptionEnter = new TextArea();
        contentPane = new StackPane();
        priceValue = new TextField();
        tituloAlergenos = new Label();
        imageView = new ImageView(new Image("/Resources/draganddrop.png"));
        selectedShowDish = selectedDish;

        this.setButton(saveButton, 20, 60, (900-900/5)-100, 20, "save","#f4c542","white",16);
        this.setButton(cancelButton, 20, 80, (900-900/5)-185,20,"cancel","red","white",16);
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
                Path dest = Paths.get("/home/user/IdeaProjects/MenuEditor/src/Resources/DishesImages/"+uploadedImage.getName());
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
            newDish.setSugars(Double.parseDouble(sugar.getText()));
            newDish.setSalt(Double.parseDouble(salt.getText()));
            newDish.setFat(Double.parseDouble(fat.getText()));
            newDish.setWeight(Double.parseDouble(weight.getText()));
            newDish.setCarboHydrates(Double.parseDouble(carbohydrates.getText()));


            rigthPane.getChildren().removeAll(tituloPrecio,selector,weight,sugar, fat, saturated, salt, carbohydrates, tituloCategorias, nutritionInfo, energy, priceValue, contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
            contentPane.getChildren().remove(imageView);
            eb.addDish(newDish, selector.getValue().toString());
            selectedShowDish = eb.getDish(newDish.getName());
            this.onClickEditAllergens();

        });

        tituloPrecio.setText("PRECIO: ");
        priceValue.setLayoutY(tituloPrecio.getLayoutY());
        priceValue.setLayoutX(tituloPrecio.getLayoutX()+75);
        priceValue.setPrefSize(60,10);
        priceValue.setMaxSize(60,10);
        priceValue.setPromptText(Double.toString(0)+"€");


        nameEnter.setLayoutX(50);
        nameEnter.setLayoutY(30);
        nameEnter.setPromptText("NAME");

        tituloDescripcion.setLayoutX(360);
        tituloDescripcion.setLayoutY(370);

        descriptionEnter.setLayoutX(360);
        descriptionEnter.setLayoutY(400);
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


        tituloNutricion.setLayoutX(410);
        nutritionInfo.setLayoutX(410);
        nutritionInfo.setLayoutY(130);
        nutritionInfo.setText("Energy: \n\n"+
                "Carbohydrates: \n\n"+
                "Salt: \n\n"+
                "Saturated Fat: \n\n"+
                "Fat: \n\n"+
                "Sugar: \n\n"+
                "Weight: ");
        energy = new TextField();
        energy.setLayoutY(nutritionInfo.getLayoutY()-7);
        energy.setLayoutX(nutritionInfo.getLayoutX()+160);
        energy.setPromptText("0.0");
        energy.setPrefSize(70,10);

        carbohydrates = new TextField();
        carbohydrates.setLayoutY(nutritionInfo.getLayoutY()+30);
        carbohydrates.setLayoutX(nutritionInfo.getLayoutX()+160);
        carbohydrates.setPromptText("0.0");
        carbohydrates.setPrefSize(70,10);

        salt = new TextField();
        salt.setLayoutY(nutritionInfo.getLayoutY()+63);
        salt.setLayoutX(nutritionInfo.getLayoutX()+160);
        salt.setPromptText("0.0");
        salt.setPrefSize(70,10);

        saturated = new TextField();
        saturated.setLayoutY(nutritionInfo.getLayoutY()+96);
        saturated.setLayoutX(nutritionInfo.getLayoutX()+160);
        saturated.setPromptText("0.0");
        saturated.setPrefSize(70,10);

        fat = new TextField();
        fat.setLayoutY(nutritionInfo.getLayoutY()+132);
        fat.setLayoutX(nutritionInfo.getLayoutX()+160);
        fat.setPromptText("0.0");
        fat.setPrefSize(70,10);

        sugar = new TextField();
        sugar.setLayoutY(nutritionInfo.getLayoutY()+165);
        sugar.setLayoutX(nutritionInfo.getLayoutX()+160);
        sugar.setPromptText("0.0");
        sugar.setPrefSize(70,10);

        weight = new TextField();
        weight.setLayoutY(nutritionInfo.getLayoutY()+197);
        weight.setLayoutX(nutritionInfo.getLayoutX()+160);
        weight.setPromptText("0.0");
        weight.setPrefSize(70,10);

        List<Category> categorias = eb.getCategories();

        selector = new ChoiceBox();
        for (Category categoria:categorias) {

            selector.getItems().add(categoria.getName());

        }

        selector.setValue(selector.getItems().get(0));
        selector.setLayoutX(tituloCategorias.getLayoutX());
        selector.setLayoutY(tituloCategorias.getLayoutY()+30);

        contentPane.getChildren().add(imageView);
        rigthPane.getChildren().addAll(selector,weight,sugar, fat, saturated, salt, carbohydrates, nutritionInfo, energy, priceValue, contentPane,descriptionEnter, cancelButton, saveButton,nameEnter);

        rigthPane.getChildren().addAll(tituloAlergenos,tituloCategorias,tituloNutricion,tituloDescripcion,tituloPrecio);
        leftPane.setDisable(true);






    }

    public void onClickEdit(ActionEvent editEvent) {
        rigthPane.getChildren().removeAll(tituloDescripcion,categoria, tituloCategorias, imageView, description, editDish,editAllergens, tituloImagen,iconsHolder,nutritionInfo);
        topPane.getChildren().removeAll(addButton);
        saveButton = new JFXButton();
        removeButton = new JFXButton();
        cancelButton = new JFXButton();
        nameEnter = new TextField();
        descriptionEnter = new TextArea();
        contentPane = new StackPane();
        priceValue = new TextField();
        tituloAlergenos = new Label();
        selectedShowDish = selectedDish;



        tituloAlergenos.setLayoutX(360);
        tituloAlergenos.setLayoutY(400);
        tituloAlergenos.setText("ALLERGENS & WARNINGS:");
        tituloAlergenos.setFont(Font.font("DIN Alternate Bold", 20));

        this.setButton(saveButton, 20, 60, (900-900/5)-100, 20, "save","#f4c542","white",16);
        this.setButton(removeButton, 20, 80, (900-900/5)-270, 20, "remove","red","white",16);
        this.setButton(cancelButton, 20, 80, (900-900/5)-185,20,"cancel","#f4c542","white",16);
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
                Path dest = Paths.get("/home/user/IdeaProjects/MenuEditor/src/Resources/DishesImages/"+uploadedImage.getName());
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
            updateDish.setSugars(Double.parseDouble(sugar.getText()));
            updateDish.setSalt(Double.parseDouble(salt.getText()));
            updateDish.setFat(Double.parseDouble(fat.getText()));
            updateDish.setWeight(Double.parseDouble(weight.getText()));
            updateDish.setCarboHydrates(Double.parseDouble(carbohydrates.getText()));

            eb.updateDish(updateDish,selectedDish.getIdDishes(),selector.getValue().toString());
            rigthPane.getChildren().removeAll(tituloPrecio,selector,weight,sugar, fat, saturated, salt, carbohydrates, tituloCategorias, nutritionInfo, energy, priceValue, contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
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
            rigthPane.getChildren().removeAll(selector,weight,sugar, fat, saturated, salt, carbohydrates, tituloCategorias, nutritionInfo, energy, priceValue, contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
            contentPane.getChildren().remove(imageView);
            this.initializeDishesScreen();

        });


        tituloPrecio.setLayoutX(tituloPrecio.getLayoutX()-40);
        tituloPrecio.setText("PRECIO: ");
        priceValue.setLayoutY(tituloPrecio.getLayoutY());
        priceValue.setLayoutX(tituloPrecio.getLayoutX()+75);
        priceValue.setPrefSize(60,10);
        priceValue.setMaxSize(60,10);
        priceValue.setText(Double.toString(selectedDish.getPrice()));


        nameEnter.setLayoutX(50);
        nameEnter.setLayoutY(30);
        nameEnter.setText(selectedDish.getName());

        tituloDescripcion.setLayoutX(360);
        tituloDescripcion.setLayoutY(370);

        descriptionEnter.setLayoutX(360);
        descriptionEnter.setLayoutY(400);
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


        tituloNutricion.setLayoutX(360);
        nutritionInfo.setLayoutX(360);
        nutritionInfo.setText("Energy: \n\n"+
                "Carbohydrates: \n\n"+
                "Salt: \n\n"+
                "Saturated Fat: \n\n"+
                "Fat: \n\n"+
                "Sugar: \n\n"+
                "Weight: ");
        energy = new TextField();
        energy.setLayoutY(nutritionInfo.getLayoutY()-7);
        energy.setLayoutX(nutritionInfo.getLayoutX()+110);
        energy.setText(selectedDish.getEnergy().toString());
        energy.setPrefSize(70,10);

        carbohydrates = new TextField();
        carbohydrates.setLayoutY(nutritionInfo.getLayoutY()+30);
        carbohydrates.setLayoutX(nutritionInfo.getLayoutX()+110);
        carbohydrates.setText(selectedDish.getCarboHydrates().toString());
        carbohydrates.setPrefSize(70,10);

        salt = new TextField();
       salt.setLayoutY(nutritionInfo.getLayoutY()+63);
        salt.setLayoutX(nutritionInfo.getLayoutX()+110);
        salt.setText(selectedDish.getSalt().toString());
        salt.setPrefSize(70,10);

        saturated = new TextField();
        saturated.setLayoutY(nutritionInfo.getLayoutY()+96);
        saturated.setLayoutX(nutritionInfo.getLayoutX()+110);
        saturated.setText(selectedDish.getSaturedFat().toString());
        saturated.setPrefSize(70,10);

        fat = new TextField();
        fat.setLayoutY(nutritionInfo.getLayoutY()+132);
        fat.setLayoutX(nutritionInfo.getLayoutX()+110);
        fat.setText(selectedDish.getFat().toString());
        fat.setPrefSize(70,10);

        sugar = new TextField();
        sugar.setLayoutY(nutritionInfo.getLayoutY()+165);
        sugar.setLayoutX(nutritionInfo.getLayoutX()+110);
        sugar.setText(selectedDish.getSugars().toString());
        sugar.setPrefSize(70,10);

        weight = new TextField();
        weight.setLayoutY(nutritionInfo.getLayoutY()+197);
        weight.setLayoutX(nutritionInfo.getLayoutX()+110);
        weight.setText(selectedDish.getWeight().toString());
        weight.setPrefSize(70,10);

        List<Category> categorias = eb.getCategories();

        selector = new ChoiceBox();
        String assignCategory = eb.getCategoryByDish(selectedDish.getIdDishes());
        for (Category categoria:categorias) {
            if(assignCategory.equals(categoria.getName()))
                selector.setValue(categoria.getName());

            selector.getItems().add(categoria.getName());

        }


        selector.setLayoutX(tituloCategorias.getLayoutX());
        selector.setLayoutY(tituloCategorias.getLayoutY()+30);

        contentPane.getChildren().add(imageView);
        rigthPane.getChildren().addAll(tituloDescripcion, selector,weight,sugar, fat, saturated, salt, carbohydrates, tituloCategorias, nutritionInfo, energy, priceValue, contentPane,descriptionEnter,removeButton, cancelButton, saveButton,nameEnter);
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
        Label tituloPlatos = new Label();
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
        tituloPlatos.setText("Dish");
        tituloPlatos.setFont(Font.font("DIN Alternate Bold", 30));
        tituloPlatos.setTextFill(Paint.valueOf("white"));
        tituloPlatos.setLayoutX(400);

        topPane.getChildren().addAll(tituloPlatos, logo, back);
    }

    private void initializeDishesScreen(){

        rigthPane.getChildren().clear();
        eb = new EditorBusiness();
        imageView = new ImageView();
        description = new Text();
        tituloDescripcion = new Label();
        tituloImagen = new Label();
        tituloCategorias = new Label();
        tituloNutricion = new Label();
        tituloPrecio = new Label();
        addButton = new JFXButton();
        nutritionInfo = new Label();
        editDish = new JFXButton();
        editAllergens = new JFXButton();
        iconsHolder = new AnchorPane();
        categoria = new Label();



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

        tituloDescripcion.setLayoutX(380);
        tituloDescripcion.setLayoutY(300);
        tituloDescripcion.setText("DESCRIPTION:");
        tituloDescripcion.setFont(Font.font("DIN Alternate Bold", 20));

        tituloCategorias.setLayoutX(30);
        tituloCategorias.setLayoutY(300);
        tituloCategorias.setText("CATEGORIA:");
        tituloCategorias.setFont(Font.font("DIN Alternate Bold", 20));



        tituloNutricion.setLayoutX(380);
        tituloNutricion.setLayoutY(90);
        tituloNutricion.setText("INFORMACIÓN NUTRICIONAL:");
        tituloNutricion.setFont(Font.font("DIN Alternate Bold", 20));



        tituloPrecio.setLayoutX(220);
        tituloPrecio.setLayoutY(300);
        tituloPrecio.setFont(Font.font("DIN Alternate Bold", 20));


       description.setLayoutX(380);
        description.setLayoutY(342);

        description.setWrappingWidth(300);
        description.maxHeight(100);
        description.maxWidth(300);


        nutritionInfo.setLayoutX(380);
        nutritionInfo.setLayoutY(130);

        categoria.setLayoutX(140);
        categoria.setLayoutY(300);
        categoria.setFont(Font.font("DIN Alternate Bold", 20));


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



        this.setButton(editDish, 20, 60, (900 - 900 / 5) - 100, 20, "edit", "#f4c542", "white", 16);
        editDish.setVisible(true);
        editDish.setOnAction((ActionEvent event)->{
            this.onClickEdit(event);
        });


        this.setButton(editAllergens, 10, 300, 15, 390, "WARNING & ALLERGENS (click to edit):", "green", "white", 16);
        editAllergens.setVisible(true);
        editAllergens.setTextAlignment(TextAlignment.LEFT);

        editAllergens.setOnAction(e -> {
            this.onClickEditAllergens();
        });


        List<Dish> platos = eb.getDishes();
        if(platos.size() >= 1 ) {
            Image imagen = new Image("Resources/DishesImages/" + platos.get(0).getImage());
            this.selectedImage = imagen;
            imageView.setImage(imagen);
            description.setText(platos.get(0).getDescription());
            tituloImagen.setText(platos.get(0).getName());
            nutritionInfo.setText(nutritionInfoText(platos.get(0)));
            tituloPrecio.setText("PRECIO: " + platos.get(0).getPrice() + "€");
            categoria.setText(eb.getCategoryByDish(platos.get(0).getIdDishes()));
            selectedDish=platos.get(0);
            allergensByDish = eb.getAllergensbyDish(platos.get(0).getIdDishes());
            int numImage = 1;
            iconsHolder.setLayoutX(15);
            iconsHolder.setLayoutY(400);
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

        if ((platos.size()) * 60 > 560) {
            leftPane.setPrefHeight((platos.size()) * 60);
        } else {
            leftPane.setPrefHeight(560);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }

        leftPane.setPrefWidth(900 / 5);
        leftPane.setDisable(false);
        leftPane.getChildren().clear();
        leftPane.setStyle("-fx-background-color: black");

        topPane.getChildren().add(addButton);

        if(platos.size()>0) {
            this.createLabels(platos);
            scrollPane.setContent(leftPane);
            rigthPane.getChildren().addAll(categoria, iconsHolder, editDish,editAllergens, nutritionInfo, tituloDescripcion,tituloCategorias,tituloNutricion,tituloPrecio, imageView, description, tituloImagen);

        }


    }


}
