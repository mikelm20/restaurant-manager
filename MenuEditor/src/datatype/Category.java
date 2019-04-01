package datatype;

import editor.FTPURL;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="category")
public class Category {

    @Attribute(name="name",required = false)
    private String name;

    @Element(name = "imageURL",required = false)
    private String imageFileDirectory;

    @ElementList(name = "dish",required = false, inline = true)
    private List<Dish> dishes;

    private int itemNumber;

    public Category(String name,List<Dish> dishes) {
        this.name = name;
        this.dishes = dishes;
    }

    public Category(){

    }

    public Category(editor.Category category,List<Dish> dishList){
        if(category !=null){
            this.name = category.getName();
            this.imageFileDirectory = FTPURL.getImageURL()+"/CategoriesImages/"+category.getImage();
            this.dishes = dishList;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public String getImageFileDirectory() {
        return imageFileDirectory;
    }

    public void setImageFileDirectory(String imageFileDirectory) {
        this.imageFileDirectory = imageFileDirectory;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }
}
