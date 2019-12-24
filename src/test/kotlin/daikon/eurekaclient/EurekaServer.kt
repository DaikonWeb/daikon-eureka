package daikon.eurekaclient

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.PropertySource


@EnableEurekaServer
@SpringBootApplication
@PropertySource("classpath:eureka-server.properties")
open class EurekaServer : AutoCloseable {
    private lateinit var run: ConfigurableApplicationContext

    fun start(): EurekaServer {
        run = SpringApplication.run(EurekaServer::class.java)
        return this
    }

    override fun close() {
        run.close()
    }
}