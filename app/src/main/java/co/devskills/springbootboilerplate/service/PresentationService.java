package co.devskills.springbootboilerplate.service;

import co.devskills.springbootboilerplate.dto.request.PollRequest;
import co.devskills.springbootboilerplate.dto.response.CurrentPollResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PresentationService {
    UUID createPresentation(List<PollRequest> pollRequests);
    Optional<CurrentPollResponse> moveToNextPoll(UUID presentationId);
    Optional<CurrentPollResponse> getCurrentPoll(UUID presentationId);
    boolean presentationExists(UUID presentationId);
}
