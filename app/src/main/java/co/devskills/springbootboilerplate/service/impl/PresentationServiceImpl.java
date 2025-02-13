package co.devskills.springbootboilerplate.service.impl;

import co.devskills.springbootboilerplate.dto.request.PollRequest;
import co.devskills.springbootboilerplate.dto.response.CurrentPollResponse;
import co.devskills.springbootboilerplate.dto.response.OptionResponse;
import co.devskills.springbootboilerplate.entity.Option;
import co.devskills.springbootboilerplate.entity.Poll;
import co.devskills.springbootboilerplate.entity.Presentation;
import co.devskills.springbootboilerplate.repository.PresentationRepository;
import co.devskills.springbootboilerplate.service.PresentationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PresentationServiceImpl implements PresentationService {

    private final PresentationRepository presentationRepository;

    @Override
    @Transactional
    public String createPresentation(List<PollRequest> pollRequests) {
        if (pollRequests == null || pollRequests.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Polls cannot be empty");
        }

        log.info("Creating a new presentation with {} polls", pollRequests.size());

        // Initialize Presentation before use
        String presentationId = UUID.randomUUID().toString();
        Presentation presentation = Presentation.builder()
                .withId(presentationId)
                .withCurrentPollIndex(-1)
                .withPolls(new ArrayList<>()) // Initialize empty list
                .build();

        // Now add polls and link them to the presentation
        List<Poll> polls = pollRequests.stream()
                .map(this::convertToPoll)
                .peek(poll -> poll.setPresentation(presentation)) // Correct linking
                .collect(Collectors.toList());

        presentation.getPolls().addAll(polls); // Add polls after initialization

        Presentation savedPresentation = presentationRepository.save(presentation);
        log.info("Presentation created successfully with ID: {}", savedPresentation.getId());

        return savedPresentation.getId();
    }

    @Override
    public Optional<CurrentPollResponse> moveToNextPoll(String presentationId) {
        log.info("Moving to next poll for presentation ID: {}", presentationId);
        Presentation presentation = presentationRepository.findById(presentationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Presentation not found"));

        List<Poll> polls = presentation.getPolls();

        if (polls.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No polls available in this presentation.");
        }

        if (presentation.getCurrentPollIndex() + 1 >= polls.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No more polls available.");
        }

        // Move to the next poll
        presentation.setCurrentPollIndex(presentation.getCurrentPollIndex() + 1);
        presentationRepository.save(presentation);

        return getCurrentPoll(presentationId);
    }

    @Override
    public Optional<CurrentPollResponse> getCurrentPoll(String presentationId) {
        log.info("Fetching current poll for presentation ID: {}", presentationId);

        Presentation presentation = presentationRepository.findById(presentationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Presentation not found"));

        if (presentation.getPolls().isEmpty()) { // ✅ Check if polls exist
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No polls exist in this presentation.");
        }

        if (presentation.getCurrentPollIndex() < 0 || presentation.getCurrentPollIndex() >= presentation.getPolls().size()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "No active poll in this presentation.");
        }

        Poll currentPoll = presentation.getPolls().get(presentation.getCurrentPollIndex());

        return Optional.of(new CurrentPollResponse(
                currentPoll.getId(), // ✅ Ensure correct UUID handling
                currentPoll.getQuestion(),
                currentPoll.getOptions().stream()
                        .map(option -> new OptionResponse(option.getKey(), option.getValue()))
                        .collect(Collectors.toList())
        ));
    }


    @Override
    public boolean presentationExists(String presentationId) {
        return presentationRepository.existsById(presentationId);
    }

    private Poll convertToPoll(PollRequest pollRequest) {
        Poll poll = Poll.builder()
                .withId(UUID.randomUUID().toString())
                .withQuestion(pollRequest.question())
                .withOptions(new ArrayList<>()) // ✅ Ensure options list is initialized
                .build();

        List<Option> options = pollRequest.options().stream()
                .map(optionRequest -> Option.builder()
                        .withKey(optionRequest.key())
                        .withValue(optionRequest.value())
                        .withPoll(poll) // ✅ Ensure options are linked to poll
                        .build())
                .toList();

        poll.getOptions().addAll(options); // ✅ Add options to poll

        return poll;
    }

}
