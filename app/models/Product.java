package models;

import java.util.*;
import javax.persistence.*;

import com.avaje.ebean.Model;
import play.data.format.*;
import play.data.validation.*;

@Entity
public class Product extends Model {

    @Id
    private Long id;

    @Constraints.Required
    private String name;

    @ManyToOne
    private Category category;

    @Constraints.Required
    private String description;

    @Constraints.Required
    private int	stock;

    @Constraints.Required
    private double price;

    public  Product() {
    }

    public  Product(Long id, String name, Category category, String description, int stock, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.stock = stock;
        this.price = price;
    }

    public static Finder<Long,Product> find = new Finder<Long,Product>(Product.class);

    public static List<Product> findAll() {
        return Product.find.all();
    }

	public static List<Product> findAll(String filter){
		return Product.find.where()
								.ilike("name" , "%" + filter + "%")
								.orderBy( "name asc")
								.findList();
	}

	public static List<Product> findFilter(Long catID, String filter){
		return Product.find.where()
						.eq("categories.id", catID)
						.ilike("name" , "%" + filter + "%")
						.orderBy( "name asc")
						.findList();
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}