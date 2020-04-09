package daikon.eureka

import com.netflix.appinfo.MyDataCenterInstanceConfig
import daikon.core.Context
import daikon.port

class HttpDataCenterInstanceConfig(namespace: String, private val context: Context) : MyDataCenterInstanceConfig(namespace) {

    override fun getSecurePort(): Int {
        return context.port()
    }

    override fun getNonSecurePort(): Int {
        return context.port()
    }
}