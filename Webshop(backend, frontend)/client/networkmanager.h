#ifndef NETWORKMANAGER_H
#define NETWORKMANAGER_H

#include <QObject>
#include <QNetworkAccessManager>
#include <QVariant>

class NetworkManager : public QObject
{
    Q_OBJECT
    QNetworkAccessManager manager;
public:
    explicit NetworkManager(QObject *parent = nullptr);

// adatok visszakuldese a feluletnek/fogadasa feluletrol
// es mas jelzesek
signals:
    void loginSuccess(QString username);
    void loginUnSuccessfully();
    void goRegSuccess();
    void registerSuccess();
    void homeSuccess();
    void categorySuccess();
    void cartSuccess();
    void sortByCategorySucces(QList<QString> productId, QList<QString> productName, QList<QString> productPrice, QList<QString> productDescription, QList<QString> productCategory);
    void getProductsFromCartSucces(QList<QString> productId, QList<QString> productName, QList<QString> productPrice, QList<QString> productDescription, QList<QString> productCategory);
    void logoutSuccess();
    void productSuccess(QList<QString> productId, QList<QString> productName, QList<QString> productPrice, QList<QString> productDescription, QList<QString> productCategory);
    void settingsSuccess();
    void goToLoginSuccess();
    void fillSettingsSuccess(QString id_user, QString username, QString email, QString firstName, QString lastName, QString phoneNumber,
                             QString id_home, QString country_home, QString county_home, QString city_home, QString zipCode_home, QString streetName_home, QString streetNumber_home, QString door_home,
                             QString id_billing, QString country_billing, QString county_billing, QString city_billing, QString zipCode_billing, QString streetName_billing, QString streetNumber_billing, QString door_billing);
    void updateSuccess();
    void deleteUser();
    void goToInfoSuccess();
    void buySuccess();
    void chartSuccess(QList<QString> categories, QList<int> values, int sum);
    void cartUpdateSuccess();
    void deleteFromCartSuccess();
    void sendSumSuccess(double cartSum);
    void testResponse();

// fuggvenyek, amelyek a networkmanager.cpp ben vannak megirva
public slots:
    void login(QVariant username, QVariant password);
    void goRegFunction();
    void homeFunction();
    void categoryFunction();
    void cartFunction();
    void sortByCategoryFunction(QVariant category);
    void registerFunction(QVariant username, QVariant password, QVariant email, QVariant firstName, QVariant lastName, QVariant phoneNumber,
                          QVariant country_home, QVariant county_home, QVariant city_home, QVariant zipCode_home, QVariant streetName_home, QVariant streetNumber_home, QVariant door_home,
                          QVariant country_billing, QVariant county_billing, QVariant city_billing, QVariant zipCode_billing, QVariant streetName_billing, QVariant streetNumber_billing, QVariant door_billing);
    void logoutFunction();
    void productFunction();
    void settingsFunction();
    void settingsUpdateFunction(QVariant id_user, QVariant username, QVariant password, QVariant email, QVariant firstName, QVariant lastName, QVariant phoneNumber,
                                QVariant id_home, QVariant country_home, QVariant county_home, QVariant city_home, QVariant zipCode_home, QVariant streetName_home, QVariant streetNumber_home, QVariant door_home,
                                QVariant id_billing, QVariant country_billing, QVariant county_billing, QVariant city_billing, QVariant zipCode_billing, QVariant streetName_billing, QVariant streetNumber_billing, QVariant door_billing);
    void goToLoginFunction();
    void addToCartFunction(QVariant productId);
    void deleteFromCartFunction(QVariant productId);
    void getFromCartFunction();
    void deleteCartFunction();
    void deleteUserFunction();
    void goToInfoFunction();
    void buyFunction();
    void getChartFunction();
    void testFunction();

private slots:
    void requestFinished(QNetworkReply *reply);
};

#endif // NETWORKMANAGER_H
