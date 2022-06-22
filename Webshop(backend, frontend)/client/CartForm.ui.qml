import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Window 2.2
import QtQuick.Layouts 1.6

Item {
    id: item1

    Image {
        id: image2
        anchors.fill: parent
        source: "qrc:/img/cartBack.svg"
        opacity: 0.5
        z: -1
    }

    Rectangle {
        width: 1200; height: 150

        color: "#6BBAF9"
        z: 1
    }

    Button {
        id: buttonHome
        x: 50
        y: 30
        text: qsTr("Home")
        font.pointSize: 15
        implicitWidth: 125
        implicitHeight: 50
        font.weight: Font.Bold
        icon.source: "qrc:/img/home.svg"
        padding: 10
        z: 2
    }
    property alias buttonHome: buttonHome

    Button {
        id: menuCategory
        x: 50
        y: 80
        implicitWidth: 125
        implicitHeight: 50
        text: qsTr("Category")
        font.pointSize: 15
        z: 2
    }
    property alias menuCategory: menuCategory

    Label {
        id: label
        text: qsTr("Cart Page")
        anchors.top: parent.top
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment: Text.AlignVCenter
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.topMargin: 30
        font.pointSize: 40
        color: "white"
        font.weight: Font.Bold
        z: 2
    }

    ColumnLayout {
        id: clProducts
        width: 1000
        anchors.top: label.bottom
        anchors.topMargin: 150
        anchors.horizontalCenter: parent.horizontalCenter
        Label {
            id: product_label
            text: qsTr("Products")
            font.pointSize: 13
            font.bold: true
        }
        RowLayout {
            z: 2
            Button {
                id: buttonProduct
                text: qsTr("Delete cart")
            }
            Button {
                id: buttonBuy
                text: qsTr("Buy")
            }
            RowLayout {
                Label {
                    id: sum_text
                    text: qsTr("Amount payable: ")
                }
                Label {
                    id: sum
                    text: qsTr("0")
                }
            }
        }
        RowLayout {
            CartList {
                id: prodList

                ListModel {
                    id: mainModel
                    ListElement {
                        subAlpha: []
                    }
                }
            }
        }
    }
    property alias clProducts: clProducts
    property alias buttonProduct: buttonProduct
    property alias buttonBuy: buttonBuy
    property alias prodList: prodList
    property alias mainModel: mainModel
    property alias sum: sum.text

}
