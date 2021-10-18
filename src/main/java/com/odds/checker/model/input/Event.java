package com.odds.checker.model.input;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper=false)
public class Event extends Unique {

    private String name;

    private Set<String> tags;
}
