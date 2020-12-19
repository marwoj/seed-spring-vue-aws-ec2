package pl.seed.mail

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Keeps sent mails in a thread-safe list. The list must be cleared between tests.
 *
 * The recommended way of using it is registering as an extension:
 * ```
 * @RegisterExtension @JvmField val emailService: RecordingEmailService
 * ```
 *
 * When registered as an extension, the list is cleared before each test method.
 */
class RecordingEmailService : EmailService, BeforeEachCallback {
    private val _emails = CopyOnWriteArrayList<Mail>()

    val emails: List<Mail>
        get() = _emails

    private fun reset() {
        _emails.clear()
    }

    inline fun <reified T : Mail> findOne(): T {
        return emails.find { it is T }!! as T
    }

    override fun send(email: Mail) {
        _emails.add(email)
    }

    override fun beforeEach(context: ExtensionContext?) {
        reset()
    }
}
