package com.amaze.filemanager.filesystem.files

import java.io.BufferedInputStream
import java.io.BufferedOutputStream

object StreamCrypt {

    fun encrypt(
        password: String?, inputStream: BufferedInputStream, outputStream: BufferedOutputStream
    ) {
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