#include "networkmanager.h"
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QDebug>
#include <QJsonDocument>
#include <QJsonObject>
#include <QJsonArray>
#include <QGuiApplication>
#include <QQmlApplicationEngine>

QByteArray  BearerToken;
QString uname;
int productIdGlobal;

NetworkManager::NetworkManager(QObject *parent) : QObject(parent)
{
    // ha lefut a keres elkapja a jelet, majd a valaszt kuldi tovabb
    connect(&this-> manager, SIGNAL(finished(QNetworkReply*)), this, SLOT(requestFinished(QNetworkReply*)));
}

void NetworkManager::login(QVariant username, QVariant password)
{
    BearerToken = "";
    uname = username.toString();
    QString urlString = QString("http://localhost:8080/login");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");

    QJsonObject obj;
    obj["username"] = username.toString();
    obj["password"] = password.toString();

    QJsonDocument doc(obj);
    QByteArray data = doc.toJson();

    manager.post(request, data);
}

void NetworkManager::homeFunction()
{
    emit homeSuccess();
}

void NetworkManager::categoryFunction()
{
    emit categorySuccess();
}

void NetworkManager::cartFunction()
{
    emit cartSuccess();
}

void NetworkManager::goRegFunction()
{
    emit goRegSuccess();
}

void NetworkManager::logoutFunction()
{
    BearerToken = "";
    emit logoutSuccess();
}

void NetworkManager::goToLoginFunction()
{
    emit goToLoginSuccess();
}

void NetworkManager::productFunction()
{
    QString urlString = QString("http://localhost:8080/product");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);
    manager.get(request);
}

void NetworkManager::registerFunction(QVariant username, QVariant password, QVariant email, QVariant firstName, QVariant lastName, QVariant phoneNumber,
                                      QVariant country_home, QVariant county_home, QVariant city_home, QVariant zipCode_home, QVariant streetName_home, QVariant streetNumber_home, QVariant door_home,
                                      QVariant country_billing, QVariant county_billing, QVariant city_billing, QVariant zipCode_billing, QVariant streetName_billing, QVariant streetNumber_billing, QVariant door_billing)
{
    QString urlString = QString("http://localhost:8080/register");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader(QNetworkRequest::ContentTypeHeader, "application/json");

    QJsonObject obj;
    if(username.toString() != "") {
        obj["username"] = username.toString();
    }
    if(password.toString() != "") {
        obj["password"] = password.toString();
    }
    if(email.toString() != "") {
        obj["email"] = email.toString();
    }
    if(firstName.toString() != "") {
        obj["firstName"] = firstName.toString();
    }
    if(lastName.toString() != "") {
        obj["lastName"] = lastName.toString();
    }
    if(phoneNumber.toString() != "") {
        obj["phoneNumber"] = phoneNumber.toString();
    }

    QJsonObject homeAddressObj;
    if(country_home.toString() != "") {
        homeAddressObj["country"] = country_home.toString();
    }
    if(county_home.toString() != "") {
        homeAddressObj["county"] = county_home.toString();
    }
    if(city_home.toString() != "") {
        homeAddressObj["city"] = city_home.toString();
    }
    if(zipCode_home.toString() != "") {
        homeAddressObj["zipCode"] = zipCode_home.toString();
    }
    if(streetName_home.toString() != "") {
        homeAddressObj["streetName"] = streetName_home.toString();
    }
    if(streetNumber_home.toInt() > 0) {
        homeAddressObj["streetNumber"] = streetNumber_home.toInt();
    }
    if(door_home.toString() != "") {
        homeAddressObj["door"] = door_home.toString();
    }
    if(!homeAddressObj.isEmpty()) {
        obj["homeAddress"] = homeAddressObj;
    }

    QJsonObject billingAddressObj;
    if(country_billing.toString() != "") {
        billingAddressObj["country"] = country_billing.toString();
    }
    if(county_billing.toString() != "") {
        billingAddressObj["county"] = county_billing.toString();
    }
    if(city_billing.toString() != "") {
        billingAddressObj["city"] = city_billing.toString();
    }
    if(zipCode_billing.toString() != "") {
        billingAddressObj["zipCode"] = zipCode_billing.toString();
    }
    if(streetName_billing.toString() != "") {
        billingAddressObj["streetName"] = streetName_billing.toString();
    }
    if(streetNumber_billing.toInt() > 0) {
        billingAddressObj["streetNumber"] = streetNumber_billing.toInt();
    }
    if(door_billing.toString() != "") {
        billingAddressObj["door"] = door_billing.toString();
    }
    if(!billingAddressObj.isEmpty()) {
        obj["billingAddress"] = billingAddressObj;
    }

    QJsonDocument doc(obj);
    QByteArray data = doc.toJson();
    manager.post(request, data);
}

