package com.odds.checker.model.input;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Outcome extends Unique {

    private String label;

    private int odds;
}
