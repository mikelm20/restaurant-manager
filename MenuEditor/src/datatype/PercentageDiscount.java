package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "percentage")
public class PercentageDiscount {

    @Attribute(name = "quantity")
    private float quantity;

    public PercentageDiscount() {
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
