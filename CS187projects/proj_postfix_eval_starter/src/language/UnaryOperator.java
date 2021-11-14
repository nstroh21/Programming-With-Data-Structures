package language;

public abstract class UnaryOperator<T> implements Operator<T>{

    protected Operand<T> op0;

    /** {@inheritDoc} */
    public int getNumberOfArguments() {
        return 1;
    }

    /** {@inheritDoc} */
    @Override
    public void setOperand(int i, Operand<T> operand) {
        if (i > 0) {
            throw new IllegalArgumentException("Unary Operator only accepts operand 0 but received" + i + ".");
        }
        else if (op0 != null) {
            throw new IllegalStateException("Operand" + i + "was previously set.");
        }
        else if (operand == null){
            throw new NullPointerException("Could not set null operand.");
        }
        else{ 
            op0 = operand;
        }
    }
}
