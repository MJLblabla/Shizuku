package moe.shizuku.manager.adb

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.IOException
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.ServerSocket

@RequiresApi(Build.VERSION_CODES.R)
class AdbMdns2(
    context: Context, private val serviceType: String,
    private val port: (Int) -> Unit
) : NsdManager.DiscoveryListener {

    internal class ResolveListener(private val adbMdns: AdbMdns2) : NsdManager.ResolveListener {
        override fun onResolveFailed(nsdServiceInfo: NsdServiceInfo, i: Int) {}

        override fun onServiceResolved(nsdServiceInfo: NsdServiceInfo) {
            adbMdns.onServiceResolved(nsdServiceInfo)
        }
    }

    private var registered = false
    private var running = false
    private var serviceName: String? = null

    private val nsdManager: NsdManager = context.getSystemService(NsdManager::class.java)

    fun start() {
        if (running) return
        running = true
        nsdManager.discoverServices(serviceType, NsdManager.PROTOCOL_DNS_SD, this)
    }

    fun stop() {
        if (!running) return
        running = false
        nsdManager.stopServiceDiscovery(this)
    }

    override fun onServiceFound(info: NsdServiceInfo) {
        Log.v(TAG, "onServiceFound: ${info.port}")
        try {
            nsdManager.resolveService(info, ResolveListener(this))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onServiceLost(info: NsdServiceInfo) {
        if (info.serviceName == serviceName) port.invoke(-1)
    }

    fun onServiceResolved(resolvedService: NsdServiceInfo) {
        val inf = NetworkInterface.getNetworkInterfaces()
        inf?:return
        if (running && inf.asSequence()
                .any { networkInterface ->
                    networkInterface.inetAddresses
                        .asSequence()
                        .any { resolvedService.host.hostAddress == it.hostAddress }
                }
            && isPortAvailable(resolvedService.port)
        ) {
            serviceName = resolvedService.serviceName
            port.invoke(resolvedService.port)
        }
    }

    private fun isPortAvailable(port: Int) = try {
        ServerSocket().use {
            it.bind(InetSocketAddress("127.0.0.1", port), 1)
            false
        }
    } catch (e: IOException) {
        true
    }

    override fun onDiscoveryStarted(serviceType: String) {
        Log.v(TAG, "onDiscoveryStarted: $serviceType")
        registered = true
    }

    override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
        Log.v(TAG, "onStartDiscoveryFailed: $serviceType, $errorCode")
    }

    override fun onDiscoveryStopped(serviceType: String) {
        Log.v(TAG, "onDiscoveryStopped: $serviceType")
        registered = false
    }

    override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
        Log.v(TAG, "onStopDiscoveryFailed: $serviceType, $errorCode")
    }

    companion object {
        const val TLS_CONNECT = "_adb-tls-connect._tcp"
        const val TLS_PAIRING = "_adb-tls-pairing._tcp"
        const val TAG = "AdbMdns"
    }
}
