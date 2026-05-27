package com.tiendafriki.pedidos;

import com.tiendafriki.pedidos.model.DetallePedido;
import com.tiendafriki.pedidos.model.Pedido;
import com.tiendafriki.pedidos.repository.DetallePedidoRepository;
import com.tiendafriki.pedidos.repository.PedidoRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(
            PedidoRepository repositoryPedido,
            DetallePedidoRepository repositoryDetalle
    ) {

        return args -> {

            if (repositoryPedido.count() == 0) {

                Pedido p1 = new Pedido(
                        null,
                        1,
                        "cliente1@correo.cl",
                        "12345678",
                        "Av. Siempre Viva 123",
                        43000,
                        LocalDate.now(),
                        "Creado",
                        new ArrayList<>()
                );

                repositoryPedido.save(p1);

                repositoryDetalle.save(new DetallePedido(
                        null,
                        1,
                        1,
                        23000,
                        p1
                ));

                repositoryDetalle.save(new DetallePedido(
                        null,
                        3,
                        2,
                        10000,
                        p1
                ));

                Pedido p2 = new Pedido(
                        null,
                        2,
                        "cliente2@correo.cl",
                        "87654321",
                        "Pasaje Konoha 456",
                        30000,
                        LocalDate.now(),
                        "Creado",
                        new ArrayList<>()
                );

                repositoryPedido.save(p2);

                repositoryDetalle.save(new DetallePedido(
                        null,
                        4,
                        3,
                        10000,
                        p2
                ));

                Pedido p3 = new Pedido(
                        null,
                        3,
                        "cliente3@correo.cl",
                        "11223344",
                        "Villa Rivendel 789",
                        53000,
                        LocalDate.now(),
                        "Creado",
                        new ArrayList<>()
                );

                repositoryPedido.save(p3);

                repositoryDetalle.save(new DetallePedido(
                        null,
                        2,
                        1,
                        43000,
                        p3
                ));

                repositoryDetalle.save(new DetallePedido(
                        null,
                        3,
                        1,
                        10000,
                        p3
                ));

                System.out.println(
                        "[+] DataLoader De Pedidos Ejecutado Correctamente [>_<] ..."
                );
            }
        };
    }
}