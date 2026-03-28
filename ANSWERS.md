# Assignment Questions

## Instructions
Answer all 4 questions with detailed explanations. Each answer should be **3-5 sentences minimum** and demonstrate your understanding of the concepts.

---

## Question 1: Thread vs Process

**Question**: Explain the difference between a **thread** and a **process**. Why did we use threads in this assignment instead of creating separate processes?

**Your Answer:**

A process is basically a heavy standalone program that has its own dedicated memory and resources, while a thread is a much lighter unit that runs inside a process. Creating processes for every small task is a waste of resources because they don't share memory easily and take more time to start up. In this simulation, using threads is the smarter choice because they share the same memory space and can communicate way faster. This makes the whole program more efficient and keeps the overhead low since we aren't duplicating the entire environment for every task. It’s all about getting the job done without slowing down the system with unnecessary resource management

---

## Question 2: Ready Queue Behavior

**Question**: In Round-Robin scheduling, what happens when a process doesn't finish within its time quantum? Explain using an example from your program output.

**Your Answer:**

[In Round-Robin scheduling, if a process doesn't finish its execution within the assigned time quantum, the CPU interrupts it immediately to let the next process in line run. The interrupted process is then moved straight to the back of the Ready Queue to wait for its next turn. This cycle keeps things fair and prevents any single task from hogging the processor for too long. Looking at the output, you can see how the scheduler forces a switch even if the process still has more work to do. It’s a constant rotation that keeps the system responsive for all active tasks at the same time]

Example from my output:
```
[? P1 executing quantum [3000ms]
? Quantum progress: [====================] 100%
? P1 completed quantum 3000ms | Overall progress: [==========          ] 48%
  Remaining time: 3151ms
? P1 yields CPU for context switch
? P1 (Priority: 5) added to ready queue | Burst time: 6151ms]
```

**Explanation of example:**
[In this snippet, P1 was executing but only managed to complete 48% of its work before the 3000ms quantum expired. The output shows that the remaining time is still 3151ms, so the scheduler forced it to yield the CPU. P1 was then sent back to the ready queue behind the other processes, demonstrating how the algorithm maintains fairness.]

---

## Question 3: Thread States

**Question**: A thread can be in different states: **New**, **Runnable**, **Running**, **Waiting**, **Terminated**. Walk through these states for one process (P1) from your simulation.

**Your Answer:**

[Write your answer here. For each state, explain when P1 enters that state during the simulation. Use your understanding of the code to trace through the lifecycle.]

1. **New**: [P1 was in this state when the simulation first initialized all 11 processes and their initial burst times (like P1's 6151ms) were first assigned.]

2. **Runnable**: [Runnable: P1 entered this state as soon as the "SCHEDULER STARTING" message appeared and it was placed in the initial Ready Queue behind P2.]

3. **Running**: [Running: The terminal shows P1 is in the Running state during the "executing quantum [3000ms]" phase where it actively uses the CPU.]

4. **Waiting**: [Waiting: This state occurs during the "context switch" phase when P1 has yielded the CPU and is waiting for the scheduler to cycle back through the other 10 processes.]

5. **Terminated**: [Terminated: A process like P2 reached this state when the output says "P2 finished execution!", meaning its remaining time hit 0ms and it was removed from the queue]

---

## Question 4: Real-World Applications

**Question**: Give **TWO** real-world examples where Round-Robin scheduling with threads would be useful. Explain why this scheduling algorithm works well for those scenarios.

**Your Answer:**

### Example 1: [Name of application/scenario]

**Description**: 
[When you have multiple tabs open in a browser, each tab is essentially a separate task. One might be loading a video, while another is rendering a complex news page and a third is just sitting idle.]

**Why Round-Robin works well here**: 
[Round-Robin is perfect here because it gives every tab a small slice of CPU time in rotation. This prevents a "heavy" tab that's loading a big file from freezing your entire browser. It keeps the interface responsive, so you can still switch tabs or scroll even while the background work is happening..]

### Example 2: [Name of application/scenario]

**Description**: 
These apps have to manage different threads at once: one for downloading the video data (buffering), one for decoding the frames, and another for handling the user interface and playback controls.

**Why Round-Robin works well here**: 
By using Round-Robin, the app ensures that the "buffering" thread doesn't take all the processing power and cause the "UI" thread to lag. It switches between them so fast that the video stays smooth while the controls remain snappy. This balance is what makes the app feel stable and professional even on mid-range devices
---

## Summary

**Key concepts I understood through these questions:**
1. How the Time Quantum (3000ms in my case) is the main factor that controls how often a context switch happens in Round-Robin.
2. The massive difference in overhead between a Process and a Thread, and why threads are way better for this kind of simulation.
3. How to track a thread's lifecycle from being New to Terminated by watching the console logs and remaining burst time.

**Concepts I need to study more:**
1. How to balance the Priority with Round-Robin to make sure high-priority tasks don't wait too long.
2. 2. The best way to handle Context Switches more efficiently so the CPU doesn't waste too much time switching between threads.
