package co.devskills.springbootboilerplate.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CreatePresentationRequest(
    @NotEmpty(message = "Polls cannot be empty")
    List<PollRequest> polls
) {}
