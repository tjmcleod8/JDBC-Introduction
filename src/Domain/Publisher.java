package Domain;

public class Publisher {
    //private fields
    private String name;
    private String phone;
    private String city;

    public Publisher(){}
    //constructor
    public Publisher(String name, String phone, String city) {
        this.name=name;
        this.phone=phone;
        this.city=city;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
