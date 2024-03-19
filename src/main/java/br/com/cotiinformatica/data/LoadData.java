package br.com.cotiinformatica.data;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.helpers.CryptoHelper;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@Component
public class LoadData implements ApplicationRunner {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Usuario usuario = new Usuario();
		
		usuario.setId(UUID.randomUUID());
		usuario.setNome("Usuário Administrador");
		usuario.setEmail("admin@cotiinformatica.com.br");
		usuario.setSenha(CryptoHelper.createSHA256("@Teste123"));
		usuario.setDataHoraCadastro(new Date());
		
		// Verificando se o usuário não existe no banco de dados
		if(usuarioRepository.findByEmail(usuario.getEmail()) == null) {
			// Salvando o usuário no banco de dados
			usuarioRepository.save(usuario);
		}
	}

	
}
