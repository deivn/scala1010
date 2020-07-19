package cn.time.order.controller;

import cn.time.order.entity.Order;
import cn.time.order.entity.Product;
import cn.time.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value="/{id}")
    public Order findById(@PathVariable("id") Integer id){
        return orderService.findById(id);
    }

    /**
     * 基于ribbon调用远程微服务
     * 1.使用@LoadBalance声明RestTemplate
     * 2.使用服务名称替换ip地址
     * @param id
     * @return
     */
    @RequestMapping(value="/buy/{id}", method= RequestMethod.GET)
    public Product order(@PathVariable("id") Integer id){
        //调用discoveryClient方法
        Product product = restTemplate.getForObject("http://product-service/product/1", Product.class);
        return product;
    }

//    @GetMapping("/buy/{id}")
//    public String order(@PathVariable("id") Integer id){
//        Product product = restTemplate.getForObject("http://127.0.0.1:9002/product/1", Product.class);
//        System.out.println(product);
//        return "操作成功";
//    }

//    @RequestMapping(value="/buy/{id}", method= RequestMethod.GET)
//    public Product order(@PathVariable("id") Integer id){
//        //调用discoveryClient方法
//        //已调用服务名称获取所有元数据
//        List<ServiceInstance> instances = discoveryClient.getInstances("product-service");
//        for(ServiceInstance instance: instances){
//            System.out.println(instance);
//        }
//        //获取唯一的元数据
//        ServiceInstance instance = instances.get(0);
//        Product product = restTemplate.getForObject("http://"+instance.getHost()+":"+instance.getPort()+"/product/1", Product.class);
//        return product;
//    }
}
