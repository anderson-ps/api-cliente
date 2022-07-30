package br.com.apicliente.service;

import br.com.apicliente.model.Cliente;
import br.com.apicliente.model.ClienteRepository;
import br.com.apicliente.model.Endereco;
import br.com.apicliente.model.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {
        String cep = cliente.getEndereco().getCep();
        Endereco novoEndereco = viaCepService.consultarCep(cep);
        cliente.setEndereco(novoEndereco);
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if (clienteBd.isPresent()) {
            inserir(cliente);
        }
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }
}
