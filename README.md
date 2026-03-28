
link vid :https://drive.google.com/drive/folders/1M1EC3wD94ybcHtPUkKyZ1PSNjz_lygKi











# CS3701 Operating Systems - Assignment 1: Multithreading
## Round-Robin CPU Scheduler Simulation

### 📋 Assignment Overview

This assignment evaluates your ability to implement and work with multithreading in Java while introducing professional software development practices including version control (GitHub), code documentation, and project presentation. You will work with a CPU scheduling simulation that uses a **Round-Robin algorithm** with a fixed **time quantum**.

**What You Will Do:**
- Fork a starter repository from GitHub and personalize it with your information
- Modify existing Java code to add three new features related to process scheduling
- Document your development process and reflect on what you learned
- Create a video demonstration explaining your work and understanding
- Submit your work through your public GitHub repository

**Important**: This assignment is worth **10%** of your final grade.

---

## 🎯 Learning Objectives

By completing this assignment, you will:
- **Understand Threading:** Learn how to create and manage Java threads using the `Runnable` interface
- **Master Thread Lifecycle:** Understand thread states, scheduling, and coordination using `Thread.start()`, `Thread.join()`, and `Thread.sleep()`
- **Practice Version Control:** Develop professional habits with Git and GitHub, including meaningful commits and repository management
- **Enhance Documentation Skills:** Learn to document code changes, reflect on your learning process, and explain technical concepts clearly
- **Develop Testing Skills:** Verify that your modifications work correctly and produce expected results
- **Build Communication Skills:** Present and explain your technical work through video demonstration

---

## � Background: Understanding the Starter Code

The starter code simulates a CPU scheduling system where multiple processes compete for CPU time. Each process is represented by a `Process` class, and the `SchedulerSimulation` class manages their execution using a Round-Robin algorithm. Understanding these classes will help you complete the assignment.

