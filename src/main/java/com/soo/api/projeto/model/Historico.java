package com.soo.api.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "Historico")
public class Historico {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
	
	@Column(name="valor_movimentado")
    private Double valorMovimentado;
    
    @Column(name="data_movimento")
    private Date dataMovimento;
    
    @Column(name="tipo")
    private String tipo;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getValorMovimentado() {
		return valorMovimentado;
	}

	public void setValorMovimentado(Double valorMovimentado) {
		this.valorMovimentado = valorMovimentado;
	}

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	@Column(name="id_conta")
    private Long idConta;
}
