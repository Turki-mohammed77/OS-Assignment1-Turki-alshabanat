import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

// Standard ANSI codes for coloring terminal output
class Colors {
   
    public static final String RESET = "\u001B[0m";
    public static final String BOLD = "\u001B[1m";
    public static final String CYAN = "\u001B[36m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String BLUE = "\u001B[34m";
    public static final String RED = "\u001B[31m";
    public static final String BG_BLUE = "\u001B[44m";
    public static final String BG_GREEN = "\u001B[42m";
    public static final String WHITE = "\u001B[37m";
    public static final String BRIGHT_WHITE = "\u001B[97m";
    public static final String BRIGHT_CYAN = "\u001B[96m";
    public static final String BRIGHT_YELLOW = "\u001B[93m";
    public static final String BRIGHT_GREEN = "\u001B[92m";
}

// Represents a system process that runs as a separate thread
class Process implements Runnable {
    private String name; // ID or name of the task
    private int burstTime; // Total CPU time needed for completion
    private int timeQuantum; // Maximum duration allowed per CPU turn
    private int remainingTime; // Time still needed to finish the job
    
    // FEATURE 1: Added ranking/priority attribute
    private int priority; 
    
    // FEATURE 3: Variables for tracking idle/waiting periods
    private long creationTime; 
    private long totalWaitingTime; 
    private long lastReadyTime; 

    // Constructor to set up process details and initial states
    // FEATURE 1: Include priority in initialization
    // FEATURE 3: Set up timestamps for wait calculations
    public Process(String name, int burstTime, int timeQuantum, int priority) {
        this.name = name;
        this.burstTime = burstTime;
        this.timeQuantum = timeQuantum;
        this.remainingTime = burstTime; 
        this.priority = priority; 
        
        // FEATURE 3: Log the moment of process entry
        this.creationTime = System.currentTimeMillis(); 
        this.totalWaitingTime = 0; 
        this.lastReadyTime = this.creationTime; // Process starts in ready state
    }

    // Execution logic triggered when the thread starts
    @Override
    public void run() {
        // Determine if we run for a full quantum or just the remaining time
        int runTime = Math.min(timeQuantum, remainingTime); 
        
        // Display start of current execution slice
        String quantumBar = createProgressBar(0, 15);
        System.out.println(Colors.BRIGHT_GREEN + "  ▶️ " + Colors.BOLD + Colors.CYAN + name + 
                          Colors.RESET + Colors.GREEN + " processing slice" + Colors.RESET + 
                          " [" + runTime + "ms] ");
        
        try {
            // Break down execution into steps for visual progress updates
            int steps = 5; 
            int stepTime = runTime / steps;
            
            for (int i = 1; i <= steps; i++) {
                Thread.sleep(stepTime);
                int quantumProgress = (i * 100) / steps;
                quantumBar = createProgressBar(quantumProgress, 15);
                
                // Update terminal line with current progress
                System.out.print("\r  " + Colors.YELLOW + "⚡" + Colors.RESET + 
                                " Slice progress: " + quantumBar);
            }
            System.out.println(); // Line break after slice ends
            
        } catch (InterruptedException e) {
            System.out.println(Colors.RED + "\n  ✗ " + name + " encountered an interruption." + Colors.RESET);
        }
        
        remainingTime -= runTime; // Update work left after this turn
        int overallProgress = (int) (((double)(burstTime - remainingTime) / burstTime) * 100);
        String overallProgressBar = createProgressBar(overallProgress, 20);
        
        System.out.println(Colors.YELLOW + "  ⏸ " + Colors.CYAN + name + Colors.RESET + 
                          " finished slice of " + Colors.BRIGHT_YELLOW + runTime + "ms" + Colors.RESET + 
                          " │ Total completion: " + overallProgressBar);
        System.out.println(Colors.MAGENTA + "     Remaining workload: " + remainingTime + "ms" + Colors.RESET);
        
        // Check if task needs to go back to the queue or is done
        if (remainingTime > 0) {
            System.out.println(Colors.BLUE + "  ↻ " + Colors.CYAN + name + Colors.RESET + 
                              " pausing for context switch" + Colors.RESET);
        } else {
            // Task has reached 100% completion
            System.out.println(Colors.BRIGHT_GREEN + "  ✓ " + Colors.BOLD + Colors.CYAN + name + 
                              Colors.RESET + Colors.BRIGHT_GREEN + " is now finished!" + 
                              Colors.RESET);
        }
        System.out.println();
    }
    
