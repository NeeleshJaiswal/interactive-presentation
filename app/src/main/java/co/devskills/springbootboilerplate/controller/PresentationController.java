package co.devskills.springbootboilerplate.controller;

import co.devskills.springbootboilerplate.dto.request.CreatePresentationRequest;
import co.devskills.springbootboilerplate.dto.response.CreatePresentationResponse;
import co.devskills.springbootboilerplate.dto.response.CurrentPollResponse;
import co.devskills.springbootboilerplate.service.PresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/presentations")
@RequiredArgsConstructor
@Slf4j
public class PresentationController {

    private final PresentationService presentationService;

    @PostMapping
    public ResponseEntity<CreatePresentationResponse> createPresentation(
            @Valid @RequestBody CreatePresentationRequest request) {
        log.info("Creating a new presentation with request: {}", request);

        String presentationId = presentationService.createPresentation(request.polls()); // FIX: Use direct field access
        // FIX: Change UUID to String

        log.info("Presentation created successfully with ID: {}", presentationId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreatePresentationResponse(presentationId));
    }

    @PutMapping("/{presentationId}/polls/current")
    public ResponseEntity<CurrentPollResponse> moveToNextPoll(@PathVariable String presentationId) {
        log.info("Moving to the next poll for presentation ID: {}", presentationId);

        return presentationService.moveToNextPoll(presentationId)
                .map(response -> {
                    log.info("Moved to next poll successfully for presentation ID: {}", presentationId);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    log.warn("No more polls available for presentation ID: {}", presentationId);
                    return ResponseEntity.notFound().build();
                });
    }

    @GetMapping("/{presentationId}/polls/current")
    public ResponseEntity<CurrentPollResponse> getCurrentPoll(@PathVariable String presentationId) {
        log.info("Fetching current poll for presentation ID: {}", presentationId);

        return presentationService.getCurrentPoll(presentationId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("No active poll available for presentation ID: {}", presentationId);
                    return ResponseEntity.notFound().build();
                });
    }
}
