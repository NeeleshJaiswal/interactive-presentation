package co.devskills.springbootboilerplate.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import co.devskills.springbootboilerplate.dto.request.PollRequest;
import co.devskills.springbootboilerplate.dto.response.CurrentPollResponse;
import co.devskills.springbootboilerplate.dto.response.OptionResponse;
import co.devskills.springbootboilerplate.entity.Option;
import co.devskills.springbootboilerplate.entity.Poll;
import co.devskills.springbootboilerplate.entity.Presentation;
import co.devskills.springbootboilerplate.repository.PresentationRepository;
import co.devskills.springbootboilerplate.service.PresentationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PresentationServiceImpl implements PresentationService {
    private final PresentationRepository presentationRepository;

    @Override
    public UUID createPresentation(List<PollRequest> pollRequests) {
        Presentation presentation = Presentation.builder()
            .currentPollIndex(-1)
            .polls(new ArrayList<>())  // Initialize list
            .build();
    
        List<Poll> polls = pollRequests.stream()
            .map(this::convertToPoll)
            .peek(poll -> poll.setPresentation(presentation)) // Ensure polls are linked
            .collect(Collectors.toList());
    
        presentation.getPolls().addAll(polls); // Add instead of set
        return presentationRepository.save(presentation).getId();
    }

    

    @Override
    public Optional<CurrentPollResponse> moveToNextPoll(UUID presentationId) {
        return presentationRepository.findById(presentationId)
            .flatMap(presentation -> {
                int nextIndex = presentation.getCurrentPollIndex() + 1;
                if (nextIndex >= presentation.getPolls().size()) {
                    return Optional.empty(); // No more polls
                }
                presentation.setCurrentPollIndex(nextIndex);
                presentationRepository.save(presentation);
                
                // Return the poll at the new index
                Poll currentPoll = presentation.getPolls().get(nextIndex);
                return Optional.of(convertToResponse(currentPoll));
            });
    }

    @Override
    public Optional<CurrentPollResponse> getCurrentPoll(UUID presentationId) {
        return presentationRepository.findById(presentationId)
                .filter(presentation -> presentation.getCurrentPollIndex() >= 0)
                .filter(presentation -> presentation.getCurrentPollIndex() < presentation.getPolls().size())
                .map(presentation -> convertToResponse(
                        presentation.getPolls().get(presentation.getCurrentPollIndex())
                ));
    }

    @Override
    public boolean presentationExists(UUID presentationId) {
        return presentationRepository.existsById(presentationId);
    }

    private Poll convertToPoll(PollRequest pollRequest) {
        Poll poll = Poll.builder()
            .question(pollRequest.getQuestion())
            .options(new ArrayList<>()) // Initialize empty list
            .build();
        List<Option> options = pollRequest.getOptions().stream()
            .map(opt -> Option.builder()
                .key(opt.getKey())
                .value(opt.getValue())
                .poll(poll) // Set bidirectional relationship
                .build()
            ).collect(Collectors.toList());
    
        poll.setOptions(options);
    
        return poll;
    }

    private CurrentPollResponse convertToResponse(Poll poll) {
        return CurrentPollResponse.builder()
                .pollId(poll.getId())
                .question(poll.getQuestion())
                .options(poll.getOptions().stream()
                        .map(opt -> new OptionResponse(opt.getKey(), opt.getValue()))
                        .collect(Collectors.toList()))
                .build();
    }
}