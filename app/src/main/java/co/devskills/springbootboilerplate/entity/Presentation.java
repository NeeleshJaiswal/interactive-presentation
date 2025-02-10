package co.devskills.springbootboilerplate.entity;

import lombok.*;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Presentation {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT -1") // Default value
    private int currentPollIndex;

    @OneToMany(mappedBy = "presentation", cascade = CascadeType.ALL)
    @OrderBy("order ASC")
    private List<Poll> polls = new ArrayList<>();
}