void NetworkManager::settingsFunction() {
    emit settingsSuccess();
}

void NetworkManager::sortByCategoryFunction(QVariant category){

    QString urlString = QString("http://localhost:8080/product/category/");
    urlString.append(category.toString());
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);

    manager.get(request);
}


void NetworkManager::settingsUpdateFunction(QVariant id_user, QVariant username, QVariant password, QVariant email, QVariant firstName, QVariant lastName, QVariant phoneNumber,
                                            QVariant id_home, QVariant country_home, QVariant county_home, QVariant city_home, QVariant zipCode_home, QVariant streetName_home, QVariant streetNumber_home, QVariant door_home,
                                            QVariant id_billing, QVariant country_billing, QVariant county_billing, QVariant city_billing, QVariant zipCode_billing, QVariant streetName_billing, QVariant streetNumber_billing, QVariant door_billing)
{
    QString urlString = QString("http://localhost:8080/user");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);

    QJsonObject obj;
    if(id_user.toInt() > 0) {
        obj["id"] = id_user.toInt();
    }
    if(username.toString() != "") {
        obj["username"] = username.toString();
    }
    if(password.toString() != "") {
        obj["password"] = password.toString();
    }
    if(email.toString() != "") {
        obj["email"] = email.toString();
    }
    if(firstName.toString() != "") {
        obj["firstName"] = firstName.toString();
    }
    if(lastName.toString() != "") {
        obj["lastName"] = lastName.toString();
    }
    if(phoneNumber.toString() != "") {
        obj["phoneNumber"] = phoneNumber.toString();
    }

    QJsonObject homeAddressObj;
    if(id_home.toInt() > 0) {
        homeAddressObj["id"] = id_home.toInt();
    }
    if(country_home.toString() != "") {
        homeAddressObj["country"] = country_home.toString();
    }
    if(county_home.toString() != "") {
        homeAddressObj["county"] = county_home.toString();
    }
    if(city_home.toString() != "") {
        homeAddressObj["city"] = city_home.toString();
    }
    if(zipCode_home.toString() != "") {
        homeAddressObj["zipCode"] = zipCode_home.toString();
    }
    if(streetName_home.toString() != "") {
        homeAddressObj["streetName"] = streetName_home.toString();
    }
    if(streetNumber_home.toInt() > 0) {
        homeAddressObj["streetNumber"] = streetNumber_home.toInt();
    }
    if(door_home.toString() != "") {
        homeAddressObj["door"] = door_home.toString();
    }
    if(!homeAddressObj.isEmpty()) {
        obj["homeAddress"] = homeAddressObj;
    }

    QJsonObject billingAddressObj;
    if(id_billing.toInt() > 0) {
        billingAddressObj["id"] = id_billing.toInt();
    }
    if(country_billing.toString() != "") {
        billingAddressObj["country"] = country_billing.toString();
    }
    if(county_billing.toString() != "") {
        billingAddressObj["county"] = county_billing.toString();
    }
    if(city_billing.toString() != "") {
        billingAddressObj["city"] = city_billing.toString();
    }
    if(zipCode_billing.toString() != "") {
        billingAddressObj["zipCode"] = zipCode_billing.toString();
    }
    if(streetName_billing.toString() != "") {
        billingAddressObj["streetName"] = streetName_billing.toString();
    }
    if(streetNumber_billing.toInt() > 0) {
        billingAddressObj["streetNumber"] = streetNumber_billing.toInt();
    }
    if(door_billing.toString() != "") {
        billingAddressObj["door"] = door_billing.toString();
    }
    if(!billingAddressObj.isEmpty()) {
        obj["billingAddress"] = billingAddressObj;
    }

    QJsonDocument doc(obj);
    QByteArray data = doc.toJson();
    manager.put(request, data);
}


