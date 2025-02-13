package co.devskills.springbootboilerplate.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;

public record CreatePresentationResponse(
    @JsonProperty("presentation_id")
    String presentationId
) {}
