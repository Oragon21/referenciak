import QtQuick 2.4
import QtQuick.Controls 2.15

ApplicationWindow {
    maximumWidth: 1200
    maximumHeight: 800
    minimumWidth: 1200
    minimumHeight: 800
    visible: true
    title: qsTr("Webshop")

    function showMessage(text, title)
    {
        messageBox.text = text;
        messageBox.title = title;
        messageBox.visible = true;
    }

    Timer {
        id: timer
    }

    function delay(delayTime, cb) {
        timer.interval = delayTime;
        timer.repeat = false;
        timer.triggered.connect(cb);
        timer.start();
    }


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

    SwipeView {
        id: swipeView
        anchors.fill: parent
        currentIndex: 0
        interactive: false

        Login {
            id:login
            objectName: "Login"
        }

        Register {
            id:register
            objectName: "Register"
        }

        Home {
            id:home
            objectName: "Home"
        }

        Category {
            id:category
            objectName: "Category"
        }

        Cart {
            id:cart
            objectName: "Cart"
        }

        Settings {
            id:settings
            objectName: "Settings"
        }

        Info {
            id:info
            objectName: "Info"
        }

        Connections {
            target: networkManager
            onHomeSuccess: {
                swipeView.currentIndex = 2
            }
            onCategorySuccess: {
                swipeView.currentIndex = 3
            }
            onCartSuccess: {
                swipeView.currentIndex = 4
            }
            onSortByCategorySucces:{
                category.mainModel.get(0).subAlpha = []
                for (var i = 0; i < productId.length; i++)  {
                    category.mainModel.get(0).subAlpha.append({
                                                                 productId: productId[i],
                                                                 productName: productName[i],
                                                                 productPrice: productPrice[i],
                                                                 productDescription: productDescription[i],
                                                                 productCategory: productCategory[i]
                                                             })
                }
            }

            onGetProductsFromCartSucces:{
                cart.mainModel.get(0).subAlpha = []
                for (var i = 0; i < productId.length; i++)  {
                    cart.mainModel.get(0).subAlpha.append({
                                                                 productId: productId[i],
                                                                 productName: productName[i],
                                                                 productPrice: productPrice[i],
                                                                 productDescription: productDescription[i],
                                                                 productCategory: productCategory[i]
                                                             })
                }
            }

            onLoginSuccess: {
                swipeView.currentIndex = 2
                home.name = username
                category.name = username
                settings.name = username
                info.name = username
                login.username = ""
                login.password = ""
            }
            onLoginUnSuccessfully: {
                showMessage("Username or password is incorrect!", "Alert box")
            }
            onGoRegSuccess: {
                swipeView.currentIndex = 1
            }
            onRegisterSuccess: {
                register.username = ""
                register.password = ""
                register.password2 = ""
                register.email = ""
                register.firstName = ""
                register.lastName = ""
                register.phoneNumber = ""

                register.country_home = ""
                register.county_home = ""
                register.city_home = ""
                register.zipCode_home = ""
                register.streetName_home = ""
                register.streetNumber_home = ""
                register.door_home = ""

                register.country_billing = ""
                register.county_billing = ""
                register.city_billing = ""
                register.zipCode_billing = ""
                register.streetName_billing = ""
                register.streetNumber_billing = ""
                register.door_billing = ""

                swipeView.currentIndex = 0
            }
            onLogoutSuccess: {
                swipeView.currentIndex = 0
                home.name = "User Name"
                category.name = "User Name"
                settings.name = "User Name"
                info.name = "User Name"
                home.mainModel.get(0).subAlpha = []
                category.mainModel.get(0).subAlpha = []
                cart.mainModel.get(0).subAlpha = []
            }
            onProductSuccess: {
                home.mainModel.get(0).subAlpha = []
                for (var i = 0; i < productId.length; i++)  {
                    home.mainModel.get(0).subAlpha.append({
                                                                 productId: productId[i],
                                                                 productName: productName[i],
                                                                 productPrice: productPrice[i],
                                                                 productDescription: productDescription[i],
                                                                 productCategory: productCategory[i]
                                                             })
                }
            }

            onSettingsSuccess: {
                swipeView.currentIndex = 5
            }
            onGoToLoginSuccess: {
                swipeView.currentIndex = 0
            }
            onFillSettingsSuccess: {
                settings.id_user = id_user
                settings.username =  username
                settings.email = email
                settings.firstName = firstName
                settings.lastName = lastName
                settings.phoneNumber = phoneNumber
                settings.id_home = id_home
                settings.country_home = country_home
                settings.county_home = county_home
                settings.city_home = city_home
                settings.zipCode_home = zipCode_home
                settings.streetName_home = streetName_home
                settings.streetNumber_home = streetNumber_home
                settings.door_home = door_home
                settings.id_billing = id_billing
                settings.country_billing = country_billing
                settings.county_billing = county_billing
                settings.city_billing = city_billing
                settings.zipCode_billing = zipCode_billing
                settings.streetName_billing = streetName_billing
                settings.streetNumber_billing =  streetNumber_billing
                settings.door_billing = door_billing
            }
            onUpdateSuccess: {
                showMessage("Update was successful!", "Alert box")
                swipeView.currentIndex = 2
            }
            onDeleteUser: {
                swipeView.currentIndex = 0
                home.name = "User Name"
                category.name = "User Name"
                settings.name = "User Name"
                home.mainModel.get(0).subAlpha = []
                category.mainModel.get(0).subAlpha = []
                cart.mainModel.get(0).subAlpha = []
                showMessage("User deleted successfully!", "Alert box")
            }
            onGoToInfoSuccess: {
                swipeView.currentIndex = 6
            }
            onBuySuccess: {
                showMessage("Purchase successful!", "Alert box")
                swipeView.currentIndex = 2
            }
            onChartSuccess: {
                info.pieSeries.clear()
                for(var i=0; i < categories.length; i++) {
                    info.pieSeries.append(categories[i], sum/values[i])
                }
            }
            onCartUpdateSuccess: {
                showMessage("New item added successfully!", "Alert box")
            }

            onDeleteFromCartSuccess: {
                showMessage("Product deleted successfully!", "Alert box")
            }

            onSendSumSuccess: {
                cart.sum = cartSum
            }
//            onTestResponse: {
//                swipeView.currentIndex = 1
//                delay(1000, function() {
//                    register.username = "test"
//                    register.password = "test"
//                    register.password2 = "test"
//                    register.email = "test@test.hu"
//                    register.firstName = "test1"
//                    register.lastName = "test2"
//                    register.phoneNumber = "0612345678"

//                    register.country_home = "home"
//                    register.county_home = ""
//                    register.city_home = "home"
//                    register.zipCode_home = "1117"
//                    register.streetName_home = "street"
//                    register.streetNumber_home = "137"
//                    register.door_home = ""

//                    register.country_billing = ""
//                    register.county_billing = ""
//                    register.city_billing = ""
//                    register.zipCode_billing = ""
//                    register.streetName_billing = ""
//                    register.streetNumber_billing = ""
//                    register.door_billing = ""

//                });

//                delay(1000, function() {
//                    register.registerPressed(register.username,register.password,register.email,register.firstName,register.lastName,register.phoneNumber,
//                                register.country_home,register.county_home,register.city_home,register.zipCode_home,register.streetName_home,register.streetNumber_home,register.door_home,
//                                register.country_billing,register.county_billing,register.city_billing,register.zipCode_billing,register.streetName_billing,register.streetNumber_billing,register.door_billing)
//                    swipeView.currentIndex = 0

//                });

//                delay(1000, function() {
//                    login.username = "test"
//                    login.password = "test"

//                });

//                delay(1000, function() {
//                    login.loginPressed(login.username,login.password)
//                });

//                delay(1000, function() {
//                    home.toCartSignal(1);
//                    home.toCartSignal(2);
//                    home.toCartSignal(3);
//                    swipeView.currentIndex = 4
//                });
//            }
        }
    }
}
