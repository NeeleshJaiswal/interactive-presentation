package co.devskills.springbootboilerplate.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
public class CreatePresentationResponse {
    @JsonProperty("presentation_id")
    private UUID presentationId;
}
