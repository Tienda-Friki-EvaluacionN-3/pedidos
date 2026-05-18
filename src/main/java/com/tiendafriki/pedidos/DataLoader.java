package com.tiendafriki.pedidos;

import com.tiendafriki.pedidos.model.DetallePedido;
import com.tiendafriki.pedidos.model.Pedido;
import com.tiendafriki.pedidos.repository.DetallePedidoRepository;
import com.tiendafriki.pedidos.repository.PedidoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class DataLoader {

        @Bean
        CommandLineRunner init(PedidoRepository repositoryPedido, DetallePedidoRepository repositoryDetalle) {
                return args -> {
                        if (repositoryPedido.count() == 0) {    

                                Pedido p1 = repositoryPedido.save(new Pedido(null,
                                                1,
                                                "ig.gonzalezk@gmail.com",
                                                "62342506",
                                                "Av. santa marta 123",
                                                40980,
                                                LocalDate.of(2026, 4, 23),
                                                "Creado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 1, 2, 7990, p1));
                                repositoryDetalle.save(new DetallePedido(null, 2, 1, 24990, p1));

                                Pedido p2 = repositoryPedido.save(new Pedido(null,
                                                2,
                                                "crllompot@gmail.com",
                                                "12345678",
                                                "Av. Santalima Pe 2",
                                                17970,
                                                LocalDate.of(2026, 5, 7),
                                                "Pagado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 3, 3, 5990, p2));

                                Pedido p3 = repositoryPedido.save(new Pedido(null,
                                                3,
                                                "fevelizf@gmail.com",
                                                "87654321",
                                                "Av. Panconcarne 777",
                                                37970,
                                                LocalDate.of(2026, 4, 23),
                                                "Pagado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 4, 1, 19990, p3));
                                repositoryDetalle.save(new DetallePedido(null, 5, 2, 8990, p3));

                                Pedido p4 = repositoryPedido.save(new Pedido(null,
                                                4,
                                                "peperezong@gmail.com",
                                                "62342506",
                                                "Av. Santa Marta 123",
                                                23960,
                                                LocalDate.of(2026, 4, 23),
                                                "Cancelado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 6, 4, 5990, p4));

                                Pedido p5 = repositoryPedido.save(new Pedido(null,
                                                5,
                                                "masanchezp@gmail.com",
                                                "90817263",
                                                "Av. Los Pinos 45",
                                                34990,
                                                LocalDate.of(2026, 3, 15),
                                                "Cancelado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 7, 1, 34990, p5));

                                Pedido p6 = repositoryPedido.save(new Pedido(null,
                                                6,
                                                "lufernandezm@gmail.com",
                                                "11223344",
                                                "Av. Central 890",
                                                25980,
                                                LocalDate.of(2026, 2, 10),
                                                "Cancelado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 8, 2, 12990, p6));

                                Pedido p7 = repositoryPedido.save(new Pedido(null,
                                                7,
                                                "anperezr@gmail.com",
                                                "55667788",
                                                "Av. Libertad 321",
                                                9990,
                                                LocalDate.of(2026, 1, 1),
                                                "Pagado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 9, 1, 9990, p7));

                                Pedido p8 = repositoryPedido.save(new Pedido(null,
                                                8,
                                                "jomartineza@gmail.com",
                                                "99887766",
                                                "Av. Siempre Viva 742",
                                                14970,
                                                LocalDate.of(2025, 12, 28),
                                                "Pagado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 10, 3, 4990, p8));

                                Pedido p9 = repositoryPedido.save(new Pedido(null,
                                                9,
                                                "carodriguezl@gmail.com",
                                                "44556677",
                                                "Av. Norte 555",
                                                29980,
                                                LocalDate.of(2025, 11, 19),
                                                "Cancelado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 11, 2, 14990, p9));

                                Pedido p10 = repositoryPedido.save(new Pedido(null,
                                                10,
                                                "dagomezv@gmail.com",
                                                "77889900",
                                                "Av. Sur 111",
                                                29990,
                                                LocalDate.of(2025, 10, 5),
                                                "Creado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 12, 1, 29990, p10));

                                Pedido p11 = repositoryPedido.save(new Pedido(null,
                                                11,
                                                "palopezd@gmail.com",
                                                "33445566",
                                                "Av. Oeste 222",
                                                21980,
                                                LocalDate.of(2025, 9, 30),
                                                "Creado",
                                                null));

                                repositoryDetalle.save(new DetallePedido(null, 13, 2, 10990, p11));
                        }
                };

        }

}
