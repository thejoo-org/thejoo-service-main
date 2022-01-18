package com.thejoo.thejooservicemain.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.common.BitMatrix
import com.thejoo.thejooservicemain.entity.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class QrCodeProviderService(
    private val multiFormatWriter: MultiFormatWriter,
) {
    val log: Logger = LoggerFactory.getLogger(javaClass)

    fun generateQrCodeForReadableToken(token: String): ByteArray? {
        log.info("Generating QR Code for $token....")
        val matrix: BitMatrix = multiFormatWriter.encode(token, BarcodeFormat.QR_CODE, DEFAULT_WIDTH, DEFAULT_HEIGHT)
        val byteArrayOutputStream = ByteArrayOutputStream()
        MatrixToImageWriter.writeToStream(matrix, MediaType.IMAGE_PNG.subtype, byteArrayOutputStream, MatrixToImageConfig())
        return byteArrayOutputStream.toByteArray()
    }

    companion object {
        const val DEFAULT_WIDTH = 300
        const val DEFAULT_HEIGHT = 300
    }
}