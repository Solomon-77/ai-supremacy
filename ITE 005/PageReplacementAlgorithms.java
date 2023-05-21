import java.util.*;

public class PageReplacementAlgorithms {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nPage Replacement Algorithms");
        System.out.print("\nEnter the number of frames: ");
        int numFrames = scanner.nextInt();

        System.out.print("Enter the number of pages: ");
        int numPages = scanner.nextInt();

        int[] pages = new int[numPages];
        System.out.println("Enter the page sequence: ");
        for (int i = 0; i < numPages; i++) {
            pages[i] = scanner.nextInt();
        }

        System.out.println("\n--- First-In-First-Out (FIFO) ---");
        fifo(pages, numFrames);

        System.out.println("\n--- Optimal Algorithm ---");
        optimal(pages, numFrames);

        System.out.println("\n--- Least Recently Used (LRU) ---");
        lru(pages, numFrames);

        System.out.println("\n--- Most Recently Used (MRU) ---");
        mru(pages, numFrames);

        System.out.println("\n--- Least Frequently Used (LFU) ---");
        lfu(pages, numFrames);

        System.out.println("\n--- Most Frequently Used (MFU) ---");
        mfu(pages, numFrames);

        scanner.close();
    }

    public static void fifo(int[] pages, int numFrames) {
        Queue<Integer> frameQueue = new LinkedList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!frameQueue.contains(page)) {
                if (frameQueue.size() == numFrames) {
                    frameQueue.poll();
                }
                frameQueue.offer(page);
                pageFaults++;
            }
        }

        System.out.println("Page faults: " + pageFaults);
    }

    public static void optimal(int[] pages, int numFrames) {
        List<Integer> frameList = new ArrayList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (!frameList.contains(page)) {
                if (frameList.size() == numFrames) {
                    int farthestIndex = -1;
                    int farthestPage = -1;
                    for (int i = 0; i < frameList.size(); i++) {
                        int currentPage = frameList.get(i);
                        int nextPageIndex = getNextPageIndex(pages, currentPage);
                        if (nextPageIndex == -1) {
                            farthestIndex = i;
                            break;
                        }
                        if (nextPageIndex > farthestIndex) {
                            farthestIndex = nextPageIndex;
                            farthestPage = currentPage;
                        }
                    }
                    if (farthestIndex != -1) {
                        frameList.remove(Integer.valueOf(farthestPage));
                    }
                }
                frameList.add(page);
                pageFaults++;
            }
        }

        System.out.println("Page faults: " + pageFaults);
    }

    public static int getNextPageIndex(int[] pages, int page) {
        for (int i = 0; i < pages.length; i++) {
            if (pages[i] == page) {
                return i;
            }
        }
        return -1;
    }

    public static void lru(int[] pages, int numFrames) {
        List<Integer> frameList = new ArrayList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (frameList.contains(page)) {
                frameList.remove(Integer.valueOf(page));
            } else {
                if (frameList.size() == numFrames) {
                    frameList.remove(0);
                }
                pageFaults++;
            }
            frameList.add(page);
        }

        System.out.println("Page faults: " + pageFaults);
    }

    public static void mru(int[] pages, int numFrames) {
        List<Integer> frameList = new ArrayList<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (frameList.contains(page)) {
                frameList.remove(Integer.valueOf(page));
                frameList.add(page);
            } else {
                if (frameList.size() == numFrames) {
                    frameList.remove(frameList.size() - 1);
                }
                frameList.add(page);
                pageFaults++;
            }
        }

        System.out.println("Page faults: " + pageFaults);
    }

    public static void lfu(int[] pages, int numFrames) {
        Map<Integer, Integer> frameMap = new HashMap<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (frameMap.containsKey(page)) {
                frameMap.put(page, frameMap.get(page) + 1);
            } else {
                if (frameMap.size() == numFrames) {
                    int leastFreqPage = Integer.MAX_VALUE;
                    int leastFreqCount = Integer.MAX_VALUE;
                    for (Map.Entry<Integer, Integer> entry : frameMap.entrySet()) {
                        if (entry.getValue() < leastFreqCount) {
                            leastFreqPage = entry.getKey();
                            leastFreqCount = entry.getValue();
                        }
                    }
                    frameMap.remove(leastFreqPage);
                }
                frameMap.put(page, 1);
                pageFaults++;
            }
        }

        System.out.println("Page faults: " + pageFaults);
    }

    public static void mfu(int[] pages, int numFrames) {
        Map<Integer, Integer> frameMap = new HashMap<>();
        int pageFaults = 0;

        for (int page : pages) {
            if (frameMap.containsKey(page)) {
                frameMap.put(page, frameMap.get(page) + 1);
            } else {
                if (frameMap.size() == numFrames) {
                    int mostFreqPage = Integer.MIN_VALUE;
                    int mostFreqCount = Integer.MIN_VALUE;
                    for (Map.Entry<Integer, Integer> entry : frameMap.entrySet()) {
                        if (entry.getValue() > mostFreqCount) {
                            mostFreqPage = entry.getKey();
                            mostFreqCount = entry.getValue();
                        }
                    }
                    frameMap.remove(mostFreqPage);
                }
                frameMap.put(page, 1);
                pageFaults++;
            }
        }

        System.out.println("Page faults: " + pageFaults);
    }
}