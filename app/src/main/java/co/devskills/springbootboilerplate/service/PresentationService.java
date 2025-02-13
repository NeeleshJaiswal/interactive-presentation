package co.devskills.springbootboilerplate.service;

import co.devskills.springbootboilerplate.dto.request.PollRequest;
import co.devskills.springbootboilerplate.dto.response.CurrentPollResponse;

import java.util.List;
import java.util.Optional;

public interface PresentationService {

    /**
     * Creates a new presentation and returns its ID.
     */
    String createPresentation(List<PollRequest> pollRequests);

    /**
     * Moves to the next poll in the presentation.
     */
    Optional<CurrentPollResponse> moveToNextPoll(String presentationId);

    /**
     * Retrieves the current poll in the presentation.
     */
    Optional<CurrentPollResponse> getCurrentPoll(String presentationId);

    /**
     * Checks if a presentation exists by ID.
     */
    boolean presentationExists(String presentationId);
}
