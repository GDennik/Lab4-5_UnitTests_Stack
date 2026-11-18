package su.dng.stack;

import org.junit.Test;

import static org.junit.Assert.*;

public class StackTest {

    Stack stack = new Stack();

    @Test
    public void push() {
        stack.push("foo");
        assertEquals(1, stack.size());

        stack.push("bar");
        assertEquals(2, stack.size());

        stack.push(null);
        assertEquals(3, stack.size());
    }

    @Test
    public void pop() {
        stack.push("first");
        stack.push("second");

        assertEquals("second", stack.pop());
        assertEquals(1, stack.size());

        assertEquals("first", stack.pop());
        assertEquals(0, stack.size());
    }

    @Test(expected = IllegalStateException.class)
    public void popEmpty() {
        stack.pop();
    }

    @Test
    public void sizeEmpty() {
        assertEquals(0, stack.size());
    }

    @Test
    public void size() {
        stack.push("foo");
        assertEquals(1, stack.size());
    }

    @Test
    public void memoryLeak() {

        Runtime.getRuntime().gc();
        long usedMemB = getUsedMem();

        byte[] arr = new byte[1024 * 1024 * 100];
        Runtime.getRuntime().gc();
        long usedMemA = getUsedMem();

        arr = null;
        Runtime.getRuntime().gc();
        long usedMemAgc = getUsedMem();

        printMemB(usedMemB, usedMemA, usedMemAgc);
        System.out.println();
        Runtime.getRuntime().gc();

        long usedMemSB = getUsedMem();

        byte[] sarr = new byte[1024 * 1024 * 100];
        stack.push(sarr);
        Runtime.getRuntime().gc();
        long usedMemSA = getUsedMem();

        stack.pop();
        sarr = null;
        Runtime.getRuntime().gc();
        long usedMemSAgc = getUsedMem();

        printMemA(usedMemSB, usedMemSA, usedMemSAgc);
        Runtime.getRuntime().gc();

        boolean memoryLeakDetected = false;
        if (usedMemSA - 2 * 1024 * 1024 < usedMemSAgc && usedMemSAgc < usedMemSA + 2 * 1024 * 1024) {
            memoryLeakDetected = true;
        }
        if (memoryLeakDetected) {
            fail("memory leak is detected, stack memory control is incorrect");
        }
    }

    private static void printMemB(long usedMemB, long usedMemA, long usedMemAgc) {
        System.out.println("Not Stack mem:");
        System.out.print("Before - ");
        System.out.println(usedMemB / 1024 / 1024);
        System.out.print("After use - ");
        System.out.println(usedMemA / 1024 / 1024);
        System.out.print("After unuse - ");
        System.out.println(usedMemAgc / 1024 / 1024);
    }

    private static void printMemA(long usedMemSB, long usedMemSA, long usedMemSAgc) {
        System.out.println("Stack mem:");
        System.out.print("Before - ");
        System.out.println(usedMemSB / 1024 / 1024);
        System.out.print("After use - ");
        System.out.println(usedMemSA / 1024 / 1024);
        System.out.print("After unuse - ");
        System.out.println(usedMemSAgc / 1024 / 1024);
    }

    private static long getUsedMem() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

}