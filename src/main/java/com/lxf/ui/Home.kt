package com.lxf.ui

import com.lxf.bean.FileDetail
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.Pane
import javafx.scene.layout.Priority

class Home : Pane() {
    private val controller = HomeController()

    init {
        //UI
        val btnSelectPath = Button("选择搜索路径").apply {
            setOnAction { controller.selectPath(scene.window) }
        }
        val labelSelectPath = Label()
        val hBoxSelectPath = HBox().apply {
            spacing = 20.0
            alignment = Pos.CENTER_LEFT
            children.addAll(btnSelectPath, labelSelectPath)
        }

        val textFieldSelectContent = TextField().apply {

        }
        val btnSelectContent = Button("搜索").apply {
            setOnAction { controller.selectContent(textFieldSelectContent.text) }
        }
        val hBoxSelectContent = HBox().apply {
            spacing = 20.0
            children.addAll(textFieldSelectContent, btnSelectContent)
            HBox.setHgrow(textFieldSelectContent, Priority.ALWAYS)
        }

        val matchContents = ListView<FileDetail>().apply {
            prefHeight = 200.0
            selectionModel.selectedItemProperty().addListener { _, _, newValue -> controller.selectMatch(newValue) }
        }

        val textDetail = TextArea()

        prefWidth = 1000.0
        prefHeight = 700.0
        children.addAll(hBoxSelectPath, hBoxSelectContent, matchContents, textDetail)

        //位置
        hBoxSelectPath.apply {
            layoutXProperty().bind(this@Home.widthProperty().multiply(0.2))
            layoutYProperty().bind(this@Home.heightProperty().multiply(0.03))
        }

        hBoxSelectContent.apply {
            layoutXProperty().bind(this@Home.widthProperty().multiply(0.2))
            layoutYProperty().bind(hBoxSelectPath.layoutYProperty().add(50))
            prefWidthProperty().bind(this@Home.widthProperty().multiply(0.6))
        }

        matchContents.apply {
            layoutXProperty().bind(this@Home.widthProperty().multiply(0.2))
            layoutYProperty().bind(hBoxSelectContent.layoutYProperty().add(50))
            prefWidthProperty().bind(this@Home.widthProperty().multiply(0.6))
        }

        textDetail.apply {
            layoutXProperty().bind(this@Home.widthProperty().multiply(0.2))
            layoutYProperty().bind(matchContents.layoutYProperty().add(matchContents.prefHeight).add(20))
            prefWidthProperty().bind(this@Home.widthProperty().multiply(0.6))
            prefHeightProperty().bind(this@Home.heightProperty().multiply(0.45))
        }

        //展示
        uiListener(labelSelectPath, matchContents, textDetail)
    }

    private fun uiListener(labelSelectPath: Label, matchContents: ListView<FileDetail>, textDetail: TextArea) {
        controller.selectPath.addListener { _, _, newValue -> labelSelectPath.text = newValue }
        controller.selectPath.set("/home/lxf/data/notes")

        matchContents.items = controller.matchContents

        controller.fileContent.addListener { _, _, newValue -> textDetail.text = newValue }
        controller.contentIndex.addListener { _, _, newValue -> textDetail.scrollTop = 15 * newValue.toDouble() }
    }
}
