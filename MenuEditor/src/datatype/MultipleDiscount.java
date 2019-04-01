package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "multiple")

public class MultipleDiscount {
    @Attribute(name = "ordered")
    private int ordered;
    @Attribute(name = "paid")
    private int paid;

    public MultipleDiscount() {
    }

    public int getOrdered() {
        return ordered;
    }

    public void setOrdered(int ordered) {
        this.ordered = ordered;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }
}