    // Generates a visual ASCII-style progress bar
    private String createProgressBar(int progress, int width) {
        int filled = (progress * width) / 100;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < width; i++) {
            if (i < filled) {
                bar.append(Colors.GREEN + "█" + Colors.RESET);
            } else {
                bar.append(Colors.WHITE + "░" + Colors.RESET);
            }
        }
        bar.append("] ").append(progress).append("%");
        return bar.toString();
    }

    // Handles the final remaining process without time-slicing
    public void runToCompletion() {
        try {
            // Direct execution of all remaining time
            System.out.println(Colors.BRIGHT_CYAN + "  ⚡ " + Colors.BOLD + Colors.CYAN + name + 
                              Colors.RESET + Colors.BRIGHT_CYAN + " is the lone process, finishing now" + 
                              Colors.RESET + " [" + remainingTime + "ms]");
            Thread.sleep(remainingTime); 
            remainingTime = 0; // Mark as fully done
            System.out.println(Colors.BRIGHT_GREEN + "  ✓ " + Colors.BOLD + Colors.CYAN + name + 
                              Colors.RESET + Colors.BRIGHT_GREEN + " is now finished!" + Colors.RESET);
            System.out.println();
        } catch (InterruptedException e) {
            System.out.println(Colors.RED + "  ✗ " + name + " failed during final run." + Colors.RESET);
        }
    }

    // Getter methods for accessing process properties
    public String getName() {
        return name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }
    
    // FEATURE 1: Access priority level
    public int getPriority() {
        return priority;
    }
    
    // FEATURE 3: Access creation timestamp
    public long getCreationTime() {
        return creationTime;
    }
    
    // FEATURE 3: Access total accumulated wait time
    public long getTotalWaitingTime() {
        return totalWaitingTime;
    }
    
    // FEATURE 3: Access the last time the process became ready
    public long getLastReadyTime() {
        return lastReadyTime;
    }
    
    // FEATURE 3: Update wait statistics before execution begins
    public void updateWaitingTime() {
        long currentTime = System.currentTimeMillis();
        long waitTime = currentTime - lastReadyTime; 
        totalWaitingTime += waitTime;
    }
    
    // FEATURE 3: Record when a process re-enters the waiting state
    public void setLastReadyTime(long time) {
        this.lastReadyTime = time;
    }

    // Simple check to see if the work is finished
    public boolean isFinished() {
        return remainingTime <= 0;
    }
}

public class SchedulerSimulation {
    
    // FEATURE 2: Global counter to track every switch between processes
    private static int contextSwitchCount = 0;
    
    // FEATURE 3: Collection of finished processes for the final report
    private static List<Process> completedProcesses = new ArrayList<>();

