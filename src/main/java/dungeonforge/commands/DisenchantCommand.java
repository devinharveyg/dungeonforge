package dungeonforge.commands;

import dungeonforge.items.EnchantmentRegistry;
import dungeonforge.items.Item;
import dungeonforge.items.Weapon;


public class DisenchantCommand extends AbstractCommand {




    public DisenchantCommand(GameContext ctx, String args) { super(ctx, args); }

    @Override
    protected boolean doExecute() {

        String target = args.trim();
        if(target.isEmpty()){
            bus().message("try different weapon name");
            return false;
        }
        Item item = player().findItem(target);
        if(item == null){
            bus().message("you are not carrying that");
            return false;
        }
        if(!(item instanceof Weapon w)){
            bus().message("only weapons can be disenchanted");
            return false;
        }

        Weapon peeled = w.peel();
        if(peeled == w){
            bus().message("the "+ w.getName()+" is not enchanted");
            return false;
        }

        boolean wasEquipped = (player().getEquippedWeapon() == w);

        player().removeItem(w);
        player().addItem(peeled);
        if(wasEquipped) { player().equip(peeled);}
        bus().message("The enchantments have been stripped " + peeled.describe());


        return true;
    }

    @Override public String getDescription() { return ("disenchantment " + args).trim(); }
}