void NetworkManager::addToCartFunction(QVariant productId) {
    // ez csak ellenorzes vegett kell, mert maskor is hasznaljuk a cart ot
    productIdGlobal = productId.toInt();
    QString urlString = QString("http://localhost:8080/cart");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);
    manager.get(request);
}

void NetworkManager::deleteFromCartFunction(QVariant productId) {
    QString urlString = QString("http://localhost:8080/cart/");
    urlString.append(productId.toString());
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);
    if(urlString.at(urlString.length()-1) != '/')
        manager.deleteResource(request);
}

void NetworkManager::getFromCartFunction() {
    productIdGlobal = 0;
    QString urlString = QString("http://localhost:8080/cart");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);
    manager.get(request);
}

void NetworkManager::deleteCartFunction() {
    QString urlString = QString("http://localhost:8080/cart");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);
    manager.deleteResource(request);
}

void NetworkManager::deleteUserFunction() {
    QString urlString = QString("http://localhost:8080/user");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);
    manager.deleteResource(request);
}

void NetworkManager::goToInfoFunction() {
    emit goToInfoSuccess();
}

void NetworkManager::buyFunction() {
    QString urlString = QString("http://localhost:8080/cart/buy");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);
    QByteArray data;
    manager.post(request, data);
}

bool chartFlag = false;

void NetworkManager::getChartFunction() {
    QString urlString = QString("http://localhost:8080/product");
    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
    request.setRawHeader("Authorization", BearerToken);
    chartFlag = true;
    manager.get(request);
}

void NetworkManager::testFunction() {
    emit testResponse();
}

