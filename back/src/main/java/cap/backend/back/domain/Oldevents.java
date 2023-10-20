package cap.backend.back.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Oldevents {
    @Id
    @GeneratedValue
    @Column(name="oldevents_id")
    private long id;


    @Column(name="oldeventname")
    private String name;

    @Column(name="oldeventsyear")
    private int year;

}
