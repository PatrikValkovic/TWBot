package cz.valkovic.twbot.modules.core.pipeping;

/**
 * Register pipes into the system.
 */
public interface PipesRegistrationService {

    /**
     * Register location pipe.
     * @param from Namespace on which the pipes is defined.
     * @param namespace Namespae that the pipe defines.
     * @param pipeClass Class representing the pipe.
     */
    void registerLocationPipe(String from, String namespace, Class<? extends LocationPipe> pipeClass);

    /**
     * Register parsing pipe.
     * @param from Namespace on which the pipe will be called.
     * @param pipeClass Class representing the pipe.
     */
    void registerParsingPipe(String from, Class<? extends ParsingPipe> pipeClass);

}
