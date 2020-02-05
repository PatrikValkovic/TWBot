package cz.valkovic.twbot.modules.core.directories;

/**
 * Interface that handles locations for storing data.
 */
public interface DirectoriesService {

    /**
     * Get path where to store application configuration.
     * The path should be created and writable.
     */
    String getConfigDir();

    /**
     * Get path where to store application data.
     * The path should be created and writable.
     */
    String getDataDir();

    /**
     * Get path where to store application logs.
     * The path should be created and writable.
     */
    String getLogDir();
}
