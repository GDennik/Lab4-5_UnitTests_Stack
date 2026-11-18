package su.dng;

import su.dng.stack.Stack;

public class Main {
    static void main() {
        Stack stack = new Stack();
        IO.println("Stack size = " + stack.size());
        stack.push("Hello world!");
        IO.println("Stack size = " + stack.size());
        Object value = stack.pop();
        IO.println("value = " +value);
        IO.println("Stack size = " + stack.size());
    }
}
