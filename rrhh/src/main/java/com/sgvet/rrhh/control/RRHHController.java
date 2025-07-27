package com.sgvet.rrhh.control;

import com.sgvet.rrhh.boundary.RRHHRepository;
import com.sgvet.rrhh.entity.RRHH;

import java.util.List;

public class RRHHController {

    private RRHHRepository rrhhRepository = new RRHHRepository();

    public Boolean crearRRHH(RRHH RRHH) {
        try {
            // rrhhRepository.insertar(RRHH);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RRHH> listarRRHHes() {
        return rrhhRepository.listarTodos();
    }

    public void eliminarRRHH(int id) {
        // rrhhRepository.eliminarPorId(id);
    }

    public RRHH buscarRRHH(int id) {
        List<RRHH> lista = rrhhRepository.listarTodos();
        for (RRHH rrhh : lista) {
            if (rrhh.getId() == id) {
                return rrhh;
            }
        }
        return null; // Si no se encuentra
    }


    public boolean solicitarVacaciones(int id, String fechaInicio, String fechaFin) {
        RRHH rrhh = buscarRRHH(id);
        if (rrhh != null) {
            System.out.println("Solicitud de vacaciones:");
            System.out.println("RRHH ID: " + id);
            System.out.println("Desde: " + fechaInicio + " Hasta: " + fechaFin);
            // Aquí podrías guardar en base de datos o lista en memoria
            return true;
        }
        return false;
    }

    public boolean solicitarPermiso(int id, String motivo, String fecha) {
        RRHH rrhh = buscarRRHH(id);
        if (rrhh != null) {
            System.out.println("Solicitud de permiso:");
            System.out.println("RRHH ID: " + id);
            System.out.println("Motivo: " + motivo);
            System.out.println("Fecha: " + fecha);
            // Aquí podrías guardar en base de datos o lista en memoria
            return true;
        }
        return false;
    }
}
