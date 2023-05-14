package com.smartclock;

import java.time.LocalTime;

public abstract class TimeUtil {
    public static boolean isBetween(LocalTime time, Horario horario) {
        if (horario.getSegundoHorario().isBefore(horario.getPrimeiroHorario())) {
            if (time.isAfter(horario.getPrimeiroHorario()) && time.isBefore(LocalTime.MAX)) {
                return true;
            }

            return time.isBefore(horario.getSegundoHorario());
        }

        return time.isAfter(horario.getPrimeiroHorario()) && time.isBefore(horario.getSegundoHorario());
    }
}
