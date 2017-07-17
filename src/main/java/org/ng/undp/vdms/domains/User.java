package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import org.ng.undp.vdms.domains.security.Permission;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.utils.ShortUUID;
import org.ng.undp.vdms.utils.Utility;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Samuel on 10/13/2016.
 */
@Entity
@Table(name = "users")
@Setter
@Getter
//@ToString(exclude = "password")
@Audited
public class User {

    public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();


    @GeneratedValue(strategy = GenerationType.AUTO)

    @Id

    private Long id;

    @Column(name = "phone")
    private String phone;


    @NotNull

    @Column(unique = true)
    private String uuid;

    private String lastname;

    @NotNull
    private String firstname;

    @NotNull
    @Column(unique = true)
    private String email;


    @Column(unique = true)
    private String username;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;



    /* Password forgot and reset feature fields */

    private String resetPasswordToken;
    private Date resetPasswordExpires;


    /* Subscription control fields */

    private boolean enabled = true;
    private boolean accountNonExpired = true;
    private boolean credentialsNonExpired = true;
    private boolean accountNonLocked = true;
    private LocalDateTime lastLoggedIn;


    @ManyToOne
    @NotAudited
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @NotAudited
    @JoinColumn(name = "state_id")
    private State state;

    private String gender;
    @ManyToOne
    @NotAudited

    @JoinColumn(name = "district_id")
    private District district;

    private Date created_at;

    private Date updated_at;

    /*
     * This field is here to check whether a field is deleted or not.
     * by default, the field is NULL, if it carries a timestamp instead of null
     * then the record has been deleted,
     */
    private Date deleted_at;


    //UserTypes Field

    //private String[] userTypes = {UserType.VENDOR.toString()};

    //UserTypes Field
    private String[] userTypes;
    //Password Config field

    //Password Config field

    // public void setPassword(String password) {
    //     this.password = PASSWORD_ENCODER.encode(password);
    // }

    public User() {

    }

    public User(String lastname, String firstname, String email, String username, String password, String... usertypes) {

        this.lastname = lastname;
        this.firstname = firstname;
        this.username = username;


        this.password = password;


        this.userTypes = usertypes;
        this.email = email;


    }

    @ManyToMany(fetch = FetchType.EAGER)
    @NotAudited
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_uuid"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;


    public Set<Permission> getPermissions() {

        if (roles == null) {
            roles = new HashSet<>();
        }
        return roles.stream().map(role ->
                role.getPermissions().stream().map(perm -> perm).collect(Collectors.toSet())
        ).flatMap(Collection::stream).collect(Collectors.toSet());
    }



    public Set<String> getRoleNames() {
        if (roles == null) {
            roles = new HashSet<>();
        }

        return roles.stream().map(p -> p.getName()).collect(Collectors.toSet());
    }


    @PreUpdate
    void updatedAt() {

        this.updated_at = new Date();
        //this must happen to differentiate between null email an and empty String ""
        if (StringUtils.isBlank(this.email)) {
            this.email = null;
        }

        if (StringUtils.isBlank(this.password)) {
            String password = "12345";
            this.password = PASSWORD_ENCODER.encode(password);

        } else if (!Utility.isBcryptHashed(this.password)) {
            System.out.println("The Submitted password is from user entity: " + this.password);
            this.setPassword(PASSWORD_ENCODER.encode(this.password));
        }


    }

    @PrePersist
    void createAt() {

        this.uuid = ShortUUID.shortUUID();
        this.created_at = this.updated_at = new Date();
        //this must happen to differentiate between null email an and empty String ""

        if (StringUtils.isBlank(this.email)) {
            this.email = null;
        }
        if (StringUtils.isBlank(this.password)) {
            String password = "12345";
            System.out.println("password : 12345");
            this.password = PASSWORD_ENCODER.encode(password);

        } else if (!Utility.isBcryptHashed(this.password)) {
            this.setPassword(PASSWORD_ENCODER.encode(this.password));
        }


        //create entry for setting Role based on UserType found
    }


    @Override
    public String toString() {
        return "User{" + "id=" + id + ", lastname=" + lastname + ", firstbame=" + firstname + ", email=" + email + ", username=" + username + ", userTypes=" + userTypes + ", country=" + country + ", state=" + state + ", roles=" + roles + '}';
    }

    public int getColumnCount() {
        return getClass().getDeclaredFields().length;
    }

    public void hashPassword(String password) {
        this.password = PASSWORD_ENCODER.encode(password);
    }
}
