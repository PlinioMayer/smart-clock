package com.smartclock;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.List;

public class SmartClockServletTest {
    private final SmartClockServlet smartClockServlet = new SmartClockServlet();

    @Test
    public void testSubtracao() {
        List<Horario> horariosTrabalho;
        List<Horario> marcacoesFeitas;
        List<Horario> atrasos;
        List<Horario> horasExtra;

        horariosTrabalho = List.of(
                new Horario("08:00", "12:00")
        );
        marcacoesFeitas = List.of(
                new Horario("07:00", "11:00")
        );
        atrasos = List.of(
                new Horario("11:00", "12:00")
        );
        horasExtra = List.of(
                new Horario("07:00", "08:00")
        );

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        horariosTrabalho = List.of(
                new Horario("08:00", "12:00"),
                new Horario("13:30", "17:30")
        );
        marcacoesFeitas = List.of(
                new Horario("06:00", "20:00")
        );
        atrasos = List.of();
        horasExtra = List.of(
                new Horario("06:00", "08:00"),
                new Horario("12:00", "13:30"),
                new Horario("17:30", "20:00")
        );

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        marcacoesFeitas = List.of(
                new Horario("07:00", "12:30"),
                new Horario("14:00", "17:00")
        );
        atrasos = List.of(
                new Horario("13:30", "14:00"),
                new Horario("17:00", "17:30")
        );
        horasExtra = List.of(
                new Horario("07:00", "08:00"),
                new Horario("12:00", "12:30")
        );

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        marcacoesFeitas = List.of(
                new Horario("07:00", "12:30"),
                new Horario("14:00", "18:00")
        );
        atrasos = List.of(
                new Horario("13:30", "14:00")
        );
        horasExtra = List.of(
                new Horario("07:00", "08:00"),
                new Horario("12:00", "12:30"),
                new Horario("17:30", "18:00")
        );

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        horariosTrabalho = List.of(
                new Horario("22:00", "05:00")
        );
        marcacoesFeitas = List.of(
                new Horario("21:00", "04:00")
        );
        atrasos = List.of(
                new Horario("04:00", "05:00")
        );
        horasExtra = List.of(
                new Horario("21:00", "22:00")
        );

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        horariosTrabalho = List.of(
                new Horario("22:00", "05:00")
        );
        marcacoesFeitas = List.of(
                new Horario("03:00", "07:00")
        );
        atrasos = List.of(
                new Horario("22:00", "03:00")
        );
        horasExtra = List.of(
                new Horario("05:00", "07:00")
        );

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        horariosTrabalho = List.of(
                new Horario("22:00", "05:00"),
                new Horario("12:00", "16:00")
        );
        marcacoesFeitas = List.of(
                new Horario("11:00", "15:00"),
                new Horario("03:00", "07:00")
        );
        atrasos = List.of(
                new Horario("22:00", "03:00"),
                new Horario("15:00", "16:00")
        );
        horasExtra = List.of(
                new Horario("05:00", "07:00"),
                new Horario("11:00", "12:00")
        );

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        horariosTrabalho = List.of(
                new Horario("22:00", "05:00"),
                new Horario("12:00", "16:00")
        );
        marcacoesFeitas = List.of(
                new Horario("03:00", "07:00")
        );
        atrasos = List.of(
                new Horario("22:00", "03:00"),
                new Horario("12:00", "16:00")
        );
        horasExtra = List.of(
                new Horario("05:00", "07:00")
        );

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        horariosTrabalho = List.of(
                new Horario("08:00", "18:00"),
                new Horario("19:00", "22:00")
        );
        marcacoesFeitas = List.of(
                new Horario("08:00", "12:00"),
                new Horario("13:00", "14:00"),
                new Horario("15:00", "16:00"),
                new Horario("17:00", "18:00")
        );
        atrasos = List.of(
                new Horario("12:00", "13:00"),
                new Horario("14:00", "15:00"),
                new Horario("16:00", "17:00"),
                new Horario("19:00", "22:00")
        );
        horasExtra = List.of();

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        horariosTrabalho = List.of(
                new Horario("08:00", "12:00"),
                new Horario("14:00", "18:00")
        );
        marcacoesFeitas = List.of(
                new Horario("08:00", "12:00"),
                new Horario("14:00", "18:00")
        );
        atrasos = List.of();
        horasExtra = List.of();

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));

        horariosTrabalho = List.of(
                new Horario("08:00", "12:00"),
                new Horario("14:00", "18:00")
        );
        marcacoesFeitas = List.of(
                new Horario("08:00", "12:00"),
                new Horario("06:00", "07:30"),
                new Horario("14:00", "18:00"),
                new Horario("18:30", "20:00")
        );
        atrasos = List.of();
        horasExtra = List.of(
                new Horario("18:30", "20:00"),
                new Horario("06:00", "07:30")
        );

        assertThat(atrasos, CoreMatchers.hasItems(smartClockServlet.subtracao(horariosTrabalho, marcacoesFeitas).toArray(new Horario[]{})));
        assertThat(horasExtra, CoreMatchers.hasItems(smartClockServlet.subtracao(marcacoesFeitas, horariosTrabalho).toArray(new Horario[]{})));
    }
}
