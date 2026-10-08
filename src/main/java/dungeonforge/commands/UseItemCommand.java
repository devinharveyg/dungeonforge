package dungeonforge.commands;

import dungeonforge.events.GameEvent;
import dungeonforge.items.Hourglass;
import dungeonforge.items.Item;
import dungeonforge.items.Potion;

public class UseItemCommand extends AbstractCommand{


    private boolean rewound = false;

    public UseItemCommand(GameContext ctx, String args) {
        super(ctx, args);
    }

    @Override
    protected boolean doExecute() {
        Item item = player().findItem(args);
        if(item == null){
            bus().message("you do not have that to use");
            return false;
        }

        if(item instanceof Hourglass hourglass){
            if(!hourglass.hasCharges()){
                bus().message("the hourglass is empty");
                return false;
            }
            hourglass.spendCharges();
            rewound = true;
            bus().message("Sand runs backwards. the last action was undone");
            if(!ctx.getHistory().undoLast()){
                bus().message("there was nothing to take back");
            }
            return false;
        }

        if(item instanceof Potion potion){
            player().heal(potion.getHealAmount());
            player().removeItem(item);
            bus().publish(GameEvent.message("you drink "+potion.getName()+". HP"+player().getHp() +"/" +player().getMaxHp()));
            return true;
        }

        bus().message("you can not think of a use for "+item.getName());
        return false;
    }

    @Override
    public String getDescription(){
        return ("use " + args).trim();
    }
}
