package datatype;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "payment-info")
public class PaymentInfo {

    @Element(name = "itemNumber",required = false)
    private int itemNumber;
    @Element(name = "noTaxPrice",required = false)
    private Float woTaxPrice;
    @Element(name = "tax",required = false)
    private Float tax;
    @Element(name = "price",required = false)
    private Float finalPrice;
    @Element(name = "discountPrice",required = false)
    private Float discount;
    @Element(name = "currency",required = false)
    private String currency;
    @Element(name = "discounts",required = false)
    private Discounts discounts;

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Float getWoTaxPrice() {
        return woTaxPrice;
    }

    public void setWoTaxPrice(Float woTaxPrice) {
        this.woTaxPrice = woTaxPrice;
    }

    public Float getTax() {
        return tax;
    }

    public void setTax(Float tax) {
        this.tax = tax;
    }

    public Float getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Float finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Discounts getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Discounts discounts) {
        this.discounts = discounts;
    }
}

