package simkit.random;

import java.util.Arrays;
import simkit.BasicEventList;
import simkit.BasicSimEntity;
import simkit.ReRunnable;
import simkit.SimEvent;

/**
 *
 * @author ahbuss
 */
public class ResettingTraceVariate extends TraceVariate {

    private RandomVariate resetterVariate;

    private int numberOfTrace;

    private Resetter resetter;

    public ResettingTraceVariate() {
        this.resetter = new Resetter();
    }

    @Override
    public void setParameters(Object... params) {
        if (params.length < 3) {
            throw new IllegalArgumentException("ResettingTraceVariate needs 3 or 4 parameters: "
                    + params.length);
        }
        super.setParameters(Arrays.copyOf(params, 2));
        switch (params.length) {
            case 4:
                if (params[2] instanceof RandomVariate) {
                    setResetterVariate((RandomVariate) params[2]);
                } else {
                    throw new IllegalArgumentException("Not a RandomVariate: "
                            + params[2].getClass().getName());
                }
                if (params[3] instanceof Integer) {
                    setNumberOfTrace((Integer) params[3]);
                } else {
                    throw new IllegalArgumentException("Not an Integer: "
                            + params[3].getClass().getName());
                }
                break;
            case 3:
                if (params[1] instanceof RandomVariate) {
                    setResetterVariate((RandomVariate) params[1]);
                } else {
                    throw new IllegalArgumentException("Not a RandomVariate: "
                            + params[1].getClass().getName());
                }
                if (params[2] instanceof Integer) {
                    setNumberOfTrace((Integer) params[2]);
                } else {
                    throw new IllegalArgumentException("Not an Integer: "
                            + params[2].getClass().getName());
                }
                break;

            default:
        }

    }

    /**
     * @return the resetterVariate
     */
    public RandomVariate getResetterVariate() {
        return resetterVariate;
    }

    /**
     * @param resetterVariate the resetterVariate to set
     */
    public void setResetterVariate(RandomVariate resetterVariate) {
        this.resetterVariate = resetterVariate;
    }

    /**
     * @return the numberOfTrace
     */
    public int getNumberOfTrace() {
        return numberOfTrace;
    }

    /**
     * @param numberOfTrace the numberOfTrace to set
     */
    public void setNumberOfTrace(int numberOfTrace) {
        if (numberOfTrace <= 0) {
            throw new IllegalArgumentException("numberOfTrace must be > 0: " + numberOfTrace);
        }
        this.numberOfTrace = numberOfTrace;

    }

    public void setEventList(BasicEventList eventList) {
        resetter.setEventList(eventList);
        eventList.addRerun(resetter);
    }

    @Override
    public String toString() {
        return String.format("%s %s %,d values",
                "ResettingTrace", resetterVariate, numberOfTrace);
    }

    public class Resetter extends BasicSimEntity implements ReRunnable {

        @Override
        public void reset() {
            ResettingTraceVariate.this.getRandomNumber().resetSeed();
            double[] values = new double[numberOfTrace];
            for (int i = 0; i < values.length; ++i) {
                values[i] = resetterVariate.generate();
            }
            setTraceValues(values);
        }

        @Override
        public void handleSimEvent(SimEvent event) {
        }

        @Override
        public void processSimEvent(SimEvent event) {
        }

    }

}
