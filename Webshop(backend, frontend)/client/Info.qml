import QtQuick 2.4

InfoForm {
    signal sortByCategorySignal(var category);
    signal homeSignal();
    buttonHome.onClicked: {
        homeSignal()
    }
    signal categorySignal();
    menuCategory.onClicked: {
        categorySignal()
    }
    signal settingsSignal();
    settings.onClicked: {
        settingsSignal()
    }
    signal logoutPressed();
    logout.onClicked: {
        logoutPressed()
    }
    signal cartSignal();
    buttonCart.onClicked: {
        cartSignal()
    }

    signal getChartSignal();
    addButton.onClicked: {
        getChartSignal()
    }

}
