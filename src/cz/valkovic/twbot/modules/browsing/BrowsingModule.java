package cz.valkovic.twbot.modules.browsing;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import cz.valkovic.twbot.controls.MyWebView;
import cz.valkovic.twbot.modules.core.importing.TWModule;
import cz.valkovic.twbot.modules.core.tabs.TabRegistrationService;

@TWModule
public class BrowsingModule extends AbstractModule {

    @Inject
    public static void register(TabRegistrationService tabs){
        tabs.register("Browser", MyWebView.class, false);
    }

    @Override
    protected void configure() {
        requestStaticInjection(BrowsingModule.class);
    }

}
