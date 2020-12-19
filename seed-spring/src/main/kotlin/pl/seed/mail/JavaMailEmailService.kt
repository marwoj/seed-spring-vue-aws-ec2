package pl.seed.mail

import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import reactor.core.scheduler.Schedulers
import javax.mail.Message.RecipientType
import javax.mail.internet.MimeMessage

@Component
class JavaMailEmailService internal constructor(
    private val mailSender: JavaMailSender
) : EmailService {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun send(email: Mail) {
        val message = mailSender.createMimeMessage().apply {
            subject = email.title
            setText(email.body)
            setRecipients(RecipientType.TO, email.to)
        }
        sendAsync(message)
    }

    private fun sendAsync(message: MimeMessage) {
        logger.info("Schedule message send: $message")
        Schedulers.boundedElastic()
            .schedule { mailSender.send(message) }
    }
}