void NetworkManager::requestFinished(QNetworkReply *reply)
{
    // 1 - Head
    // 2 - Get
    // 3 - Put
    // 4 - Post
    // 5 - Delete
    // 6 - Custom
    int operation = reply->operation();

    // GET
    if (operation == 2) {
        QString url = reply->url().toString();
        if(url == "http://localhost:8080/user") {
            // SettingForm
            QByteArray data = reply -> readAll();
            QJsonDocument jsonDoc = QJsonDocument::fromJson(data);
            QJsonArray array = jsonDoc.array();
            if(array.size() == 0) {
                QJsonObject obj = jsonDoc.object();
                QString id_user = QString::number(obj.value("id").toInteger());
                QString username = obj.value("username").toString();
                // passwordot nincs amiert kodolva vissza adni
                QString email = obj.value("email").toString();
                QString firstName = obj.value("firstName").toString();
                QString lastName = obj.value("lastName").toString();;
                QString phoneNumber = obj.value("phoneNumber").toString();
                QJsonObject homeAddressObj = obj.value("homeAddress").toObject();
                QString id_home = QString::number(homeAddressObj.value("id").toInteger());
                QString country_home = homeAddressObj["country"].toString();
                QString county_home = homeAddressObj["county"].toString();
                QString city_home = homeAddressObj["city"].toString();
                QString zipCode_home = homeAddressObj["zipCode"].toString();
                QString streetName_home = homeAddressObj["streetName"].toString();
                QString streetNumber_home;
                if(homeAddressObj.value("streetNumber").toInteger()){
                    streetNumber_home= QString::number(homeAddressObj.value("streetNumber").toInteger());
                }
                QString door_home = homeAddressObj["door"].toString();
                QJsonObject billingAddressObj = obj.value("billingAddress").toObject();
                QString id_billing = QString::number(billingAddressObj.value("id").toInteger());
                QString country_billing = billingAddressObj["country"].toString();
                QString county_billing = billingAddressObj["county"].toString();
                QString city_billing = billingAddressObj["city"].toString();
                QString zipCode_billing = billingAddressObj["zipCode"].toString();
                QString streetName_billing = billingAddressObj["streetName"].toString();
                QString streetNumber_billing;
                if(billingAddressObj.value("streetNumber").toInteger()){
                    streetNumber_billing = QString::number(billingAddressObj.value("streetNumber").toInteger());
                }
                QString door_billing = billingAddressObj["door"].toString();
                emit fillSettingsSuccess(id_user,username,email,firstName,lastName,phoneNumber,
                                         id_home, country_home,county_home,city_home,zipCode_home,streetName_home,streetNumber_home,door_home,
                                         id_billing, country_billing,county_billing,city_billing,zipCode_billing,streetName_billing,streetNumber_billing,door_billing);
            }
            // loginForm
            if(!jsonDoc.isEmpty()) {
               emit loginSuccess(uname);
            } else {
               emit loginUnSuccessfully();
            }
        }
        if(url == "http://localhost:8080/product") {
            if(chartFlag == true) {
                QByteArray data = reply -> readAll();
                QJsonDocument jsonDoc = QJsonDocument::fromJson(data);
                QJsonArray array = jsonDoc.array();
                QList<QString> productCategory;
                foreach (const QJsonValue & v, array)
                {
                    QJsonObject obj = v.toObject();
                    productCategory.append(obj.value("category").toString());
                }
                qDebug() << productCategory;
                QMultiHash<QString, int> counts;
                foreach (const QString & category, productCategory) {
                    if (counts.contains(category))
                        counts[category] = counts[category] + 1;
                    else
                        counts.insert(category, 1);
                }
                QList<QString> categories;
                QList<int> values;
                int sum = 0;
                QMultiHash<QString, int>::iterator i = counts.begin();
                while (i != counts.end()) {
                    qDebug() << i.value() << " " << i.key();
                    categories.append(i.key());
                    values.append(i.value());
                    sum = sum + i.value();
                    ++i;
                }
                chartFlag = false;
                emit chartSuccess(categories, values, sum);
            }else {
                QByteArray data = reply -> readAll();
                QJsonDocument jsonDoc = QJsonDocument::fromJson(data);
                QJsonArray array = jsonDoc.array();
                QList<QString> productId;
                QList<QString> productName;
                QList<QString> productPrice;
                QList<QString> productDescription;
                QList<QString> productCategory;

                foreach (const QJsonValue & v, array)
                {
                    QJsonObject obj = v.toObject();
                    productId.append(QString::number(obj.value("id").toInteger()));
                    productName.append(obj.value("name").toString());
                    productPrice.append(QString::number(obj.value("price").toDouble()));
                    productDescription.append(obj.value("description").toString());
                    productCategory.append(obj.value("category").toString());
                }
                emit productSuccess(productId, productName, productPrice, productDescription, productCategory);
            }
        }
        if(url.contains("http://localhost:8080/product/category")) {
            QByteArray data = reply -> readAll();
            QJsonDocument jsonDoc = QJsonDocument::fromJson(data);
            QJsonArray array = jsonDoc.array();
            QList<QString> productId;
            QList<QString> productName;
            QList<QString> productPrice;
            QList<QString> productDescription;
            QList<QString> productCategory;
            foreach (const QJsonValue & v, array)
            {
                QJsonObject obj = v.toObject();
                productId.append(QString::number(obj.value("id").toInteger()));
                productName.append(obj.value("name").toString());
                productPrice.append(QString::number(obj.value("price").toDouble()));
                productDescription.append(obj.value("description").toString());
                productCategory.append(obj.value("category").toString());
            }
            emit sortByCategorySucces(productId, productName, productPrice, productDescription, productCategory);

        }

        if(url.contains("http://localhost:8080/cart")) {
            // cart ba toltunk fel
            if(productIdGlobal > 0) {
                bool cartContainsProduct = false;
                QByteArray data = reply -> readAll();
                QJsonDocument jsonDoc = QJsonDocument::fromJson(data);
                QJsonObject cartObj = jsonDoc.object();
                QJsonArray array = cartObj.value("products").toArray();
                QList<QString> productId;
                foreach (const QJsonValue & v, array)
                {
                    QJsonObject obj = v.toObject();
                    int prodId = obj.value("id").toInteger();
                    // ha mar felvettuk
                    if(prodId == productIdGlobal) {
                        cartContainsProduct = true;
                    }
                }
                // csak akkor toltjuk fel, ha meg ilyen id-val rendelkezo nincs a cart-ban
                if(cartContainsProduct == false) {
                    QString urlString = QString("http://localhost:8080/cart/");
                    urlString.append(QString::number(productIdGlobal));
                    QNetworkRequest request = QNetworkRequest(QUrl(urlString));
                    request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
                    request.setRawHeader("Authorization", BearerToken);

                    QByteArray data;
                    manager.put(request, data);
                }
            } else {
                QByteArray data = reply -> readAll();
                QJsonDocument jsonDoc = QJsonDocument::fromJson(data);
                QJsonObject cartObj = jsonDoc.object();
                QJsonArray array = cartObj.value("products").toArray();
                QList<QString> productId;
                QList<QString> productName;
                QList<QString> productPrice;
                QList<QString> productDescription;
                QList<QString> productCategory;
                double cartSum = 0;
                foreach (const QJsonValue & v, array)
                {
                    QJsonObject obj = v.toObject();
                    productId.append(QString::number(obj.value("id").toInteger()));
                    productName.append(obj.value("name").toString());
                    productPrice.append(QString::number(obj.value("price").toDouble()));
                    productDescription.append(obj.value("description").toString());
                    productCategory.append(obj.value("category").toString());
                    cartSum += obj.value("price").toDouble();
                }
                emit getProductsFromCartSucces(productId, productName, productPrice, productDescription, productCategory);
                emit sendSumSuccess(cartSum);
            }
        }
    }

    // PUT
    if (operation == 3) {
        QString url = reply->url().toString();
        if(url == "http://localhost:8080/user") {
            QVariant statusCode = reply->attribute( QNetworkRequest::HttpStatusCodeAttribute );
            int status = statusCode.toInt();
            if(status == 200) {
                emit updateSuccess();
                qDebug() << "User Frissitve!!!%.%";
            } else {
                qDebug() << "User NEM frissult!!! " << status;
            }
        }
        if(url.contains("http://localhost:8080/cart/")) {
            QVariant statusCode = reply->attribute( QNetworkRequest::HttpStatusCodeAttribute );
            int status = statusCode.toInt();
            if(status == 200) {
                emit cartUpdateSuccess();
            }
        }
    }

    // POST
    if (operation == 4) {
        QString url = reply->url().toString();
        if(url.contains("http://localhost:8080/login")) {
            // token lekerese a headerbol
            auto reqHeaderName = reply->rawHeaderList();
            QVariantMap reqHeaders;
            BearerToken = reply->rawHeader("Authorization");

            QString urlString = QString("http://localhost:8080/user");
            QNetworkRequest request = QNetworkRequest(QUrl(urlString));
            request.setHeader( QNetworkRequest::ContentTypeHeader, "application/json" );
            request.setRawHeader("Authorization", BearerToken);
            manager.get(request);
        }

        if(url.contains("http://localhost:8080/register")) {
            QVariant statusCode = reply->attribute( QNetworkRequest::HttpStatusCodeAttribute );
            int status = statusCode.toInt();
            // CREATED
            if(status == 201) {
                emit registerSuccess();
                qDebug() << "User Felkuldve!!!%.%";
            } else {
                qDebug() << "Usert NEM kuldte fel!!! " << status;
            }
        }
        if(url.contains("http://localhost:8080/cart/buy")) {
            QVariant statusCode = reply->attribute( QNetworkRequest::HttpStatusCodeAttribute );
            int status = statusCode.toInt();
            if(status == 200) {
                emit buySuccess();
                qDebug() << "Vasarlas sikeres!!!%.%";
            }
        }
    }

    if(operation == 5) {
        QString url = reply->url().toString();
        if(url.contains("http://localhost:8080/user")){
            QVariant statusCode = reply->attribute( QNetworkRequest::HttpStatusCodeAttribute );
            int status = statusCode.toInt();
            if(status == 200) {
                emit deleteUser();
            }
        }
        if(url.contains("http://localhost:8080/cart/")){
            QVariant statusCode = reply->attribute( QNetworkRequest::HttpStatusCodeAttribute );
            int status = statusCode.toInt();
            if(status == 200) {
                emit deleteFromCartSuccess();
            }
        }
    }
}
