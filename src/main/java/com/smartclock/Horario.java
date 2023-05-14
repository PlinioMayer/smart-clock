package com.smartclock;

import java.time.LocalTime;

public class Horario {
    private LocalTime primeiroHorario;
    private LocalTime segundoHorario;

    public Horario() {
    }

    public Horario(String primeiroHorario, String segundoHorario) {
        this.primeiroHorario = LocalTime.parse(primeiroHorario);
        this.segundoHorario = LocalTime.parse(segundoHorario);
    }

    public Horario(LocalTime primeiroHorario, LocalTime saida) {
        this.primeiroHorario = primeiroHorario;
        this.segundoHorario = saida;
    }

    public Horario copy() {
        return new Horario(primeiroHorario, segundoHorario);
    }

    public void clear() {
        primeiroHorario = null;
        segundoHorario = null;
    }

    public LocalTime getPrimeiroHorario() {
        return primeiroHorario;
    }

    public void setPrimeiroHorario(LocalTime primeiroHorario) {
        this.primeiroHorario = primeiroHorario;
    }

    public LocalTime getSegundoHorario() {
        return segundoHorario;
    }

    public void setSegundoHorario(LocalTime segundoHorario) {
        this.segundoHorario = segundoHorario;
    }

    @Override
    public int hashCode() {
        return this.primeiroHorario.hashCode() - this.segundoHorario.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Horario)) {
            return false;
        }

        if (obj == this) {
            return true;
        }

        Horario other = (Horario) obj;

        return this.primeiroHorario.equals(other.primeiroHorario) && this.segundoHorario.equals(other.segundoHorario);
    }
}
