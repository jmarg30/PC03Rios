package com.movileii.isil.pc03_rios.servicios.impl;

import android.content.res.AssetManager;

import com.movileii.isil.pc03_rios.daos.UsuarioDao;
import com.movileii.isil.pc03_rios.daos.impl.DaoFactory;
import com.movileii.isil.pc03_rios.daos.impl.UsuarioDaoImplFile;
import com.movileii.isil.pc03_rios.entidades.Usuario;
import com.movileii.isil.pc03_rios.servicios.UsuarioService;
import com.movileii.isil.pc03_rios.util.Util;

public class UsuarioServiceImpl implements UsuarioService {
    private UsuarioDao dao;
    private AssetManager manager;
    public UsuarioServiceImpl() {
        DaoFactory fabrica = DaoFactory.getInstance();
        dao = fabrica.getUsuarioDao(Util.opc);
    }

    @Override
    public Usuario ingresar(String dni, String password) {
        return dao.validar(dni, password);
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
        if(dao instanceof UsuarioDaoImplFile){
            ((UsuarioDaoImplFile) dao).setManager(manager);
        }
    }
}
