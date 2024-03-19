package br.com.cotiinformatica.controllers;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.components.JwtTokenComponent;
import br.com.cotiinformatica.dtos.AutenticarUsuarioRequestDto;
import br.com.cotiinformatica.dtos.CriarUsuarioRequestDto;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.helpers.CryptoHelper;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuariosController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private JwtTokenComponent jwtTokenComponent;

	@PostMapping("criar")
	public ResponseEntity<String> criar(@RequestBody @Valid CriarUsuarioRequestDto dto) {
		
		try {
			
			if(usuarioRepository.findByEmail(dto.getEmail()) != null) {
				//HTTP 422 (UNPROCESSABLE ENTITY)
				return ResponseEntity.status(422).body("O email informado já está cadastrado. Tente outro.");
			}
			
			Usuario usuario = new Usuario();
			
			usuario.setId(UUID.randomUUID());
			usuario.setNome(dto.getNome());
			usuario.setEmail(dto.getEmail());
			usuario.setSenha(CryptoHelper.createSHA256(dto.getSenha()));
			usuario.setDataHoraCadastro(new Date());
			
			usuarioRepository.save(usuario);
			
			return ResponseEntity.status(201).body("Usuário cadastrado com sucesso.");
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
	
	@PostMapping("autenticar")
	public ResponseEntity<String> autenticar(@RequestBody @Valid AutenticarUsuarioRequestDto dto) {
		
		try {
			
			Usuario usuario = usuarioRepository.findByEmailAndSenha
					(dto.getEmail(), CryptoHelper.createSHA256(dto.getSenha()));
			
			if(usuario != null) {
				// Gerando o TOKEN JWT
				String accessToken = jwtTokenComponent.generateToken(usuario.getEmail());
				
				return ResponseEntity.status(200).body(accessToken);
			}
			else {
				//HTTP 401 (UNAUTHORIZED)
				return ResponseEntity.status(401).body("Acesso negado, usuário não encontrado.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(500).body(e.getMessage());
		}
	}
}
