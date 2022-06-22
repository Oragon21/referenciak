import QtQuick 2.4

CartForm {
    signal homeSignal();
    buttonHome.onClicked: {
        homeSignal()
    }
    signal categorySignal();
    menuCategory.onClicked: {
        categorySignal()
    }
    // add to Cart a Product
    signal deleteFromCartSignal(var index);
    signal deleteCartSignal();
    signal getFromCartSignal();
    buttonProduct.onClicked: {
        deleteCartSignal()
    }
    signal buySignal();
    buttonBuy.onClicked: {
        buySignal()
    }

    Timer {
        id: serviceListItemTimer
        interval: 500
        running: true
        repeat: true
        onTriggered: {
            getFromCartSignal()
        }
    }
}
