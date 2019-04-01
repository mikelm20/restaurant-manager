package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "weight")
public class Weight {

    @Attribute(name="unit", required=false)
    private String unit;
    @Text
    private Float weight;

    public Weight() {
    }

    public Weight(String unit, Float weight) {
        this.unit = unit;
        this.weight = weight;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
