package src.server.Callbacks;

import src.server.UserConnection;

import java.util.UUID;

@FunctionalInterface
public interface AddUserToRoomCallback {
    public void addUserToRoom(UUID uuid, UserConnection connection);
}
