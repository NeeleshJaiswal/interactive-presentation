package co.devskills.springbootboilerplate.dto.request;

import lombok.Data;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;

@Data
public class CreatePresentationRequest {
    @NotEmpty(message = "Polls cannot be empty")
    private List<PollRequest> polls;
}
