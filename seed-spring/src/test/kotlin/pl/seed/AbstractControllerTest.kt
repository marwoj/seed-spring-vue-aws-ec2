package pl.seed

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.request
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
abstract class AbstractControllerTest {

    protected lateinit var mvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    fun createMvc(context: WebApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply<DefaultMockMvcBuilder>(springSecurity())
            .defaultRequest<DefaultMockMvcBuilder>(
                get("/")
                    .with(csrf()) // add correct CSRF to all requests (not just GET)
                    .contentType(APPLICATION_JSON)
            )
            .build()
    }

    protected fun toJson(o: Any): String = objectMapper.writeValueAsString(o)

    protected fun performAsync(request: MockHttpServletRequestBuilder): ResultActions {
        val requested = mvc.perform(request)
        val mvcResult = requested
            .andExpect(request().asyncStarted())
            .andReturn()
        return mvc.perform(asyncDispatch(mvcResult))
    }
}
