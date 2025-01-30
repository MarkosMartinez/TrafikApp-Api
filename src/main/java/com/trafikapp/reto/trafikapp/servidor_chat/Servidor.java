package com.trafikapp.reto.trafikapp.servidor_chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Servidor implements ApplicationRunner {
    private static final int PUERTO = 5000;
    private static Set<ClienteHilo> clientes = new CopyOnWriteArraySet<>();

     @Override
    public void run(ApplicationArguments args) {
        new Thread(this::iniciarServidor).start();  // Ejecuta en un hilo separado
    }
    private  void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor iniciado en el puerto " + PUERTO);
            while (true) {
                // Acepta nuevas conexiones
                Socket socket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " + socket.getInetAddress());

                // Crea un nuevo hilo para manejar al nuevo cliente
                ClienteHilo clienteHilo = new ClienteHilo(socket, clientes);

                // Agrega el hilo del nuevo cliente a la lista de clientes
                clientes.add(clienteHilo);

                // Inicia el hilo para el nuevo cliente
                clienteHilo.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
