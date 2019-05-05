package editor;

import org.simpleframework.xml.Root;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "dishes", schema = "menu", catalog = "")
public class Dish {
    private int idDishes;
    private String description;
    private double price;
    private String name;
    private Double energy;
    private Double fat;
    private Double saturedFat;
    private Double carboHydrates;
    private Double sugars;
    private Double salt;
    private Double weight;
    private String image;
    private Double proteins;


    @Basic
    @Column(name = "proteins", nullable = true, precision = 0)
    public Double getProteins() {
        return proteins;
    }

    public void setProteins(Double proteins) {
        this.proteins = proteins;
    }

    @Id
    @Column(name = "idDishes", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getIdDishes() {
        return idDishes;
    }

    public void setIdDishes(int idDishes) {
        this.idDishes = idDishes;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 150)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 150)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "energy", nullable = true, precision = 0)
    public Double getEnergy() {
        return energy;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    @Basic
    @Column(name = "fat", nullable = true, precision = 0)
    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    @Basic
    @Column(name = "saturedFat", nullable = true, precision = 0)
    public Double getSaturedFat() {
        return saturedFat;
    }

    public void setSaturedFat(Double saturedFat) {
        this.saturedFat = saturedFat;
    }

    @Basic
    @Column(name = "carboHydrates", nullable = true, precision = 0)
    public Double getCarboHydrates() {
        return carboHydrates;
    }

    public void setCarboHydrates(Double carboHydrates) {
        this.carboHydrates = carboHydrates;
    }

    @Basic
    @Column(name = "sugars", nullable = true, precision = 0)
    public Double getSugars() {
        return sugars;
    }

    public void setSugars(Double sugars) {
        this.sugars = sugars;
    }

    @Basic
    @Column(name = "salt", nullable = true, precision = 0)
    public Double getSalt() {
        return salt;
    }

    public void setSalt(Double salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "weight", nullable = true, precision = 0)
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "image", nullable = true, length = 255)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return idDishes == dish.idDishes &&
                Double.compare(dish.price, price) == 0 &&
                Objects.equals(description, dish.description) &&
                Objects.equals(name, dish.name) &&
                Objects.equals(energy, dish.energy) &&
                Objects.equals(fat, dish.fat) &&
                Objects.equals(saturedFat, dish.saturedFat) &&
                Objects.equals(carboHydrates, dish.carboHydrates) &&
                Objects.equals(sugars, dish.sugars) &&
                Objects.equals(salt, dish.salt) &&
                Objects.equals(weight, dish.weight) &&
                Objects.equals(image, dish.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDishes, description, price, name, energy, fat, saturedFat, carboHydrates, sugars, salt, weight, image);
    }
}
