package co.devskills.springbootboilerplate.entity;

import lombok.*;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "presentation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, setterPrefix = "with")
public class Presentation {
    @Id
    @Column(columnDefinition = "TEXT")
    private String id = UUID.randomUUID().toString();

    @OneToMany(mappedBy = "presentation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Poll> polls = new ArrayList<>();

    @Column(nullable = false)
    private int currentPollIndex;
}

