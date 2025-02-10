package co.devskills.springbootboilerplate.entity;

import lombok.*;
import java.util.*;
import jakarta.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "presentation_id")
    private Presentation presentation;
}