package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "composition")
public class Energy {

    @Attribute(name="units", required=false)
    private String unit;
    @Text
    private Float energy;

    public Energy(String unit, Float energy) {
        this.unit = unit;
        this.energy = energy;
    }

    public Energy() {

    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getEnergy() {
        return energy;
    }

    public void setEnergy(Float energy) {
        this.energy = energy;
    }
}
