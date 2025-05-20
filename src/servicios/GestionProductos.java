package servicios;

import modelo.Producto;
import modelo.Pedido;
import excepciones.StockInsuficienteException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestionProductos {
    private List<Producto> productos;
    private List<Pedido> pedidos;
    private int nextProductoId;
    private int nextPedidoId;
    private Scanner scanner;

    public GestionProductos() {
        this.productos = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.nextProductoId = 1;
        this.nextPedidoId = 1;
        this.scanner = new Scanner(System.in);
    }

    public void agregarProducto() {
        System.out.println("\n--- Agregar Producto ---");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Precio: ");
        double precio = Double.parseDouble(scanner.nextLine());
        
        System.out.print("Stock: ");
        int stock = Integer.parseInt(scanner.nextLine());
        
        Producto producto = new Producto(nextProductoId++, nombre, precio, stock);
        productos.add(producto);
        System.out.println("Producto agregado con éxito!");
    }

    public void listarProductos() {
        System.out.println("\n--- Lista de Productos ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos registrados.");
        } else {
            for (Producto producto : productos) {
                System.out.println(producto);
            }
        }
    }

    public void buscarActualizarProducto() {
        System.out.println("\n--- Buscar/Actualizar Producto ---");
        System.out.print("Ingrese ID o nombre del producto: ");
        String input = scanner.nextLine();
        
        Producto productoEncontrado = null;
        
        try {
            int id = Integer.parseInt(input);
            productoEncontrado = productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        } catch (NumberFormatException e) {
            productoEncontrado = productos.stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(input))
                .findFirst()
                .orElse(null);
        }
        
        if (productoEncontrado == null) {
            System.out.println("Producto no encontrado.");
            return;
        }
        
        System.out.println("Producto encontrado:");
        System.out.println(productoEncontrado);
        
        System.out.print("\n¿Desea actualizar este producto? (s/n): ");
        String opcion = scanner.nextLine();
        
        if (opcion.equalsIgnoreCase("s")) {
            System.out.print("Nuevo nombre (" + productoEncontrado.getNombre() + "): ");
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.isEmpty()) {
                productoEncontrado.setNombre(nuevoNombre);
            }
            
            System.out.print("Nuevo precio (" + productoEncontrado.getPrecio() + "): ");
            String nuevoPrecioStr = scanner.nextLine();
            if (!nuevoPrecioStr.isEmpty()) {
                productoEncontrado.setPrecio(Double.parseDouble(nuevoPrecioStr));
            }
            
            System.out.print("Nuevo stock (" + productoEncontrado.getStock() + "): ");
            String nuevoStockStr = scanner.nextLine();
            if (!nuevoStockStr.isEmpty()) {
                productoEncontrado.setStock(Integer.parseInt(nuevoStockStr));
            }
            
            System.out.println("Producto actualizado con éxito!");
        }
    }

    public void eliminarProducto() {
        System.out.println("\n--- Eliminar Producto ---");
        System.out.print("Ingrese ID del producto a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        Producto productoAEliminar = null;
        for (Producto producto : productos) {
            if (producto.getId() == id) {
                productoAEliminar = producto;
                break;
            }
        }
        
        if (productoAEliminar != null) {
            System.out.print("¿Está seguro que desea eliminar el producto " + productoAEliminar.getNombre() + "? (s/n): ");
            String confirmacion = scanner.nextLine();
            if (confirmacion.equalsIgnoreCase("s")) {
                productos.remove(productoAEliminar);
                System.out.println("Producto eliminado con éxito!");
            } else {
                System.out.println("Eliminación cancelada.");
            }
        } else {
            System.out.println("Producto no encontrado.");
        }
    }

    public void crearPedido() {
        System.out.println("\n--- Crear Pedido ---");
        if (productos.isEmpty()) {
            System.out.println("No hay productos disponibles para crear un pedido.");
            return;
        }
        
        Pedido pedido = new Pedido(nextPedidoId++);
        boolean continuar = true;
        
        while (continuar) {
            listarProductos();
            System.out.print("\nIngrese ID del producto a agregar al pedido (0 para terminar): ");
            int idProducto = Integer.parseInt(scanner.nextLine());
            
            if (idProducto == 0) {
                continuar = false;
            } else {
                Producto productoSeleccionado = null;
                for (Producto producto : productos) {
                    if (producto.getId() == idProducto) {
                        productoSeleccionado = producto;
                        break;
                    }
                }
                
                if (productoSeleccionado == null) {
                    System.out.println("ID de producto no válido.");
                    continue;
                }
                
                System.out.print("Cantidad deseada (Stock disponible: " + productoSeleccionado.getStock() + "): ");
                int cantidad = Integer.parseInt(scanner.nextLine());
                
                try {
                    if (cantidad > productoSeleccionado.getStock()) {
                        throw new StockInsuficienteException("No hay suficiente stock para " + productoSeleccionado.getNombre());
                    }
                    
                    pedido.agregarProducto(productoSeleccionado, cantidad);
                    productoSeleccionado.setStock(productoSeleccionado.getStock() - cantidad);
                    System.out.println("Producto agregado al pedido.");
                    
                } catch (StockInsuficienteException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        
        if (!pedido.getProductos().isEmpty()) {
            pedidos.add(pedido);
            System.out.println("\nPedido creado con éxito!");
            System.out.println(pedido);
        } else {
            System.out.println("Pedido vacío, no se guardó.");
        }
    }

    public void listarPedidos() {
        System.out.println("\n--- Lista de Pedidos ---");
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
                System.out.println("----------------------");
            }
        }
    }
}