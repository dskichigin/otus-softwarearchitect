package dsk.otus.softwarearchitect.task4.test.entity;

public class UserEntity {

    private String id;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    private boolean newEntity = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isNew() {
        return newEntity;
    }

    public void setNew(boolean newEntity) {
        this.newEntity = newEntity;
    }
}
