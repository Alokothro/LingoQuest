package lingoquestpackage.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class LeaderboardEntry {
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty coins;

    public LeaderboardEntry(String username, int coins) {
        this.username = new SimpleStringProperty(username);
        this.coins = new SimpleIntegerProperty(coins);
    }

    public String getUsername() {
        return username.get();
    }

    public int getCoins() {
        return coins.get();
    }
}