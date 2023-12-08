package com.mkandeel.retrofit_rxjava_.Helper;

public enum Direction {
    up,
    down,
    left,
    right;

    public static Direction fromAngle(double angle) {
        if (inRange(angle, 45, 135)) {
            return Direction.up;
        } else if (inRange(angle, 0, 45) || inRange(angle, 315, 360)) {
            return Direction.right;
        } else if (inRange(angle, 225, 315)) {
            return Direction.down;
        } else {
            return Direction.left;
        }

    }

    /**
     * @param angle an angle
     * @param init  the initial bound
     * @param end   the final bound
     * @return returns true if the given angle is in the interval [init, end).
     */
    private static boolean inRange(double angle, float init, float end) {
        return (angle >= init) && (angle < end);
    }
}
