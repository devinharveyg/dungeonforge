package dungeonforge.factory;

import dungeonforge.config.RandomSource;
import dungeonforge.core.Monster;
import dungeonforge.items.*;

public class ForgeThemeKit implements ThemeKit{



    private final MonsterFactory factory;

    private static final String[] FLAVORS = {
            "Heat rolls of the walls in slow waves",
            "Cinders drift upward from a grate in the floor",
            "Somewhere below a hammer strikes, nobody is holding it",
            "The stone has been melted and reset badly"
    };

    public ForgeThemeKit(MonsterFactory factory){
        this.factory = factory;
    }






    @Override
    public String themeName() {
        return "Forge";
    }

    @Override
    public Monster createMonster(int depth) {
        String id = RandomSource.getInstance().pick(factory.idsForTheme("forge"));
        return factory.create(id, depth);
    }

    @Override
    public Monster createBoss(int depth) {
        return factory.create("forge_tyrant", depth);
    }

    @Override
    public Item createLoot(int depth) {
        switch(RandomSource.getInstance().nextInt(4)) {
            case 0:
                return new Weapon("Slag Cleaver", 4.0, 55 + depth * 10, 5 + depth);
            case 1:
                return new Armor("Scorched Plate", 6.0, 60 + depth * 8, 2 + depth);
            case 2:
                return new Potion("Quench Draught", 0.4, 25, 18 + depth * 3);
            default:
                return new Treasure("Ingot of Bright Iron", 0.2, 60 + depth * 15);
        }
    }

    @Override
    public String createRoomFlavor() {
        return RandomSource.getInstance().pick(FLAVORS);
    }
}
