package hueHarmony.web.model;

import hueHarmony.web.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

//    @PasswordValidation(groups = {onCreation.class})
    @Column(name = "password", columnDefinition = "TEXT", nullable = false)
    private String password;

//    @NotEmpty(message = "Email cannot be empty.", groups = {onCreation.class, onUpdate.class})
//    @Email(message = "Invalid email entered.", groups = {onCreation.class, onUpdate.class})
    @Column(name = "email", nullable = false, unique = true)
    private String email;

//    @UsernameValidation(groups = {onCreation.class, onUpdate.class})
    @Column(name = "username", nullable = false, unique = true)
    private String username;

//    @NameValidation(minLength = 10, name = "Full name", groups = {onCreation.class, onUpdate.class})
    @Column(name = "full_name")
    private String fullName = null;

    @Column(name = "profile_image", columnDefinition = "TEXT")
    private String profileImage = null;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", columnDefinition = "VARCHAR", length = 10, nullable = false)
    private UserStatus userStatus;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
//    @JoinColumn(name = "user_id", nullable = false)
//    private List<LoanStatus> loanStatusList;
//
//    @OneToMany(mappedBy = "createdUser", cascade = CascadeType.MERGE)
//    @JoinColumn(name = "user_id", nullable = false)
//    private List<Loan> createdLoans;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
//    @JoinColumn(name = "user_id", nullable = false)
//    private List<OrderStatus> orderStatusList;
//
//    @OneToMany(mappedBy = "createdUser", cascade = CascadeType.MERGE)
//    @JoinColumn(name = "user_id", nullable = false)
//    private List<Order> createdOrders;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false)
    )
    private List<Role> roles = new ArrayList<>();
}
