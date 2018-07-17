package source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Item {

	private String nome;
	private Categoria categoria;
	private Map<String, Double> precos = new HashMap<String, Double>();
	private int id;

	public Item(String nome, String categoria, int qnt, String unidadeDeMedida, String localDeCompra, double preco,
			int id) {
		validandoEntradasNome(nome);
		validandoEntradasCategoria(categoria);
		validandoEntradasLocal(localDeCompra);
		validandoEntradasPreco(preco);
		validandoEntradasUnidadeMedida(unidadeDeMedida);
		this.id = id;
		this.nome = nome;
		this.precos.put(localDeCompra, preco);
		this.categoria = new ItemPorQnt(categoria, qnt, unidadeDeMedida);
	}

	public Item(String nome, String categoria, double kg, String localDeCompra, double preco, int id) {
		validandoEntradasNome(nome);
		validandoEntradasCategoria(categoria);
		validandoEntradasLocal(localDeCompra);
		validandoEntradasPreco(preco);
		this.id = id;
		this.nome = nome;
		this.precos.put(localDeCompra, preco);
		this.categoria = new ItemPorQuilo(categoria, kg);
	}

	public Item(String nome, String categoria, int unidade, String localDeCompra, double preco, int id) {
		validandoEntradasNome(nome);
		validandoEntradasCategoria(categoria);
		validandoEntradasLocal(localDeCompra);
		validandoEntradasPreco(preco);
		validandoEntradasValorUnidade(unidade);
		this.id = id;
		this.nome = nome;
		this.precos.put(localDeCompra, preco);
		this.categoria = new ItemPorUnidade(categoria, unidade);
	}

	public int getId() {
		return this.id;
	}

	public void setNome(String novoValor) {
		this.nome = novoValor;
	}

	public void adicionaPrecoItem(String supermercado, double preco) {
		this.precos.put(supermercado, preco);
	}

	public String toString() {
		if (this.categoria instanceof ItemPorQnt) {
			return this.id + ". " + this.nome + ", " + categoria.getCategoria() + ", " + this.categoria.getQuantidade()
					+ " " + this.categoria.getUnidadeDeMedida() + ", Preco: " + getListaPrecos();
		} else if (this.categoria instanceof ItemPorQuilo) {
			return this.id + ". " + this.nome + ", " + categoria.getCategoria() + ", Preco por quilo: "
					+ getListaPrecos();
		}
		return this.id + ". " + this.nome + ", " + categoria.getCategoria() + ", Preco: " + getListaPrecos();
	}

	protected String getListaPrecos() {
		StringBuilder listaDePrecos = new StringBuilder("<");
		for (String localDeCompra : precos.keySet()) {
			listaDePrecos.append(String.format("%s, R$ %.2f;", localDeCompra, precos.get(localDeCompra)));
		}
		return listaDePrecos.toString() + ">";
	}

	public String getCategoria() {
		return categoria.getCategoria();
	}

	public void atualizaItem(String atributo, String novoValor) {
		if ("nome".equals(atributo)) {
			this.nome = novoValor;
		}
		this.categoria.atualizaItem(atributo, novoValor);

	}

	public String getNome() {
		return this.nome;
	}

	public double getPreco() {
		List<Double> listPrecos = new ArrayList<>(precos.values());

		return Collections.min(listPrecos);

	}

	/////////////////////////////////////////////////////// METODOSPRIVADOS///////////////////////////////////////////////////////

	private void validandoEntradasUnidadeMedida(String unidadeDeMedida) {
		if (unidadeDeMedida.equals(null) || unidadeDeMedida.trim().equals("")) {
			throw new NullPointerException("Erro no cadastro de item: unidade de medida nao pode ser vazia ou nula.");
		}

	}

	private void validandoEntradasPreco(double preco) {
		if (preco <= 0) {
			throw new IllegalArgumentException("Erro no cadastro de item: preco de item invalido.");
		}

	}

	private void validandoEntradasLocal(String localDeCompra) {
		if (localDeCompra.equals(null) || localDeCompra.trim().equals("")) {
			throw new NullPointerException("Erro no cadastro de item: local de compra nao pode ser vazio ou nulo.");
		}

	}

	private void validandoEntradasCategoria(String categoria) {
		if (categoria.equals(null) || categoria.trim().equals("")) {
			throw new NullPointerException("Erro no cadastro de item: categoria nao pode ser vazia ou nula.");
		}

		if (!categoria.equals("limpeza") && !categoria.equals("alimento industrializado")
				&& !categoria.equals("alimento nao industrializado") && !categoria.equals("higiene pessoal")) {
			throw new IllegalArgumentException("Erro no cadastro de item: categoria nao existe.");
		}

	}

	private void validandoEntradasNome(String nome) {
		if (nome.equals(null) || nome.trim().equals("")) {
			throw new NullPointerException("Erro no cadastro de item: nome nao pode ser vazio ou nulo.");
		}
	}

	private void validandoEntradasValorUnidade(int unidade) {
		if (unidade < 0) {
			throw new IllegalArgumentException(
					"Erro no cadastro de item: valor de unidade nao pode ser menor que zero.");
		}

	}

}