import QtQuick 2.4
import QtQuick.Controls 2.3
import QtQuick 2.0
import QtCharts 2.15

HomeForm {

    signal homeSignal();
    buttonHome.onClicked: {
        homeSignal()
    }
    signal goToInfoSignal();
    buttonInfo.onClicked: {
        goToInfoSignal()
    }
    signal categorySignal();
    menuCategory.onClicked: {
        categorySignal()
    }
    signal cartSignal();
    buttonCart.onClicked: {
        cartSignal()
    }
    signal settingsSignal();
    settings.onClicked: {
        settingsSignal()
    }
    signal logoutPressed();
    logout.onClicked: {
        logoutPressed()
    }

    signal productSignal();
//    Component.onCompleted: {
//        productSignal()
//    }
    Timer {
        id: serviceListItemTimer
        interval: 500
        running: true
        repeat: true
        onTriggered: {
            productSignal()
        }
    }
    signal toCartSignal(var index);

}