### `Process` Class:
The `Process` class represents an individual process in the CPU scheduling simulation. Each process has **attributes** like a *name*, *burst time*, *remaining time*, and *time quantum*. **The process will either run for a specified time quantum or, if it is the last remaining process in the system, it will run until completion without [yielding](https://en.wikipedia.org/wiki/Yield_(multithreading)) the CPU.**

#### Attributes:
- `String name`: The name of the process (e.g., "P1", "P2"). Used for identification in output messages.
- `int burstTime`: The total amount of time (in milliseconds) that the process requires to complete its execution. This is randomly assigned when the process is created.
- `int timeQuantum`: The time slice (in milliseconds) for which a process is allowed to run in each round-robin cycle. This value is fixed for all processes.
- `int remainingTime`: The remaining time (in milliseconds) for the process to complete. Initially, this is equal to the burstTime and decreases as the process runs.

#### Methods:
- `Process(String name, int burstTime, int timeQuantum)`: Constructor that initializes the `Process` object with a given `name`, `burstTime`, and `timeQuantum`. The `remainingTime` is initially set to be the same as the `burstTime`.
- `void run()`: 
  - Implements the `Runnable` interface's `run()` method. This method simulates the execution of the process for one time quantum or the remaining time, whichever is smaller.
  - After running for the allowed time, the remaining time is updated.
  - If the process still has time left to execute, it yields the CPU and is re-enqueued; otherwise, it completes its execution.
  - This method is always called when a `Thread` is started for the process.
- `void runToCompletion()`: 
  - This method is called when the process is the last one in the queue. Instead of running for a time quantum, the process runs until its remaining time reaches zero, meaning the process completes in a single run without being interrupted.
  - This method ensures that the last process does not get re-enqueued but instead finishes execution in one go.
- `String getName()`: A simple getter method to retrieve the name of the process.
- `int getBurstTime()`: A getter method to retrieve the total burst time (i.e., the full time the process needs for completion).
- `int getRemainingTime()`: A getter method to retrieve the remaining time that the process needs to complete its execution.
- `boolean isFinished()`: This method checks whether the process has completed its execution. It returns true if the remainingTime is less than or equal to zero, meaning no time is left for the process to run.

**Note:** The complete `Process` class implementation is available in the starter repository you will fork from GitHub.

### `SchedulerSimulation` Class:
The `SchedulerSimulation` class is responsible for managing the lifecycle and scheduling of processes. **It handles the creation of processes, placing them in a queue, and running them using a Round-Robin scheduling algorithm**. **When only one process remains, it runs to completion without respect to the time quantum**.

#### Attributes:
No instance variables in the class, but several data structures and constants are used within the methods.

#### Methods:
1. `public static void main(String[] args)`: This is the entry point of the program. It simulates the scheduling of processes based on a Round-Robin algorithm with a fixed time quantum.

   **Key Tasks in main:**
   - **Time Quantum:** Defines the time quantum for scheduling, which is a random value between 2000 and 5000 milliseconds with a step of 1000 milliseconds. **The random seed is based on the student ID, so you are required to replace the seed with your student ID.**
   - **Process Creation:** Creates a specified number of processes (numProcesses). This number is randomly chosen between 10 and 20 according to the provided seed.
   - **Process Queueing:** Processes are added to a FIFO queue (`processQueue`), and each process is associated with its corresponding thread using a `Map<Thread, Process> (processMap)`. Each process has a unique name and burst time (randomly assigned between `timeQuantum/2` and `3×timeQuantum`).
   - **Scheduler Loop:** The scheduler retrieves each process from the queue and executes it using a thread. It checks whether a process is finished after its time quantum:
     - *If the process is not finished and there are other processes in the queue, it is re-enqueued.*
     - *If the process is the last one remaining, it is allowed to run until completion without being interrupted.*
   - **Completion:** The simulation ends when all processes have finished execution.

2. `public static void addProcessToQueue(Process process, Queue<Thread> processQueue, Map<Thread, Process> processMap)`: 
   - This helper method is responsible for adding a `Process` to the `processQueue` and associating it with a new `Thread` in the `processMap`. It also prints a message indicating that the process has entered the ready queue.
   
   **Parameters:**
   - `Process process`: The process to be added to the queue.
   - `Queue<Thread> processQueue`: The queue that manages threads in a First-In-First-Out (FIFO) order.
   - `Map<Thread, Process> processMap`: A mapping of threads to processes, so the scheduler can easily retrieve the process associated with each thread.
   
   - **Message:** Prints details about the process, including its burst time and remaining time, when it enters the ready queue.

**Note:** The complete `SchedulerSimulation` class implementation is available in the starter repository you will fork from GitHub. You will modify this file to add your student ID and implement the required features.

---

## �🚀 Getting Started

### Step 1: Fork This Repository

1. **Create a GitHub account** using your **university email** (@std.psau.edu.sa)
   - Go to https://github.com
   - Click "Sign up"
   - Use your university email: `yourID@std.psau.edu.sa`
   - Verify your email address

2. **Fork this repository**:
   - Go to the starter repository: `https://github.com/makopt/OS-Assignment1-Starter`
   - Make sure you’re logged in to GitHub with your university email account
   - Click the **"Fork"** button at the top right of this page
   - This creates your own copy of the repository
   - Your repository will be at: `https://github.com/YOUR_USERNAME/OS-Assignment1-Starter`

3. **Rename your forked repository**:
   - Go to your forked repository Settings
   - Change the repository name to: `OS-Assignment1-YourFirstName-YourLastName`
   - Example: `OS-Assignment1-Mohammed-Ahmed`

4. **Make sure your repository is PUBLIC**:
   - Go to Settings → scroll down to "Danger Zone"
   - Verify visibility is set to "Public"
   - If private, click "Change visibility" → "Make public"

### Step 2: Clone Your Repository to Your Computer

```bash
git clone https://github.com/YOUR_USERNAME/OS-Assignment1-YourFirstName-YourLastName.git
cd OS-Assignment1-YourFirstName-YourLastName
```

### Step 3: Set Up Your Student ID

**⚠️ CRITICAL FIRST STEP ⚠️**

Open `SchedulerSimulation.java` and **immediately** change line 92:

```java
// BEFORE (DO NOT SUBMIT THIS):
int studentID = 123456789;

// AFTER (Use YOUR real ID):
int studentID = 441234567;  // Replace with YOUR actual student ID
```

**Save this change and commit it right away**:

```bash
git add SchedulerSimulation.java
git commit -m "Set my student ID: 441234567"
git push origin main
```

✅ This personalizes your assignment and prevents identical outputs!

---

## 📂 Repository Structure

Your repository should have these files:

```
OS-Assignment1-YourName/
├── README.md                    # This file - project overview
├── SchedulerSimulation.java     # Main code file (YOU WILL MODIFY THIS)
├── DEVELOPMENT_LOG.md           # Your development timeline (FILL THIS)
├── REFLECTION.md                # Your learning reflections (FILL THIS)
├── ANSWERS.md                   # Answers to assignment questions (FILL THIS)
└── .gitignore                   # Git ignore file (already configured)
```

---

## ✅ Your Tasks (4 Parts - 10 Marks Total)

### **Part 1: GitHub Account & Repository Setup (3 marks)**

1. **Step 1: Create GitHub Account** (if you don't have one):
   - Go to [github.com/signup](https://github.com/signup)
   - **CRITICAL:** Use your university email: `[yourid]@std.psau.edu.sa`
   - Example: `442105123@std.psau.edu.sa`
   - Choose a professional username (e.g., mohammed-ahmed-441)
   - Verify your email address before proceeding
   - **Note:** Using non-university email results in -0.25 marks penalty

2. **Step 2: Fork the Starter Repository:**
   - Make sure you're logged in to GitHub with your university email account
   - Go to the starter repository: `https://github.com/makopt/OS-Assignment1-Starter`
   - Click the **"Fork"** button at the top right corner
   - Wait for GitHub to create your copy (takes 5-10 seconds)
   - You will be redirected to YOUR forked repository
   - Your URL will be: `https://github.com/[YOUR-USERNAME]/OS-Assignment1-Starter`

3. **Step 3: Rename Your Repository:**
   - On YOUR forked repository page, click **"Settings"** tab (top right)
   - Under "Repository name", change from `O-Assignment1-Starter` to: `OS-Assignment1-YourFirstName-YourLastName`
   - Example: `OS-Assignment1-Mohammed-Ahmed`
   - Click **"Rename"** button
   - **Verify repository is PUBLIC:** Scroll down to "Danger Zone" section, check visibility is "Public"

4. **Step 4: Set Your Student ID in Code:**
   - In your repository, click on `SchedulerSimulation.java`
   - Click the pencil/edit icon to edit the file
   - Find line 92: `int studentID = 123456789;`
   - Replace `123456789` with YOUR actual student ID
   - Scroll down and click **"Commit changes"**
   - Commit message: `"Set my student ID: [YOUR-ID]"`
   - Click **"Commit changes"** again to confirm
   - **This is mandatory - your output must be unique!**

**Repository Structure:** The starter repository contains these files:
```
OS-Assignment1-Starter/
├── SchedulerSimulation.java  (Main code - YOU WILL MODIFY THIS)
├── DEVELOPMENT_LOG.md         (Fill with your development entries)
├── REFLECTION.md              (Answer reflection questions here)
├── ANSWERS.md                 (Answer assignment questions here)
├── README.md                  (Instructions - read carefully!)
└── .gitignore                 (Already configured for Java)
```

**Important:** Read the `README.md` file in the repository - it contains detailed instructions for every step!

**Enhanced Code Features:** The starter code includes several enhancements to help you visualize the scheduling process:
- **Color-Coded Output:** The code uses ANSI color codes to display different types of information (process states, queue status, completion messages) in different colors for better readability.
- **Visual Progress Bars:** Each process shows both quantum-level progress (during execution) and overall completion progress using visual progress bars.
- **Randomized Simulation:** The simulation uses your student ID as a seed to randomly generate:
  - Time quantum (2000-5000ms)
  - Number of processes (10-20 processes)
  - Burst time for each process
- **Ready Queue Visualization:** Before each process execution, the current state of the ready queue is displayed.
- **Formatted Headers:** Professional-looking headers and boxes to organize the output.

**These features are already implemented - your job is to add the three new features described in Part 2!**

---

## 🛠️ Recommended Development Environment

**We strongly recommend using Visual Studio Code (VS Code) as your IDE:**

### Why VS Code?
- **Download**: Visit [Visual Studio Code](https://code.visualstudio.com/download) and install VS Code for your operating system
- **GitHub Integration**: VS Code has built-in Git support that makes committing and pushing changes easier
- **Sign in to GitHub**: In VS Code, click the account icon (bottom left) and sign in with your GitHub account - this will link your IDE to your repositories
- **Clone Your Repository**: Use *File > Open Folder* or use the Command Palette (Ctrl/Cmd+Shift+P) and search for "Git: Clone" to clone your forked repository

### Extensions to Install:
- **GitLens** - Enhances Git capabilities and visualization
- **Java Extension Pack** - Provides Java language support, debugging, and project management
- **Markdown All in One** - Useful for editing your markdown documentation files
- You can follow these video tutorials to set up your environment and GitHub and Java integration in VS Code: [VS Code GitHub Integration Tutorial](https://www.youtube.com/watch?v=i_23KUAEtUM) and [Java Development in VS Code Tutorial](https://www.youtube.com/watch?v=BB0gZFpukJU)

### Making Commits:
After editing files, use the Source Control panel (Ctrl/Cmd+Shift+G) to stage, commit, and push your changes directly from VS Code.

**Note:** You are free to use any IDE or text editor you prefer (IntelliJ IDEA, Eclipse, Notepad++, etc.), but VS Code is recommended for its excellent GitHub integration. *Search online for tutorials on "VS Code GitHub integration" or "how to use Git in VS Code" for detailed guides.*

---

### **Part 2: Code Implementation (3 marks)**

**Your Task:** Modify `SchedulerSimulation.java` to add three new features:

**Important Guidelines:**
- Make **one commit per feature** - do NOT commit everything at once!
- Test each feature before committing
- Add clear comments explaining your additions
- Keep the existing functionality working

**Commit Examples:**
- ✅ `"Feature 1: Added priority field to Process class"`
- ✅ `"Feature 2: Implemented context switch counter"`
- ✅ `"Feature 3: Added waiting time tracking and summary"`
- ❌ `"done"` or `"update"` or `"final version"` (too vague!)

#### 2.1: Feature 1 - Add Process Priority (1 mark)
- Add a `priority` field to the Process class (integer 1-5, where 5 is highest)
- Generate random priorities when creating processes
- Display priority when a process enters the ready queue
- Example output: `"P1 (Priority: 4) enters the ready queue..."`
- Add clear comments explaining your additions

#### 2.2: Feature 2 - Count Context Switches (1 mark)
- Add a static counter variable for context switches
- Increment the counter each time a new process starts running
- Display total context switches at the end of simulation
- Example: `"Total context switches: 47"`
- A context switch occurs every time the CPU switches to a different process

#### 2.3: Feature 3 - Track Waiting Time (1 mark)
- Add fields to track when each process was created and total time spent waiting
- Calculate waiting time for each process
- Use `System.currentTimeMillis()` to track time
- Display a summary table at the end showing:
  - Process Name
  - Burst Time
  - Waiting Time
- Format the output in a clear, readable table

**Commit after EACH feature**:
```bash
git add SchedulerSimulation.java
git commit -m "Added [feature name]: [brief description]"
git push origin main
```

### **Part 3: Documentation (2 marks)**

Complete these three markdown files in your repository to document your work and demonstrate your understanding:

#### 3.1: DEVELOPMENT_LOG.md (1 mark)
Track your development process with **minimum 5 entries**:
- Date and time of work session
- What you worked on (specific features or tasks)
- Challenges encountered during this session
- Solutions you implemented
- Time spent (approximate)
- **Template with example format is provided in the file!**
- Entries should be spread across different dates (not all at once)

#### 3.2: REFLECTION.md (0.5 marks)
Answer the **4 reflection questions** provided in the file:
1. What did you learn about multithreading from this assignment?
2. What was the most challenging part of this assignment and why?
3. How did you overcome the challenges you faced?
4. How can multithreading concepts be applied in real-world applications?
- Each answer: **minimum 5-7 sentences**
- Write in your own words, showing genuine reflection
- Connect concepts to your actual experience with the code

#### 3.3: ANSWERS.md (0.5 marks)
Answer the **4 technical questions** in the provided template:
- Each answer: **minimum 3-5 sentences**
- Include specific examples from your code or output
- Use proper technical terminology (thread, process, time quantum, context switch, etc.)
- Reference relevant parts of your code where appropriate

### **Part 4: Video Demonstration (2 marks)**

Create a **2-3 minute video** (not shorter, not longer) showing:

1. **Introduction (30 seconds)**:
   - State your name and student ID
   - Show your GitHub repository homepage
   - Verify repository is public and uses university email

2. **Code Walkthrough (1 minute)**:
   - Navigate through your repository files
   - Show your three modifications (priority, context switches, waiting time)
   - Briefly explain each feature you added

3. **Execution Demo (30 seconds)**:
   - Open your IDE (VS Code, IntelliJ IDEA, Eclipse, or similar)
   - Show the `SchedulerSimulation.java` file open in the IDE
   - Compile and run using your IDE's run button/feature
   - Show console output with your student ID and unique simulation parameters

4. **Concept Explanation (30 seconds)**:
   - Pick ONE threading concept you learned
   - Options: `Thread.start()`, `Thread.join()`, `Thread.sleep()`, or how the ready queue works
   - Explain it in your own words using your code as example

5. **Closing (10 seconds)**:
   - Show your commit history (at least 3 commits)
   - Thank you message

**Video Requirements:**
- Upload video to **Google Drive**
- Set sharing permissions to "Anyone with the link can view"
- Name your video file: `StudentID_Assignment1_Demo.mp4` (or .mov, .avi)
- Add the Google Drive link in your `README.md` file
- Show your face OR use clear voice narration
- Screen recording showing your code and execution
- Good audio quality (test your microphone first!)

**Recording Tools:**
- **Mac:** QuickTime Player (File → New Screen Recording)
- **Windows:** Xbox Game Bar (Win + G) or OBS Studio
- **Linux:** SimpleScreenRecorder or OBS Studio

**Important Notes:**
- Use your IDE (VS Code, IntelliJ IDEA, Eclipse, etc.) to compile and run the code - NOT the terminal
- Make sure the IDE console output is clearly visible in your video
- Show your unique simulation parameters: student ID, time quantum, and number of processes
- Explain concepts in your own words to demonstrate genuine understanding

**Upload your video to Google Drive and add the shareable link to your README.md file. Make sure the link is accessible to anyone with the link.**

---

## 📝 Questions to Answer (in ANSWERS.md)

These questions help you demonstrate technical understanding. Each answer should be thoughtful and specific.

### Question 1: Thread vs Process
- **Explain the difference** between a thread and a process
- **Why did we use threads** in this assignment instead of separate processes?
- **Mention at least TWO specific differences** (e.g., memory sharing, creation overhead, communication speed)
- **Reference relevant parts** of `SchedulerSimulation.java` where appropriate
- Minimum: 3-5 sentences

### Question 2: Ready Queue Behavior
- In Round-Robin scheduling, **what happens when a process doesn't finish** within its time quantum?
- **Explain using a specific example** from YOUR program output
- **Copy a relevant snippet** from your terminal output showing a process being re-queued
- **Explain why this re-queueing behavior** is important for fairness in CPU scheduling
- Minimum: 3-5 sentences

### Question 3: Thread Lifecycle
- A thread typically goes through these states: **New, Runnable, Running, Waiting, Terminated**
- **Walk through these states** for one process (e.g., P1) from your simulation
- For each state, **explain WHEN P1 enters that state** during execution
- **Use specific method calls** from the code (`Thread.start()`, `Thread.join()`, `Thread.sleep()`) in your explanation
- Minimum: 3-5 sentences

### Question 4: Real-World Applications
- **Describe TWO real-world scenarios** where Round-Robin scheduling with threads would be useful
- For each scenario: **What is the application/system?** (e.g., web server, game engine, media player)
- For each scenario: **Why is Round-Robin scheduling appropriate?** (consider fairness, responsiveness, predictability)
- **Explain how the concepts** from this assignment apply to each scenario
- Minimum: 3-5 sentences

---

## ⚠️ Academic Integrity

### Critical Warnings:

1. **AI-Generated Code**: 
   - Pure AI-generated submissions without understanding will receive **zero marks**
   - You may be asked to explain your code in person
   - You must be able to answer questions about your implementation

2. **Plagiarism**: 
   - Copying from classmates results in **zero marks for all parties**
   - Each student has unique output based on their student ID

3. **Commit History**: 
   - Single bulk commits show poor practice and will be penalized
   - Commits must be spread over different dates/times

4. **Video Authenticity**: 
   - You must be able to answer questions about your work
   - Video should show genuine understanding, not memorized explanations

---

## 📅 Submission Instructions

### What to Submit on Blackboard:

Submit a **text file** named: `YourName_StudentID_Assignment1.txt`

**The file should contain:**
```
Student Name: [Your Full Name]
Student ID: [Your Student ID]
GitHub Username: [your-github-username]
Repository Link: https://github.com/[your-username]/OS-Assignment1-[YourName]
Video Link: [Google Drive link - make sure it's accessible to anyone with the link]
Date Submitted: [Submission Date]
```

**Example:**
```
Student Name: Mohammed Ahmed Abdullah
Student ID: 442105123
GitHub Username: mohammed-ahmed-441
Repository Link: https://github.com/mohammed-ahmed-441/OS-Assignment1-Mohammed-Ahmed
Video Link: https://drive.google.com/file/d/1aBcDeFgHiJkLmNoPqRsTuVwXyZ/view?usp=sharing
Date Submitted: March 31, 2026
```

### Before You Submit - Final Checklist:

- [ ] Repository is **PUBLIC** (check Settings → Visibility)
- [ ] Student ID is set correctly in `SchedulerSimulation.java` (line 92)
- [ ] Code compiles without errors: `javac SchedulerSimulation.java`
- [ ] Code runs successfully: `java SchedulerSimulation`
- [ ] At least 3 meaningful commits with good messages
- [ ] Commits spread over different dates (not all at once!)
- [ ] `DEVELOPMENT_LOG.md` completed (minimum 5 entries)
- [ ] `REFLECTION.md` completed (4 questions answered)
- [ ] `ANSWERS.md` completed (4 questions answered)
- [ ] Video is 2-3 minutes long
- [ ] Video uploaded to Google Drive with shareable link added to README.md
- [ ] GitHub account uses university email (@std.psau.edu.sa)

---

## 🏆 Grading Summary (Total: 10 marks)

Your work will be evaluated based on the following criteria:

- **Part 1 - GitHub Setup & Personalization (3 marks):** 
  - GitHub account created with university email (@std.psau.edu.sa) (0.5 marks)
  - Repository properly forked and renamed (1 mark)
  - Student ID correctly set in code (1 mark)
  - Repository is public and accessible (0.5 marks)

- **Part 2 - Code Implementation (3 marks):** 
  - Feature 1: Priority field implemented correctly (1 mark)
  - Feature 2: Context switch counter working properly (1 mark)
  - Feature 3: Waiting time tracking accurate (1 mark)
  - Code compiles without errors and runs successfully
  - Clear comments added to explain modifications
  - Separate commits for each feature

- **Part 3 - Documentation (2 marks):** 
  - DEVELOPMENT_LOG.md complete with 5+ meaningful entries (1 mark)
  - REFLECTION.md with 4 questions answered thoroughly (0.5 marks)
  - ANSWERS.md with 4 technical questions answered correctly (0.5 marks)
  - All answers demonstrate understanding and use proper terminology

- **Part 4 - Video Demonstration (2 marks):** 
  - Video is 2-3 minutes long (0.5 marks)
  - Shows repository, code walkthrough, and execution (0.5 marks)
  - Explains one threading concept clearly (0.5 marks)
  - Good audio/visual quality and accessible Google Drive link (0.5 marks)

- **Professional Practices:**
  - Minimum 3 meaningful commits with descriptive messages
  - Commits spread over multiple sessions (not all at once)
  - Proper file organization and naming conventions

### Penalties:
- Late submission: -2 marks per day
- Missing video: -2 marks
- Single commit or all commits in last hour: -1 mark
- Private repository: -1 mark
- Not using university email for GitHub: -0.5 marks
- AI-generated without understanding: 0 marks (entire assignment)
- Plagiarism: 0 marks + academic misconduct report

---

## 📚 Additional Resources

### Java Threading Basics:
- Oracle Java Tutorials: [Concurrency](https://docs.oracle.com/javase/tutorial/essential/concurrency/)
- Your textbook: Chapter 4 - Threads & Concurrency

### Git & GitHub:
- [GitHub Guides](https://guides.github.com/)
- [Git Cheat Sheet](https://education.github.com/git-cheat-sheet-education.pdf)

### Round-Robin Scheduling:
- Your textbook: Chapter 5 - CPU Scheduling
- Wikipedia: [Round-robin scheduling](https://en.wikipedia.org/wiki/Round-robin_scheduling)

---

## 🎓 Important Information

**Deadline:** March 31, 2026

**Late Policy:** -2 marks per day late

Good luck! Start early, commit regularly, and demonstrate your understanding of Java threading through this assignment.
