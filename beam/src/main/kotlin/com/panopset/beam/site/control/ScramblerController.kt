package com.panopset.beam.site.control

import com.panopset.compat.Stringop
import com.panopset.compat.TextScrambler
import com.panopset.beam.web.ScramblerInput
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller
class ScramblerController {

    @PostMapping("/scramble")
    fun scramble(@RequestBody si: ScramblerInput, response: HttpServletResponse?): ResponseEntity<String?>? {
        val result: String
        val text: String = si.text
        val koi: String = si.koi
        val passphrase: String = si.passphrase
        val k = Stringop.parseInt(koi, 10000)
        result = try {
            TextScrambler().withKeyObtentionIters(k).encrypt(passphrase, text)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
        return ResponseEntity.ok(result)
    }

    @PostMapping("/unscramble")
    fun unscramble(@RequestBody si: ScramblerInput, response: HttpServletResponse?): ResponseEntity<String?>? {
        val result: String
        val text: String = si.text
        val koi: String = si.koi
        val passphrase: String = si.passphrase
        val k = Stringop.parseInt(koi, 10000)
        result = try {
            TextScrambler().withKeyObtentionIters(k).decrypt(passphrase, text)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
        }
        return ResponseEntity.ok(result)
    }
}
