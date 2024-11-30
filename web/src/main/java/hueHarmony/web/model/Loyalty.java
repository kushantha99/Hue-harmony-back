package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="loyalty")
public class Loyalty {

    @Id
    private String contactNo;

    @Column()
    private float loyaltyPoints;

    private String loyaltyStatus;

}
