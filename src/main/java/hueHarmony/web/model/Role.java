package hueHarmony.web.model;

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
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(name = "role_name", columnDefinition = "VARCHAR(20)")
    private String roleName;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<User> users = new ArrayList<>();
}
