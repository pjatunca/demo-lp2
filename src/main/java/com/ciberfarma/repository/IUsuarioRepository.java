package com.ciberfarma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ciberfarma.model.Usuario;
import java.util.List;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{
	// select * from tb_usuarios where usr_usua = ? and cla_usua = ?
	// QueryMethod
	// Ej. select * from tb_usuarios where ape_usua = ? 
	//     Usuario findByApe_usua(String ape_usua);
	Usuario findByCorreoAndClave(String correo, String clave);
}




