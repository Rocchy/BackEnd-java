package modelo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private List<Producto> productos;
    private List<Integer> cantidades;
    private double total;

    public Pedido(int id) {
        this.id = id;
        this.productos = new ArrayList<>();
        this.cantidades = new ArrayList<>();
        this.total = 0;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        productos.add(producto);
        cantidades.add(cantidad);
        total += producto.getPrecio() * cantidad;
    }

    public int getId() {
        return id;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public List<Integer> getCantidades() {
        return cantidades;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido ID: ").append(id).append("\n");
        for (int i = 0; i < productos.size(); i++) {
            sb.append("- ").append(productos.get(i).getNombre())
              .append(" x").append(cantidades.get(i))
              .append(" = $").append(productos.get(i).getPrecio() * cantidades.get(i))
              .append("\n");
        }
        sb.append("Total: $").append(total);
        return sb.toString();
    }
}