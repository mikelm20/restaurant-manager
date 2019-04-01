package datatype;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name="allergens")
public class Allergen {

    @Text
    private int allergen;

    public Allergen(){

    }

    public Allergen(int allergenID){
this.allergen = allergenID;
    }

    public int getAllergen() {
        return allergen;
    }

    public void setAllergen(int allergen) {
        this.allergen = allergen;
    }
}
