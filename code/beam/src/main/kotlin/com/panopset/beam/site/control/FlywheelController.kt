package com.panopset.beam.site.control

import com.panopset.compat.Stringop
import com.panopset.flywheel.FlywheelListDriver
import com.panopset.flywheel.LineFeedRules
import com.panopset.beam.web.FwInput
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

@Controller
class FlywheelController {
    @GetMapping("/version")
    fun getVersion(): ResponseEntity<String> {
        return ResponseEntity.ok("<html><head><title>Panopset Beam Version</title></head><body>asdfasd</body></html>")
    }


    @PostMapping("/af")
    fun getResult(@RequestBody fwInput: FwInput, response: HttpServletResponse): ResponseEntity<String> {
        var result = "Input not found."
        val listParam = Stringop.stringToList(fwInput.listStr)
        try {
            if (fwInput.template.isNotBlank()) {
                result = FlywheelListDriver.Builder(listParam, fwInput.template)
                    .withLineFeedRules(LineFeedRules(fwInput.lineBreakStr, fwInput.listBreakStr))
                    .withTokens(fwInput.tokens).withSplitz(fwInput.splitz).build().output
            }
        } catch (e: IOException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "IOException in /af POST", e)
            result = e.message ?: "Unknown error in af POST occurred, see server logs."
        }
        return ResponseEntity.ok(result)
    }
}
