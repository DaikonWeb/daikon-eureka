package daikon.eurekaclient

import org.assertj.core.api.AbstractCharSequenceAssert
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

object Assert {
    fun withTimeout(seconds: Int, function: () -> AbstractCharSequenceAssert<*, *>) {
        val startTime = LocalDateTime.now()
        do {
            try {
                function()
                break
            } catch (t: Throwable) {
                if (startTime.until(LocalDateTime.now(), ChronoUnit.SECONDS) > seconds) {
                    throw RuntimeException("Timeout reached", t)
                }

                Thread.sleep(500)
            }
        } while (true)
    }
}