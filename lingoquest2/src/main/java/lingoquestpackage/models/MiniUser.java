package lingoquestpackage.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class MiniUser {
    private final SimpleStringProperty username;
    private final SimpleIntegerProperty coinsEarned;

    public MiniUser(String username, int coinsEarned) {
        this.username = new SimpleStringProperty(username);
        this.coinsEarned = new SimpleIntegerProperty(coinsEarned);
    }

    public String getUsername() {
        return username.get();
    }

    public int getCoinsEarned() {
        return coinsEarned.get();
    }
}