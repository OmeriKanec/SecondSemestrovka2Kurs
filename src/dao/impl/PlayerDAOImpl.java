package src.dao.impl;


import dao.DAO;
import models.Player;
import src.DataBaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlayerDAOImpl implements DAO<Player> {

    @Override
    public void create(Player p) {
        try {
            PreparedStatement statement = DataBaseConnection.getConn().prepareStatement("insert into players(username, password, avatarfaceid, avatarskinid) values (?, ?, ?, ?)");
            statement.setString(1, p.getUserName());
            statement.setString(2, p.getPassword());
            statement.setInt(3, p.getAvatarFaceId());
            statement.setInt(4, p.getAvatarSkinId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Player get(long id) {
        try {
            PreparedStatement statement = DataBaseConnection.getConn().prepareStatement(
                    "select * from players where id = ?"
            );
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Player p = new Player(rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("money"),
                        rs.getInt("avatarfaceid"),
                        rs.getInt("avatarskinid"));
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void update(Player p) {
        try {
            PreparedStatement statement = DataBaseConnection.getConn().prepareStatement("update players set username = ?, money = ?, avatarfaceid = ?, avatarskinid = ? where id = ?");
            statement.setString(1, p.getUserName());
            statement.setInt(2, p.getMoney());
            statement.setInt(3, p.getAvatarFaceId());
            statement.setInt(4, p.getAvatarSkinId());
            statement.setLong(5, p.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<Player> getAll() {
        return null;
    }
}
