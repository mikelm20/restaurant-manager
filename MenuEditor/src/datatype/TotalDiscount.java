package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "total")
public class TotalDiscount {

    @Attribute(name = "quantity")
    private int quantity;

    public TotalDiscount() {
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
