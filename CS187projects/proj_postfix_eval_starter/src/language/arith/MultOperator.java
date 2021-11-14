package language.arith;

import language.BinaryOperator;
import language.Operand;

/**
 * The {@code MultOperator} is an operator that performs multiplication on two integers.
 */
public class MultOperator extends BinaryOperator<Integer> {

  /** {@inheritDoc} */
  @Override
  public Operand<Integer> performOperation() {
    if (op0 == null || op1 == null) {
      throw new IllegalStateException("Both operands must be set prior to performing operation.");
    }
    else{
      Integer mult = op0.getValue() * op1.getValue();
      return new Operand<Integer>(mult); 
    }
  }
  
  public String toString() {
    return "*";
  }
}
