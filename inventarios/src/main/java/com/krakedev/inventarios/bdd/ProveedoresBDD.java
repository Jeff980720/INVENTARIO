package com.krakedev.inventarios.bdd;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.krakedev.inventarios.entidades.Categoria;
import com.krakedev.inventarios.entidades.DetalleEntrega;
import com.krakedev.inventarios.entidades.DetallePedido;
import com.krakedev.inventarios.entidades.Entrega;
import com.krakedev.inventarios.entidades.Pedido;
import com.krakedev.inventarios.entidades.Productos;
import com.krakedev.inventarios.entidades.Proveedor;
import com.krakedev.inventarios.entidades.TipoDocumento;
import com.krakedev.inventarios.entidades.UnidadesMedida;
import com.krakedev.inventarios.excepciones.KrakedevException;
import com.krakedev.inventarios.utils.ConexionBDD;

public class ProveedoresBDD {
	public ArrayList<Proveedor> buscar(String subcadena) throws KrakedevException {
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Proveedor proveedor = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement(
					"select prov.identificador,prov.tipo_documento,td.descripcion,prov.nombre,prov.telefono,prov.correo,prov.direccion "
							+ "from proveedores prov, tipo_documentos td " + "where prov.tipo_documento=td.codigo "
							+ "and upper (nombre) like ?");
			ps.setString(1, "%" + subcadena.toUpperCase() + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				String identificador = rs.getString("identificador");
				String tipoDocumento = rs.getString("tipo_documento");
				String descripciontipodoc = rs.getString("descripcion");
				String nombre = rs.getString("nombre");
				String telefono = rs.getString("telefono");
				String correo = rs.getString("correo");
				String direccion = rs.getString("direccion");
				TipoDocumento td = new TipoDocumento(tipoDocumento, descripciontipodoc);

				proveedor = new Proveedor(identificador, td, nombre, telefono, correo, direccion);
				proveedores.add(proveedor);
			}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("error al consultar. Detalle: " + e.getMessage());
		}

