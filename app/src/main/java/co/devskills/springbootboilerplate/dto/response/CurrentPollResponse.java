package co.devskills.springbootboilerplate.dto.response;

import java.util.List;
import java.util.UUID;

public record CurrentPollResponse(
    String pollId,
    String question,
    List<OptionResponse> options
) {}
