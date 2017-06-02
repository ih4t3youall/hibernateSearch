package edu.curso.java.hibernate.bo;

import java.util.*;

import javax.persistence.*;

import org.hibernate.search.annotations.*;




@Entity
//para decirle que va con fulltextsearch
@Indexed
public class Cliente {
	
	private Long id;
//	le digo que lo tome en cuenta tokenized es que busque palabras
//      store no significa que no guarde el dato(valor) de la tabla al lado del indice, basicamente evita que que vaya a la base de datos
//      un tokenized busca por palabra completa
	@Field(index=Index.TOKENIZED, store=Store.NO) 
	private String nombre;
//	index embebed indexa relaciones, y si le pongo store.yes me guarda el padre y el hijo
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String apellido;
	
	private Date fechaAlta;
	
	private Double saldo;
	
	private boolean esClienteVip;
	
	private String email;
	
	@Field(index=Index.TOKENIZED, store=Store.NO)
	private String cuit;

	public String getCuit() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit = cuit;
	}

	private Direccion direccion;
	
	private Set<Pedido> pedidos = new HashSet<Pedido>();
	
	@ManyToOne
	@JoinColumn(name="idDireccion")
	public Direccion getDireccion() {
		return direccion;
	}
	
	@OneToMany
	@JoinColumn(name="idCliente")
	public Set<Pedido> getPedidos() {
		return pedidos;
	}
	public void setPedidos(Set<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public Double getSaldo() {
		return saldo;
	}
	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	public boolean isEsClienteVip() {
		return esClienteVip;
	}
	public void setEsClienteVip(boolean esClienteVip) {
		this.esClienteVip = esClienteVip;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
