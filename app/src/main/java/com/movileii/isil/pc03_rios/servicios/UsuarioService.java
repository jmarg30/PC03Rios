package com.movileii.isil.pc03_rios.servicios;

import com.movileii.isil.pc03_rios.entidades.Usuario;


public interface UsuarioService {
    public Usuario ingresar(String dni, String password);
}
