package dungeonforge;

import dungeonforge.config.GameConfig;
import dungeonforge.config.RandomSource;
import dungeonforge.core.GameWorld;
import dungeonforge.core.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Random;

public class SingletonTest {
    void freshSingletons(){
        GameConfig.resetForTests();
        RandomSource.resetForTests();

    }

    @Test
    void configReturnsTheSameInstance(){
        assertSame(GameConfig.getInstance(), GameConfig.getInstance());
    }

    @Test
    void configConstructorIsPrivate(){
        Constructor<?>[] ctors = GameConfig.class.getDeclaredConstructors();
        assertEquals(1 , ctors.length, "singleton must only have one constructor");
        assertTrue(Modifier.isPrivate(ctors[0].getModifiers()));

    }

    @Test
    void playerBuiltWithConfigValues(){
        Player p = new Player("Test");
        assertEquals(GameConfig.getInstance().getInt("playerStartingHp"), p.getMaxHp());
        assertEquals(GameConfig.getInstance().getInt("playerStartingAttack"), p.getAttackPower());

    }


    @Test
    void unknownKeysDoNotCrashTheGame(){
        assertEquals(0, GameConfig.getInstance().getInt("fake"));
    }


    @Test
    void randomSourceConfigReturnsTheSameInstance(){
        assertSame(RandomSource.getInstance(), RandomSource.getInstance());
    }

    @Test
    void randomSourceConfigConstructorIsPrivate(){
        Constructor<?>[] ctors = RandomSource.class.getDeclaredConstructors();
        assertEquals(1 , ctors.length, "singleton must only have one constructor");
        assertTrue(Modifier.isPrivate(ctors[0].getModifiers()));

    }

    @Test
    void theSameSeedProducesSameSeedSequence(){
        RandomSource.getInstance().reseed(12345L);
        int[] first = tenRolls();

        RandomSource.getInstance().reseed(12345L);
        int[] second = tenRolls();

        assertArrayEquals(first, second);

    }


    @Test
    void theDifferentSeedProducesDifferentSeedSequence(){
        RandomSource.getInstance().reseed(15L);
        int[] first = tenRolls();

        RandomSource.getInstance().reseed(2L);
        int[] second = tenRolls();

        assertFalse(java.util.Arrays.equals(first, second), "A different seed should produce a different sequence");

    }

    @Test
    void theSameSeedProducesTheSameDungeon(){
        RandomSource.getInstance().reseed(12345L);
        int monstersFirstRun = new GameWorld(new Player("firstRun")).totalMonsters();


        RandomSource.getInstance().reseed(12345L);
        int monstersSecondRun = new GameWorld(new Player("secondRun")).totalMonsters();


        assertEquals(monstersFirstRun, monstersSecondRun);
    }

    private int[] tenRolls(){
        int[] out = new int[10];
        for(int i = 0; i < out.length; i++){
            out[i] = RandomSource.getInstance().nextInt(1000);
        }
        return out;
    }
}
