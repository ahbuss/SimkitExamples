package simkit.stat;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import simkit.Schedule;
import simkit.components.ArrivalProcess;
import simkit.random.RandomVariate;
import simkit.random.RandomVariateFactory;

/**
 *
 * @author ahbuss
 */
public class TestSignedSimpleStatsTimeVarying extends ArrivalProcess {

    private RandomVariate gen;

    public TestSignedSimpleStatsTimeVarying(RandomVariate iat, RandomVariate gen) {
        super(iat);
        setGen(gen);
    }

    @Override
    public void doArrival() {
        super.doArrival();
        firePropertyChange("normal", gen.generate());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RandomVariate exp = RandomVariateFactory.getInstance("Exponential", 2.5);
        RandomVariate normal = RandomVariateFactory.getInstance("Normal", 0.0, 1.0);
        TestSignedSimpleStatsTimeVarying test
                = new TestSignedSimpleStatsTimeVarying(exp, normal);
        test.setGen(normal);
        SignedSimpleStatsTimeVarying sstv = new SignedSimpleStatsTimeVarying("normal");
//        sstv.setSign(SignedSimpleStatsTimeVarying.Sign.NEGATIVE);
        test.addPropertyChangeListener(sstv);
        System.out.println(test);
        int number = 100000;

        Schedule.stopOnEvent(number, "Arrival");
        Schedule.reset();
        Schedule.startSimulation();

        System.out.println(sstv);

        SignedSimpleStatsTally ssst = new SignedSimpleStatsTally("", SignedSimpleStatsTimeVarying.Sign.POSITIVE);

        for (int i = 0; i < number; ++i) {
            ssst.newObservation(normal.generate());
        }
        System.out.println(ssst);

        System.out.printf("theoretical mean (?): %,.4f%n", sqrt(0.5 / PI));

        ssst.reset();
        ssst.setSign(SignedSimpleStatsTimeVarying.Sign.NEGATIVE);
        for (int i = 0; i < number; ++i) {
            ssst.newObservation(normal.generate());
        }
        System.out.println(ssst);

    }

    /**
     * @return the gen
     */
    public RandomVariate getGen() {
        return gen;
    }

    /**
     * @param gen the gen to set
     */
    public void setGen(RandomVariate gen) {
        this.gen = gen;
    }

}
