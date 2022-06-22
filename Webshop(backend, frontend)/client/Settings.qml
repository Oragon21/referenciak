import QtQuick 2.4

SettingsForm {
    signal homeSignal();
    buttonHome.onClicked: {
        homeSignal()
    }
    signal cartSignal();
    buttonCart.onClicked: {
        cartSignal()
    }
    signal logoutPressed();
    logout.onClicked: {
        logoutPressed()
    }
    signal settingsPressed(var id_user, var username, var password, var email, var firstName, var lastName, var phoneNumber,
                           var id_home, var country_home, var county_home, var city_home, var zipCode_home, var streetName_home, var streetNumber_home, var door_home,
                           var id_billing, var country_billing, var county_billing, var city_billing, var zipCode_billing, var streetName_billing, var streetNumber_billing, var door_billing);
    buttonSettings.onClicked: {
        settingsPressed(id_user,username,password,email,firstName,lastName,phoneNumber,
                        id_home,country_home,county_home,city_home,zipCode_home,streetName_home,streetNumber_home,door_home,
                        id_billing,country_billing,county_billing,city_billing,zipCode_billing,streetName_billing,streetNumber_billing,door_billing)
    }
    signal deleteUserSignal();
    buttonDeleteSettings.onClicked: {
        deleteUserSignal()
    }

    onWindowChanged: {

    }
}
