package com.panopset.tests.blackjackEngine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.panopset.blackjackEngine.DeckPile;

public class DeckPileTest {

  @Test
  void test() {
    DeckPile.reset();
    Assertions.assertTrue(DeckPile.pull());
    Assertions.assertFalse(DeckPile.pull());
    Assertions.assertTrue(DeckPile.pull());
  }
}
