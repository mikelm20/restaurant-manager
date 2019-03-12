package editor;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;

public class EditorBusiness {

    private EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("MenuPU");
    private EntityManager em = emf.createEntityManager();




    public List<Category> getCategories(){

        List<Category> categorias = (List<Category>)em.createQuery("SELECT c FROM Category c").getResultList();

        return categorias;

    }

    public String getImage(String name)
    {
        Category categoria = (Category)em.createQuery("SELECT c FROM Category c WHERE c.name=:name")
                .setParameter("name", name)
                .getSingleResult();

        return categoria.getImage();

    }

    public Category getCategory(String name){
        Category categoria = (Category)em.createQuery("SELECT c FROM Category c WHERE c.name=:name")
                .setParameter("name", name)
                .getSingleResult();

        return categoria;

    }

    public void updateCategory(Category categoria,int id)
    {
        Category uCategory = em.find(Category.class,id);
        uCategory.setName(categoria.getName());
        uCategory.setDescription(categoria.getDescription());
        uCategory.setImage(categoria.getImage());
        em.getTransaction().begin();
        em.persist(uCategory);
        em.getTransaction().commit();

    }

    public void removeCategory(int id)
    {
       Category categoria = em.find(Category.class, id);
       em.getTransaction().begin();
       em.remove(categoria);
       em.getTransaction().commit();
    }

    public void addCategory(Category categoria){

        em.getTransaction().begin();
        em.persist(categoria);
        em.getTransaction().commit();

    }

    public List<Dish> getDishes(){

        List<Dish> platos = (List<Dish>)em.createQuery("SELECT d FROM Dish d").getResultList();

        return platos;

    }

    public Dish getDish(String name){
        Dish plato = (Dish)em.createQuery("SELECT d FROM Dish d WHERE d.name=:name")
                .setParameter("name", name)
                .getSingleResult();

        return plato;

    }

    public List<Allergens> getAllergens()
    {
        List<Allergens> alergenos = (List<Allergens>)em.createQuery("SELECT a FROM Allergens a").getResultList();

        return alergenos;

    }

    public void addAllergens(boolean array[], int idDish){


        List<DiAl> combs = (List<DiAl>)em.createQuery("SELECT c FROM DiAl c WHERE c.dishesByDishesIdDishes.idDishes=:idDish")
                .setParameter("idDish",idDish)
                .getResultList();

        for(DiAl comb : combs){
            DiAl rm = em.find(DiAl.class,comb.getIddiAl());
            em.getTransaction().begin();
            em.remove(rm);
            em.getTransaction().commit();

        }

        int index = 0;

        while(index<14)
        {

            if(array[index])
            {
                DiAl comb = new DiAl();
                Allergens allergen = em.find(Allergens.class, index+1);
                Dish dish = em.find(Dish.class, idDish);
                comb.setDishesByDishesIdDishes(dish);
                comb.setAllergensByAllergensId(allergen);
                em.getTransaction().begin();
                em.persist(comb);
                em.getTransaction().commit();


            }
            index+=1;

        }

    }

    public List<Allergens> getAllergensbyDish(int idDish){

        List<DiAl> combs = (List<DiAl>)em.createQuery("SELECT c FROM DiAl c WHERE c.dishesByDishesIdDishes.idDishes=:idDish")
                .setParameter("idDish",idDish)
                .getResultList();

        List<Allergens> alergenos = new ArrayList<Allergens>();

        for(DiAl comb : combs)
        {
            alergenos.add(comb.getAllergensByAllergensId());
        }

        return alergenos;

    }

    public void removeDish (int idDish){

        Dish rm = em.find(Dish.class,idDish);
        em.getTransaction().begin();
        em.remove(rm);
        em.getTransaction().commit();



    }

    public void updateDish(Dish dish, int id, String categoryName){

        Dish uDish = em.find(Dish.class,id);
        uDish.setName(dish.getName());
        uDish.setDescription(dish.getDescription());
        uDish.setImage(dish.getImage());
        uDish.setPrice(dish.getPrice());
        uDish.setEnergy(dish.getEnergy());
        uDish.setCarboHydrates(dish.getCarboHydrates());
        uDish.setFat(dish.getFat());
        uDish.setWeight(dish.getWeight());
        uDish.setSalt(dish.getSalt());
        uDish.setSugars(dish.getSugars());
        uDish.setSaturedFat(dish.getSaturedFat());

        em.getTransaction().begin();
        em.persist(uDish);
        em.getTransaction().commit();

        uDish = em.find(Dish.class,id);
        Category categoria = getCategory(categoryName);


        DiCa comb;

        try {
             comb = (DiCa) em.createQuery("SELECT c FROM DiCa c WHERE c.dishesByDishesIdDishes.idDishes=:idDish")
                    .setParameter("idDish", id)
                    .getSingleResult();
        }
        catch(Exception NoResultException){
            comb = new DiCa();
        }

        comb.setCategoriesByCategoriesIdcategories(categoria);
        comb.setDishesByDishesIdDishes(uDish);

        em.getTransaction().begin();
        em.persist(comb);
        em.getTransaction().commit();

    }

    public String getCategoryByDish(int id)
    {
        DiCa comb = (DiCa) em.createQuery("SELECT c FROM DiCa c WHERE c.dishesByDishesIdDishes.idDishes=:idDish")
                .setParameter("idDish", id)
                .getSingleResult();

        return comb.getCategoriesByCategoriesIdcategories().getName();
    }


    public int addDish(Dish newDish, String categoryName){

        em.getTransaction().begin();
        em.persist(newDish);
        em.getTransaction().commit();

        Dish dish = (Dish) em.createQuery("SELECT d FROM Dish d WHERE d.name=:name")
                .setParameter("name", newDish.getName())
                .getSingleResult();

        Category categoria = getCategory(categoryName);
        DiCa comb = new DiCa();
        comb.setCategoriesByCategoriesIdcategories(categoria);
        comb.setDishesByDishesIdDishes(dish);

        em.getTransaction().begin();
        em.persist(comb);
        em.getTransaction().commit();

        return dish.getIdDishes();

    }


}
