package com.Game.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CardTypeTest {

    @Test
    public void testEnumValues() {
        CardType[] values = CardType.values();
        // Expecting 4 enum constants in the order defined in the source file.
        assertEquals(4, values.length);
        assertEquals(CardType.BOMB, values[0]);
        assertEquals(CardType.BLOCKADE, values[1]);
        assertEquals(CardType.AIRLIFT, values[2]);
        assertEquals(CardType.NEGOTIATE, values[3]);
    }
}
