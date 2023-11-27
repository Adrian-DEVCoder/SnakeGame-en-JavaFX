package org.example;

import java.util.Objects;

public class RegistroPuntuacion implements Comparable<RegistroPuntuacion> {
    private String nombreUsuario;
    private int puntuacion;

    public RegistroPuntuacion(String nombreUsuario, int puntuacion) {
        this.nombreUsuario = nombreUsuario;
        this.puntuacion = puntuacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    @Override
    public int compareTo(RegistroPuntuacion otro) {
        return Integer.compare(this.puntuacion, otro.puntuacion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegistroPuntuacion that = (RegistroPuntuacion) o;
        return puntuacion == that.puntuacion && Objects.equals(nombreUsuario, that.nombreUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombreUsuario, puntuacion);
    }
}
