import java.util.Scanner;

public class CPUSchedulingAlgorithm {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nCPU Scheduling");
        System.out.println("1. First Come First Serve");
        System.out.println("2. Shortest Job First (Preemptive)");
        System.out.println("3. Shortest Job First (Non-Preemptive)");
        System.out.println("4. Priority Queue (Preemptive)");
        System.out.println("5. Priority Queue (Non-Preemptive)");
        System.out.println("6. Round Robin");
        System.out.print("\nChoose your option: ");

        int algorithm = scanner.nextInt();

        switch (algorithm) {
            case 1:
                firstComeFirstServe();
                break;
            case 2:
                shortestJobFirstPreemptive();
                break;
            case 3:
                shortestJobFirstNonPreemptive();
                break;
            case 4:
                priorityQueuePreemptive();
                break;
            case 5:
                priorityQueueNonPreemptive();
                break;
            case 6:
                roundRobin();
                break;
            default:
                System.out.println("Invalid Option");
                break;
        }
    }

    private static void firstComeFirstServe() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();

            if (i == 0) {
                completionTime[i] = burstTime[i];
            } else {
                completionTime[i] = completionTime[i - 1] + burstTime[i];
            }

            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
        }

        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        for (int i = 0; i < n; i++) {
            avgTurnaroundTime += turnaroundTime[i];
            avgWaitingTime += waitingTime[i];
        }
        avgTurnaroundTime /= n;
        avgWaitingTime /= n;

        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" +
                    completionTime[i] + "\t\t" + turnaroundTime[i] + "\t\t" + waitingTime[i]);
        }
        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
    }

    private static void shortestJobFirstPreemptive() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];
        boolean[] isCompleted = new boolean[n];
        int currentTime = 0;
        int completedProcesses = 0;

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
            isCompleted[i] = false;
        }

        while (completedProcesses < n) {
            int shortestJobIndex = -1;
            int shortestBurstTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && arrivalTime[i] <= currentTime && burstTime[i] < shortestBurstTime) {
                    shortestJobIndex = i;
                    shortestBurstTime = burstTime[i];
                }
            }

            if (shortestJobIndex == -1) {
                currentTime++;
                continue;
            }

            completionTime[shortestJobIndex] = currentTime + 1;
            turnaroundTime[shortestJobIndex] = completionTime[shortestJobIndex] - arrivalTime[shortestJobIndex];
            waitingTime[shortestJobIndex] = turnaroundTime[shortestJobIndex] - burstTime[shortestJobIndex];
            burstTime[shortestJobIndex]--;
            currentTime++;

            if (burstTime[shortestJobIndex] == 0) {
                isCompleted[shortestJobIndex] = true;
                completedProcesses++;
            }
        }

        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        for (int i = 0; i < n; i++) {
            avgTurnaroundTime += turnaroundTime[i];
            avgWaitingTime += waitingTime[i];
        }
        avgTurnaroundTime /= n;
        avgWaitingTime /= n;

        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" +
                    completionTime[i] + "\t\t" + turnaroundTime[i] + "\t\t" + waitingTime[i]);
        }
        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
    }

    private static void shortestJobFirstNonPreemptive() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];
        boolean[] isCompleted = new boolean[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
            isCompleted[i] = false;
        }

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            int shortestJobIndex = -1;
            int shortestBurstTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && arrivalTime[i] <= currentTime && burstTime[i] < shortestBurstTime) {
                    shortestJobIndex = i;
                    shortestBurstTime = burstTime[i];
                }
            }

            if (shortestJobIndex == -1) {
                currentTime++;
                continue;
            }

            completionTime[shortestJobIndex] = currentTime + burstTime[shortestJobIndex];
            turnaroundTime[shortestJobIndex] = completionTime[shortestJobIndex] - arrivalTime[shortestJobIndex];
            waitingTime[shortestJobIndex] = turnaroundTime[shortestJobIndex] - burstTime[shortestJobIndex];
            isCompleted[shortestJobIndex] = true;
            completedProcesses++;
            currentTime = completionTime[shortestJobIndex];
        }

        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        for (int i = 0; i < n; i++) {
            avgTurnaroundTime += turnaroundTime[i];
            avgWaitingTime += waitingTime[i];
        }
        avgTurnaroundTime /= n;
        avgWaitingTime /= n;

        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" +
                    completionTime[i] + "\t\t" + turnaroundTime[i] + "\t\t" + waitingTime[i]);
        }
        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
    }

    private static void priorityQueuePreemptive() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] priority = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];
        boolean[] isCompleted = new boolean[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
            System.out.print("Enter priority for process " + (i + 1) + ": ");
            priority[i] = scanner.nextInt();
            isCompleted[i] = false;
        }

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            int highestPriorityProcessIndex = -1;
            int highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && arrivalTime[i] <= currentTime && priority[i] < highestPriority) {
                    highestPriorityProcessIndex = i;
                    highestPriority = priority[i];
                }
            }

            if (highestPriorityProcessIndex == -1) {
                currentTime++;
                continue;
            }

            completionTime[highestPriorityProcessIndex] = currentTime + 1;
            turnaroundTime[highestPriorityProcessIndex] = completionTime[highestPriorityProcessIndex]
                    - arrivalTime[highestPriorityProcessIndex];
            waitingTime[highestPriorityProcessIndex] = turnaroundTime[highestPriorityProcessIndex]
                    - burstTime[highestPriorityProcessIndex];
            burstTime[highestPriorityProcessIndex]--;
            currentTime++;

            if (burstTime[highestPriorityProcessIndex] == 0) {
                isCompleted[highestPriorityProcessIndex] = true;
                completedProcesses++;
            }
        }

        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        for (int i = 0; i < n; i++) {
            avgTurnaroundTime += turnaroundTime[i];
            avgWaitingTime += waitingTime[i];
        }
        avgTurnaroundTime /= n;
        avgWaitingTime /= n;

        System.out.println(
                "\nProcess\tArrival Time\tBurst Time\tPriority\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" +
                    priority[i] + "\t\t" + completionTime[i] + "\t\t" + turnaroundTime[i] + "\t\t" + waitingTime[i]);
        }
        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
    }

    private static void priorityQueueNonPreemptive() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] priority = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];
        boolean[] isCompleted = new boolean[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
            System.out.print("Enter priority for process " + (i + 1) + ": ");
            priority[i] = scanner.nextInt();
            isCompleted[i] = false;
        }

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            int highestPriorityProcessIndex = -1;
            int highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && arrivalTime[i] <= currentTime && priority[i] < highestPriority) {
                    highestPriorityProcessIndex = i;
                    highestPriority = priority[i];
                }
            }

            if (highestPriorityProcessIndex == -1) {
                currentTime++;
                continue;
            }

            completionTime[highestPriorityProcessIndex] = currentTime + burstTime[highestPriorityProcessIndex];
            turnaroundTime[highestPriorityProcessIndex] = completionTime[highestPriorityProcessIndex]
                    - arrivalTime[highestPriorityProcessIndex];
            waitingTime[highestPriorityProcessIndex] = turnaroundTime[highestPriorityProcessIndex]
                    - burstTime[highestPriorityProcessIndex];
            isCompleted[highestPriorityProcessIndex] = true;
            completedProcesses++;
            currentTime = completionTime[highestPriorityProcessIndex];
        }

        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        for (int i = 0; i < n; i++) {
            avgTurnaroundTime += turnaroundTime[i];
            avgWaitingTime += waitingTime[i];
        }
        avgTurnaroundTime /= n;
        avgWaitingTime /= n;

        System.out.println(
                "\nProcess\tArrival Time\tBurst Time\tPriority\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" +
                    priority[i] + "\t\t" + completionTime[i] + "\t\t" + turnaroundTime[i] + "\t\t" + waitingTime[i]);
        }
        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
    }

    private static void roundRobin() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] remainingTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];
        boolean[] isCompleted = new boolean[n];

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
            remainingTime[i] = burstTime[i];
            isCompleted[i] = false;
        }

        System.out.print("Enter the time quantum: ");
        int timeQuantum = scanner.nextInt();

        int currentTime = 0;
        int completedProcesses = 0;

        while (completedProcesses < n) {
            boolean isProcessCompleted = true;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && arrivalTime[i] <= currentTime) {
                    isProcessCompleted = false;

                    if (remainingTime[i] <= timeQuantum) {
                        currentTime += remainingTime[i];
                        completionTime[i] = currentTime;
                        turnaroundTime[i] = completionTime[i] - arrivalTime[i];
                        waitingTime[i] = turnaroundTime[i] - burstTime[i];
                        isCompleted[i] = true;
                        completedProcesses++;
                        remainingTime[i] = 0;
                    } else {
                        currentTime += timeQuantum;
                        remainingTime[i] -= timeQuantum;
                    }
                }
            }

            if (isProcessCompleted)
                currentTime++;
        }

        double avgTurnaroundTime = 0;
        double avgWaitingTime = 0;
        for (int i = 0; i < n; i++) {
            avgTurnaroundTime += turnaroundTime[i];
            avgWaitingTime += waitingTime[i];
        }
        avgTurnaroundTime /= n;
        avgWaitingTime /= n;

        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println((i + 1) + "\t\t" + arrivalTime[i] + "\t\t" + burstTime[i] + "\t\t" +
                    completionTime[i] + "\t\t" + turnaroundTime[i] + "\t\t" + waitingTime[i]);
        }
        System.out.println("\nAverage Turnaround Time: " + avgTurnaroundTime);
        System.out.println("Average Waiting Time: " + avgWaitingTime);
    }
}