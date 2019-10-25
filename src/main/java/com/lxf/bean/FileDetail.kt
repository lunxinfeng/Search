package com.lxf.bean

/**
 * @param path 文件路径
 * @param matchContent 匹配的文件内容
 * @param index 匹配内容为第几行
 */
data class FileDetail(val path: String, val matchContent: String, val index: Int){
    override fun toString(): String {
        return matchContent
    }
}