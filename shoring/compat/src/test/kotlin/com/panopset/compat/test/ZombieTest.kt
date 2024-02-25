package com.panopset.compat.test

import com.panopset.compat.Zombie
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class ZombieTest {
    var foo = "bar"
    @Test
    fun test() {
        Assertions.assertTrue(Zombie.isActive)
        Assertions.assertEquals("bar", foo)
        Zombie.addStopAction { foo = "bat" }
        Assertions.assertEquals("bar", foo)
        Assertions.assertTrue(Zombie.isActive)
        Assertions.assertEquals("bar", foo)
        Assertions.assertTrue(Zombie.isActive)
        Zombie.stop()
        Assertions.assertFalse(Zombie.isActive)
        Assertions.assertEquals("bat", foo)
        Zombie.stop()
        Assertions.assertFalse(Zombie.isActive)
        Assertions.assertEquals("bat", foo)
        Zombie.resume()
        Assertions.assertTrue(Zombie.isActive)
    }
}
