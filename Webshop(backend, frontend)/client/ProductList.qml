import QtQuick 2.0
import QtQuick.Controls 2.15


ListView {
    width: 900
    height: 600

    model: mainModel
    delegate: Column {
        width: 300
        Text { text: name }
        Grid {
            columns: 4
            spacing: 6

            Repeater {
                id: repeater
                model: subAlpha

                Rectangle {
                    width: 250; height: 250
                    color: "white"
                    radius: 10

                RoundButton{
                        text: "Add to cart"
                        icon.source: "qrc:/img/addToCart.svg"
                        onClicked:{
                            toCartSignal(productId)
                        }
                    }


                Text {

                    textFormat: Text.RichText
                    font.pointSize: 13
                    fontSizeMode: Text.Fit
                    width: 250
                    wrapMode: Text.WordWrap
                    horizontalAlignment: Text.AlignHCenter
                    verticalAlignment: Text.AlignVCenter
                    color: "black"

                    text:
                        "<div><table><br></br><caption><h4>" + productName + "</h4>"+
                          "</caption>"+
                             "<tr><th>Price</th> <td> " + productPrice + " </td> </tr> <tr><th>Description</th> <td>" + productDescription + "</td> </tr>"+
                             "<tr><th>Category</th> <td> " + productCategory + " </td> </tr> </div>"



                    }


                }
            }






           /* Repeater {
                model: subAlpha
                RoundButton{
                    text: "Add to cart"
                    icon.source: "qrc:/img/addToCart.svg"
                    onClicked:{
                        clickIndexSignal(index)
                        clickIndexFunction(index)
                    }
                }
            }*/
        }
    }
}
//    Component.onCompleted: {
//        mainModel.get(0).subAlpha.append({
//                                             productId: "12",
//                                             productName: "Alma",
//                                             productPrice: "45",
//                                             productDescription: "hosszuleirasaitttalalhato",
//                                             productCategory: "GARDEN"
//                                         })
//    }

