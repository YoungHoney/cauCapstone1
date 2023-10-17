package cap.backend.back.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Oldevents {
    @Id
    @Column(name="oldeventname")
    private String name;

    private int year;

}
