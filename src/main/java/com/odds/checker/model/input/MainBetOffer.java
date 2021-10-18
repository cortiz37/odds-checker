package com.odds.checker.model.input;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class MainBetOffer extends Unique {

    private List<Outcome> outcomes;
}
