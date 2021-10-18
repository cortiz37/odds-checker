package com.odds.checker.model.output;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Odds {

    private final String label;

    private final double value;
}
