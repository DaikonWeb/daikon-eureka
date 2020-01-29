package daikon.eureka

import com.netflix.appinfo.ApplicationInfoManager
import com.netflix.appinfo.InstanceInfo.InstanceStatus.UP
import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider
import com.netflix.discovery.DefaultEurekaClientConfig
import com.netflix.discovery.DiscoveryClient
import daikon.Context
import daikon.HttpServer

fun HttpServer.initDiscoveryClient(namespace: String = "eureka"): HttpServer {
    afterStart { ctx ->
        val instanceConfig = HttpDataCenterInstanceConfig(namespace, ctx)
        instanceConfig.securePort
        val instanceInfo = EurekaConfigBasedInstanceInfoProvider(instanceConfig).get()
        val applicationInfoManager = ApplicationInfoManager(instanceConfig, instanceInfo)
        val discoveryClient = DiscoveryClient(applicationInfoManager, DefaultEurekaClientConfig(namespace))
        applicationInfoManager.setInstanceStatus(UP)
        ctx.addAttribute("discoveryClient", discoveryClient)
    }
    beforeStop { ctx ->
        ctx.discoveryClient().shutdown()
    }
    return this
}

fun Context.discoveryClient(): DiscoveryClient {
    return getAttribute("discoveryClient")
}