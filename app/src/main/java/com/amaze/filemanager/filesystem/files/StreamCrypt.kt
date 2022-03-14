package com.amaze.filemanager.filesystem.files

import java.io.BufferedInputStream
import java.io.BufferedOutputStream

object StreamCrypt {
    private var passwd: String? = null

    fun encrypt(
        password: String, inputStream: BufferedInputStream, outputStream: BufferedOutputStream
    ) {
        passwd = password
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

    fun decrypt(
        password: String, inputStream: BufferedInputStream, outputStream: BufferedOutputStream
    ) {
        check(password == passwd) {
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