package com.amaze.filemanager.filesystem.files

import com.amaze.filemanager.BuildConfig
import java.io.BufferedInputStream
import java.io.BufferedOutputStream

object StreamCrypt {
    private const val placeholder = BuildConfig.APPLICATION_ID

    fun encrypt(
        password: String, inputStream: BufferedInputStream, outputStream: BufferedOutputStream
    ) {
        val buffer = ByteArray(GenericCopyUtil.DEFAULT_BUFFER_SIZE)
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
        val result = (password + placeholder).toByteArray()
        val byteArray = ByteArray(result.size).also {
            inputStream.read(it)
        }
        check(result.joinToString() == byteArray.joinToString()) {
            "password wrong"
        }
        val buffer = ByteArray(GenericCopyUtil.DEFAULT_BUFFER_SIZE)
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