package solver;

import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import solver.impl.Solver;

public class NewtonSolver implements Solver {

	final double initialData;
	final double eps;
	final GenericFunction function;

	public NewtonSolver(final Real init, final Real eps, final GenericFunction fun) {
		initialData = init.doubleValue();
		this.eps = eps.doubleValue();
		function = fun;
	}

	private Element doStep(final double lastVal) throws Throwable {
		double ans = lastVal
				- (((Real) function.value(Generator.getInstance().getFieldGenerator().getRealLine().get(lastVal)))
						.getDoubleValue()
						/ ((Real) function.getDerivative()
								.value(Generator.getInstance().getFieldGenerator().getRealLine().get(lastVal)))
										.getDoubleValue());
		return Generator.getInstance().getFieldGenerator().getRealLine().get(ans);
	}

	@Override
	public Element solve() throws Throwable {
		double lastVal = initialData;
		double newVal = ((Real) doStep(lastVal)).doubleValue();
		while (Math.abs(newVal - lastVal) > eps) {
			lastVal = newVal;
			newVal = ((Real) doStep(lastVal)).doubleValue();
		}
		return Generator.getInstance().getFieldGenerator().getRealLine().get(newVal);
	}
}
