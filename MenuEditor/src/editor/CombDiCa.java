package editor;

import java.util.List;

public class CombDiCa {

    private Category categoria;
    private List<Dish> dishes;

    public void setCategory(Category categoria){

        this.categoria = categoria;
    }

    public void setDishes(List<Dish> dishes){

        this.dishes = dishes;
    }

    public Category getCategoria(){

        return this.categoria;
    }

    public List<Dish> getDishes(){

        return this.dishes;
    }


}
