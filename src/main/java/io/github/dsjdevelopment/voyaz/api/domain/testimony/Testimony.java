package io.github.dsjdevelopment.voyaz.api.domain.testimony;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "testimonials")
@Entity(name = "Testimony")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Testimony {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photo;

    @Column(name = "text_testimony")
    private String textTestimony;

    @Column(name = "name_person_testimony")
    private String namePersonTestimony;

    public Testimony(TestimonyRegistrationData data) {
        this.photo = data.photo();
        this.textTestimony = data.textTestimony();
        this.namePersonTestimony = data.namePersonTestimony();
    }

    public void dataUpdate(TestimonyUpdateData data) {
        if (data.photo() != null) {
            this.photo = data.photo();
        }
        if (data.textTestimony() != null) {
            this.textTestimony = data.textTestimony();
        }
        if (data.namePersonTestimony() != null) {
            this.namePersonTestimony = data.namePersonTestimony();
        }

    }

}
