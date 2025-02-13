package co.devskills.springbootboilerplate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "option")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true, setterPrefix = "with")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;
    private String value;

    @ManyToOne
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;
}

