import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Window 2.2
import QtQuick.Layouts 1.6
import QtCharts 2.13

Item {
    id: item1
    Image {
        id: image2
        anchors.fill: parent
        source: "qrc:/img/INFOBACK.svg"
        opacity: 0.5
        z: -1
    }
    Rectangle {
        width: 1200; height: 150

        color: "#EECA92"
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

    Button {
        id: menuCategory
        x: 50
        y: 80
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
       icon.source: "qrc:/img/cart.svg"
       implicitWidth: 125
       implicitHeight: 50
       font.pointSize: 15
       font.weight: Font.Bold
   }
   property alias buttonCart: buttonCart

    Label {
        id: label
        text: qsTr("Info Page")
        anchors.top: parent.top
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment: Text.AlignVCenter
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.topMargin: 30
        font.pointSize: 40
        z:2
        color: "white"
        font.weight: Font.Bold
    }

    RowLayout {
        id: clInfo
        width: 1000
        anchors.top: label.bottom
        anchors.topMargin: 100
        anchors.horizontalCenter: parent.horizontalCenter
        Label {
            id: chart_label
            text: qsTr("Distribution of products by category")
            font.pointSize: 13
            font.bold: true
            anchors.top: parent.top
        }

        ChartView {
            id: chart
            x: 200
            width: 500
            height: 400
            theme: ChartView.ChartThemeBrownSand
            antialiasing: true
            anchors.left: parent.left
            anchors.top: chart_label.bottom

            PieSeries {
                id: pieSeries
            }
        }

        Button {
            id: addButton
            text: "Show informations"
            anchors.left: chart.right
            anchors.leftMargin: 200
        }

    }


    property alias clInfo: clInfo
    property alias chart_label: chart_label
    property alias chart: chart
    property alias pieSeries: pieSeries
    property alias addButton: addButton
}
