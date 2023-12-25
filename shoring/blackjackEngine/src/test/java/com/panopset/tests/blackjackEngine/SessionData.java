package com.panopset.tests.blackjackEngine;

interface SessionData {

    String[] BLACKJACK_WWCD = new String[]{
            "ace of clubs",
            "six of clubs",
            "jack of clubs",
            "jack of clubs",
            "jack of clubs"
    };

    String[] SPLITACES_DLRBLACKJACK = new String[]{
            "ace of clubs",
            "ace of clubs",
            "ace of clubs",
            "jack of clubs",
            "jack of spades",
            "jack of hearts",
            "jack of diamonds"
    };

    String[] SPLITACES_FIRST_HAND_PLAYER_BLACKJACK = new String[]{
            "ace of clubs",
            "seven of spades",
            "ace of hearts",
            "king of spades",
            "ten of diamonds",
            "eight of spades"
    };

    String[] SPLITACES_DLRHIT_TO_21 = new String[]{
            // initial deal
            "ace of clubs",
            "four of clubs",
            "ace of clubs",
            "jack of clubs",

            // player splits to to soft 21s
            "jack of spades",
            "jack of hearts",

            // dealer hits his 14 to 21.
            "seven of diamonds"
    };

    String[] SPLITACES_DLRTIE = new String[]{
            "ace of clubs",
            "ace of clubs",
            "ace of clubs",
            "jack of clubs",
            "jack of clubs",
            "jack of spades",
    };
}
