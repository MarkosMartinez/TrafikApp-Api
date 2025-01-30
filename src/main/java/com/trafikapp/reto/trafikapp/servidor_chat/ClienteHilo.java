package com.trafikapp.reto.trafikapp.servidor_chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;

public class ClienteHilo extends Thread {
    private Socket socket;
    private Set<ClienteHilo> clientes;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public ClienteHilo(Socket socket, Set<ClienteHilo> clientes) {
        this.socket = socket;
        this.clientes = clientes;
    }

    @Override
    public void run() {
        try {
            // Establecer las conexiones de entrada y salida
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter = new PrintWriter(socket.getOutputStream(), true);

            // Informar a todos los clientes sobre la nueva conexión
            enviarMensaje("Un nuevo usuario se ha conectado.", null);

            String mensaje;
            while ((mensaje = bufferedReader.readLine()) != null) {
                if (mensaje.equalsIgnoreCase("salir")) {
                    break;  // Si el mensaje es "salir", cerrar la conexión
                }
                System.out.println("Mensaje recibido: " + mensaje);
                
                // Enviar el mensaje a todos los clientes conectados, excepto al remitente
                enviarMensaje(mensaje, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión cuando se termine o haya un error
            cerrarConexion();
        }
    }

    private void enviarMensaje(String mensaje, ClienteHilo remitente) {
        // Enviar el mensaje a todos los clientes, excepto al remitente
        synchronized (clientes) {
            for (ClienteHilo cliente : clientes) {
                if (cliente != remitente) {
                    System.out.println("Enviando mensaje a " + cliente.socket.getInetAddress());
                    cliente.printWriter.println(mensaje);
                }
            }
        }
    }

    private void cerrarConexion() {
        try {
            // Cerrar el socket del cliente
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Eliminar al cliente de la lista de clientes conectados
        synchronized (clientes) {
            clientes.remove(this);
        }

        // Informar a los demás clientes que un usuario se ha desconectado
        enviarMensaje("Un usuario se ha desconectado.", null);
    }
}
