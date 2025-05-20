package principal;

import servicios.GestionProductos;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestionProductos gestion = new GestionProductos();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        
        while (!salir) {
            mostrarMenu();
            System.out.print("\nElija una opción: ");
            String opcion = scanner.nextLine();
            
            switch (opcion) {
                case "1":
                    gestion.agregarProducto();
                    break;
                case "2":
                    gestion.listarProductos();
                    break;
                case "3":
                    gestion.buscarActualizarProducto();
                    break;
                case "4":
                    gestion.eliminarProducto();
                    break;
                case "5":
                    gestion.crearPedido();
                    break;
                case "6":
                    gestion.listarPedidos();
                    break;
                case "7":
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
            
            System.out.println("\nPresione Enter para continuar...");
            scanner.nextLine();
        }
        
        scanner.close();
    }
    
    private static void mostrarMenu() {
        System.out.println("\n=================================== SISTEMA DE GESTIÓN ===================================");
        System.out.println("1) Agregar producto");
        System.out.println("2) Listar productos");
        System.out.println("3) Buscar/Actualizar producto");
        System.out.println("4) Eliminar producto");
        System.out.println("5) Crear un pedido");
        System.out.println("6) Listar pedidos");
        System.out.println("7) Salir");
        System.out.println("======================================================================================");
    }
}