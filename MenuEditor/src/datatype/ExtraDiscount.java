package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "extra")
public class ExtraDiscount {
    @Attribute(name = "item")
    private int item;
    @Attribute(name = "quantity")
    private float quantity;

    public ExtraDiscount() {
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
