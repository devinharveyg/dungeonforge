package dungeonforge.states;

import dungeonforge.commands.GameContext;

import java.util.List;

public class GameOverState implements GameState{
    private final static List<String> VERBS = List.of(
            "quests",
            "history", "status", "quit"
    );

    @Override
    public void onEnter(GameContext ctx){
        ctx.getBus().message(
                ctx.getPlayer().isAlive() ? "--- you leave the dungeon behind --- 'quests' for your record or quit"
                        : " --- you died ---'quests' to see how far you got or quit"
        );
    }



    @Override
    public List<String> availableCommands() {
        return VERBS;
    }

    @Override
    public String name() {
        return "Game Over";
    }
}
