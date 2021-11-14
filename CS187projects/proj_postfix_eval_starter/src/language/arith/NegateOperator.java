package language.arith;

import language.Operand;
import language.UnaryOperator;

/** The {@code NegateOperator} is an operator that performs negation on a single integer */

public class NegateOperator extends UnaryOperator<Integer> {
  /** {@inheritDoc} */
  public Operand<Integer> performOperation() {
    if (op0 == null) {
      throw new IllegalStateException("Operand must be set to perform operation.");
    }
    else{
      int neg = -1 * op0.getValue();
      Operand<Integer> result = new Operand<Integer>(neg);
      return result;
    }
  }
  
  public String toString() {
    return "!";
  }
}