    public static void main(String[] args) {
        // NOTE: Input your unique Student ID to generate custom results
        int studentID = 445050223;  
        
        Random random = new Random(studentID);
        
        // Define a random time quantum between 2s and 5s in 1s intervals
        int timeQuantum = 2000 + random.nextInt(4) * 1000; 
        
        // Randomly decide the number of processes (range 10 to 20)
        int numProcesses = 10 + random.nextInt(11); 
        
        // Queue to hold threads in the order they arrive
        Queue<Thread> processQueue = new LinkedList<>();
        
        // Data structure to link threads to their corresponding Process objects
        Map<Thread, Process> processMap = new HashMap<>();
        
        // Print simulation dashboard with basic settings
        System.out.println("\n" + Colors.BOLD + Colors.BRIGHT_CYAN + 
                          "╔═══════════════════════════════════════════════════════════════════════════════════════╗" + 
                          Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + 
                          Colors.BG_BLUE + Colors.BRIGHT_WHITE + Colors.BOLD + 
                          "                          CPU SCHEDULING DASHBOARD                                " + 
                          Colors.RESET + Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + 
                          "╠═══════════════════════════════════════════════════════════════════════════════════════╣" + 
                          Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + 
                          Colors.YELLOW + "  ⚙ Task Count:    " + Colors.RESET + Colors.BRIGHT_YELLOW + 
                          String.format("%-65s", numProcesses) + 
                          Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + 
                          Colors.YELLOW + "  ⏱ Time Slice:    " + Colors.RESET + Colors.BRIGHT_YELLOW + 
                          String.format("%-65s", timeQuantum + "ms") + 
                          Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + 
                          Colors.YELLOW + "  🔑 Student ID:    " + Colors.RESET + Colors.BRIGHT_YELLOW + 
                          String.format("%-65s", studentID) + 
                          Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + 
                          "╚═══════════════════════════════════════════════════════════════════════════════════════╝" + 
                          Colors.RESET + "\n");
        
        // Create and register each individual process
        for (int i = 1; i <= numProcesses; i++) {
            // Assign a random burst time based on the quantum size
            int burstTime = timeQuantum/2 + random.nextInt(2 * timeQuantum + 1);
            
            // FEATURE 1: Assign a random priority level (1 to 5)
            int priority = 1 + random.nextInt(5); 
            
            // Construct the process instance
            Process process = new Process("P" + i, burstTime, timeQuantum, priority);
            
            // Place the process into the system queue
            addProcessToQueue(process, processQueue, processMap);
        }
        
        // Start the scheduling cycle
        System.out.println(Colors.BOLD + Colors.GREEN + 
                          "╔════════════════════════════════════════════════════════════════════════════════╗" + 
                          Colors.RESET);
        System.out.println(Colors.BOLD + Colors.GREEN + "║" + Colors.RESET + 
                          Colors.BG_GREEN + Colors.WHITE + Colors.BOLD + 
                          "                        ▶️  SCHEDULER ENGINE ACTIVE  ◀️                         " + 
                          Colors.RESET + Colors.BOLD + Colors.GREEN + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.GREEN + 
                          "╚════════════════════════════════════════════════════════════════════════════════╝" + 
                          Colors.RESET + "\n");
        
        // Loop until every task in the queue is dealt with
        while (!processQueue.isEmpty()) {
            // Retrieve the next thread from the FIFO queue
            Thread currentThread = processQueue.poll(); 
            
            // FEATURE 2: Track a new context switch occurrence
            contextSwitchCount++;
            
            // Get process data for the current thread
            Process process = processMap.get(currentThread);
            
            // FEATURE 3: Update waiting time just before execution starts
            process.updateWaitingTime();
            
            // Visualize the current state of the ready queue
            System.out.println(Colors.BOLD + Colors.MAGENTA + "┌─ Current Queue " + "─".repeat(65) + Colors.RESET);
            System.out.print(Colors.MAGENTA + "│ " + Colors.RESET + Colors.BRIGHT_WHITE + "[" + Colors.RESET);
            int queueCount = 0;
            for (Thread thread : processQueue) {
                Process p = processMap.get(thread);
                if (queueCount > 0) System.out.print(Colors.WHITE + " → " + Colors.RESET);
                System.out.print(Colors.BRIGHT_CYAN + p.getName() + Colors.RESET);
                queueCount++;
            }
            if (queueCount == 0) {
                System.out.print(Colors.YELLOW + "empty" + Colors.RESET);
            }
            System.out.println(Colors.BRIGHT_WHITE + "]" + Colors.RESET);
            System.out.println(Colors.BOLD + Colors.MAGENTA + "└" + "─".repeat(79) + Colors.RESET + "\n");
            
            // Execute the thread for the current quantum
            currentThread.start();
            
            try {
                // Wait for the thread's slice to conclude
                currentThread.join();
            } catch (InterruptedException e) {
                System.out.println("Main thread interrupted.");
            }
            
            // Manage process after its CPU turn
            if (!process.isFinished()) {
                // Return to queue if there are other pending tasks
                if (!processQueue.isEmpty()) {
                    // FEATURE 3: Timestamp the start of the next waiting period
                    process.setLastReadyTime(System.currentTimeMillis());
                    
                    // Re-insert process for a later turn
                    addProcessToQueue(process, processQueue, processMap);
                } else {
                    // Finish the task immediately if no one else is waiting
                    System.out.println(Colors.BRIGHT_YELLOW + "  ⚠ " + Colors.CYAN + process.getName() + 
                                      Colors.RESET + Colors.YELLOW + " only one left → concluding now" + 
                                      Colors.RESET);
                    process.runToCompletion(); 
                    
                    // FEATURE 3: Archive completed task
                    completedProcesses.add(process);
                }
            } else {
                // FEATURE 3: Task finished within its slice
                completedProcesses.add(process);
            }
        }
        
        // Notification for end of simulation
        System.out.println(Colors.BOLD + Colors.BRIGHT_GREEN + 
                          "╔════════════════════════════════════════════════════════════════════════════════╗" + 
                          Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_GREEN + "║" + Colors.RESET + 
                          Colors.BG_GREEN + Colors.WHITE + Colors.BOLD + 
                          "                     ✓  SIMULATION FINISHED  ✓                                " + 
                          Colors.RESET + Colors.BOLD + Colors.BRIGHT_GREEN + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_GREEN + 
                          "╚════════════════════════════════════════════════════════════════════════════════╝" + 
                          Colors.RESET + "\n");
        
        // FEATURE 2: Show context switch summary
        System.out.println(Colors.BOLD + Colors.BRIGHT_YELLOW + 
                          "╔════════════════════════════════════════════════════════════════════════════════╗" + 
                          Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_YELLOW + "║" + Colors.RESET + 
                          Colors.BG_BLUE + Colors.BRIGHT_WHITE + Colors.BOLD + 
                          "                        PERFORMANCE STATS                                        " + 
                          Colors.RESET + Colors.BOLD + Colors.BRIGHT_YELLOW + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_YELLOW + 
                          "╠════════════════════════════════════════════════════════════════════════════════╣" + 
                          Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_YELLOW + "║" + Colors.RESET + 
                          Colors.CYAN + "  🔄 Total Switches:         " + Colors.RESET + 
                          Colors.BRIGHT_CYAN + String.format("%-52s", contextSwitchCount) + 
                          Colors.BOLD + Colors.BRIGHT_YELLOW + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_YELLOW + 
                          "╚════════════════════════════════════════════════════════════════════════════════╝" + 
                          Colors.RESET + "\n");
        
        // FEATURE 3: Output the detailed wait time table
        displayWaitingTimeSummary();
    }
    
