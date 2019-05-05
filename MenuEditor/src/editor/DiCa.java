package editor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DiCa", schema = "menu", catalog = "")
public class DiCa {
    private int iddiCa;
    private Dish dishesByDishesIdDishes;
    private Category categoriesByCategoriesIdcategories;

    @Id
    @Column(name = "iddiCa", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getIddiCa() {
        return iddiCa;
    }

    public void setIddiCa(int iddiCa) {
        this.iddiCa = iddiCa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiCa diCa = (DiCa) o;
        return iddiCa == diCa.iddiCa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(iddiCa);
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
    @JoinColumn(name = "categories_idcategories", referencedColumnName = "idcategories", nullable = false)
    public Category getCategoriesByCategoriesIdcategories() {
        return categoriesByCategoriesIdcategories;
    }

    public void setCategoriesByCategoriesIdcategories(Category categoriesByCategoriesIdcategories) {
        this.categoriesByCategoriesIdcategories = categoriesByCategoriesIdcategories;
    }
}
