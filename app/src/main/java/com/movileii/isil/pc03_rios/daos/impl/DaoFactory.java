package com.movileii.isil.pc03_rios.daos.impl;

import com.movileii.isil.pc03_rios.daos.UsuarioDao;

import static com.movileii.isil.pc03_rios.util.Util.FILE;
import static com.movileii.isil.pc03_rios.util.Util.REST;
import static com.movileii.isil.pc03_rios.util.Util.SQLITE;


public class DaoFactory {
    private static final DaoFactory ourInstance = new DaoFactory();

    public static DaoFactory getInstance() {
        return ourInstance;
    }

    private DaoFactory() {
    }

    public UsuarioDao getUsuarioDao(int tipo){
        switch (tipo){
            case FILE: return new UsuarioDaoImplFile();
            case SQLITE: return new UsuarioDaoImplSQlite();
            case REST: return new UsuarioDaoImplRest();
            default: return null;
        }
    }
}
