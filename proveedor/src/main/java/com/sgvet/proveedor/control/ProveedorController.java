package com.sgvet.proveedor.control;

import com.sgvet.proveedor.boundary.ProveedorRepository;
import com.sgvet.proveedor.entity.Proveedor;

import java.util.List;

public class ProveedorController {

    private ProveedorRepository proveedorRepository = new ProveedorRepository();
    // Aquí puedes agregar métodos para manejar las solicitudes relacionadas con los Proveedors
    // Por ejemplo, crear, actualizar, eliminar y listar Proveedors

    public Boolean crearProveedor(Proveedor Proveedor) {
        // Lógica para crear un Proveedor
        try{

            proveedorRepository.insertar(Proveedor);
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Proveedor> listarProveedores() {
        // Lógica para listar todos los Proveedors
        return proveedorRepository.listarTodos();
    }
    
}
