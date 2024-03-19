package br.com.cotiinformatica;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cotiinformatica.dtos.AutenticarUsuarioRequestDto;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutenticarUsuarioTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@Order(1)
	public void autenticarUsuarioComSucessoTest() throws Exception {
		
		AutenticarUsuarioRequestDto dto = new AutenticarUsuarioRequestDto();
		dto.setEmail("admin@cotiinformatica.com.br");
		dto.setSenha("@Teste123");
		
		MvcResult result = mockMvc.perform(post("/api/usuarios/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isOk())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertNotNull(content);
	}
	
	@Test
	@Order(2)
	public void acessoNegadoTest() throws Exception {
		
		AutenticarUsuarioRequestDto dto = new AutenticarUsuarioRequestDto();
		dto.setEmail("admin@cotiinformatica.com.br");
		dto.setSenha("@Teste999"); // Senha errada
		
		MvcResult result = mockMvc.perform(post("/api/usuarios/autenticar")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
				.andExpect(status().isUnauthorized())
				.andReturn();
		
		String content = result.getResponse().getContentAsString();
		assertTrue(content.contains("Acesso negado, usuário não encontrado."));
	}
}
