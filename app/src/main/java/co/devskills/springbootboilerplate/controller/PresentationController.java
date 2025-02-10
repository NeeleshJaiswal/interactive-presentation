package co.devskills.springbootboilerplate.controller;

import co.devskills.springbootboilerplate.dto.request.CreatePresentationRequest;
import co.devskills.springbootboilerplate.dto.response.CreatePresentationResponse;
import co.devskills.springbootboilerplate.dto.response.CurrentPollResponse;
import co.devskills.springbootboilerplate.service.PresentationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/presentations")
@RequiredArgsConstructor
@Slf4j
public class PresentationController {

    private final PresentationService presentationService;

    private UUID parsePresentationId(String presentationId) {
        try {
            return UUID.fromString(presentationId);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Presentation not found");
        }
    }

    @PostMapping
    public ResponseEntity<CreatePresentationResponse> createPresentation(
            @Valid @RequestBody CreatePresentationRequest request) {
        log.info("Received request: {}", request);  // Add logging
        UUID presentationId = presentationService.createPresentation(request.getPolls());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreatePresentationResponse(presentationId));
    }

    @PutMapping("/{presentationId}/polls/current")
    public ResponseEntity<CurrentPollResponse> moveToNextPoll(@PathVariable String presentationId) {
        UUID uuid = parsePresentationId(presentationId);
        return presentationService.moveToNextPoll(uuid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{presentationId}/polls/current")
    public ResponseEntity<CurrentPollResponse> getPresenterCurrentPoll(@PathVariable String presentationId) {
        UUID uuid = parsePresentationId(presentationId);
        return presentationService.getCurrentPoll(uuid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{presentationId}/poll/current")
    public ResponseEntity<CurrentPollResponse> getAudienceCurrentPoll(@PathVariable String presentationId) {
        UUID uuid = parsePresentationId(presentationId);
        if (!presentationService.presentationExists(uuid)) {
            return ResponseEntity.notFound().build();
        }
        return presentationService.getCurrentPoll(uuid)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}