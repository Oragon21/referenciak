import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Window 2.2
import QtQuick.Layouts 1.6
import QtQuick 2.0
import QtCharts 2.0



Item {
    id: item1

    Image {
        id: image2
        anchors.fill: parent
        source: "qrc:/img/homeBack.svg"
        opacity: 0.5
        z: -1
    }

    Rectangle {
        width: 1200; height: 150

        color: "#A782EC"
        z: 1
    }

// Bal felso resz
    Button {
        id: buttonHome
        x: 50
        y: 0
        text: qsTr("Home")
        implicitWidth: 125
        implicitHeight: 50
        font.pointSize: 15
        font.weight: Font.Bold
        icon.source: "qrc:/img/home.svg"
        z: 2
    }

    property alias buttonHome: buttonHome

    Button {
        id: buttonInfo
        x: 50
        y: 50
        text: qsTr("Info")
        implicitWidth: 125
        implicitHeight: 50
        font.pointSize: 15
        font.weight: Font.Bold
        icon.source: "qrc:/img/info.svg"
        z: 2
    }

    property alias buttonInfo: buttonInfo

    Button {
        id: menuCategory
        x: 50
        y: 100
        text: qsTr("Category")
        implicitWidth: 125
        implicitHeight: 50
        font.pointSize: 15
        z: 2
    }
    property alias menuCategory: menuCategory

 // Jobb felso resz
    Image {
        id: usericon
        width: 51
        height: 51
        anchors.top: namemenu.top
        anchors.left: namemenu.right
        source: "qrc:/img/user.svg"
         z: 2
    }
    MenuBar {
        id: namemenu
        x: 1000
        y: 30
        z: 2
        font.pointSize: 15
        Menu {
            id: name
            title: qsTr("User Name")
            MenuItem {
                id: settings
                text: qsTr("&Settings")
                icon.source: "qrc:/img/settings.svg"
            }
            MenuItem {
                id: logout
                text: qsTr("&Logout")
                icon.source: "qrc:/img/logout.svg"
            }
        }
    }
    property alias name: name.title
    property alias settings: settings
    property alias logout: logout

    Button {
        id: buttonCart
        x: 1000
        y: 80
        text: qsTr("Cart")
        icon.color: "transparent"
        z: 2
        icon.source: "qrc:/img/cart.svg"
        implicitWidth: 125
        implicitHeight: 50
        font.pointSize: 15
        font.weight: Font.Bold
    }
    property alias buttonCart: buttonCart
 // stb

    Label {
        id: label
        text: qsTr("Home Page")
        color: "white"
        font.bold: true
        anchors.top: parent.top
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment: Text.AlignVCenter
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.topMargin: 30
        font.pointSize: 40
        z: 2
        font.weight: Font.ExtraLight
    }

    ColumnLayout {
        id: clProducts
        width: 1000
        anchors.top: label.bottom
        anchors.topMargin: 150
        z: 2
        anchors.horizontalCenter: parent.horizontalCenter
        Label {
            id: product_label
            text: qsTr("Products")
            font.pointSize: 13
            font.bold: true
        }
        RowLayout {
            ProductList {
                id: prodList

                ListModel {
                    id: mainModel
                    ListElement {

                        color: "white"
                        subAlpha: []
                    }
                }
            }
        }

    }


    property alias clProducts: clProducts
    property alias prodList: prodList
    property alias mainModel: mainModel
}
