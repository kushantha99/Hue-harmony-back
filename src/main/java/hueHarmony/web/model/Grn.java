package hueHarmony.web.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "grn")
public class Grn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int grnId;
}
