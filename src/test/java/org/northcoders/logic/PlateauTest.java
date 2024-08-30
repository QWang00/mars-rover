package org.northcoders.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlateauTest {

    Plateau plateau;

    @Test
    void plateauConstructor_validDimension() {
        plateau = new Plateau(new int[] {3,4}, "P1");
        assertEquals(3, plateau.getMaxX());
        assertEquals(4, plateau.getMaxY());
        assertEquals("P1", plateau.getName());
    }




}