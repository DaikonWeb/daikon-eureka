package daikon.eurekaclient

import daikon.HttpServer
import daikon.eurekaclient.Assert.withTimeout
import khttp.get
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS

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
                withTimeout(50) { assertThat(get("http://localhost:8761/").text).contains("GARLIC") }
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
                        res.write("I called onion and it responds: ${get(homePage).text}")
                    }
                    .start().use {
                        withTimeout(60) {
                            assertThat(get("http://localhost:4545/").text)
                                .isEqualTo("I called onion and it responds: I'm onion")
                        }
                    }
            }
    }
}