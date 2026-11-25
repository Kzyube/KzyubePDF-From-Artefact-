package com.chethan616.clearpdf.ui.home

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ScannedWebLinkTest {
    @Test fun acceptsWholeWebAddresses() {
        for (url in listOf("https://example.org/file.pdf?q=a%20b#page=2", "HTTP://localhost:8080/", "https://[::1]/")) {
            assertEquals(url, scannedWebLink(url))
        }
        assertEquals("https://xn--bcher-kva.example:8080/č", scannedWebLink("https://bücher.example:8080/č"))
    }

    @Test fun leavesOtherPayloadsAsText() {
        for (value in listOf("Meeting: https://example.org", "WIFI:T:WPA;S:Office;;", "intent://scan", "javascript:alert(1)",
            "mailto:me@example.org", "https:///path", "example.org", "https://example.org:99999", "https://example.org\nextra")) {
            assertNull(value, scannedWebLink(value))
        }
    }
}
