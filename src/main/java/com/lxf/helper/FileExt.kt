package com.lxf.helper

import com.lxf.bean.FileDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.buffer
import okio.source
import java.io.File

/**
 * 查询指定目录下的所有md文件
 */
suspend fun getAllFilesEndWithMD(dir: File, fileList: MutableList<File>): List<File> {
    return withContext(Dispatchers.IO) {
        if (!dir.isDirectory) {
            fileList.add(dir)
            return@withContext fileList
        }
        val files = dir.listFiles { pathname -> pathname.canRead() && !pathname.isHidden && (pathname.name.endsWith(".md") || pathname.isDirectory) }
        if (files != null) {
            for (file in files) {
                if (file.isDirectory) {
                    getAllFilesEndWithMD(file, fileList)
                } else {
                    fileList.add(file)
                }
            }
        }
        return@withContext fileList
    }
}

/**
 * 获取指定文件中可以匹配搜索内容的相关内容
 */
suspend fun matchContent(file: File, content: String): MutableList<FileDetail> {
    return withContext(Dispatchers.IO) {
        val result = mutableListOf<FileDetail>()
        val buffer = file.source().buffer()
        var readString = buffer.readUtf8Line()
        var index = 1//第几行
        while (readString != null) {
            if (readString.toString().contains(content)) {
                result.add(FileDetail(file.path, readString.toString(), index))
            }
            readString = buffer.readUtf8Line()
            index++
        }
        buffer.close()
        return@withContext result
    }
}

/**
 * 读取文件全部内容
 */
suspend fun readAll(file: File): String {
    return withContext(Dispatchers.IO){
        val buffer = file.source().buffer()
        val content = buffer.readUtf8()
        buffer.close()
        return@withContext content
    }
}