package br.com.cotiinformatica.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CriarUsuarioRequestDto {

	@Size(min = 8, max = 100, message = "Informe o nome do usuário de 8 a 100 caracteres.")
	@NotBlank(message = "O campo nome do usuário é obrigatório.")
	private String nome;
	
	@Email(message = "Informe um endereço de email válido.")
	@NotBlank(message = "O campo email do usuário é obrigatório.")
	private String email;
	
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
			message = "Informe a senha com letras maíusculas, minúsculas, números, caracteres especiais e pelo menos 8 caracteres.")
	@NotBlank(message = "O campo senha do usuário é obrigatório.")
	private String senha;
}
