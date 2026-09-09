package editor;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import datatype.*;
import login.LoginBusiness;
import login.LoginData;
import utils.XMLParseUtils;

public class EditorBusiness {

    private EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("MenuPU");
    private EntityManager em = emf.createEntityManager();




    public List<Category> getCategories(){

        List<Category> categories = (List<Category>)em.createQuery("SELECT c FROM Category c").getResultList();

        return categories;

    }

    public String getImage(String name)
    {
        Category category = (Category)em.createQuery("SELECT c FROM Category c WHERE c.name=:name")
                .setParameter("name", name)
                .getSingleResult();

        return category.getImage();

    }

    public Category getCategory(String name){
        Category category = (Category)em.createQuery("SELECT c FROM Category c WHERE c.name=:name")
                .setParameter("name", name)
                .getSingleResult();

        return category;

    }

    public void updateCategory(Category category,int id)
    {
        Category uCategory = em.find(Category.class,id);
        uCategory.setName(category.getName());
        uCategory.setDescription(category.getDescription());
        uCategory.setImage(category.getImage());
        em.getTransaction().begin();
        em.persist(uCategory);
        em.getTransaction().commit();

    }

    public void removeCategory(int id)
    {
       Category category = em.find(Category.class, id);
       em.getTransaction().begin();
       em.remove(category);
       em.getTransaction().commit();
    }

    public void addCategory(Category category){

        em.getTransaction().begin();
        em.persist(category);
        em.getTransaction().commit();

    }

    public List<Dish> getDishes(){

        List<Dish> dishes = (List<Dish>)em.createQuery("SELECT d FROM Dish d").getResultList();

        return dishes;

    }

    public Dish getDish(String name){
        Dish dish = (Dish)em.createQuery("SELECT d FROM Dish d WHERE d.name=:name")
                .setParameter("name", name)
                .getSingleResult();

        return dish;

    }

    public Dish getDish(int id){

        return em.find(Dish.class,id);

    }

    public List<Allergens> getAllergens()
    {
        List<Allergens> allergens = (List<Allergens>)em.createQuery("SELECT a FROM Allergens a").getResultList();

        return allergens;

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

        List<Allergens> allergens = new ArrayList<Allergens>();

        for(DiAl comb : combs)
        {
            allergens.add(comb.getAllergensByAllergensId());
        }

        return allergens;

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
        uDish.setProteins(dish.getProteins());

        em.getTransaction().begin();
        em.persist(uDish);
        em.getTransaction().commit();

        uDish = em.find(Dish.class,id);
        Category category = getCategory(categoryName);


        DiCa comb;

        try {
             comb = (DiCa) em.createQuery("SELECT c FROM DiCa c WHERE c.dishesByDishesIdDishes.idDishes=:idDish")
                    .setParameter("idDish", id)
                    .getSingleResult();
        }
        catch(Exception NoResultException){
            comb = new DiCa();
        }

        comb.setCategoriesByCategoriesIdcategories(category);
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

        Category category = getCategory(categoryName);
        DiCa comb = new DiCa();
        comb.setCategoriesByCategoriesIdcategories(category);
        comb.setDishesByDishesIdDishes(dish);

        em.getTransaction().begin();
        em.persist(comb);
        em.getTransaction().commit();

        return dish.getIdDishes();

    }

    public List<Dish> getDishesByCategory(int id){

        List<Dish> dishes = new ArrayList<Dish>();

        List<DiCa> diCa = (List<DiCa>)em.createQuery("SELECT d FROM DiCa d WHERE d.categoriesByCategoriesIdcategories.idcategories=:id")
                .setParameter("id",id)
                .getResultList();

        for(DiCa di : diCa){

            dishes.add(di.getDishesByDishesIdDishes());

        }

        return dishes;
    }

    public void getMenu(){

        List<CombDiCa> comb = new ArrayList<CombDiCa>();
       List<Category> categories = this.getCategories();

       for(Category category : categories){
           CombDiCa element = new CombDiCa();
           element.setCategory(category);
           element.setDishes(this.getDishesByCategory(category.getIdcategories()));
           comb.add(element);
       }

       Menu menu = new Menu();
       List<datatype.Category> listCategories = new ArrayList<>();


       if(comb !=null){
           for (CombDiCa combDica:comb) {
               List<datatype.Dish> listDish = new ArrayList<>();
               if(combDica!=null && combDica.getCategory()!=null && combDica.getDishes()!=null){
                   for (Dish dish:combDica.getDishes()) {
                       if(dish!=null){
                           EnergeticComposition energeticComposition = new EnergeticComposition("g/100g",new Energy("Kcal",dish.getEnergy().floatValue()),dish.getFat().floatValue(),dish.getSaturedFat().floatValue(),dish.getCarboHydrates().floatValue(),dish.getSugars().floatValue(),dish.getProteins().floatValue(),dish.getSalt().floatValue());
                           List<Allergen> allergenList = new ArrayList<>();
                           List<Warning> warningList = new ArrayList<>();
                           if(getAllergensbyDish(dish.getIdDishes())!=null){
                               for (Allergens allergen:getAllergensbyDish(dish.getIdDishes())){
                                   if(allergen!=null){
                                       allergenList.add(new Allergen(allergen.getId()));
                                   }
                               }
                           }
                           listDish.add(new datatype.Dish(dish,energeticComposition,allergenList,warningList));
                       }
                   }
                   listCategories.add(new datatype.Category(combDica.getCategory(),listDish));
               }
           }
       }

       LoginBusiness lb = new LoginBusiness();
       LoginData user = lb.getUserData();
       menu.setCategories(listCategories);
       menu.setRestaurantName(user.getName());
       menu.setPrimaryColor(user.getColor());
       menu.setSecondaryColor(user.getSecondaryColor());

        XMLParseUtils.updateMenu(FTPURL.getMenuURL()+"/menu.xml",menu);

       }
}