    // Logic for adding a task to the queue and updating tracking map
    // FEATURE 1: Print priority status upon entry
    public static void addProcessToQueue(Process process, Queue<Thread> processQueue, 
                                        Map<Thread, Process> processMap) {
        // Instantiate a new execution thread
        Thread thread = new Thread(process);
        
        // Add to system tracking structures
        processQueue.add(thread);
        processMap.put(thread, process);
        
        // FEATURE 1: Visual log including process priority
        System.out.println(Colors.BLUE + "  ➕ " + Colors.BOLD + Colors.CYAN + process.getName() + 
                          Colors.RESET + Colors.YELLOW + " (Prio: " + process.getPriority() + ")" + 
                          Colors.RESET + Colors.BLUE + " entered ready queue" + Colors.RESET + 
                          " │ Workload: " + Colors.YELLOW + process.getBurstTime() + "ms" + 
                          Colors.RESET);
    }
    
    // FEATURE 3: Displays final metrics including wait times and averages
    public static void displayWaitingTimeSummary() {
        // Table header decoration
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + 
                          "╔════════════════════════════════════════════════════════════════════════════════╗" + 
                          Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + 
                          Colors.BG_BLUE + Colors.BRIGHT_WHITE + Colors.BOLD + 
                          "                     WAIT TIME ANALYTICS SUMMARY                                 " + 
                          Colors.RESET + Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + 
                          "╠════════════════════════════════════════════════════════════════════════════════╣" + 
                          Colors.RESET);
        
        // Define header row
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + 
                          "  " + Colors.BOLD + Colors.BRIGHT_WHITE + 
                          String.format("%-12s", "Task ID") + 
                          String.format("%-15s", "Burst Time") + 
                          String.format("%-15s", "Priority") + 
                          String.format("%-20s", "Wait Time") + 
                          Colors.RESET + "          " +
                          Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
        
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + 
                          "╠════════════════════════════════════════════════════════════════════════════════╣" + 
                          Colors.RESET);
        
        long totalWaitingTime = 0;
        
        // Loop through all data to populate the table
        for (Process process : completedProcesses) {
            String waitTimeStr = process.getTotalWaitingTime() + "ms";
            
            System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + 
                              "  " + Colors.BRIGHT_CYAN + 
                              String.format("%-12s", process.getName()) + Colors.RESET +
                              Colors.YELLOW + 
                              String.format("%-15s", process.getBurstTime() + "ms") + Colors.RESET +
                              Colors.MAGENTA + 
                              String.format("%-15s", process.getPriority()) + Colors.RESET +
                              Colors.BRIGHT_GREEN + 
                              String.format("%-20s", waitTimeStr) + Colors.RESET +
                              "          " +
                              Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
            
            totalWaitingTime += process.getTotalWaitingTime();
        }
        
        // Calculate the final average metric
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + 
                          "╠════════════════════════════════════════════════════════════════════════════════╣" + 
                          Colors.RESET);
        
        double avgWaitingTime = (double) totalWaitingTime / completedProcesses.size();
        
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET + 
                          "  " + Colors.BOLD + Colors.BRIGHT_YELLOW + 
                          String.format("%-42s", "Mean Waiting Duration:") + 
                          String.format("%-20s", String.format("%.2fms", avgWaitingTime)) + 
                          Colors.RESET + "          " +
                          Colors.BOLD + Colors.BRIGHT_CYAN + "║" + Colors.RESET);
        
        System.out.println(Colors.BOLD + Colors.BRIGHT_CYAN + 
                          "╚════════════════════════════════════════════════════════════════════════════════╝" + 
                          Colors.RESET + "\n");
    }
}
