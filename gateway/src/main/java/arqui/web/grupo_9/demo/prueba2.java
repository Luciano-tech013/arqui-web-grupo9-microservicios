package arqui.web.grupo_9.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RequestPredicates.path;
import static org.springframework.web.servlet.function.RouterFunctions.route;

//@Configuration
//public class prueba2 {
//
//        @Bean
//        public RouterFunction<ServerResponse> gatewayRouterFunctionsPath() {
//            return route()
//                    .route(path("api/viajes"), (a) -> { return (ServerResponse) a.uriBuilder().path("http://localhost:8081");})
//                    .build();
//        }
//}
