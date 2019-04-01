package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "discount")
public class Discount {

    @Attribute(name = "code",required = false)
    private String code;
    @Attribute(name = "expires")
    private Long expires;
    @Attribute(name = "public")
    private boolean publicDiscount;
    @ElementList( name = "dish",required = false, inline = true)
    private List<DishDiscount> order;
    @Element(name = "total",required = false)
    private TotalDiscount totalDiscount;
    @Element(name = "percentage",required = false)
    private PercentageDiscount percentageDiscount;

    public Discount() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    public List<DishDiscount> getOrder() {
        return order;
    }

    public void setOrder(List<DishDiscount> order) {
        this.order = order;
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

    public boolean isPublicDiscount() {
        return publicDiscount;
    }

    public void setPublicDiscount(boolean publicDiscount) {
        this.publicDiscount = publicDiscount;
    }
}
