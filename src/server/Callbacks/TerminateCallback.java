package src.server.Callbacks;

import java.util.UUID;

@FunctionalInterface
public interface TerminateCallback {
    public void terminate(UUID uuid);
}
