# Week 7 Evidence

## 1. BEFORE

```bash
mvn -q exec:java
```

Play a few turns, then answer:

**Paste five consecutive `else if` branches from `gameLoop()`:**

```java
if (verb.isEmpty()) {
            } else if (verb.equals("north") || verb.equals("south") || verb.equals("down")) {
            } else if (verb.equals("attack")) {
            } else if (verb.equals("look")) {
            } else if (verb.equals("take"))
```

**You want to add `equip`. List every edit you would make:**

```
} else if (verb.equals("equip"))
```

**You want `e` to mean `equip`. List every *additional* edit:**

```
} else if (verb.equals("e"))
```

**You want undo. Where in `gameLoop()` would the code go?** *(There is no good answer. Say
why in one sentence.)*

There is no good answer because of how tightly coupled the if/else chain is.


## 2. AFTER — US-5.1

**Paste your new `gameLoop()` in full. It should fit in a dozen lines:**

```java
private static void gameLoop(CommandParser parser, GameContext ctx) {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
        while (ctx.isRunning() && ctx.getPlayer().isAlive()) {
            System.out.print("\n[HP " + ctx.getPlayer().getHp() + "/" + ctx.getPlayer().getMaxHp() + "] > ");
            System.out.flush();

            String line;
            try { line = in.readLine(); } catch (Exception e) { break; }
            if (line == null) break;
            System.out.println();

            Command command = parser.parse(line, ctx);
            command.execute();
        }
    }

```

**Paste the registry lines that add `drop` and its alias:**

```java
register("drop", DropCommand::new);

```

## 3. AFTER — US-5.2

**The naive-undo failure table from Part D2 step 2:**

| | HP | XP | Gold | Monster HP |
|---|---|---|---|---|
| Before attack | | | | |
| After attack | | | | |
| After naive undo | | | | |

**The same table after `TurnSnapshot`:**

| | HP | XP | Gold | Monster HP |
|---|---|---|---|---|
| Before attack | | | | |
| After attack | | | | |
| After snapshot undo | | | | |

## 4. AFTER — US-5.3 and US-5.4

**Paste a `loot` that picks up three things, then an `undo` that puts them all back:**

```
loot
[HP 76/80] > 
  you take Bone Shortsword
  you take Bone Shortsword
  you take Brave Shroud

inventory
[HP 76/80] > 
  You are carrying: 
     - Small Healing Draught [heals 22] (0.3kg, 20g)
     - Chronomaster's hourglass [2 charges] (rewinds time)
     - Bone Shortsword [dmg 6] (2.0kg, 50g)
     - Bone Shortsword [dmg 6] (2.0kg, 50g)
     - Brave Shroud [def 3] (3.0kg, 43g)
```

**Paste the hourglass rewinding a turn, with `inventory` before and after:**

```
use Chronomaster's hourglass
[HP 76/80] > 
  Sand runs backwards. the last action was undone

i
[HP 76/80] > 
  you inventory is empty
```

**How long did US-5.4 actually take you?** _30__ minutes

## 5. The replay

**Paste your `history` output and the first ten lines of replaying it with `--script=`:**

```
[INFO] --- exec:3.2.0:java (default-cli) @ dungeonforge ---
=========================================
        D U N G E O N F O R G E
  A Head First Design Patterns project
=========================================
  version 0.6.0   seed 20260829

  type 'help' for what you can do. you are carrying an hourglass -- use hourglass rewinds a turn
  [L1R0level 1: Crypt
  "Burial niches line the walls, most are empty"
  Exits: north

[script] >n
  You go north.
  Ghoul Ghoul looses a shot for 2

[script] >a
  You hit Ghoul for 10
  Ghoul Ghoul looses a shot for 2

[script] >a
  You hit Ghoul for 10
  ACHIEVEMENT: First Blood--Defeat your first monster
  Ghoul dies.12

[script] >a
  Nothing to attack

[script] >a
  Nothing to attack

[script] >loot
  you take Bone Shortsword
  you take Bone Shortsword
  you take Brave Shroud

[script] >quit
  you lay down your pack
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.492 s
[INFO] Finished at: 2026-10-09T08:40:29-07:00
[INFO] ------------------------------------------------------------------------

```

## 6. Tests and CI

**`mvn test` summary:**

```
$ mvn test
[INFO] Scanning for projects...
[INFO]
[INFO] ------------------< edu.redwoods.cis18:dungeonforge >-------------------
[INFO] Building DungeonForge 0.7.0
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- resources:3.4.0:resources (default-resources) @ dungeonforge ---
[INFO] Copying 3 resources from src\main\resources to target\classes
[INFO]
[INFO] --- compiler:3.13.0:compile (default-compile) @ dungeonforge ---
[INFO] Nothing to compile - all classes are up to date.
[INFO]
[INFO] --- resources:3.4.0:testResources (default-testResources) @ dungeonforge ---
[INFO] skip non existing resourceDirectory C:\Users\HLF\Documents\GitHub\dungeonforge\src\test\resources
[INFO]
[INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ dungeonforge ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 5 source files with javac [debug release 21] to target\test-classes
[INFO]
[INFO] --- surefire:3.2.5:test (default-test) @ dungeonforge ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running dungeonforge.CommandTest
[INFO] Tests run: 19, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.165 s -- in dungeonforge.CommandTest
[INFO] Running dungeonforge.FactoryTest
[INFO] Tests run: 16, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.052 s -- in dungeonforge.FactoryTest
[INFO] Running dungeonforge.SingletonTest
[INFO] Tests run: 10, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.024 s -- in dungeonforge.SingletonTest
[INFO] Running dungeonforge.SkeletonTest
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.006 s -- in dungeonforge.SkeletonTest
[INFO] Running dungeonforge.StrategyObserverTest
[INFO] Tests run: 17, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.043 s -- in dungeonforge.StrategyObserverTest
[INFO]
[INFO] Results:
[INFO]
[INFO] Tests run: 64, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  3.519 s
[INFO] Finished at: 2026-10-09T08:42:06-07:00
[INFO] ------------------------------------------------------------------------
```

**Green CI URL:**

## 7. Sprint review — one sentence

> What can the project do now that it could not do last week?

The project can store command history and replay it via a script.

