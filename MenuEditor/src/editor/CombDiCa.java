package editor;

import java.util.List;

public class CombDiCa {

    private Category category;
    private List<Dish> dishes;

    public void setCategory(Category category){

        this.category = category;
    }

    public void setDishes(List<Dish> dishes){

        this.dishes = dishes;
    }

    public Category getCategory(){

        return this.category;
    }

    public List<Dish> getDishes(){

        return this.dishes;
    }


}
