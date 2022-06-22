import QtQuick 2.4

CategoryForm {
    signal sortByCategorySignal(var category);
    signal homeSignal();
    buttonHome.onClicked: {
        homeSignal()
    }
       menu1.onClicked: {label = "ART"
       sortByCategorySignal("ART")
       }
       menu2.onClicked: { label = "ELECTRONICS"
       sortByCategorySignal("ELECTRONICS")
       }
       menu3.onClicked: { label = "FASHION"
       sortByCategorySignal("FASHION")
       }
       menu4.onClicked: { label = "GARDEN"
       sortByCategorySignal("GARDEN")
       }
       menu5.onClicked: { label = "CAR"
       sortByCategorySignal("CAR")
       }
       menu6.onClicked: { label = "INSTRUMENTS"
       sortByCategorySignal("INSTRUMENTS")
       }
       menu7.onClicked: { label = "SPORTS"
       sortByCategorySignal("SPORTS")
       }
       menu8.onClicked: { label = "GAMES"
       sortByCategorySignal("GAMES")
       }
       menu9.onClicked: { label = "BABY"
       sortByCategorySignal("BABY")
       }
       menu10.onClicked: { label = "HEALTH"
       sortByCategorySignal("HEALTH")
       }
       menu11.onClicked: { label = "BEAUTY"
       sortByCategorySignal("BEAUTY")
       }
       menu12.onClicked: { label = "BUSINESS"
       sortByCategorySignal("BUSINESS")
       }
       menu13.onClicked: { label = "OTHER"
       sortByCategorySignal("OTHER")
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

    signal toCartSignal(var index);



}
