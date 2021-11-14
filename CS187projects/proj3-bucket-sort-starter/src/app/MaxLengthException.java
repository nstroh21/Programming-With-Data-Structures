package app;
public class MaxLengthException extends Exception {
    public MaxLengthException() {
        super("Input integer exceeds max amount of places allowed");
    } 
}
