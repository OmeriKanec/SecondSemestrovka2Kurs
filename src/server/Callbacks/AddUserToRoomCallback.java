package src.server.Callbacks;

import src.server.UserConnection;

import java.util.UUID;

@FunctionalInterface
public interface AddUserToRoomCallback {
    public boolean addUserToRoom(UUID uuid, UserConnection connection);
}
