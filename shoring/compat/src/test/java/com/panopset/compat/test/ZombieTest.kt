package com.panopset.compat.test

import com.panopset.compat.Zombie
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ZombieTest {
    var foo = "bar"
    @Test
    fun test() {
        val zombie = Zombie()
        Assertions.assertTrue(zombie.isActive)
        Assertions.assertEquals("bar", foo)
        zombie.addStopAction { foo = "bat" }
        Assertions.assertEquals("bar", foo)
        Assertions.assertTrue(zombie.isActive)
        Assertions.assertEquals("bar", foo)
        Assertions.assertTrue(zombie.isActive)
        zombie.stop()
        Assertions.assertFalse(zombie.isActive)
        Assertions.assertEquals("bat", foo)
        zombie.stop()
        Assertions.assertFalse(zombie.isActive)
        Assertions.assertEquals("bat", foo)
        zombie.resume()
        Assertions.assertTrue(zombie.isActive)
    }
}