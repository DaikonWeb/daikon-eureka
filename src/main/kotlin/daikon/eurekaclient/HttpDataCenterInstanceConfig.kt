package daikon.eurekaclient

import com.netflix.appinfo.MyDataCenterInstanceConfig
import daikon.Context

class HttpDataCenterInstanceConfig(namespace: String, private val context: Context) : MyDataCenterInstanceConfig(namespace) {

    override fun getSecurePort(): Int {
        return context.port()
    }

    override fun getNonSecurePort(): Int {
        return context.port()
    }
}