		return proveedores;
	}

	public ArrayList<TipoDocumento> recuperar() throws KrakedevException {
		ArrayList<TipoDocumento> documento = new ArrayList<TipoDocumento>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		TipoDocumento documentos = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement("select codigo,descripcion from tipo_documentos");
			rs = ps.executeQuery();
			while (rs.next()) {
				String codigo = rs.getString("codigo");
				String descripcion = rs.getString("descripcion");

				documentos = new TipoDocumento(codigo, descripcion);
				documento.add(documentos);
			}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("error al consultar. Detalle: " + e.getMessage());
		}

		return documento;
	}

	public void insertar(Proveedor prov) throws KrakedevException {
		Connection con = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			PreparedStatement ps = con.prepareStatement(
					"insert into proveedores(identificador,tipo_documento,nombre,telefono,correo,direccion) values (?,?,?,?,?,?)");
			ps.setString(1, prov.getIdentificador());
			ps.setString(2, prov.getTipoDocumento().getCodigo());
			ps.setString(3, prov.getNombre());
			ps.setString(4, prov.getTeledfono());
			ps.setString(5, prov.getCorreo());
			ps.setString(6, prov.getDireccion());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar cliente. Detalle: " + e.getMessage());
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<Productos> buscarProductos(String subcadena) throws KrakedevException {
		ArrayList<Productos> productos = new ArrayList<Productos>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Productos product = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement(
					"select prod.codigo_prod,prod.nombre,udm.codigo as nombre_udm,udm.descripcion as descripcion_udm,cast(prod.precio_venta as decimal(6,2)),prod.tien_iva,cast(prod.coste as decimal(5,4)),cat.codigo_cat as categoria,cat.nombre as nombre_categoria,prod.stock  "
							+ "from productos prod, unidades_medida udm, categorias cat "
							+ "where prod.udm = udm.codigo " + "and prod.categoria = cat.codigo_cat "
							+ "and upper (prod.nombre) like ?");
			ps.setString(1, "%" + subcadena.toUpperCase() + "%");
			rs = ps.executeQuery();
			while (rs.next()) {
				int codProd = rs.getInt("codigo_prod");
				String nombreProducto = rs.getString("nombre");
				String nombreUDM = rs.getString("nombre_udm");
				String descripcionUDM = rs.getString("descripcion_udm");
				BigDecimal precioVenta = rs.getBigDecimal("precio_venta");
				boolean tieneIVA = rs.getBoolean("tien_iva");
				BigDecimal coste = rs.getBigDecimal("coste");
				int codigoCategoria = rs.getInt("categoria");
				String nombreCategoria = rs.getString("nombre_categoria");
				int stock = rs.getInt("stock");

				UnidadesMedida udm = new UnidadesMedida();
				udm.setCodigo(nombreUDM);
				udm.setDescripcion(descripcionUDM);

				Categoria categoria = new Categoria();
				categoria.setCodigo(codigoCategoria);
				categoria.setNombre(nombreCategoria);

				product = new Productos();
				product.setCodigo(codProd);
				product.setNombre(nombreProducto);
				product.setUnidadMedida(udm);
				product.setPrecioVenta(precioVenta);
				product.setTieneIva(tieneIVA);
				product.setCoste(coste);
				product.setCategoria(categoria);
				product.setStock(stock);

				productos.add(product);
			}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("error al consultar. Detalle: " + e.getMessage());
		}

		return productos;
	}

	public void crearProducto(Productos producto) throws KrakedevException {
		Connection con = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			PreparedStatement ps = con
					.prepareStatement("insert into productos(nombre,udm,precio_venta,tien_iva,coste,categoria,stock) "
							+ "values (?,?,?,?,?,?,?)");
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadMedida().getCodigo());
			ps.setBigDecimal(3, producto.getPrecioVenta());
			ps.setBoolean(4, producto.isTieneIva());
			;
			ps.setBigDecimal(5, producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar producto. Detalle: " + e.getMessage());
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void modificarProducto(Productos producto) throws KrakedevException {
		Connection con = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			PreparedStatement ps = con.prepareStatement(
					"update productos set nombre=?,udm=?,precio_venta=?,tien_iva=?,coste=?,categoria=?,stock=?\r\n"
							+ "where codigo_prod=?");
			ps.setString(1, producto.getNombre());
			ps.setString(2, producto.getUnidadMedida().getCodigo());
			ps.setBigDecimal(3, producto.getPrecioVenta());
			ps.setBoolean(4, producto.isTieneIva());
			ps.setBigDecimal(5, producto.getCoste());
			ps.setInt(6, producto.getCategoria().getCodigo());
			ps.setInt(7, producto.getStock());
			ps.setInt(8, producto.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al modificar producto. Detalle: " + e.getMessage());
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void insertarPedido(Pedido pedido) throws KrakedevException {
		Connection con = null;
		ResultSet rsClave = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		int codigoCabecera = 0;

		Date fechaActual = new Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());

		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement("insert into cabecera_pedido(proveedor,fecha,estatus) " + "values (?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pedido.getProveedor().getIdentificador());
			ps.setDate(2, fechaSQL);
			ps.setString(3, "S");

			ps.executeUpdate();

			rsClave = ps.getGeneratedKeys();
			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}

			ArrayList<DetallePedido> detallesPedido = pedido.getDetalles();
			DetallePedido det;
			for (int i = 0; i < detallesPedido.size(); i++) {
				det = detallesPedido.get(i);
				psDet = con.prepareStatement(
						"insert into detalle_pedido(cabecera_pedio,producto,cantidad_solicitada,subtotal,cantidad_recivida) "
								+ "values (?,?,?,?,?)");
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getProducto().getCodigo());
				psDet.setInt(3, det.getCantidadSolicitada());
				BigDecimal pc = det.getProducto().getCoste();
				BigDecimal cantidad = new BigDecimal(det.getCantidadSolicitada());
				BigDecimal subtotal = pc.multiply(cantidad);
				psDet.setBigDecimal(4, subtotal);
				psDet.setInt(5, 0);
				psDet.executeUpdate();

			}

			System.out.println("CODIGO GENERADO>>>>>" + codigoCabecera);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar pedido. Detalle: " + e.getMessage());
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void modificarPedido(Pedido pedido) throws KrakedevException {
		Connection con = null;
//		ResultSet rsClave = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		PreparedStatement psHistStock = null;
//		int codigoCabecera = 0;

		Date fechaActual = new Date();
		Timestamp fechaActualHora = new Timestamp(fechaActual.getTime());

		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement("update cabecera_pedido set estatus=? where numero=?");
			ps.setString(1, "R");
			ps.setInt(2, pedido.getCodigo());

			ps.executeUpdate();

//			rsClave = ps.getGeneratedKeys();
//			if (rsClave.next()) {
//				codigoCabecera = rsClave.getInt(1);
//			}

			ArrayList<DetallePedido> detallesPedido = pedido.getDetalles();
			DetallePedido det;
			for (int i = 0; i < detallesPedido.size(); i++) {
				det = detallesPedido.get(i);
				psDet = con.prepareStatement(
						"update detalle_pedido set cantidad_recivida=?, subtotal=? " + "where codigo=?");
				psDet.setInt(1, det.getCantidadRecibida());
				BigDecimal pc = det.getProducto().getCoste();
				BigDecimal cantidadRec = new BigDecimal(det.getCantidadRecibida());
				BigDecimal subtotal = pc.multiply(cantidadRec);
				psDet.setBigDecimal(2, subtotal);
				psDet.setInt(3, det.getCodigo());

				psDet.executeUpdate();

				psHistStock = con.prepareStatement(
						"insert into historial_stock(fecha,referencia,producto,cantidad)" + "values (?,?,?,?)");
				psHistStock.setTimestamp(1, fechaActualHora);
				psHistStock.setString(2, "Pedido" + pedido.getCodigo());
				psHistStock.setInt(3, det.getProducto().getCodigo());
				psHistStock.setInt(4, det.getCantidadRecibida());

				System.out.println("Kardex editado");
				psHistStock.executeUpdate();
			}

//			System.out.println("CODIGO GENERADO>>>>>" + codigoCabecera);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al modificar pedido. Detalle: " + e.getMessage());
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void insertarEntrega(Entrega entrega) throws KrakedevException {
		Connection con = null;
		ResultSet rsClave = null;
		PreparedStatement ps = null;
		PreparedStatement psDet = null;
		PreparedStatement psHistStock = null;
		int codigoCabecera = 0;

		Date fechaActual = new Date();
		java.sql.Date fechaSQL = new java.sql.Date(fechaActual.getTime());
		Date fechaActualhist = new Date();
		Timestamp fechaActualHora = new Timestamp(fechaActualhist.getTime());

		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement(
					"insert into cabecera_ventas(fecha,total_sin_iva,iva,total) " + "values (?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1, fechaSQL);
			BigDecimal subtotal = new BigDecimal(entrega.getTotalSinIVA());
			ps.setBigDecimal(2, subtotal);
			BigDecimal iva = new BigDecimal(entrega.getIva());
			ps.setBigDecimal(3, iva);
			BigDecimal total = new BigDecimal(entrega.getTotal());
			ps.setBigDecimal(4, total);

			ps.executeUpdate();

			rsClave = ps.getGeneratedKeys();
			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}
			System.out.println("CODIGO GENERADO CEBECERA>>>>>" + codigoCabecera);
			ArrayList<DetalleEntrega> detallesEntrega = entrega.getDetalles();
			DetalleEntrega det;
			for (int i = 0; i < detallesEntrega.size(); i++) {
				det = detallesEntrega.get(i);
				psDet = con.prepareStatement(
						"insert into ventas(cabecera_venta,producto,cantidad,precio_venta,subtotal,subtotal_con_iva) "
								+ "values (?,?,?,?,?,?)");
				psDet.setInt(1, codigoCabecera);
				psDet.setInt(2, det.getCodProd().getCodigo());
				psDet.setInt(3, det.getCantidad());
				psDet.setBigDecimal(4, det.getCodProd().getPrecioVenta());
				if (det.getCodProd().isTieneIva() == true) {
					double porcenateIva = 1.12;
					BigDecimal porcenateIva1 = new BigDecimal(porcenateIva);
					BigDecimal pv1 = det.getCodProd().getPrecioVenta();
					BigDecimal cantidad1 = new BigDecimal(det.getCantidad());
					BigDecimal subtotalsinIva = pv1.multiply(cantidad1);

					BigDecimal subtotal1 = subtotalsinIva.add(subtotalsinIva);

					BigDecimal pv2 = det.getCodProd().getPrecioVenta();
					BigDecimal cantidad2 = new BigDecimal(det.getCantidad());
					BigDecimal subtotalconIVA = pv2.multiply(cantidad2);
					BigDecimal subtotalconIva = subtotalconIVA.multiply(porcenateIva1);

					BigDecimal subtotalconIva1 = subtotalsinIva.add(subtotalconIva);

					BigDecimal totalsoloiva = subtotalconIva.subtract(subtotalsinIva);

					psDet.setBigDecimal(5, subtotalsinIva);
					psDet.setBigDecimal(6, subtotalconIva);

					ps = con.prepareStatement(
							"update cabecera_ventas set total_sin_iva=?,iva=?,total=? where codigo=?");
					ps.setBigDecimal(1, subtotal1);
					ps.setBigDecimal(2, totalsoloiva);
					ps.setBigDecimal(3, subtotalconIva1);
					ps.setInt(4, codigoCabecera);

				} else {
					BigDecimal pv1 = det.getCodProd().getPrecioVenta();
					BigDecimal cantidad1 = new BigDecimal(det.getCantidad());
					BigDecimal subtotalsinIva = pv1.multiply(cantidad1);

					BigDecimal subtotal2 = subtotalsinIva.add(subtotalsinIva);

					BigDecimal pv2 = det.getCodProd().getPrecioVenta();
					BigDecimal cantidad2 = new BigDecimal(det.getCantidad());
					BigDecimal subtotalconIva = pv2.multiply(cantidad2);

					BigDecimal subtotalconIva2 = subtotalsinIva.add(subtotalconIva);

					BigDecimal totalsoloiva1 = subtotalconIva.subtract(subtotalsinIva);

					psDet.setBigDecimal(5, subtotalsinIva);
					psDet.setBigDecimal(6, subtotalconIva);

					ps = con.prepareStatement(
							"update cabecera_ventas set total_sin_iva=?,iva=?,total=? where codigo=?");
					ps.setBigDecimal(1, subtotal2);
					ps.setBigDecimal(2, totalsoloiva1);
					ps.setBigDecimal(3, subtotalconIva2);
					ps.setInt(4, codigoCabecera);
				}

				psDet.executeUpdate();

				ps.executeUpdate();

				psHistStock = con.prepareStatement(
						"insert into historial_stock(fecha,referencia,producto,cantidad)" + "values (?,?,?,?)");
				psHistStock.setTimestamp(1, fechaActualHora);
				psHistStock.setString(2, "Venta" + codigoCabecera);
				psHistStock.setInt(3, det.getCodProd().getCodigo());
				psHistStock.setInt(4, det.getCantidad() * -1);

				System.out.println("Kardex editado");
				psHistStock.executeUpdate();

				System.out.println("CODIGO GENERADO>>>>>" + codigoCabecera);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar entrega. Detalle: " + e.getMessage());
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void crearCategoria(Categoria categoria) throws KrakedevException {
		Connection con = null;
		ResultSet rsClave = null;
		int codigoCabecera = 0;
		try {
			con = ConexionBDD.obtenerConeccion();
			PreparedStatement ps = con.prepareStatement("insert into categorias (nombre) " + "values (?);",
					Statement.RETURN_GENERATED_KEYS);
			rsClave = ps.getGeneratedKeys();
			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}
			System.out.println("CODIGO GENERADO categoria>>>>>" + codigoCabecera);
			ps.setString(1, categoria.getNombre());
//			ps.setInt(2, categoria.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar categoria. Detalle: " + e.getMessage());
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void actualizarCategoria(Categoria categoria) throws KrakedevException {
		Connection con = null;
		ResultSet rsClave = null;
		int codigoCabecera = 0;
		try {
			con = ConexionBDD.obtenerConeccion();
			PreparedStatement ps = con.prepareStatement(
					"update categorias set nombre=?,categoria_padre=? where codigo_cat=?",
					Statement.RETURN_GENERATED_KEYS);
			rsClave = ps.getGeneratedKeys();
			if (rsClave.next()) {
				codigoCabecera = rsClave.getInt(1);
			}
			System.out.println("CODIGO GENERADO categoria>>>>>" + codigoCabecera);
			ps.setString(1, categoria.getNombre());
			ps.setInt(2, categoria.getCategoriaPadre().getCodigo());
			ps.setInt(3, categoria.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("Error al insertar categoria. Detalle: " + e.getMessage());
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public ArrayList<Categoria> recuperarCategoria() throws KrakedevException {
		ArrayList<Categoria> categoria = new ArrayList<Categoria>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Categoria categorias = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement("select codigo_cat, nombre from categorias ");
			rs = ps.executeQuery();
			while (rs.next()) {
				Categoria cat = new Categoria();
				
				int codigo = rs.getInt("codigo_cat");
				String nombre = rs.getString("nombre");
				
				categorias = new Categoria(codigo, nombre);
				categoria.add(categorias);
			}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("error al consultar. Detalle: " + e.getMessage());
		}

		return categoria;
	}
	
	public ArrayList<DetallePedido> buscarPedidoPorProveedor(String proveedor) throws KrakedevException {
		ArrayList<DetallePedido> pedidos = new ArrayList<DetallePedido>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DetallePedido detallePedid = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement(
					"select dp.codigo, dp.cabecera_pedio, cp.proveedor,dp.producto,dp.cantidad_solicitada,cast(dp.subtotal as decimal(10,3)), dp.cantidad_recivida  "
					+ "from detalle_pedido dp, cabecera_pedido cp, proveedores prov "
					+ "where dp.cabecera_pedio=cp.numero "
					+ "and cp.proveedor=prov.identificador "
					+ "and  prov.identificador like ?");
			ps.setString(1, proveedor);
			rs = ps.executeQuery();
			while (rs.next()) {
				int codigo = rs.getInt("codigo");
				int cabecera = rs.getInt("cabecera_pedio");
				String proveedores = rs.getString("proveedor");
				int  codigoProducto = rs.getInt("producto");
				int  cantidad = rs.getInt("cantidad_solicitada");
				BigDecimal bubtotal = rs.getBigDecimal("subtotal");
				int cantidadRecibida = rs.getInt("cantidad_recivida");
				Productos buscarProveedor=new Productos(codigoProducto);
				Proveedor buscarProv=new Proveedor(proveedores);
				Pedido buscProv = new Pedido(cabecera,buscarProv);

				detallePedid = new DetallePedido(codigo,buscProv,buscarProveedor,cantidad,bubtotal,cantidadRecibida);
				pedidos.add(detallePedid);
			}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("error al consultar. Detalle: " + e.getMessage());
		}

		return pedidos;
	}
	
	public ArrayList<Proveedor> buscarProvPorIdentificador(String identificador) throws KrakedevException {
		ArrayList<Proveedor> proveedores = new ArrayList<Proveedor>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Proveedor proveedor = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement(
					"select prov.identificador,prov.tipo_documento,td.descripcion,prov.nombre,prov.telefono,prov.correo,prov.direccion from proveedores prov, tipo_documentos td "
					+ "where prov.tipo_documento=td.codigo "
					+ "and identificador like ?");
			ps.setString(1,identificador);
			rs = ps.executeQuery();
			while (rs.next()) {
				String codigo = rs.getString("identificador");
				String tipoDocumento = rs.getString("tipo_documento");
				String descripciontipodoc = rs.getString("descripcion");
				String nombre = rs.getString("nombre");
				String telefono = rs.getString("telefono");
				String correo = rs.getString("correo");
				String direccion = rs.getString("direccion");
				TipoDocumento td = new TipoDocumento(tipoDocumento, descripciontipodoc);

				proveedor = new Proveedor(codigo, td, nombre, telefono, correo, direccion);
				proveedores.add(proveedor);
			}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("error al consultar. Detalle: " + e.getMessage());
		}

		return proveedores;
	}
	
	public ArrayList<Productos> buscarProductoPorIdentificador(int subcadena) throws KrakedevException {
		ArrayList<Productos> productos = new ArrayList<Productos>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Productos product = null;
		try {
			con = ConexionBDD.obtenerConeccion();
			ps = con.prepareStatement(
					"select prod.codigo_prod,prod.nombre,udm.codigo as nombre_udm,udm.descripcion as descripcion_udm,cast(prod.precio_venta as decimal(6,2)),prod.tien_iva,cast(prod.coste as decimal(5,4)),cat.codigo_cat as categoria,cat.nombre as nombre_categoria,prod.stock  "
							+ "from productos prod, unidades_medida udm, categorias cat "
							+ "where prod.udm = udm.codigo " + "and prod.categoria = cat.codigo_cat "
							+ "and prod.codigo_prod = ?");
			ps.setInt(1, subcadena);
			rs = ps.executeQuery();
			while (rs.next()) {
				int codProd = rs.getInt("codigo_prod");
				String nombreProducto = rs.getString("nombre");
				String nombreUDM = rs.getString("nombre_udm");
				String descripcionUDM = rs.getString("descripcion_udm");
				BigDecimal precioVenta = rs.getBigDecimal("precio_venta");
				boolean tieneIVA = rs.getBoolean("tien_iva");
				BigDecimal coste = rs.getBigDecimal("coste");
				int codigoCategoria = rs.getInt("categoria");
				String nombreCategoria = rs.getString("nombre_categoria");
				int stock = rs.getInt("stock");

				UnidadesMedida udm = new UnidadesMedida();
				udm.setCodigo(nombreUDM);
				udm.setDescripcion(descripcionUDM);

				Categoria categoria = new Categoria();
				categoria.setCodigo(codigoCategoria);
				categoria.setNombre(nombreCategoria);

				product = new Productos();
				product.setCodigo(codProd);
				product.setNombre(nombreProducto);
				product.setUnidadMedida(udm);
				product.setPrecioVenta(precioVenta);
				product.setTieneIva(tieneIVA);
				product.setCoste(coste);
				product.setCategoria(categoria);
				product.setStock(stock);

				productos.add(product);
			}
		} catch (KrakedevException e) {
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new KrakedevException("error al consultar. Detalle: " + e.getMessage());
		}

		return productos;
	}
}
