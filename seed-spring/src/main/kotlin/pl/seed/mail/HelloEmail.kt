package pl.seed.mail

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class HelloEmail(
    @get:NotBlank @Size(max = 100) override val title: String,
    @get:NotBlank @Size(max = 100) override val body: String,
    @get:Email override val to: String
) : Mail
