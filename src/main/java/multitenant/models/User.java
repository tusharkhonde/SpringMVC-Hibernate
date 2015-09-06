package multitenant.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.lang.String;
import javax.persistence.*;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@SuppressWarnings("all")
@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull
    @Column(name="firstname")
    private String firstname;

    @NotNull
    @Column(name="lastname")
    private String lastname;

    @NotNull
    @Column(name="password")
    private String password;

    @Email
    @NotEmpty
    @Column(name="email")
    private String email;

    @NotNull
    @Column(name="tenanttype")
    private String tenanttype;

    @OneToOne(fetch = FetchType.LAZY, mappedBy="user", cascade=CascadeType.ALL)
    private Tenant tenant;

    @OneToOne(fetch = FetchType.LAZY, mappedBy="user", cascade=CascadeType.ALL)
    private Project project;

    public User(){}

    public User(String firstname, String lastname, String password, String tenanttype) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.tenanttype = tenanttype;
    }

    public String getTenanttype() {
        return tenanttype;
    }

    public void setTenanttype(String tenanttype) {
        this.tenanttype = tenanttype;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}