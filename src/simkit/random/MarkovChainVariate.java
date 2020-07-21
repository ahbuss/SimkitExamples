package simkit.random;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

/**
 *
 * @author ahbuss
 */
public class MarkovChainVariate extends RandomVariateBase implements DiscreteRandomVariate {

    private static final Logger LOGGER = Logger.getLogger(MarkovChainVariate.class.getName());

    private double[][] transitionMatrix;

    private final Map<Integer, DiscreteRandomVariate> transitions;

    private int state;

    public MarkovChainVariate() {
        transitions = new TreeMap<>();
    }

    @Override
    public double generate() {
        return (double) generateInt();
    }

    @Override
    public void setParameters(Object... params) {
        String message = "";
        switch (params.length) {
            case 1:
                state = 0;
                if (params[0] instanceof double[][]) {
                    setTransitionMatrix((double[][]) params[0]);
                } else {
                    message += ("params[0] must be double[][]: " + params[0].getClass().getSimpleName());
                }
                break;
            case 2:
                if (params[0] instanceof double[][]) {
                    setTransitionMatrix((double[][]) params[0]);
                } else {
                    message += ("params[0] must be double[][]: " + params[0].getClass().getSimpleName());
                }
                if (params[1] instanceof Integer) {
                    this.setState((int) params[1]);
                } else {
                    message +=(" params[1] if present must be Integer: " + params[1].getClass().getSimpleName());
                }
                break;
            default:
                message += ("params must be length 0 or 2: " + params.length);
                break;
        }
        if (message.length() > 0) {
            LOGGER.severe(message);
            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public Object[] getParameters() {
        Object[] params = null;

        return params;
    }

    @Override
    public int generateInt() {
        DiscreteRandomVariate drv = transitions.get(state);
        this.state = drv.generateInt();
        return state;
    }

    /**
     * @return the transitionMatrix
     */
    public double[][] getTransitionMatrix() {
        return deepCopy(transitionMatrix);
    }

    /**
     * @param transitionMatrix the transitionMatrix to set
     */
    public void setTransitionMatrix(double[][] transitionMatrix) {
        if (!isSquare(transitionMatrix)) {
            String message = "transition matrix muxt be square";
            LOGGER.severe(message);
            throw new IllegalArgumentException(message);
        }
        this.transitionMatrix = deepCopy(transitionMatrix);
        normalize(this.transitionMatrix);
        int[] states = new int[transitionMatrix.length];
        for (int row = 0; row < states.length; ++row) {
            states[row] = row;
        }
        for (int row = 0; row < transitionMatrix.length; ++row) {
            DiscreteIntegerVariate rv
                    = (DiscreteIntegerVariate) RandomVariateFactory.getDiscreteRandomVariateInstance("DiscreteInteger", states.clone(), transitionMatrix[row]);
            transitions.put(row, rv);
        }
    }

    /**
     * @return the row
     */
    public int getState() {
        return state;
    }

    /**
     * @param state the row to set
     */
    public void setState(int state) {
        this.state = state;
    }

    public static double[][] deepCopy(double[][] matrix) {
        double[][] deepCopy = new double[matrix.length][];
        for (int i = 0; i < deepCopy.length; ++i) {
            deepCopy[i] = matrix[i].clone();
        }
        return deepCopy;
    }

    public static void normalize(double[][] matrix) {
        for (int row = 0; row < matrix.length; ++row) {
            double sum = 0.0;
            for (double x : matrix[row]) {
                sum += x;
            }
            for (int col = 0; col < matrix[row].length; ++col) {
                matrix[row][col] /= sum;
            }
        }
    }
    
    public static boolean isSquare(double[][] matrix) {
        boolean square = true;
        for (int row = 0; row < matrix.length; ++row) {
            if (matrix[row].length != matrix.length) {
                square = false;
                break;
            }
        }
        return square;
    }

}
