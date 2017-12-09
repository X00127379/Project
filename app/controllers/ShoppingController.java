package controllers;

import models.shopping.*;
import models.users.Customer;
import models.users.User;
import play.db.ebean.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.With;
import views.html.*;
import models.Product;

import controllers.security.*;

public class ShoppingController extends Controller {
    
	private Customer getCurrentUser() {
		return (Customer)User.getUserById(session().get("email"));
	}

	@Security.Authenticated(Secured.class)
	@With(AuthCustomer.class)
	@Transactional
    public Result placeOrder(){
	    Customer c = getCurrentUser();

	    ShopOrder order = new ShopOrder();

	    order.setCustomer(c);
	    order.setItems(c.getBasket().getBasketItems());
	    order.save();

	    for(OrderItem i: order.getItems()){
	        i.setOrder(order);
	        i.setBasket(null);
	        i.update();

        }

        order.update();
	    c.getBasket().setBasketItems(null);
	    c.getBasket().update();

	    return ok(orderConfirmed.render(c,order));
    }

	@Security.Authenticated(Secured.class)
	@With(AuthCustomer.class)
	@Transactional
    public Result showBasket(){

	    return ok(basket.render(getCurrentUser()));

    }

	@Security.Authenticated(Secured.class)
	@With(AuthCustomer.class)
    @Transactional
    public Result addToBasket(Long id) {

        Product p = Product.find.byId(id);

        Customer customer = (Customer)User.getUserById(session().get("email"));

        if (customer.getBasket() == null) {
            customer.setBasket(new Basket());
            customer.getBasket().setCustomer(customer);
            customer.update();
        }
        customer.getBasket().addProduct(p);
        customer.update();

        return ok(basket.render(customer));
    }

    @Transactional
    public Result addOne(Long itemId) {

        OrderItem item = OrderItem.find.byId(itemId);
        item.increaseQty();
        item.update();
        return redirect(routes.ShoppingController.showBasket());
    }

    @Transactional
    public Result removeOne(Long itemId) {

        OrderItem item = OrderItem.find.byId(itemId);
        Customer c = getCurrentUser();
        c.getBasket().removeItem(item);
        c.getBasket().update();
        return ok(basket.render(c));
    }


    @Transactional
    public Result emptyBasket() {
        
        Customer c = getCurrentUser();
        c.getBasket().removeAllItems();
        c.getBasket().update();
        
        return ok(basket.render(c));
    }

	@Security.Authenticated(Secured.class)
	@With(AuthStaff.class)
    @Transactional
    public Result staffView(long id) {
        ShopOrder order = ShopOrder.find.byId(id);
        return ok(orderConfirmed.render(getCurrentUser(), order));
    }
	@Security.Authenticated(Secured.class)
	@With(AuthStaff.class)
    public Result deleteOrder(Long id){
        ShopOrder.find.ref(id).delete();

        flash("succes", "Product has been deleted");

        return redirect(routes.AdminController.staffView());
    }
}