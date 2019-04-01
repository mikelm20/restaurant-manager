package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "dish")
public class DishDiscount {

    @Attribute(name = "dishID")
    private  int dishID;
    @Element(name = "total",required = false)
    private TotalDiscount totalDiscount;
    @Element(name = "percentage",required = false)
    private PercentageDiscount percentageDiscount;
    @Element(name = "multiple",required = false)
    private MultipleDiscount multipleDiscount;
    @Element(name = "extra",required = false)
    private ExtraDiscount extraDiscount;

    public DishDiscount() {
    }

    public TotalDiscount getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(TotalDiscount totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public PercentageDiscount getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(PercentageDiscount percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public MultipleDiscount getMultipleDiscount() {
        return multipleDiscount;
    }

    public void setMultipleDiscount(MultipleDiscount multipleDiscount) {
        this.multipleDiscount = multipleDiscount;
    }

    public ExtraDiscount getExtraDiscount() {
        return extraDiscount;
    }

    public void setExtraDiscount(ExtraDiscount extraDiscount) {
        this.extraDiscount = extraDiscount;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }
}
