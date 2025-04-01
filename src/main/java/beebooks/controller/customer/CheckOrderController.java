package beebooks.controller.customer;

import beebooks.controller.BaseController;
import beebooks.service.SaleOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CheckOrderController extends BaseController {
    private final SaleOrderService saleorderService;

    @RequestMapping(value = { "/checkOrder/{code}" }, method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> getCheckOrderCode(@PathVariable("code") String code) {
        return saleorderService.checkOrderCode(code);
    }

    @RequestMapping(value = { "/checkOrder" }, method = RequestMethod.GET)
    public String viewCheckOrder() {
        return "customer/checkOrder";
    }


}
