import java.util.Arrays;
import java.util.Scanner;

public class BankersAlgorithm {
    private int numberOfProcesses;
    private int numberOfResources;
    private int[][] max;
    private int[][] allocation;
    private int[][] need;
    private int[] available;
    private boolean[] isSafe;

    public void initialize() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nBanker's Algorithm");
        System.out.print("\nEnter the number of processes: ");
        numberOfProcesses = scanner.nextInt();

        System.out.print("Enter the number of resources: ");
        numberOfResources = scanner.nextInt();

        max = new int[numberOfProcesses][numberOfResources];
        allocation = new int[numberOfProcesses][numberOfResources];
        need = new int[numberOfProcesses][numberOfResources];
        available = new int[numberOfResources];
        isSafe = new boolean[numberOfProcesses];

        System.out.println("Enter the maximum allocation matrix:");
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfResources; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter the allocation matrix:");
        for (int i = 0; i < numberOfProcesses; i++) {
            for (int j = 0; j < numberOfResources; j++) {
                allocation[i][j] = scanner.nextInt();
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }

        System.out.println("Enter the available resources:");
        for (int i = 0; i < numberOfResources; i++) {
            available[i] = scanner.nextInt();
        }

        scanner.close();
    }

    public void runBankersAlgorithm() {
        int[] work = Arrays.copyOf(available, numberOfResources);
        boolean[] finish = new boolean[numberOfProcesses];

        for (int i = 0; i < numberOfProcesses; i++) {
            if (!finish[i] && hasEnoughResources(i, work)) {
                allocateResources(i, work);
                finish[i] = true;
                isSafe[i] = true;
                i = -1; // Restart the loop
            }
        }

        if (isSystemSafe()) {
            System.out.println("The system is in a safe state.");
        } else {
            System.out.println("The system is in an unsafe state.");
        }
    }

    private boolean hasEnoughResources(int processIndex, int[] work) {
        for (int i = 0; i < numberOfResources; i++) {
            if (need[processIndex][i] > work[i]) {
                return false;
            }
        }
        return true;
    }

    private void allocateResources(int processIndex, int[] work) {
        for (int i = 0; i < numberOfResources; i++) {
            work[i] += allocation[processIndex][i];
            allocation[processIndex][i] = 0;
            need[processIndex][i] = 0;
        }
    }

    private boolean isSystemSafe() {
        for (boolean value : isSafe) {
            if (!value) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        BankersAlgorithm bankersAlgorithm = new BankersAlgorithm();
        bankersAlgorithm.initialize();
        bankersAlgorithm.runBankersAlgorithm();
    }
}
