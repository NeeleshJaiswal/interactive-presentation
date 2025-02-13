package co.devskills.springbootboilerplate.dto.request;

import java.util.List;

public record PollRequest(String question, List<OptionRequest> options) {}
