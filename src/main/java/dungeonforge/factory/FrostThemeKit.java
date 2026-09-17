package dungeonforge.factory;

import dungeonforge.config.RandomSource;
import dungeonforge.core.Monster;
import dungeonforge.items.*;

public class FrostThemeKit implements ThemeKit{


    private final MonsterFactory factory;

    private static final String[] FLAVORS = {
            "Your breath hangs in the air and does not disperse",
            "Rime creeps along the floor toward your boots",
            "It's silent here in a way that fells deliberate",
            "Ice sheets the walls and something moves behind it"
    };

    public FrostThemeKit(MonsterFactory factory){
        this.factory = factory;
    }



    @Override
    public String themeName() {
        return "Frost";
    }

    @Override
    public Monster createMonster(int depth) {
        String id = RandomSource.getInstance().pick(factory.idsForTheme("frost"));
        return factory.create(id, depth);
    }

    @Override
    public Monster createBoss(int depth) {
        return factory.create("rime_tyrant", depth);
    }

    @Override
    public Item createLoot(int depth) {
        switch(RandomSource.getInstance().nextInt(4)) {
            case 0:
                return new Weapon("Rime Shard", 3.0, 50 + depth * 10, 5 + depth);
            case 1:
                return new Armor("Frostweave Cloak", 2.0, 60 + depth * 8, 2 + depth);
            case 2:
                return new Potion("Warming Cordial", 0.4, 28, 18 + depth * 3);
            default:
                return new Treasure("Frozen Tear", 0.2, 90 + depth * 15);
        }
    }

    @Override
    public String createRoomFlavor() {
        return RandomSource.getInstance().pick(FLAVORS);
    }
}
