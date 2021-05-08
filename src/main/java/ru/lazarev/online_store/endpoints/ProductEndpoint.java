package ru.lazarev.online_store.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.lazarev.online_store.services.ProductService;
import ru.lazarev.online_store.soap.products.GetAllProductsRequest;
import ru.lazarev.online_store.soap.products.GetAllProductsResponse;
import ru.lazarev.online_store.soap.products.GetProductByNameRequest;
import ru.lazarev.online_store.soap.products.GetProductByNameResponse;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.lazarev.ru/spring/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByNameRequest")
    @ResponsePayload
    public GetProductByNameResponse getProductByName(@RequestPayload GetProductByNameRequest request) {
        GetProductByNameResponse response = new GetProductByNameResponse();
        response.setProduct(productService.getByName(request.getName()));
        return response;
    }

    /*
        Пример запроса: POST http://localhost:8189/app/ws

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.lazarev.ru/spring/ws/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getAllProductsRequest/>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAllSoapProducts().forEach(response.getProducts()::add);
        return response;
    }
}
