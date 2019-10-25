package com.lxf.ui

import com.lxf.bean.FileDetail
import com.lxf.helper.getAllFilesEndWithMD
import com.lxf.helper.matchContent
import com.lxf.helper.readAll
import javafx.beans.property.ObjectProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.stage.DirectoryChooser
import javafx.stage.Window
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.javafx.JavaFx
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

internal class HomeController {

    /**
     * 搜索路径
     */
    val selectPath = SimpleStringProperty()
    /**
     * 路径下所有文件中的所有匹配内容
     */
    val matchContents: ObservableList<FileDetail> = FXCollections.observableArrayList<FileDetail>()
    /**
     * 当前选中的匹配内容对应的文件内容
     */
    val fileContent = SimpleStringProperty()
    /**
     * 当前选中的匹配内容对应的文件内容第几行
     */
    val contentIndex = SimpleIntegerProperty()

    fun selectPath(owner: Window) {
        val directoryChooser = DirectoryChooser()
        directoryChooser.title = "请选择要搜索的路径"
        val path = directoryChooser.showDialog(owner).absolutePath
        selectPath.set(path)
    }

    fun selectContent(content: String) {
        matchContents.clear()
        val file = File(selectPath.get())
        GlobalScope.launch(Dispatchers.JavaFx) {
            val fileList = getAllFilesEndWithMD(file, ArrayList())
            fileList.sortedBy { it.length() }
                    .filter { it.length() > 0 }
                    .forEach {
                        println("====================${it.name}===================")
                        val contents = matchContent(it, content)
                        matchContents.addAll(contents)
                    }
        }
    }

    fun selectMatch(fileDetail: FileDetail) {
        GlobalScope.launch {
            val content = readAll(File(fileDetail.path))
            fileContent.set(content)
            contentIndex.set(fileDetail.index)
        }
    }
}
