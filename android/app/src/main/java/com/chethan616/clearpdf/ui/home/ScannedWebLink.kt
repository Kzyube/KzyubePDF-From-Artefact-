package com.chethan616.clearpdf.ui.home

import java.net.IDN
import java.net.URI
import java.net.URISyntaxException

/** Return a web address only when the entire QR payload is one absolute HTTP(S) URL. */
internal fun scannedWebLink(payload: String): String? {
    return try {
        val uri = URI(payload)
        if (!uri.scheme.equals("https", true) && !uri.scheme.equals("http", true)) return null
        val authority = uri.rawAuthority ?: return null
        // URI accepts Unicode paths, but expects international domain names in ASCII form.
        val address = if (uri.host == null && authority.any { it.code > 127 }) {
            val host = authority.substringBefore(':')
            val ascii = IDN.toASCII(host, IDN.USE_STD3_ASCII_RULES)
            payload.replaceFirst("://$host", "://$ascii")
        } else payload
        val server = URI(address).parseServerAuthority()
        address.takeIf { server.host != null && server.port <= 65535 }
    } catch (_: URISyntaxException) {
        null
    } catch (_: IllegalArgumentException) {
        null
    }
}
