package com.smartclock;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

@WebServlet("")
public class SmartClockServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Horario> horariosTrabalho = new ArrayList<>();
        List<Horario> marcacoesFeitas = new ArrayList<>();

        req.setAttribute("horariosTrabalho", horariosTrabalho);
        req.setAttribute("marcacoesFeitas", marcacoesFeitas);
        req.setAttribute("atrasos", subtracao(horariosTrabalho, marcacoesFeitas));
        req.setAttribute("horasExtra", subtracao(marcacoesFeitas, horariosTrabalho));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Horario> horariosTrabalho = parseHorarios("horariosTrabalho", req);
        List<Horario> marcacoesFeitas = parseHorarios("marcacoesFeitas", req);

        validaHorariosTrabalho(horariosTrabalho);
        validateHorarios(marcacoesFeitas);

        req.setAttribute("horariosTrabalho", horariosTrabalho);
        req.setAttribute("marcacoesFeitas", marcacoesFeitas);
        req.setAttribute("atrasos", subtracao(horariosTrabalho, marcacoesFeitas));
        req.setAttribute("horasExtra", subtracao(marcacoesFeitas, horariosTrabalho));
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    public List<Horario> subtracao(List<Horario> tabela1, List<Horario> tabela2) {
        List<Horario> result = new ArrayList<>();
        List<Horario> orderedTabela1 = new ArrayList<>(tabela1);
        List<Horario> orderedTabela2 = new ArrayList<>(tabela2);
        Comparator<Horario> horarioComparator = Comparator.comparing(Horario::getPrimeiroHorario);
        Horario aux = new Horario();
        boolean found;

        orderedTabela1.sort(horarioComparator);
        orderedTabela2.sort(horarioComparator);

        for (Horario registro1 : tabela1) {
            found = false;

            for (int i = 0; i < tabela2.size(); i++) {
                Horario registro2 = tabela2.get(i);
                if (TimeUtil.isBetween(registro2.getPrimeiroHorario(), registro1)) {
                    found = true;
                    if (aux.getPrimeiroHorario() == null) {
                        aux.setPrimeiroHorario(registro1.getPrimeiroHorario());
                    }

                    aux.setSegundoHorario(registro2.getPrimeiroHorario());
                    result.add(aux.copy());
                    aux.clear();
                } else if (aux.getPrimeiroHorario() != null) {
                    aux.setSegundoHorario(registro1.getSegundoHorario());
                    result.add(aux.copy());
                    aux.clear();
                }

                if (TimeUtil.isBetween(registro2.getSegundoHorario(), registro1)) {
                    found = true;
                    aux.setPrimeiroHorario(registro2.getSegundoHorario());

                    if (i == tabela2.size() - 1) {
                        aux.setSegundoHorario(registro1.getSegundoHorario());
                        result.add(aux.copy());
                        aux.clear();
                    }
                }

                if (registro1.getPrimeiroHorario() == registro2.getPrimeiroHorario() || registro1.getSegundoHorario() == registro2.getSegundoHorario()) {
                    found = true;
                }

                if (TimeUtil.isBetween(registro1.getPrimeiroHorario(), registro2) && TimeUtil.isBetween(registro1.getSegundoHorario(), registro2)) {
                    found = true;
                }
            }

            if (!found) {
                result.add(registro1.copy());
            }
        }

        return result;
    }

    private List<Horario> parseHorarios(String tabela, HttpServletRequest req) {
        List<Horario> horarios = new ArrayList<>();
        Map<String, String[]> map = req.getParameterMap();
        List<String> primeirosHorarios = Arrays.asList(map.getOrDefault(tabela + ".primeiroHorario", new String[]{}));
        List<String> segundosHorarios = Arrays.asList(map.getOrDefault(tabela + ".segundoHorario", new String[]{}));

        if (primeirosHorarios.size() != segundosHorarios.size()) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < primeirosHorarios.size(); i++) {
            horarios.add(new Horario(LocalTime.parse(primeirosHorarios.get(i)), LocalTime.parse(segundosHorarios.get(i))));
        }

        return horarios;
    }

    private void validaHorariosTrabalho(List<Horario> horarios) {
        if (horarios.size() > 3) {
            throw new IllegalArgumentException();
        }

        validateHorarios(horarios);
    }

    private void validateHorarios(List<Horario> horarios) {
        for (int i = 0; i < horarios.size() - 1; i++) {
            if (horarios.get(i).getPrimeiroHorario().equals(horarios.get(i).getSegundoHorario())) {
                throw new IllegalArgumentException();
            }

            for (int j = i + 1; j < horarios.size(); j++) {
                if (
                        TimeUtil.isBetween(horarios.get(i).getPrimeiroHorario(), horarios.get(j)) ||
                        TimeUtil.isBetween(horarios.get(i).getPrimeiroHorario(), horarios.get(j)) ||
                        horarios.get(i).getPrimeiroHorario().equals(horarios.get(j).getPrimeiroHorario()) ||
                        horarios.get(i).getPrimeiroHorario().equals(horarios.get(j).getSegundoHorario()) ||
                        horarios.get(i).getSegundoHorario().equals(horarios.get(j).getPrimeiroHorario()) ||
                        horarios.get(i).getSegundoHorario().equals(horarios.get(j).getSegundoHorario())
                ) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }
}
