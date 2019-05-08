package cz.valkovic.java.twbot.services.connectors.webview;

import cz.valkovic.java.twbot.controls.MyWebView;

public interface WebViewConnector {

    MyWebView getView();

    void bind(MyWebView view);

}
