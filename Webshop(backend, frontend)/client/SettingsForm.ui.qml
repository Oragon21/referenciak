import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Window 2.2
import QtQuick.Layouts 1.6

Item {
    id: item1

    // Bal felso resz
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
    }
    property alias buttonHome: buttonHome

    // Jobb felso resz
    Image {
        id: usericon
        width: 51
        height: 51
        anchors.top: namemenu.top
        anchors.left: namemenu.right
        source: "qrc:/img/user.svg"
    }
    MenuBar {
        id: namemenu
        x: 1000
        y: 30
        font.pointSize: 15
        anchors.leftMargin: 50
        Menu {
           id: name
           title: qsTr("User Name")
           MenuItem {
               id: logout
               text: qsTr("&Logout")
               icon.source: "qrc:/img/logout.svg"
           }
        }
    }
    property alias name: name.title
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
    // stb

    Image {
        id: image2
        anchors.fill: parent
        source: "qrc:/img/registerform.jpg"
        opacity: 0.5
        z: -1
    }

    Label {
        id: label

        text: qsTr("Settings Form")
        anchors.top: parent.top
        horizontalAlignment: Text.AlignHCenter
        verticalAlignment: Text.AlignVCenter
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.topMargin: 30
        font.pointSize: 40
        font.weight: Font.ExtraLight
    }
    ColumnLayout {
        id: clDiv
        width: 450
        anchors.top: label.bottom
        anchors.topMargin: 50
        anchors.horizontalCenter: parent.horizontalCenter

        ColumnLayout {

            id: clDatas
            width: 150
            anchors.top: clDiv.top
            anchors.topMargin: 50

            TextField {
                id: id_user
                visible: false
            }
            Label {
                id: label_username
                text: qsTr("User Name: ")
                font.bold: true
            }
            TextField {
                id: username
                placeholderText: qsTr("username")
                font.bold: true
            }
            Label {
                id: label_password
                text: qsTr("Password: ")
                font.bold: true
            }
            TextField {
                echoMode: TextInput.Password
                id: password
                placeholderText: qsTr("password")
                font.bold: true
            }
            Label {
                id: label_email
                text: qsTr("Email: ")
                font.bold: true
            }
            TextField {
                id: email
                placeholderText: qsTr("email")
                font.bold: true
            }

            Label {
                id: label_firstname
                text: qsTr("First Name: ")
                font.bold: true
            }
            TextField {
                id: firstName
                placeholderText: qsTr("firstname")
                font.bold: true
            }

            Label {
                id: label_lastname
                text: qsTr("Last Name: ")
                font.bold: true
            }
            TextField {
                id: lastName
                placeholderText: qsTr("lastname")
                font.bold: true
            }
            Label {
                id: label_phone
                text: qsTr("Phone: ")
                font.bold: true
            }
            TextField {
                id: phoneNumber
                placeholderText: qsTr("phone")
                font.bold: true
            }
        }


        ColumnLayout {
            id: clHomeAddress
            width: 150
            anchors.left: clDatas.right
            anchors.top: clDiv.top
            anchors.topMargin: 50
            anchors.leftMargin: 50

            TextField {
                id: id_home
                visible: false
                font.bold: true
            }
            Label {
                id: label_homeaddress
                text: qsTr("Home address: ")
                font.bold: true
            }
            TextField {
                id: country_home
                placeholderText: qsTr("country")
                font.bold: true
            }
            TextField {
                id: city_home
                placeholderText: qsTr("city")
                font.bold: true
            }
            TextField {
                id: zipCode_home
                placeholderText: qsTr("zipCode")
                font.bold: true
            }
            TextField {
                id: streetName_home
                placeholderText: qsTr("streetName")
                font.bold: true
            }
            TextField {
                id: county_home
                placeholderText: qsTr("county")
            }
            TextField {
                id: streetNumber_home
                placeholderText: qsTr("streetNumber")
                font.bold: true
            }
            TextField {
                id: door_home
                placeholderText: qsTr("door")
            }
        }


        ColumnLayout {
            id: clBillingAddress
            width: 150
            anchors.left: clHomeAddress.right
            anchors.top: clDiv.top
            anchors.topMargin: 50
            anchors.leftMargin: 50

            TextField {
                id: id_billing
                visible: false
            }
            Label {
                id: label_billingaddress
                text: qsTr("Billing address: ")
            }
            TextField {
                id: country_billing
                placeholderText: qsTr("country")
            }
            TextField {
                id: city_billing
                placeholderText: qsTr("city")
            }
            TextField {
                id: zipCode_billing
                placeholderText: qsTr("zipCode")
            }
            TextField {
                id: streetName_billing
                placeholderText: qsTr("streetName")
            }
            TextField {
                id: county_billing
                placeholderText: qsTr("county")
            }
            TextField {
                id: streetNumber_billing
                placeholderText: qsTr("streetNumber")
            }
            TextField {
                id: door_billing
                placeholderText: qsTr("door")
            }
        }

        ColumnLayout {
            id: clSettingsButton
            anchors.top: clDatas.bottom
            anchors.horizontalCenter: parent.horizontalCenter
            RowLayout{
                Button {
                    id: buttonSettings
                    text: qsTr("UPDATE")
                    anchors.right: buttonDeleteSettings.left
                    anchors.rightMargin: 50
                    font.pointSize: 15
                }
                Button {
                    id: buttonDeleteSettings
                    text: qsTr("DELETE")
                    font.pointSize: 15
                }
            }
        }
    }
    property alias clDiv: clDiv
    property alias clSettingsButton: clSettingsButton
    // labels
    property alias label_username: label_username.text
    property alias label_password: label_password.text
    property alias label_email: label_email.text
    property alias label_firstname: label_firstname.text
    property alias label_lastname: label_lastname.text
    property alias label_phone: label_phone.text
    // textfields
    property alias id_user: id_user.text
    property alias username: username.text
    property alias password: password.text
    property alias email: email.text
    property alias firstName: firstName.text
    property alias lastName: lastName.text
    property alias phoneNumber: phoneNumber.text

    property alias label_homeaddress: label_homeaddress.text
    property alias id_home: id_home.text
    property alias country_home: country_home.text
    property alias city_home: city_home.text
    property alias zipCode_home: zipCode_home.text
    property alias streetName_home: streetName_home.text
    property alias county_home: county_home.text
    property alias streetNumber_home: streetNumber_home.text
    property alias door_home: door_home.text

    property alias label_billingaddress: label_billingaddress.text
    property alias id_billing: id_billing.text
    property alias country_billing: country_billing.text
    property alias city_billing: city_billing.text
    property alias zipCode_billing: zipCode_billing.text
    property alias streetName_billing: streetName_billing.text
    property alias county_billing: county_billing.text
    property alias streetNumber_billing: streetNumber_billing.text
    property alias door_billing: door_billing.text

    property alias buttonSettings: buttonSettings
    property alias buttonDeleteSettings: buttonDeleteSettings

}

