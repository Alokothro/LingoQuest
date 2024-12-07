package lingoquestpackage.models;

public class MiniUser {
    private int coinsEarned;
    private String username;

    public MiniUser(String username, int coinsEarned) {
        this.username = username;
        this.coinsEarned = coinsEarned;
    }

    public String getUsername() {
        return this.username;
    }

    public int getCoinsEarned() {
        return this.coinsEarned;
    }
}
