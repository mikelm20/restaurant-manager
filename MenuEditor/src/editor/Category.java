package editor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "categories", schema = "menu")
public class Category {
    private int idcategories;
    private String name;
    private String description;
    private String image;

    @Id
    @Column(name = "idcategories", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getIdcategories() {
        return idcategories;
    }

    public void setIdcategories(int idcategories) {
        this.idcategories = idcategories;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Category that = (Category) o;
        return idcategories == that.idcategories &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcategories, name, description, image);
    }
}
