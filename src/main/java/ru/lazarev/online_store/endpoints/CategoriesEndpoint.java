package ru.lazarev.online_store.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.lazarev.online_store.services.CategoriesService;
import ru.lazarev.online_store.soap.categories.GetCategoriesByTitleRequest;
import ru.lazarev.online_store.soap.categories.GetCategoriesByTitleResponse;

@Endpoint
@RequiredArgsConstructor
public class CategoriesEndpoint {
    private static final String NAMESPACE_URI = "http://www.lazarev.ru/spring/ws/categories";
    private final CategoriesService categoriesService;

    /*
        Пример запроса: POST http://localhost:8189/app/ws

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
          xmlns:f="http://www.lazarev.ru/spring/ws/categories">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getCategoriesByTitleRequest>
                    <f:title>ABC-123</f:title>
                </f:getCategoriesByTitleRequest>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoriesByTitleRequest")
    @ResponsePayload
    @Transactional
    public GetCategoriesByTitleResponse getCategoriesByTitle(@RequestPayload GetCategoriesByTitleRequest request) {
        GetCategoriesByTitleResponse response = new GetCategoriesByTitleResponse();
        response.setCategories(categoriesService.getByTitle(request.getTitle()));
        return response;
    }
}
