package daikon.eureka

import daikon.HttpServer
import daikon.eureka.Assert.withTimeout
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import topinambur.http

@TestInstance(PER_CLASS)
class EurekaClientTest {

    private lateinit var eurekaServer: EurekaServer

    @BeforeAll
    fun beforeAll() {
        eurekaServer = EurekaServer().start()
    }

    @AfterAll
    fun afterAll() {
        eurekaServer.close()
    }

    @Test
    fun `can publish a service on eureka server`() {
        HttpServer()
            .initDiscoveryClient()
            .start().use {
                withTimeout(50) { assertThat("http://localhost:8761/".http.get().body).contains("GARLIC") }
            }
    }

    @Test
    fun `retrieve a service from the server`() {
        HttpServer(4546).initDiscoveryClient("onion")
            .get("/") { _, res -> res.write("I'm onion") }
            .start().use {
                HttpServer()
                    .initDiscoveryClient("carrot")
                    .get("/") { _, res, ctx ->
                        val homePage = ctx.discoveryClient().getNextServerFromEureka("onion", false).homePageUrl
                        res.write("I called onion and it responds: ${homePage.http.get().body}")
                    }
                    .start().use {
                        withTimeout(60) {
                            assertThat("http://localhost:4545/".http.get().body)
                                .isEqualTo("I called onion and it responds: I'm onion")
                        }
                    }
            }
    }
}