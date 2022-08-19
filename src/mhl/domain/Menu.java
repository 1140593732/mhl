package mhl.domain;

/**
 * description:
 *      id INT PRIMARY KEY auto_increment,
 * 		name VARCHAR(50) NOT NULL DEFAULT '', 菜名
 * 		type VARCHAR(50) NOT NULL DEFAULT '', 菜品种类
 * 		price DOUBLE NOT NULL DEFAULT 0)CHARSET=utf8;  价格
 */

public class Menu {

    private  Integer id;
    private String name;
    private String type;
    private double price;

    public Menu(){};

    public Menu(Integer id, String name, String type, double price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id + "\t\t\t" + name + "\t\t" + type + "\t\t" + price;
    }
}
