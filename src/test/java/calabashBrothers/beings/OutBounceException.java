package calabashBrothers.beings;

import calabashBrothers.GUI.Coordinate;

/**
 * @ Author     ：Young
 * @ Date       ：Created in 14:24 2018/12/30
 * @ Description：越界异常
 */
class OutBounceException extends Exception {
    private Coordinate outPos;
    public OutBounceException() {
        super();
    }
    public OutBounceException(String msg,Coordinate outPos) {
        super(msg);
        this.outPos = outPos;
    }
    public Coordinate getOutPos() {
        return this.outPos;
    }


}
