package controllers;

import controllers.security.AuthStaff;
import controllers.security.AuthAdmin;
import controllers.security.Secured;
import play.data.Form;
import play.data.FormFactory;
import play.db.ebean.Transactional;
import play.mvc.*;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import play.api.Environment;

import views.html.admin.*;
import models.*;
import models.users.User;

import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.File;

import models.Product;
import models.shopping.*;
import views.html.admin.*;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

@Security.Authenticated(Secured.class)
@With(AuthAdmin.class)
public class AdminController extends Controller {

    private FormFactory formFactory;

    private Environment env;

    @Inject
    public AdminController(Environment e, FormFactory f) {
        this.formFactory = f;
        this.env = e;
    }
    private User getUserFromSession() {
        return User.getUserById(session().get("email"));
    }


    public Result products(Long cat) {

        List<Category> categoriesList = Category.findAll();
        List<Product> productsList = new ArrayList<Product>();

        if (cat == 0) {
            productsList = Product.findAll();
        }
        else {
            productsList = Category.find.ref(cat).getProducts();
        }

        return ok(products.render(productsList, categoriesList, getUserFromSession(), env));
    }


    public Result addProduct() {

        Form<Product> addProductForm = formFactory.form(Product.class);

        return ok(addProduct.render(addProductForm, getUserFromSession(), env));
    }

    @Transactional
    public Result addProductSubmit() {

        String saveImageMsg;
        Form<Product> newProductForm = formFactory.form(Product.class).bindFromRequest();

        if(newProductForm.hasErrors()) {
            return badRequest(addProduct.render(newProductForm, getUserFromSession(), env));
        }

        Product p = newProductForm.get();

        if (p.getId() == null) {
            p.save();
        }

        else if (p.getId() != null) {
            p.update();
        }


        MultipartFormData data = request().body().asMultipartFormData();
        FilePart image = data.getFile("upload");


        saveImageMsg = saveFile(p.getId(), image);

        flash("success", "Product " + p.getName() + " has been created/ updated " + saveImageMsg);

        return redirect(routes.AdminController.products(0));
    }
    @Transactional
    public Result updateProduct(Long id) {

        Product p;
        Form<Product> productForm;

        try {
            p = Product.find.byId(id);

            productForm = formFactory.form(Product.class).fill(p);

            } catch (Exception ex) {
                return badRequest("error");
        }
        return ok(addProduct.render(productForm, getUserFromSession(), env));
    }

    @Transactional
    public Result deleteProduct(Long id) {

        Product.find.ref(id).delete();
        flash("success", "Product has been deleted");

        return redirect(routes.AdminController.products(0));
    }

    public String saveFile(Long id, FilePart<File> image) {
        if (image != null) {
            String mimeType = image.getContentType();
            if (mimeType.startsWith("image/")) {
                File file = image.getFile();
                ConvertCmd cmd = new ConvertCmd();
                IMOperation op = new IMOperation();
                op.addImage(file.getAbsolutePath());
                op.resize(300,200);
                op.addImage("public/images/productImages/" + id + ".jpg");
                IMOperation thumb = new IMOperation();
                thumb.addImage(file.getAbsolutePath());
                thumb.thumbnail(60);
                thumb.addImage("public/images/productImages/thumbnails/" + id + ".jpg");
                try{
                    cmd.run(op);
                    cmd.run(thumb);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                return " and image saved";
            }
        }
        return "image file missing";
    }

	

    
    public Result staffView(){

        List<ShopOrder> ordersList = ShopOrder.findAll();

        return ok(staffView.render(ordersList,User.getUserById(session().get("email"))));

    }

}
