package editor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DiAl", schema = "menu", catalog = "")
public class DiAl {
    private int iddiAl;
    private Dish dishesByDishesIdDishes;
    private Allergens allergensByAllergensId;

    @Id
    @Column(name = "iddiAl", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getIddiAl() {
        return iddiAl;
    }

    public void setIddiAl(int iddiAl) {
        this.iddiAl = iddiAl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiAl diAl = (DiAl) o;
        return iddiAl == diAl.iddiAl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iddiAl);
    }

    @ManyToOne
    @JoinColumn(name = "dishes_idDishes", referencedColumnName = "idDishes", nullable = false)
    public Dish getDishesByDishesIdDishes() {
        return dishesByDishesIdDishes;
    }

    public void setDishesByDishesIdDishes(Dish dishesByDishesIdDishes) {
        this.dishesByDishesIdDishes = dishesByDishesIdDishes;
    }

    @ManyToOne
    @JoinColumn(name = "allergens_id", referencedColumnName = "id", nullable = false)
    public Allergens getAllergensByAllergensId() {
        return allergensByAllergensId;
    }

    public void setAllergensByAllergensId(Allergens allergensByAllergensId) {
        this.allergensByAllergensId = allergensByAllergensId;
    }
}
