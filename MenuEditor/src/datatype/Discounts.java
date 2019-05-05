package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "Discounts")
public class Discounts {

    @ElementList( name = "discount",required = false, inline = true)
    private List<Discount> order;

    public Discounts() {
    }

    public List<Discount> getOrder() {
        return order;
    }

    public void setOrder(List<Discount> order) {
        this.order = order;
    }
}
