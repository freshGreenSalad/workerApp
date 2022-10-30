package com.tamaki.workerapp.data

import android.content.Context
import com.tamaki.workerapp.R
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

object SslSettings {
    fun getKeyStore(appContext: Context): KeyStore {
        val keyStoreFile = appContext.resources.openRawResource(R.raw.cert)
        val keyStorePassword = "password".toCharArray()
        val keyStore: KeyStore = KeyStore.getInstance("BKS")
        keyStore.load(keyStoreFile, keyStorePassword)
        return keyStore
    }

    fun getTrustManagerFactory(appContext: Context): TrustManagerFactory? {
        val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
        trustManagerFactory.init(getKeyStore(appContext))
        return trustManagerFactory
    }

    fun getSslContext(appContext:Context): SSLContext? {
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, getTrustManagerFactory(appContext)?.trustManagers, null)
        return sslContext
    }
}
