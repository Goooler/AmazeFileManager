package com.amaze.filemanager.filesystem.files

import com.amaze.filemanager.BuildConfig
import java.io.BufferedInputStream
import java.io.BufferedOutputStream

object StreamCrypt {
    private const val placeholder = BuildConfig.APPLICATION_ID
    private val buffer = ByteArray(8192)

    fun encrypt(
        password: String, inputStream: BufferedInputStream, outputStream: BufferedOutputStream
    ) {
        var count: Int
        try {
            outputStream.write((password + placeholder).toByteArray())
            while (inputStream.read(buffer).also { count = it } != -1) {
                outputStream.write(buffer, 0, count)
            }
        } finally {
            inputStream.close()
            outputStream.close()
        }
    }

    fun decrypt(
        password: String, inputStream: BufferedInputStream, outputStream: BufferedOutputStream
    ) {
        val passwd = (password + placeholder).toByteArray()
        val header = ByteArray(passwd.size).also(inputStream::read).joinToString()
        check(passwd.joinToString() == header) {
            "password wrong"
        }
        var count: Int
        try {
            while (inputStream.read(buffer).also { count = it } != -1) {
                outputStream.write(buffer, 0, count)
            }
        } finally {
            inputStream.close()
            outputStream.close()
        }
    }
}