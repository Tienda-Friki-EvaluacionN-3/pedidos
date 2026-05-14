package com.tiendafriki.pedidos;

import com.tiendafriki.pedidos.model.DetallePedido;
import com.tiendafriki.pedidos.model.Pedido;
import com.tiendafriki.pedidos.repository.DetallePedidoRepository;
import com.tiendafriki.pedidos.repository.PedidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

        @Bean
        CommandLineRunner init(PedidoRepository repositoryPedido, DetallePedidoRepository repositoryDetalle) {
                return args -> {
                        if (repositoryPedido.count() == 0) {

                                Pedido p1 = repositoryPedido.save(new Pedido(null,
                                                "ig.gonzalezk@gmail.com",
                                                "62342506",
                                                "Av. santa marta 123",
                                                40980,
                                                "23/04/2026",
                                                "Enviado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 2, 7990, p1));
                                repositoryDetalle.save(new DetallePedido(null, 1, 24990, p1));

                                Pedido p2 = repositoryPedido.save(new Pedido(null,
                                                "crllompot@gmail.com",
                                                "12345678",
                                                "Av. Santalima Pe 2",
                                                17970,
                                                "07/05/2026",
                                                "Cancelado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 3, 5990, p2));

                                Pedido p3 = repositoryPedido.save(new Pedido(null,
                                                "fevelizf@gmail.com",
                                                "87654321",
                                                "Av. Panconcarne 777",
                                                37970,
                                                "23/04/2026",
                                                "Reservado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 1, 19990, p3));
                                repositoryDetalle.save(new DetallePedido(null, 2, 8990, p3));

                                Pedido p4 = repositoryPedido.save(new Pedido(null,
                                                "peperezong@gmail.com",
                                                "62342506",
                                                "Av. Santa Marta 123",
                                                23960,
                                                "23/04/2026",
                                                "Enviado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 4, 5990, p4));

                                Pedido p5 = repositoryPedido.save(new Pedido(null,
                                                "masanchezp@gmail.com",
                                                "90817263",
                                                "Av. Los Pinos 45",
                                                34990,
                                                "15/03/2026",
                                                "Pendiente",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 1, 34990, p5));

                                Pedido p6 = repositoryPedido.save(new Pedido(null,
                                                "lufernandezm@gmail.com",
                                                "11223344",
                                                "Av. Central 890",
                                                25980,
                                                "10/02/2026",
                                                "Cancelado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 2, 12990, p6));

                                Pedido p7 = repositoryPedido.save(new Pedido(null,
                                                "anperezr@gmail.com",
                                                "55667788",
                                                "Av. Libertad 321",
                                                9990,
                                                "01/01/2026",
                                                "Enviado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 1, 9990, p7));

                                Pedido p8 = repositoryPedido.save(new Pedido(null,
                                                "jomartineza@gmail.com",
                                                "99887766",
                                                "Av. Siempre Viva 742",
                                                14970,
                                                "28/12/2025",
                                                "Reservado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 3, 4990, p8));

                                Pedido p9 = repositoryPedido.save(new Pedido(null,
                                                "carodriguezl@gmail.com",
                                                "44556677",
                                                "Av. Norte 555",
                                                29980,
                                                "19/11/2025",
                                                "Cancelado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 2, 14990, p9));

                                Pedido p10 = repositoryPedido.save(new Pedido(null,
                                                "dagomezv@gmail.com",
                                                "77889900",
                                                "Av. Sur 111",
                                                29990,
                                                "05/10/2025",
                                                "Enviado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 1, 29990, p10));

                                Pedido p11 = repositoryPedido.save(new Pedido(null,
                                                "palopezd@gmail.com",
                                                "33445566",
                                                "Av. Oeste 222",
                                                21980,
                                                "30/09/2025",
                                                "Pendiente",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 2, 10990, p11));
                        }
                };

        }

}
