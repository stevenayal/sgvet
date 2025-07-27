package com.sgvet.rrhh.control;

import com.sgvet.rrhh.boundary.RRHHRepository;
import com.sgvet.rrhh.entity.RRHH;

import java.util.List;

public class RRHHController {

    private RRHHRepository rrhhRepository = new RRHHRepository();
    // Aquí puedes agregar métodos para manejar las solicitudes relacionadas con los RRHHs
    // Por ejemplo, crear, actualizar, eliminar y listar RRHHs

    public Boolean crearRRHH(RRHH RRHH) {
        // Lógica para crear un RRHH
        try{

            //rrhhRepository.insertar(RRHH);
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<RRHH> listarRRHHes() {
        return rrhhRepository.listarTodos();
    }

    public void eliminarRRHH(int id) {
//        rrhhRepository.eliminarPorId(id);
    }

    public RRHH buscarRRHH(int id) {
        //return rrhhRepository.buscarPorId(id);
        return null;
    }
}
