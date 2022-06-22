import QtQuick 2.4

RegisterForm {
    function showMessage(text, title)
    {
        messageBox.text = text;
        messageBox.title = title;
        messageBox.visible = true;
    }
    signal registerPressed(var username, var password, var email, var firstName, var lastName, var phoneNumber,
                           var country_home, var county_home, var city_home, var zipCode_home, var streetName_home, var streetNumber_home, var door_home,
                           var country_billing, var county_billing, var city_billing, var zipCode_billing, var streetName_billing, var streetNumber_billing, var door_billing);
    buttonRegister.onClicked: {
        if(password === password2)
            registerPressed(username,password,email,firstName,lastName,phoneNumber,
                        country_home,county_home,city_home,zipCode_home,streetName_home,streetNumber_home,door_home,
                        country_billing,county_billing,city_billing,zipCode_billing,streetName_billing,streetNumber_billing,door_billing)
        else
            showMessage("Passwords do not match!", "Alert box")
    }
    signal goToLoginSignal();
    buttonGoToLogin.onClicked: {
        goToLoginSignal()
    }
}
