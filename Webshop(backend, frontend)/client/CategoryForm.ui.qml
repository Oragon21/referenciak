import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Window 2.2
import QtQuick.Layouts 1.6

Item {
    id: item1
    Image {
        id: image2
        anchors.fill: parent
        source: "qrc:/img/categoryBack.svg"
        opacity: 0.5
        z: -1
    }
    Rectangle {
        width: 1200; height: 150

        color: "#FFFF00"
        z: 1
    }

    Button {
        id: buttonHome
        x: 50
        y: 30
        text: qsTr("Home")
        implicitWidth: 125
        implicitHeight: 50
        font.pointSize: 15
        font.weight: Font.Bold
        icon.source: "qrc:/img/home.svg"
        z: 2
    }
    property alias buttonHome: buttonHome

    MenuBar {
        id: menubar
        x: 50
        y: 80
        z: 2
        font.pointSize: 15
        Menu {
            title: qsTr("&Category")
            MenuItem {
                id: menu1
                text: qsTr("&Art")
            }
            MenuItem {
                id: menu2
                text: qsTr("&Electronics")
            }
            MenuItem {
                id: menu3
                text: qsTr("&Fashion")
            }
            MenuItem {
                id: menu4
                text: qsTr("&Garden")
            }
            MenuItem {
                id: menu5
                text: qsTr("&Car")
            }
            MenuItem {
                id: menu6
                text: qsTr("&Instruments")
            }
            MenuItem {
                id: menu7
                text: qsTr("&Sports")
            }
            MenuItem {
                id: menu8
                text: qsTr("&Games")
            }
            MenuItem {
                id: menu9
                text: qsTr("&Baby")
            }
            MenuItem {
                id: menu10
                text: qsTr("&Health")
            }
            MenuItem {
                id: menu11
                text: qsTr("&Beauty")
            }
            MenuItem {
                id: menu12
                text: qsTr("&Business")

            }
            MenuItem {
                id: menu13
                text: qsTr("&Other")
            }
        }
    }
    property alias menu1: menu1
    property alias menu2: menu2
    property alias menu3: menu3
    property alias menu4: menu4
    property alias menu5: menu5
    property alias menu6: menu6
    property alias menu7: menu7
    property alias menu8: menu8
    property alias menu9: menu9
    property alias menu10: menu10
    property alias menu11: menu11
    property alias menu12: menu12
    property alias menu13: menu13

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
       icon.source: "qrc:/img/cart.svg"
       implicitWidth: 125
       implicitHeight: 50
       font.pointSize: 15
       z: 2
       font.weight: Font.Bold
   }
   property alias buttonCart: buttonCart
// stb

    Label {
        id: label
        text: qsTr("Category Page")
        anchors.top: parent.top
        color: "white"
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment: Text.AlignVCenter
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.topMargin: 30
        z: 2
        font.pointSize: 40
        font.weight: Font.Bold
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
            ProductList {
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
    property alias prodList: prodList
    property alias mainModel: mainModel
    property alias label: label.text
}
