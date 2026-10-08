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

```

## 6. Tests and CI

**`mvn test` summary:**

```

```

**Green CI URL:**

## 7. Sprint review — one sentence

> What can the project do now that it could not do last week?

