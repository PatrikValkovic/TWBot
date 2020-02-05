package cz.valkovic.twbot.services.navigation;

import java.net.URL;
import java.util.Arrays;

public interface NavigationService {

    NavigationService queue(String... urls);

    default NavigationService queue(URL... urls) {
        return this.queue(Arrays.stream(urls).map(URL::toString).toArray(String[]::new));
    }

}
