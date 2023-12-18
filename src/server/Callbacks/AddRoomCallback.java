package src.server.Callbacks;

import src.server.models.GameRoom;

import java.util.List;

@FunctionalInterface
public interface AddRoomCallback {
    public void AddRoom(GameRoom gameRoom);
}
