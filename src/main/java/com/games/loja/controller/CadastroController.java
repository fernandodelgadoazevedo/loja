package com.games.loja.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.games.loja.model.Cadastro;
import com.games.loja.repository.CadastroRepository;

@RestController
@RequestMapping("/cadastros")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CadastroController {

	@Autowired
	private CadastroRepository cadastroRepository;

	@GetMapping
	public ResponseEntity<List<Cadastro>> getAll() {
		return ResponseEntity.ok(cadastroRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cadastro> getById(@PathVariable Long id) {
		return cadastroRepository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Cadastro>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(cadastroRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@PostMapping
	public ResponseEntity<Cadastro> post(@Valid @RequestBody Cadastro cadastro) {
		return ResponseEntity.status(HttpStatus.CREATED).body(cadastroRepository.save(cadastro));
	}

	@PutMapping
	public ResponseEntity<Cadastro> put(@Valid @RequestBody Cadastro cadastro) {
		return cadastroRepository.findById(cadastro.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(cadastroRepository.save(cadastro)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Cadastro> cadastro = cadastroRepository.findById(id);

		if (cadastro.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		cadastroRepository.deleteById(id);
	}
}