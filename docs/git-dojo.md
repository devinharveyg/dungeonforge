# Git Dojo — my recovery notes

> Part D of Lab 2. For each drill: the command(s) you ran, **one sentence in your own
> words** on what it did, and one on when you would reach for it again.
>
> Graded on the sentences, not the commands. Commands can be copied; understanding cannot.

## The three trees — in my own words

| Tree | What lives here |
|---|---|
| Working Directory |  |
| Staging Area (Index) |  |
| HEAD |  |

---

## Drill 1 — Committed to `main` by accident

**Commands I ran:**
```bash
git switch -c fix/rescued-work    # branch now points at your commit 
git switch main 
git reset --hard origin/main      # main returns to where the remote is 
```
**What it did:**
This moved my current work into a new branch, switched the current branch to main and then reset the working directory to the last pushed to origin.
**When I would use it again:**
I would use it when I accidentally push to main before making a branch and PR.
---

## Drill 2 — Wrong commit message / forgot a file

**Commands I ran:**
```bash
echo "x" > note.txt && git add note.txt && git commit -m "asdf" 
git commit --amend -m "docs: add note file"
```
**What it did:**
This added a note file but with a improper commit message.

**Why you must not do this to a commit you already pushed:**
It can create conflicts if the commit is already pushed to origin.
---

## Drill 3 — Committed a file that should be ignored

**Commands I ran:**
```bash
git rm -r --cached target          # stop tracking, keep the local files 
echo "target/" >> .gitignore 
git add .gitignore && git commit -m "chore: untrack build output and ignore target/"
```
**What it did:**
This fixed a build artifact from being added to git.
**Why adding it to `.gitignore` alone was not enough:**
This is used when cleaning up a git directory from files that shouldn't be tracked.

---

## Drill 4 — Merge conflict

```bash
git switch main 
git switch -c feature/a 
printf '# DungeonForge - branch A title\n' > README.md 
git commit -am "docs: title from branch A" 
 
git switch main 
git switch -c feature/b 
printf '# DungeonForge - branch B title\n' > README.md 
git commit -am "docs: title from branch B" 
 
git switch main 
git merge feature/a          # clean 
git merge feature/b          # CONFLICT 
```
**In the conflict markers, which side was "mine"?**
They are both valid but need to be manually selected which to save.

**What it did:**
This created a merge conflict on the title of the README.

**How I would back out of a merge I regretted starting:**
Use the command ``` git merge --abort```  

---

## Drill 5 — "I destroyed everything"

**Commands I ran:**
```bash
git log --oneline          # note the current hash 
git reset --hard HEAD~3    # nuke the last three commits 
git log --oneline          # gone 
git reflog                 # every position HEAD has held 
git reset --hard <hash-from-before> 
```
**What `git reflog` showed me:**
It is possible to save yourself from commits that were made accidentally.

**One sentence on why this changes how nervous I should be about Git:**
This makes it easy to recover from a accidental merge or commit.
---

## Stretch — Drill 6 (detached HEAD, interactive rebase)

**Notes:**

---

## The one command I want to remember from today
git merge --abort
