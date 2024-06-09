package com.panopset.tests.blackjackEngine

val BLACKJACK_WWCD = arrayOf(
    "ace of clubs",
    "six of clubs",
    "jack of clubs",
    "jack of clubs",
    "jack of clubs"
)
val SPLITACES_DLRBLACKJACK = arrayOf(
    "ace of clubs",
    "ace of clubs",
    "ace of clubs",
    "jack of clubs",
    "jack of spades",
    "jack of hearts",
    "jack of diamonds"
)
val SPLITACES_FIRST_HAND_PLAYER_BLACKJACK = arrayOf(
    "ace of clubs",
    "seven of spades",
    "ace of hearts",
    "king of spades",
    "ten of diamonds",
    "eight of spades"
)
val SPLITACES_DLRHIT_TO_21 = arrayOf( // initial deal
    "ace of clubs",
    "four of clubs",
    "ace of clubs",
    "jack of clubs",  // player splits to soft 21s
    "jack of spades",
    "jack of hearts",  // dealer hits his 14 to 21.
    "seven of diamonds"
)
val SPLITACES_DLRTIE = arrayOf(
    "ace of clubs",
    "ace of clubs",
    "ace of clubs",
    "jack of clubs",
    "jack of clubs",
    "jack of spades"
)
