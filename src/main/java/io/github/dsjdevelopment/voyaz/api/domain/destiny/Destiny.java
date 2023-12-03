package io.github.dsjdevelopment.voyaz.api.domain.destiny;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "destinations")
@Entity(name = "Destiny")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Destiny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photo;
    private String name;
    private BigDecimal price;

    public Destiny(DestinyRegistrationData data) {
        this.photo = data.photo();
        this.name = data.name();
        this.price = data.price();
    }

    public void dataUpdate(DestinyUpdateData data) {
        if (data.photo() != null) {
            this.photo = data.photo();
        }
        if (data.name() != null) {
            this.name = data.name();
        }
        if (data.price() != null) {
            this.price = data.price();
        }

    }

}
