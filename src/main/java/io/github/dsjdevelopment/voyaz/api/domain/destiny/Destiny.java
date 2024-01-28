package io.github.dsjdevelopment.voyaz.api.domain.destiny;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "destinations")
@Entity(name = "Destiny")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Destiny {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photo1;
    private String photo2;
    private String name;
    private BigDecimal price;
    private String goal;

    @Column(name = "descriptive_text")
    private String descriptiveText;

    public Destiny(DestinyRegistrationData data) {
        this.photo1 = data.photo1();
        this.photo2 = data.photo2();
        this.name = data.name();
        this.price = data.price();
        this.goal = data.goal();
        this.descriptiveText = data.descriptiveText();
    }

    public void dataUpdate(DestinyUpdateData data) {
        this.photo1 = data.photo1();
        this.photo2 = data.photo2();
        this.name = data.name();
        this.price = data.price();
        this.goal = data.goal();
        if (data.descriptiveText() != null && !data.descriptiveText().isEmpty()){
            this.descriptiveText = data.descriptiveText();
        }

    }

}
