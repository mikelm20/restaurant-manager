package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name= "menu")
public class Menu {

    @Attribute(name="r-name", required = false)
    private String restaurantName;
    @Attribute(name = "info-1" , required = false)
    private String primaryColor;
    @Attribute(name = "info-2" , required = false)
    private String secondaryColor;

    @ElementList(name = "category",required=false, inline=true)
    private List<Category> categories;

    public Menu(String restaurantName, List<Category> categories) {
        this.restaurantName = restaurantName;
        this.categories = categories;
    }

    public Menu(){

    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }
}
