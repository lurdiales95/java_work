import com.example.choice.*;
import com.example.model.*;
import com.example.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameServiceTest {
    @Test
    @DisplayName("Rock beats scissor")
    void rockBeatsScissors() {
        Player p1 = new Player(new AlwaysPicksRockStrategy());
        Player p2 = new Player(new AlwaysPicksScissorsStrategy());

        GameService game = new GameService(p1, p2);

        GameResult result = game.playRound();
        assertEquals(GameResult.PLAYER1WINS, result);
    }

    @Test
    @DisplayName("Scissors beats paper")
    void scissorsBeatsPaper() {
        Player p1 = new Player(new AlwaysPicksScissorsStrategy());
        Player p2 = new Player(new AlwaysPicksPaperStrategy());

        GameService game = new GameService(p1, p2);

        GameResult result = game.playRound();
        assertEquals(GameResult.PLAYER1WINS, result);
    }

    @Test
    @DisplayName("Draw")
    void playersDrawPickingSame() {
        Player p1 = new Player(new AlwaysPicksScissorsStrategy());
        Player p2 = new Player(new AlwaysPicksScissorsStrategy());

        GameService game = new GameService(p1, p2);

        GameResult result = game.playRound();
        assertEquals(GameResult.DRAW, result);
    }
}
