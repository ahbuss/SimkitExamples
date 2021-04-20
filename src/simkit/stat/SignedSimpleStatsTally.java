package simkit.stat;

import static java.lang.Double.max;
import static java.lang.Double.min;
import simkit.stat.SignedSimpleStatsTimeVarying.Sign;
import static simkit.stat.SignedSimpleStatsTimeVarying.Sign.POSITIVE;

/**
 *
 * @author ahbuss
 */
public class SignedSimpleStatsTally extends SimpleStatsTally {

    private Sign sign;
    
    public SignedSimpleStatsTally(String name) {
        this(name, POSITIVE);
    }
    
    public SignedSimpleStatsTally(String name, Sign sign) {
        super(name);
        setSign(sign);
    }
    
    @Override
    public void newObservation(double x) {
        switch(sign) {
            case POSITIVE:
                super.newObservation(max(x, 0.0));
                break;
            case NEGATIVE:
                super.newObservation(min(x, 0.0));
        }
    }

    /**
     * @return the sign
     */
    public Sign getSign() {
        return sign;
    }

    /**
     * @param sign the sign to set
     */
    public final void setSign(Sign sign) {
        this.sign = sign;
    }

}
