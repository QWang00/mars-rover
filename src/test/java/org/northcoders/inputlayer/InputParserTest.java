package org.northcoders.inputlayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    InputParser inputParser;

    @BeforeEach
    void setUp() {
        inputParser = new InputParser();
    }

    @Nested
    class parseCompassDirection{
        @Test
        void parseCompassDirection_emptyString() {
            assertThrows(IllegalArgumentException.class,()-> inputParser.parseCompassDirection(""));
        }
        @Test
        void parseCompassDirection_nullString() {
            assertThrows(NullPointerException.class,()->inputParser.parseCompassDirection(null));
        }
        @Test
        void parseCompassDirection_validString() {
            CompassDirection expectedN = CompassDirection.N;
            CompassDirection expectedS = CompassDirection.S;
            CompassDirection expectedW = CompassDirection.W;
            CompassDirection expectedE = CompassDirection.E;
            assertEquals(expectedN, inputParser.parseCompassDirection("N"));
            assertEquals(expectedS, inputParser.parseCompassDirection("S"));
            assertEquals(expectedE, inputParser.parseCompassDirection("E"));
            assertEquals(expectedW, inputParser.parseCompassDirection("W"));
        }
        @Test
        void parseCompassDirection_inValidString() {
            assertThrows(IllegalArgumentException.class, ()->inputParser.parseCompassDirection("n"));
            assertThrows(IllegalArgumentException.class, ()-> inputParser.parseCompassDirection("north"));
        }
    }

    @Nested
    class parseInstruction{
        @Test
        void parseInstruction_emptyString() {
            assertThrows(IllegalArgumentException.class,()->inputParser.parseInstruction(""));
        }
        @Test
        void parseInstruction_nullString() {
            assertThrows(NullPointerException.class,()->inputParser.parseInstruction(null));
        }
        @Test
        void parseInstruction_validString() {
            List<Instruction> expectedLMR = new ArrayList<>();
            expectedLMR.add(Instruction.L);
            expectedLMR.add(Instruction.M);
            expectedLMR.add(Instruction.R);
            expectedLMR.add(Instruction.R);
            expectedLMR.add(Instruction.M);
            assertEquals(expectedLMR, inputParser.parseInstruction("LMRRM"));

        }
        @Test
        void parseInstruction_inValidString() {
            assertThrows(IllegalArgumentException.class,()->inputParser.parseInstruction("lmrrrllmm"));
            assertThrows(IllegalArgumentException.class,()->inputParser.parseInstruction("left"));
        }
    }

    @Nested
    class parsePlateauSize {
        @Test
        void parsePlateauSize_emptyString() {
            assertThrows(NumberFormatException.class, ()->inputParser.parsePlateauSize("", ""));
            assertThrows(NumberFormatException.class, ()->inputParser.parsePlateauSize("6", ""));
            assertThrows(NumberFormatException.class, ()->inputParser.parsePlateauSize("", "7"));
        }

        @Test
        void parsePlateauSize_nullString() {
            assertThrows(NullPointerException.class,()->inputParser.parsePlateauSize(null, null));
            assertThrows(NullPointerException.class,()->inputParser.parsePlateauSize(null, "5"));
            assertThrows(NullPointerException.class,()->inputParser.parsePlateauSize("9", null));
        }

        @Test
        void parsePlateauSize_validString() {
            int[] expectedXY = new int[] {6,7};
            int[] expectedXY2 = new int[] {15,15};
            assertArrayEquals(expectedXY, inputParser.parsePlateauSize("6", "7"));
            assertArrayEquals(expectedXY2, inputParser.parsePlateauSize("15", "15"));
        }

        @Test
        void parsePlateauSize_InvalidNumericString() {
            assertThrows(IllegalArgumentException.class,()->inputParser.parsePlateauSize("0", "0"));
            assertThrows(IllegalArgumentException.class,()->inputParser.parsePlateauSize("9", "-1"));
            assertThrows(IllegalArgumentException.class,()->inputParser.parsePlateauSize("-8", "56"));

        }

        @Test
        void parsePlateauSize_NonNumericValueString() {
            assertThrows(IllegalArgumentException.class,()->inputParser.parsePlateauSize("a", "0"));
            assertThrows(IllegalArgumentException.class,()->inputParser.parsePlateauSize("b", "0"));
            assertThrows(IllegalArgumentException.class,()->inputParser.parsePlateauSize("%", "d"));

        }
    }


}