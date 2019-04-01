package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

@Root(name = "order")
public class Order {

    @Attribute(name = "id",required = false)
    private int id;
    @Attribute(name = "units",required = false)
    private int units;

    public Order(){

    }

    public Order(int id, int units) {
        this.id = id;
        this.units = units;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }
}
