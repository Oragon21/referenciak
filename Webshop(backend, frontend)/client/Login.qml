import QtQuick 2.4

LoginForm {
    function showMessage(text, title)
    {
        messageBox.text = text;
        messageBox.title = title;
        messageBox.visible = true;
    }

    signal loginPressed(var username, var password);
    signal fillSettingsPressed();
    buttonLogin.onClicked: {
        if(username === "" || password === "")
            showMessage("Fields are required!", "Alert box")
        else
            loginPressed(username,password)
    }
    signal goRegPressed();
    buttonRegister.onClicked: {
        goRegPressed()
    }

//    signal testSignal();
//    test.onClicked: {
//        testSignal()
//    }
}
