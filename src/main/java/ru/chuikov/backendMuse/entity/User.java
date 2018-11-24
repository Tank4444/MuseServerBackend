package ru.chuikov.backendMuse.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="ACCOUNT")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Size(min = 8, message = "Minimum password length: 8 characters")
    @Column(name = "password",nullable = false)
    private String password;

    public User(String password, String email, List<Role> roles) {
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    @Size(min=6,max=255)
    @Column(unique = true,name = "email",nullable = false)
    private String email;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Role> roles;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public ArrayList<GrantedAuthority> getAuthorities()
    {
        ArrayList<GrantedAuthority> result = new ArrayList<>();
        for(Role r:getRoles())
        {
            result.add(new SimpleGrantedAuthority(r.getName()));
        }
        return result;
    }
}
