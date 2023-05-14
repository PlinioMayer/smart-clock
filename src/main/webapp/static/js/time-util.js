class TimeUtil {
    static between(time, timeRange) {
        if (this.compare(timeRange[0], timeRange[1]) > 0) {
            if (time >= timeRange[0] && time <= '00:00') {
                return true;
            }

            if (time <= timeRange[1]) {
                return true;
            }

            return false;
        }

        if (time >= timeRange[0] && time <= timeRange[1]) {
            return true;
        }

        return false;
    }

    static compare (time1, time2) {
        if (time1 > time2) {
            return 1;
        }

        if (time1 === time2) {
            return 0;
        }

        if (time1 < time2) {
            return -1;
        }
    }
}