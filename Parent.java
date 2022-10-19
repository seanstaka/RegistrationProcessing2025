public class Parent {
    private String name;
    private String email;
    private String phoneNumber;
    
    public Parent(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        phoneNumber = phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String toString() {
        return "Parent: " + name + " (" + phoneNumber + ")";
    }
}