package com.recettes.portalrecettes;

import com.recettes.portalrecettes.models.*;
import com.recettes.portalrecettes.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories
public class PortalRecettesApplication {

    @Autowired
    private UserDao userDao;
    @Autowired
    private IngredientDao ingredientDao;
    @Autowired
    private RecettesDao recetteDao;


    public static void main(String[] args) {
        SpringApplication.run(PortalRecettesApplication.class, args);
    }

    public void addIngredientToUser(User user, String nomIngredient) {
        Iterable<Ingredient> listIngredients = ingredientDao.findAll();
        for (Ingredient i : listIngredients) {
            if (i.getNom().equals(nomIngredient)) {
                user.getIngredients().add(i);
                userDao.save(user);
                return;
            }
        }
        System.out.println("ingredient non supporté !");

    }
    public void addListIngredientToUser(User user, List<Ingredient> ingredients){
        for(Ingredient i:ingredients){
            addIngredientToUser(user,i.getNom());
        }

    }

    public void addDataset(Recettes recette,List<Ingredient> ingredients,User [] users){

        ingredientDao.saveAll(ingredients);
        recette.setIngredient(ingredients);
        recetteDao.save(recette);

        for (User i : users) {
            i.setIngredients(ingredients);
            userDao.save(i);
        }


    }

    @PostConstruct
    public void init() {

        //Zone de création des User
        User us1 = new User("quentin@gmail.com", "miam", "quentin", "unal");
        User us2 = new User("laurine@gmail.com", "1234", "laurine", "torossian");
        User us3 = new User("sophie@gmail.com", "tasty", "sophie", "aitis");
        User us4 = new User("samuel@gmail.com", "12345", "samuel", "susoliak");


        //zone de création  des listes d'ingrédients des recettes

                //omellette fromage
        List<Ingredient> listOmletFrmg = new ArrayList<>();

            listOmletFrmg.add(new Ingredient("oeuf", "https://www.journee-mondiale.com/medias/images/journee/oeuf.png"));
            listOmletFrmg.add(new Ingredient("fromage rapé", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT_S2NXyBABiaXbdd-FbdeV9LuY0EwtowXdk6gyYS3ikWsV8mda"));

                //carbonara
        List<Ingredient> listCarbo = new ArrayList<>();

            listCarbo.add(new Ingredient("parmesan", "https://www.regal.fr/sites/art-de-vivre/files/styles/large/public/r_parmesan-istock.jpg?itok=Q6TTgV2O"));
            listCarbo.add(new Ingredient("pates", "https://www.pastadelices.fr/43/fusilli-nature-fraiches.jpg"));
            listCarbo.add(new Ingredient("lardons", "https://upload.wikimedia.org/wikipedia/commons/thumb/4/43/Lardons.jpg/1200px-Lardons.jpg"));
            listCarbo.add(new Ingredient("crème fraiche", "https://www.plaisirslaitiers.ca/var/ezflow_site/storage/images/dairy-goodness/home/recipes/creme-fraiche/258517-8-eng-CA/creme-fraiche_large.jpg"));
            listCarbo.add(new Ingredient("échalottes", "https://img-3.journaldesfemmes.com/6zLXXihuCjLfNwwgLCk2Abtn7u0=/910x607/smart/913a2e9520104564b6011257a8fd1181/ccmcms-jdf/10663185.jpg"));

                //salade fruits rouges
        List<Ingredient> listSldFrtRg = new ArrayList<>();

            listSldFrtRg.add(new Ingredient("fraises","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgE_ZdRddZA-yB02wsybNpNxUI-L-w7VfiDewMhgbZSRUI1m9R"));
            listSldFrtRg.add(new Ingredient("framboises","https://img-3.journaldesfemmes.com/lZqm2rIC2PMKPP1zVrNP7uFsyoo=/910x607/smart/91eed6d1ec5c4912849bc192c62161ab/ccmcms-jdf/10662426.jpg"));


        //Zone de création des recettes

        Recettes r1 = new Recettes("omellette au fromage", "Battre les oeufs.\n" +
                "Faire cuire les oeuf battus dans une poêle.\n" +
                "Ajouter le fromage rapé.\n" +
                "Retirer du feu.\n" , "https://cac.img.pmdstatic.net/fit/http.3A.2F.2Fprd2-bone-image.2Es3-website-eu-west-1.2Eamazonaws.2Ecom.2Fcac.2F2018.2F09.2F25.2F6d1aef0b-b0ad-4c4c-b670-63f975b72c88.2Ejpeg/748x372/quality/80/crop-from/center/omelette-au-fromage.jpeg");


        Recettes r2 = new Recettes("pates carbonara", "Cuire les pâtes.\n" +
                "Emincer les échalottes et les faire revenir à la poêle avec les lardons.\n" +
                "Ajouter la crème.\n" +
                "Une fois les pâtes cuite, incorporer la crème. Saupoudrer de parmesan. ", "https://static.750g.com/images/622-auto/f6ad72f2ac5f330143bd9bc27566dee6/comment-realiser-des-pates-carbonara-comme-en-italie.jpg");

        //Zone d'ajout du contenu

        addDataset(r1,listOmletFrmg, new User[]{us1, us4});
        addDataset(r2,listCarbo, new User[]{us3, us2});

    }
}
