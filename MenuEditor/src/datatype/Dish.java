package datatype;


import editor.FTPURL;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;
import java.util.ListResourceBundle;

@Root(name = "dish")
public class Dish {

    @Attribute(name = "id",required = false)
    private int dishID;
    @Element(name = "imageURL",required = false)
    private String imageFileDirectory;
    @Element(name = "name", required = false)
    private String name;
    @Element(name = "description", required = false)
    private String description;
    @Element(name = "price", required = false)
    private Price price;
    @ElementList(name="allergens", required = false, inline=true)
    private List<Allergen> allergens;
    @ElementList(name="warnings", required = false, inline=true)
    private List<Warning> warnings;
    @Element(name="composition", required = false)
    private EnergeticComposition energeticComposition;
    @Element(name = "weight",required = false)
    private Weight weight;
    @Element(name = "rating",required = false)
    private Float rating;

    public Dish(int dishID, String name, String description, Price price, List<Allergen> allergens, List<Warning> warnings, EnergeticComposition energeticComposition, Weight weight) {
        this.dishID = dishID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.allergens = allergens;
        this.warnings = warnings;
        this.energeticComposition = energeticComposition;
        this.weight = weight;
    }

    public Dish(){

    }

    public Dish(editor.Dish dish, EnergeticComposition energeticComposition, List<Allergen> allergens, List<Warning> warnings){
        if(dish!=null){
            this.dishID=dish.getIdDishes();
            this.name=dish.getName();
            this.description=dish.getDescription();
            //TODO: Add image file root
            this.imageFileDirectory = FTPURL.getImageURL()+"/DishesImages/" +dish.getImage();
            this.price=new Price("€",Double.valueOf(dish.getPrice()).floatValue());
            this.weight=new Weight("g",dish.getWeight().floatValue());
            this.energeticComposition = energeticComposition;
            this.allergens = allergens;
            this.warnings=warnings;
        }
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public List<Allergen> getAllergens() {
        return allergens;
    }

    public void setAllergens(List<Allergen> allergens) {
        this.allergens = allergens;
    }

    public List<Warning> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }

    public EnergeticComposition getEnergeticComposition() {
        return energeticComposition;
    }

    public void setEnergeticComposition(EnergeticComposition energeticComposition) {
        this.energeticComposition = energeticComposition;
    }

    public String getImageFileDirectory() {
        return imageFileDirectory;
    }

    public void setImageFileDirectory(String imageFileDirectory) {
        this.imageFileDirectory = imageFileDirectory;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public boolean[] getBooleanAllergens() {
        boolean list [] = new boolean[14];
        int pos = 0;

        for (boolean mBoolean :list) {
            list[pos] = false;
            pos++;
        }

        if( allergens != null && !allergens.isEmpty()) {
            for (Allergen mInt : allergens) {
                list[mInt.getAllergen() - 1] = true;
            }
        }

        return list;
    }

    public boolean[] getBooleanWarnigns(){
        boolean list [] = new boolean[5];
        int pos = 0;

        for (boolean mBoolean :list) {
            list[pos] = false;
            pos++;
        }

       if(warnings!= null && !warnings.isEmpty()) {

            for (Warning mInt : warnings) {
                list[mInt.getWarning() - 1] = true;
            }
        }

        return list;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
