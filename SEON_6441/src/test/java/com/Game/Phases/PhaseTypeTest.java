package com.Game.Phases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PhaseTypeTest {

    @Test
    public void testEnumValues() {
        PhaseType[] values = PhaseType.values();
        // Expect three phases in order.
        assertEquals(3, values.length);
        assertEquals(PhaseType.STARTUP, values[0]);
        assertEquals(PhaseType.ISSUE_ORDER, values[1]);
        assertEquals(PhaseType.ORDER_EXECUTION, values[2]);
    }
}
