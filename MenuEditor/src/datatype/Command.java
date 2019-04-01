package datatype;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name= "orders")
public class Command {

        @Attribute(name = "client-id")
        private String clientID;
        @Attribute(name = "paid", required = false)
        private Boolean paid;
        @ElementList( name = "order",required = false, inline = true)
        private List<Order> order;
        @Element(name = "payment-info",required = false)
        private PaymentInfo paymentInfo;

    public Command() {
    }

    public Command(String clientID, Boolean paid, PaymentInfo paymentInfo) {
        this.clientID = clientID;
        this.paid = paid;
        this.paymentInfo = paymentInfo;
    }

    public Command(String clientID, Boolean paid, List<Order> order) {
        this.clientID = clientID;
        this.paid = paid;
        this.order = order;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}
