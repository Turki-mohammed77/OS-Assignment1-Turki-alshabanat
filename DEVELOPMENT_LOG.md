# Development Log

## Instructions
Document your development process as you work on the assignment. Add entries showing:
- What you worked on
- Problems you encountered
- How you solved them
- Time spent

**Requirements**: Minimum 5 entries showing progression over time.

---

## Example Entry Format:

### Entry 1 - [April 1, 2026, 2:30 PM]
**What I did**: Forked the repository and set up my student ID

**Details**: 
- Created GitHub account with university email
- Forked the starter repository
- Changed student ID on line 92 to my actual ID (441234567)
- Compiled and ran the program successfully

**Challenges**: Had to install JDK first because javac wasn't recognized

**Solution**: Downloaded JDK 17 from Oracle website and set PATH variable

**Time spent**: 30 minutes

---

## Your Development Log:

Entry 1 - [March 20, 2026, 10:00 AM]
What I did: Installed Git and created a GitHub account.
Details:
* Downloaded the latest version of Git for Windows.
* Created a GitHub account using my university email.
* Configured global username and email using the terminal.
Challenges: The command git --version was not recognized initially.
Solution: Re-ran the installer and made sure to check the box "Add Git to PATH."
Time spent: 45 minutes

Entry 2 - [March 20, 2026, 11:30 AM]
What I did: Installed VS Code and Java Development Kit (JDK).
Details:
* Installed Visual Studio Code as the primary IDE.
* Installed the "Extension Pack for Java" by Microsoft.
* Set up JDK 17 to ensure the environment can compile the scheduler code.
Challenges: VS Code was showing a "Java Home not found" error.
Solution: Manually pointed the java.jdt.ls.java.home setting in VS Code to the JDK installation folder.
Time spent: 40 minutes 

---

Entry 3 - [March 21, 2026, 1:00 PM]
What I did: Integrated GitHub with VS Code and cloned the project.
Details:
* Authenticated my GitHub account within VS Code.
* Forked the starter repository for the CPU Scheduler.
* Cloned the repository locally to begin editing the code.
* Updated the Student ID in the main method.
Challenges: SSH key authentication failed when trying to push the first commit.
Solution: Switched to HTTPS authentication using a Personal Access Token (PAT).
Time spent: 1 hour

---

Entry 4 - [March 22, 2026, 3:30 PM]
What I did: Implemented Feature 1 (Priority) and Feature 2 (Context Switch Counter).
Details:
* Added a priority field to the Process class and updated the constructor.
* Integrated a static contextSwitchCount variable in the SchedulerSimulation class.
* Modified the output messages to display the priority of each process when added to the queue.
Challenges: Incrementing the context switch counter in the wrong place led to overcounting.
Solution: Moved the increment logic to the point where the scheduler pulls a thread from the queue, right before currentThread.start().
Time spent: 1.5 hours

---

Entry 5 - [March 23, 2026, 5:00 PM]
What I did: Implemented Feature 3 (Waiting Time Metrics and Summary Table).
Details:
* Added fields to track totalWaitingTime and lastReadyTime for each process.
* Created the updateWaitingTime() method to calculate duration spent in the queue.
* Developed the displayWaitingTimeSummary() method to print a formatted table at the end of the simulation.
* Calculated the Average Waiting Time for all completed processes.
Challenges: The waiting time was showing as zero for some processes.
Solution: Realized I wasn't updating the lastReadyTime when a process was re-added to the queue after its quantum expired; fixed this by calling setLastReadyTime() during re-entry.
Time spent: 2 hours 

---

### Entry 6 - [Optional - Date and Time]
**What I did**: 

**Details**: 

**Challenges**: 

**Solution**: 

**Time spent**: 

---

## Summary

**Total time spent on assignment**: [X hours]

**Most challenging part**: 

**Most interesting learning**: 

**What I would do differently next time**: 
