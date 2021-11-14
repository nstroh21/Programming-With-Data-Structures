package language.arith;

import language.BinaryOperator;
import language.Operand;

/**
 * The {@code SubOperator} is an operator that performs subtraction on two integers.
 */
public class SubOperator extends BinaryOperator<Integer> {
  /** {@inheritDoc} */
  @Override
 public Operand<Integer> performOperation() {
    if (op0 == null || op1 == null){
      throw new IllegalStateException("Could not perform operation prior to operands being set.");
    }
    Integer value = op0.getValue() - op1.getValue();
    Operand<Integer> result = new Operand<Integer>(value);
    return result;
  }
  
  public String toString() {
    return "-";
  }
}
