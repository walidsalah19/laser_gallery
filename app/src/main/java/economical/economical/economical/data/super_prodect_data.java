package economical.economical.economical.data;

public class super_prodect_data {
    String name,description,type,price,id;

    public super_prodect_data(String name, String description, String type, String price, String id) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }
}
