import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Window 2.2
import QtQuick.Layouts 1.6

Item {
    id: item1
    Image {
        id: image2
        anchors.fill: parent
        source: "qrc:/img/hatter.jpg"
        opacity: 0.5
        z: -1
    }
    Label {
        id: label
        text: qsTr("Login Form")
        anchors.top: parent.top
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment: Text.AlignVCenter
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.topMargin: 30
        font.pointSize: 40
        font.bold: true

        font.weight: Font.ExtraLight
    }

    ColumnLayout {
        id: columnLayout
        width: 250
        height: 100
        anchors.top: label.bottom
        anchors.topMargin: 50
        anchors.horizontalCenter: parent.horizontalCenter

        Label {
            id: label1
            font.pointSize: 30
            text: qsTr("User name")
            leftPadding: 0
        }

        TextField {
            id: userNameTextField
            maximumLength: 50

            Layout.fillWidth: true
            placeholderText: qsTr("User name")
        }

        Label {
            id: label2
            font.pointSize: 30
            text: qsTr("Password")
        }


        TextField {
            echoMode: TextInput.Password
            id: passwordTextField
            maximumLength: 50
            Layout.fillWidth: true
            placeholderText: qsTr("Password")
        }
        RowLayout{

            RoundButton {
                id: buttonLogin
                text: qsTr("Login")
                Layout.rightMargin: 5
                Layout.leftMargin: 5
                font.pointSize: 20


            }
            RoundButton {
                id: buttonRegister
                text: qsTr("Register")
                font.pointSize: 20
                Layout.rightMargin: 5
                Layout.leftMargin: 5

            }
        }
    }

    property alias buttonLogin: buttonLogin
    property alias buttonRegister: buttonRegister
    property alias username: userNameTextField.text
    property alias password: passwordTextField.text



    Window {
        id: messageBox
        modality: Qt.ApplicationModal
        title: ""
        visible: false
        property alias text: messageBoxLabel.text
        minimumHeight: 100
        minimumWidth: 300
        Label {
            anchors.margins: 10
            anchors.top: parent.top
            anchors.left: parent.left
            anchors.right: parent.right
            anchors.bottom: messageBoxButton.top
            horizontalAlignment: Text.AlignHCenter
            wrapMode: Text.WordWrap
            id: messageBoxLabel
            color: "black"
            text: qsTr("Message")
        }

        Button {
            anchors.margins: 10
            id: messageBoxButton
            anchors.bottom: parent.bottom
            anchors.horizontalCenter: parent.horizontalCenter
            text: qsTr("Ok")
            onClicked: messageBox.visible = false
        }
    }
    property alias messageBox: messageBox
    property alias messageBoxLabel: messageBoxLabel.text
    property alias messageBoxButton: messageBoxButton

//    Button {
//        id: test
//        text: qsTr("TEST")
//        font.pointSize: 40
//    }

//    property alias test: test

}
