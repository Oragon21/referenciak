#include "networkmanager.h"
#include <QQmlContext>
#include <QApplication>
#include <QQmlApplicationEngine>

int main(int argc, char *argv[])
{
#if QT_VERSION < QT_VERSION_CHECK(6, 0, 0)
    QCoreApplication::setAttribute(Qt::AA_EnableHighDpiScaling);
#endif
    QApplication app(argc, argv);

    QQmlApplicationEngine engine;
    const QUrl url(QStringLiteral("qrc:/main.qml"));
    QObject::connect(&engine, &QQmlApplicationEngine::objectCreated,
                     &app, [url](QObject *obj, const QUrl &objUrl) {
        if (!obj && url == objUrl)
            QCoreApplication::exit(-1);
    }, Qt::QueuedConnection);
    engine.load(url);

    NetworkManager *myManager = new NetworkManager();
    // az oldalak objektumokkent
    QObject *login = engine.rootObjects()[0] -> findChild<QObject*>("Login");
    QObject *reg = engine.rootObjects()[0] -> findChild<QObject*>("Register");
    QObject *home = engine.rootObjects()[0] -> findChild<QObject*>("Home");
    QObject *category = engine.rootObjects()[0] -> findChild<QObject*>("Category");
    QObject *cart = engine.rootObjects()[0] -> findChild<QObject*>("Cart");
    QObject *settings = engine.rootObjects()[0] -> findChild<QObject*>("Settings");
    QObject *info = engine.rootObjects()[0] -> findChild<QObject*>("Info");

    // kapcsolatok a gombnyomasok es a fuggvenyhivasok kozott
    QObject::connect(login, SIGNAL(loginPressed(QVariant, QVariant)), myManager, SLOT(login(QVariant, QVariant)));
    QObject::connect(login, SIGNAL(goRegPressed()), myManager, SLOT(goRegFunction()));
    QObject::connect(login, SIGNAL(testSignal()), myManager, SLOT(testFunction()));
    QObject::connect(home, SIGNAL(homeSignal()), myManager, SLOT(homeFunction()));
    QObject::connect(home, SIGNAL(categorySignal()), myManager, SLOT(categoryFunction()));
    QObject::connect(home, SIGNAL(cartSignal()), myManager, SLOT(cartFunction()));
    QObject::connect(home, SIGNAL(logoutPressed()), myManager, SLOT(logoutFunction()));
    QObject::connect(home, SIGNAL(productSignal()), myManager, SLOT(productFunction()));
    QObject::connect(home, SIGNAL(settingsSignal()), myManager, SLOT(settingsFunction()));
    QObject::connect(home, SIGNAL(toCartSignal(QVariant)), myManager, SLOT(addToCartFunction(QVariant)));
    QObject::connect(home, SIGNAL(goToInfoSignal()), myManager, SLOT(goToInfoFunction()));
    QObject::connect(category, SIGNAL(homeSignal()), myManager, SLOT(homeFunction()));
    QObject::connect(category, SIGNAL(cartSignal()), myManager, SLOT(cartFunction()));
    QObject::connect(category, SIGNAL(sortByCategorySignal(QVariant)), myManager, SLOT(sortByCategoryFunction(QVariant)));
    QObject::connect(category, SIGNAL(toCartSignal(QVariant)), myManager, SLOT(addToCartFunction(QVariant)));
    QObject::connect(category, SIGNAL(settingsSignal()), myManager, SLOT(settingsFunction()));
    QObject::connect(category, SIGNAL(logoutPressed()), myManager, SLOT(logoutFunction()));
    QObject::connect(cart, SIGNAL(homeSignal()), myManager, SLOT(homeFunction()));
    QObject::connect(cart, SIGNAL(categorySignal()), myManager, SLOT(categoryFunction()));
    QObject::connect(cart, SIGNAL(deleteFromCartSignal(QVariant)), myManager, SLOT(deleteFromCartFunction(QVariant)));
    QObject::connect(cart, SIGNAL(getFromCartSignal()), myManager, SLOT(getFromCartFunction()));
    QObject::connect(cart, SIGNAL(deleteCartSignal()), myManager, SLOT(deleteCartFunction()));
    QObject::connect(cart, SIGNAL(buySignal()), myManager, SLOT(buyFunction()));
    QObject::connect(reg, SIGNAL(registerPressed(QVariant,QVariant,QVariant,QVariant,QVariant,QVariant,
                                                 QVariant,QVariant,QVariant,QVariant,QVariant,QVariant,QVariant,
                                                 QVariant,QVariant,QVariant,QVariant,QVariant,QVariant,QVariant)), myManager, SLOT(registerFunction(QVariant,QVariant,QVariant,QVariant,QVariant,QVariant,
                                                                                                                                                    QVariant,QVariant,QVariant,QVariant,QVariant,QVariant,QVariant,
                                                                                                                                                    QVariant,QVariant,QVariant,QVariant,QVariant,QVariant,QVariant)));
    QObject::connect(reg, SIGNAL(goToLoginSignal()), myManager, SLOT(goToLoginFunction()));
    QObject::connect(settings, SIGNAL(homeSignal()), myManager, SLOT(homeFunction()));
    QObject::connect(settings, SIGNAL(cartSignal()), myManager, SLOT(cartFunction()));
    QObject::connect(settings, SIGNAL(logoutPressed()), myManager, SLOT(logoutFunction()));
    QObject::connect(settings, SIGNAL(settingsPressed(QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant,
                                                      QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant,
                                                      QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant)), myManager, SLOT(settingsUpdateFunction(QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant,
                                                                                                                                                                               QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant,
                                                                                                                                                                               QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant, QVariant)));
    QObject::connect(settings, SIGNAL(deleteUserSignal()), myManager, SLOT(deleteUserFunction()));
    QObject::connect(info, SIGNAL(homeSignal()), myManager, SLOT(homeFunction()));
    QObject::connect(info, SIGNAL(categorySignal()), myManager, SLOT(categoryFunction()));
    QObject::connect(info, SIGNAL(cartSignal()), myManager, SLOT(cartFunction()));
    QObject::connect(info, SIGNAL(logoutPressed()), myManager, SLOT(logoutFunction()));
    QObject::connect(info, SIGNAL(logoutPressed()), myManager, SLOT(logoutFunction()));
    QObject::connect(info, SIGNAL(getChartSignal()), myManager, SLOT(getChartFunction()));

    engine.rootContext() -> setContextProperty("networkManager", myManager);

    return app.exec();
}
