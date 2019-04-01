package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name="price")
public class Price {

    @Attribute(name="currency", required=false)
    private String currency;
    @Attribute(name = "iva", required = false)
    private Float iva;
    @Text
    private Float price;

    public Price(float v) {
    }

    public Price(String currency, Float price) {
        this.currency = currency;
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getIva() {
        return iva;
    }

    public void setIva(Float iva) {
        this.iva = iva;
    }
}
