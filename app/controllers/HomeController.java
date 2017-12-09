package controllers;

import controllers.security.AuthAdmin;
import controllers.security.Secured;
import play.mvc.*;
import play.data.*;
import play.db.ebean.Transactional;
import play.api.Environment;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

import views.html.*;

import models.*;
import models.users.*;


public class HomeController extends Controller {

    private FormFactory formFactory;

    private Environment env;

    @Inject
    public HomeController(FormFactory f, Environment e) {
        this.env = e;
        this.formFactory = f;
    }


    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }

    public Result index() {

        return ok(index.render(getUserFromSession()));
    }

    public Result about() {

        return ok(about.render(getUserFromSession()));
    }



    public Result products(Long cat, String filter) {

        List<Category> categoriesList = Category.findAll();
        List<Product> productsList = new ArrayList<Product>();

        if (cat == 0) {
            productsList = Product.findAll(filter);
        }
        else {
            productsList = Product.findFilter(cat, filter);
        }

        return ok(products.render(productsList, categoriesList, getUserFromSession(), env, cat , filter));
    }
}
