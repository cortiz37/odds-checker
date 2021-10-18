package com.odds.checker.model.output;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class Match {

    private final String name;

    private final List<Odds> odds;
}
