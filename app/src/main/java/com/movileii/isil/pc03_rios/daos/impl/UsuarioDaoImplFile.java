package com.movileii.isil.pc03_rios.daos.impl;

import android.content.res.AssetManager;


import com.movileii.isil.pc03_rios.daos.UsuarioDao;
import com.movileii.isil.pc03_rios.entidades.Usuario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class UsuarioDaoImplFile implements UsuarioDao {
    private AssetManager manager;
    @Override
    public Usuario validar(String d, String p) {
        String linea;
        try{
            InputStream entrada = manager.open("usuarios.txt");
            InputStreamReader lectura = new InputStreamReader(entrada);
            BufferedReader contenido = new BufferedReader(lectura);
            while((linea = contenido.readLine())!=null){
                StringTokenizer tokens = new StringTokenizer(linea,",");
                String dni = tokens.nextToken();
                String pas = tokens.nextToken();
                if(d.equals(dni) && p.equals(pas)){
                    Usuario user = new Usuario();
                    user.setDni(dni);
                    user.setPassword(pas);
                    user.setNombre(tokens.nextToken());
                    return user;
                }
            }
        }catch (IOException ex){
        }
        return null;
    }


    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}
