package beebooks.controller.admin;


import beebooks.controller.BaseController;
import beebooks.service.ProductService;
import beebooks.service.SaleOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class ReportController extends BaseController {

    private final ProductService productService;
    private final SaleOrderService saleorderService;

    @RequestMapping(value = {"/admin/report/list", "/admin/report"}, method = RequestMethod.GET)
    public String getReport(final Model model) {

        model.addAttribute("productCount", productService.count());
        model.addAttribute("orderCount", saleorderService.count());
        model.addAttribute("saleOrderItem", saleorderService.getTotalItemsSold());
        model.addAttribute("saleOrderValue", saleorderService.getTotalSalesValue());

        return "admin/report";
    }

}
