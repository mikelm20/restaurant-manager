package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name= "composition")
public class EnergeticComposition {

    @Attribute(name="measure", required = false)
    private String measure;
    @Element(name = "energy", required = false)
    private Energy energeticValue;
    @Element(name = "fat", required = false)
    private float fat;
    @Element(name = "saturatedfat", required = false)
    private float saturatedFats;
    @Element(name = "carbohydrates", required = false)
    private float carbohydrates;
    @Element(name = "sugars", required = false)
    private float sugars;
    @Element(name = "protein", required = false)
    private float protein;
    @Element(name = "salt", required = false)
    private float salt;

    public EnergeticComposition(String measure, Energy energeticValue, float fat, float saturatedFats, float carbohydrates, float sugars, float protein, float salt) {
        this.measure = measure;
        this.energeticValue = energeticValue;
        this.fat = fat;
        this.saturatedFats = saturatedFats;
        this.carbohydrates = carbohydrates;
        this.sugars = sugars;
        this.protein = protein;
        this.salt = salt;
    }

    public EnergeticComposition(){

    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Energy getEnergeticValue() {
        return energeticValue;
    }

    public void setEnergeticValue(Energy energeticValue) {
        this.energeticValue = energeticValue;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getSaturatedFats() {
        return saturatedFats;
    }

    public void setSaturatedFats(float saturatedFats) {
        this.saturatedFats = saturatedFats;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(float carboHydrates) {
        this.carbohydrates = carboHydrates;
    }

    public float getSugars() {
        return sugars;
    }

    public void setSugars(float sugars) {
        this.sugars = sugars;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getSalt() {
        return salt;
    }

    public void setSalt(float salt) {
        this.salt = salt;
    }

    public float[] getFloatList (){
        float[] list = new float[6];

        list[0] = energeticValue.getEnergy();
        list[1] = fat;
        list[2] = saturatedFats;
        list[3] = sugars;
        list[4] = protein;
        list[5] = salt;

        return list;

    }


}
