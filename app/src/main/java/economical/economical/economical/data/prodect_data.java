package economical.economical.economical.data;

import java.util.ArrayList;

public class prodect_data  extends super_prodect_data{
    ArrayList<String> images;

    public prodect_data(String name, String description, String type, String price, String id, ArrayList<String> images) {
        super(name, description, type, price, id);
        this.images = images;
    }
    public ArrayList<String> getImages() {
        return images;
    }

}
