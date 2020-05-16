package dsk.otus.softwarearchitect.task5.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@JsonIgnoreProperties(value = { "new" })
@Table("users")
public class UserEntity implements Persistable {

    @Id
    @Column("id")
    private String id;
    @Column("username")
    private String username;
    @Column("firstname")
    private String firstname;
    @Column("lastname")
    private String lastname;
    @Column("email")
    private String email;
    @Column("phone")
    private String phone;
    @Column("password")
    private String password;

    @Transient
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isNew() {
        return newEntity;
    }

    public void setNew(boolean newEntity) {
        this.newEntity = newEntity;
    }
}
