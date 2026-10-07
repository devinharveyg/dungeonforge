package dungeonforge.commands;

import dungeonforge.core.Player;
import dungeonforge.core.Room;
import dungeonforge.events.EventBus;

public abstract class AbstractCommand implements Command{



    protected GameContext ctx;

    public Player player() {
        return ctx.getPlayer();
    }

    public EventBus bus() {
        return ctx.getBus();
    }

    public Room room() {
        return ctx.getCurrentRoom();
    }


    protected final String args;
    private boolean actuallyRan;

    public AbstractCommand(GameContext ctx, String args){
        this.ctx = ctx;
        this.args = args == null ? "" : args.trim();

    }


    @Override
    public final void execute() {
        actuallyRan = doExecute();
        if(endsTurn()){
            ctx.runMonsterTurns();
        }

    }

    @Override
    public void undo() {

    }

    protected abstract boolean doExecute();

    @Override
    public String getDescription() {
        return "";
    }
}
