package co.devskills.springbootboilerplate.dto.response;

import lombok.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentPollResponse {
    private UUID pollId;
    private String question;
    private List<OptionResponse> options;
}