import { StatusBar } from 'expo-status-bar';
import { Alert, Button, FlatList, ScrollView, StyleSheet, Text, TextInput, View } from 'react-native';
import { useState } from 'react';

let productos = [
  { nombre: 'Samsung', categoria: '(Gama Alta)', precioCompra: '400', precioVenta: '550', id: 'C001' },
  { nombre: 'Apple', categoria: '(Gama Alta)', precioCompra: '700', precioVenta: '1200', id: 'C002' },
  { nombre: 'Xiaomi', categoria: '(Gama Media)', precioCompra: '450', precioVenta: '500', id: 'C003' },
  { nombre: 'Oppo', categoria: '(Gama Baja)', precioCompra: '300', precioVenta: '320', id: 'C004' },
  { nombre: 'Motorola', categoria: '(Gama Media)', precioCompra: '300', precioVenta: '350', id: 'C005' },
  { nombre: 'Huawei', categoria: '(Gama Alta)', precioCompra: '480', precioVenta: '600', id: 'C006' },
  { nombre: 'Realme', categoria: '(Gama Baja)', precioCompra: '330', precioVenta: '400', id: 'C007' },
]

let esProductoNuevo = true;
let indiceProducto = -1;

export default function App() {
  const [txtNombre, setNombre] = useState('');
  const [txtCategoria, setCategoria] = useState('');
  const [txtPreciocompra, setPreciocompra] = useState('');
  const [txtPrecioventa, setPrecioventa] = useState('');
  const [txtid, setId] = useState('');
  const [numElementos, setElementos] = useState(productos.length);

  let ItemProductos = (props) => {
    return <View style={styles.contenedorItem} >
      <View style={styles.itemCodigo}>
        <Text>{props.product.id}</Text>
      </View>
      <View style={styles.itemProduct}>
        <Text>{props.product.nombre} {props.product.categoria}</Text>
        <Text>{props.product.precioVenta}</Text>
      </View>
      <View style={styles.itemBotones}>
        <Button
          title=' E '
          color='#7cfc00'
          onPress={() => {
            setNombre(props.product.nombre);
            setCategoria(props.product.categoria);
            setId(props.product.id);
            setPreciocompra(props.product.precioCompra);
            setPrecioventa(props.product.precioVenta);
            esProductoNuevo = false;
            indiceProducto = props.indicep
          }}
        />
        <Button
          title=' X '
          color='#ff0000'
          onPress={() => {
            indiceProducto = props.indicep
            productos.splice(indiceProducto, 1)
            setElementos(productos.length),
            Alert.alert('Info','Registro eliminado exitosamente')
          }}
        />
      </View>
    </View>
  }

  let existeProducto = () => {
    for (let i = 0; i < productos.length; i++) {
      if (productos[i].id == txtid) {
        return true
      }
    }
    return false
  }

  let limpiar = () => {
    setCategoria('');
    setId('');
    setNombre('');
    setPreciocompra('');
    setPrecioventa('');
    esProductoNuevo = true;
  }

  let agregarProduct = () => {
    if (txtid.trim() === '' || txtNombre.trim() === '' || txtCategoria.trim() === '' || txtPreciocompra.trim() === '') {
      Alert.alert('INFO','debe ingresar todos los campos')
    } else {
      if (esProductoNuevo) {
        if (existeProducto()) {
          Alert.alert('INFO', 'ya existe un producto con el id ' + txtid)
        } else {
          let producto = { nombre: txtNombre, categoria: txtCategoria, precioCompra: txtPreciocompra, precioVenta: txtPrecioventa, id: txtid }
          productos.push(producto);
          Alert.alert('Info','Registro guardado exitosamente')
          limpiar();
          setElementos(productos.length);
        }
      } else {
        productos[indiceProducto].nombre = txtNombre;
        productos[indiceProducto].categoria = txtCategoria;
        productos[indiceProducto].precioVenta = txtPrecioventa;
        Alert.alert('Info','Registro editado exitosamente')
        limpiar();
        setElementos(productos.length);
      }
    }
  }

  let calcularPVP = () => {
    let result = parseFloat(txtPreciocompra) + (parseFloat(txtPreciocompra) * 20 / 100);
    console.log('Resultado :' + result)
    setPrecioventa(result.toFixed(2).toString());
  }
  return (
    <View style={styles.container}>
      <View style={styles.encabezado}>
        <Text style={styles.titulosMayuscula}>PRODUCTOS TELEFONICOS</Text>
      </View>
      <View style={styles.formulario}>
        <ScrollView>
          <TextInput
            style={styles.txt}
            value={txtid}
            placeholder='CODIGO DEL PRODUCTO'
            onChangeText={setId}
            editable={esProductoNuevo}
            maxLength={4}
          />
          <TextInput
            style={styles.txt}
            value={txtNombre}
            placeholder='NOMBRE DEL PRODUCTO'
            onChangeText={setNombre}
          />
          <TextInput
            style={styles.txt}
            value={txtCategoria}
            placeholder='CATEGORIA DEL PRODUCTO'
            onChangeText={setCategoria}
          />
          <TextInput
            style={styles.txt}
            value={txtPreciocompra}
            placeholder='PRECIO DE COMPRA DEL PRODUCTO'
            onChangeText={(num) => {
              setPreciocompra(num)
              calcularPVP()
            }}
            keyboardType='numeric'
          />
          <TextInput
            style={styles.txt}
            value={txtPrecioventa}
            placeholder='PRECIO DE VENTA DEL PRODUCTO'
            onChangeText={setPrecioventa}
            onPress={calcularPVP}
            editable={false}
          />
          <View style={styles.areaBotones}>
            <Button
              title='AGREGAR'
              onPress={() => {
                agregarProduct()
              }}
            />
            <Button
              title='NUEVO'
              onPress={() => {
                limpiar();
              }}
            />
            <Text>Lista Productos: {numElementos}</Text>
          </View>
        </ScrollView>
      </View>
      <View style={styles.contenidoCuerpo}>
        <FlatList style={styles.lista}
          data={productos}
          renderItem={(element) => {
            return <ItemProductos indicep={element.index} product={element.item} />
          }}
        />
      </View>
      <View style={styles.contenidoPie}>
        <Text>Autor: Jefferson Toaquiza</Text>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    // backgroundColor: '#f0f8ff',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'stretc',
    paddingTop: 50,
  },
  encabezado: {
    // backgroundColor: 'blue',
    flexDirection: 'column',
    justifyContent: 'flex-start',
    alignItems: 'center',
    margin: 1
  },
  formulario: {
    flex: 3,
    // backgroundColor: 'yellow',
    flexDirection: 'column',
    justifyContent: 'space-around',
    alignItems: 'stretch',
    paddingHorizontal: 1,
    padding: 10,
    margin: 10
  },
  lista: {
    flex: 5,
    // backgroundColor: '#7fffd4',
    // padding: 10
  },
  itemProduct: {
    flex: 5,
    backgroundColor: 'beige',
    marginBottom: 10,
    padding: 15,
    borderColor: '#7fff00',
    // borderWidth: 2,
    // borderRadius: 10,
    // flex:5,
  },
  titulosMayuscula: {
    fontSize: 16,
    marginBottom: 10,
    fontWeight: "bold"
  },
  contenidoCuerpo: {
    flex: 2,
    // backgroundColor: 'red',
    flexDirection: 'column',

  },
  contenidoPie: {
    flex: 0.3,
    // backgroundColor: '#800080',
    justifyContent: 'center',
    alignItems: 'flex-end',
    margin: 1
  },
  txt: {
    borderWidth: 1,
    borderColor: '#708090',
    borderRadius: 30,
    margin: 15
  },
  areaBotones: {
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: "center",
    margin: 1
  },
  itemCodigo: {
    backgroundColor: 'beige',
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    margin: 0.1,
    marginBottom: 10,
    padding: 1,
    // borderColor: '#7fff00',
    // borderWidth: 2,
    // borderRadius: 10,
  },
  contenedorItem: {
    // backgroundColor:'black',
    flex: 1,
    flexDirection: 'row',
    margin: 5,
    justifyContent: 'space-around',
    alignItems: 'stretch'
  },
  itemBotones: {
    backgroundColor: 'beige',
    flex: 2,
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
    margin: 0.1,
    marginBottom: 10,
    padding: 1,
  }

});
