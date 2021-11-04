package com.atzybank.springcloud.controller;

import com.atzybank.springcloud.entyties.CommonResult;
import com.atzybank.springcloud.entyties.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Set;

/**
 * RestTemplate定义了36个与REST资源交互的方法，其中的大多数都对应于HTTP的方法。
 * 其实，这里面只有11个独立的方法，其中有十个有三种重载形式，而第十一个则重载了六次，这样一共形成了36个方法。
 *
 * delete() 在特定的URL上对资源执行HTTP DELETE操作
 *
 * exchange()
 * 在URL上执行特定的HTTP方法，返回包含对象的ResponseEntity，这个对象是从响应体中
 * 映射得到的
 *
 * execute() 在URL上执行特定的HTTP方法，返回一个从响应体映射得到的对象
 *
 * getForEntity() 发送一个HTTP GET请求，返回的ResponseEntity包含了响应体所映射成的对象
 *
 * getForObject() 发送一个HTTP GET请求，返回的请求体将映射为一个对象
 *
 * postForEntity()
 * POST 数据到一个URL，返回包含一个对象的ResponseEntity，这个对象是从响应体中映射得
 * 到的
 *
 * postForObject() POST 数据到一个URL，返回根据响应体匹配形成的对象
 *
 * headForHeaders() 发送HTTP HEAD请求，返回包含特定资源URL的HTTP头
 *
 * optionsForAllow() 发送HTTP OPTIONS请求，返回对特定URL的Allow头信息
 *
 * postForLocation() POST 数据到一个URL，返回新创建资源的URL
 *
 * put() PUT 资源到特定的URL
 *
 */
@RestController
@Slf4j
public class OrderController {

    public static final String PAYMENT_URL = "http://cloud-payment-service";

    @Resource
    RestTemplate restTemplate;

    /**
     * 通用方法 exchange
     * 在 RestTemplate 中还有一个通用的方法 exchange。为什么说它通用呢？因为这个方法需要你在调用的时候去指定请求类型，
     * 即它既能做 GET 请求，也能做 POST 请求，也能做其它各种类型的请求。如果开发者需要对请求进行封装，使用它再合适不过了，
     * <p>
     * String url = "http://" + host + ":" + port + "/customheader";
     * HttpHeaders headers = new HttpHeaders();
     * headers.add("cookie","justdojava");
     * HttpEntity<MultiValueMap<String,String>> request =  new HttpEntity<>(null,headers);
     * ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
     * System.out.println(responseEntity.getBody());
     * <p>
     * 这里的参数和前面的也都差不多，注意就是多了一个请求类型的参数，然后创建一个 HttpEntity
     * 作为参数来传递。 HttpEntity 在创建时候需要传递两个参数，第一个上文给了一个 null ，
     * 这个参数实际上就相当于 POST/PUT 请求中的第二个参数，有需要可以自行定义。HttpEntity
     * 创建时的第二个参数就是请求头了，也就是说，如果使用 exchange 来发送请求，可以直接定义请求头，
     * 而不需要使用拦截器。
     * <p>
     * post 请求的方法类型除了 postForEntity 和 postForObject 之外，还有一个 postForLocation。
     * 这里的方法类型虽然有三种，但是这三种方法重载的参数基本是一样的，因此这里我还是以 postForEntity
     * 方法为例，来剖析三个重载方法的用法，最后再重点说下 postForLocation 方法。
     * 在 POST 请求中，参数的传递可以是 key/value 的形式，也可以是 JSON 数据，分别来看：
     * ① 传递 key/value 形式的参数
     * 首先在 provider 的 HelloController 类中再添加一个 POST 请求的接口，如下：
     *
     * @param payment 参数
     * @return 结果
     * @PostMapping("/hello2") public String sayHello2(String name) {
     * return "Hello " + name + " !";
     * }
     * <p>
     * 调用：
     * String url = "http://" + host + ":" + port + "/hello2";
     * MultiValueMap map = new LinkedMultiValueMap();
     * map.add("name", name);
     * ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, map, String.class);
     * return responseEntity.getBody();
     * postForEntity 方法第一个参数是请求地址，第二个参数 map 对象中存放着请求参数 key/value，第三个参数则是返回的数据类型。
     * 当然这里的第一个参数 url 地址也可以换成一个 Uri 对象，效果是一样的。
     * 这种方式传递的参数是以 key/value 形式传递的，在 post 请求中，
     * 也可以按照 get 请求的方式去传递 key/value 形式的参数，传递方式和 get 请求的传参方式基本一致，
     * 例如下面这样：
     * <p>
     * String url = "http://" + host + ":" + port + "/hello2?name={1}";
     * ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, null, String.class,name);
     * return responseEntity.getBody();
     * 此时第二个参数可以直接传一个 null。
     * <p>
     * ②传递 JSON 数据
     * 上面介绍的是 post 请求传递 key/value 形式的参数，post 请求也可以直接传递 json 数据，
     * 在 post 请求中，可以自动将一个对象转换成 json 进行传输，数据到达 provider 之后，
     * 再被转换为一个对象。
     * @Controller
     * @ResponseBody public class UserController {
     * @PostMapping("/user") public User hello(@RequestBody User user) {
     * return user;
     * }
     * }
     * <p>
     * String url = "http://" + host + ":" + port + "/user";
     * User u1 = new User();
     * u1.setUsername("牧码小子");
     * u1.setAddress("深圳");
     * ResponseEntity<User> responseEntity = restTemplate.postForEntity(url, u1, User.class);
     * return responseEntity.getBody();
     * <p>
     * 看到这段代码有人要问了，这不和前面的一样吗？是的，唯一的区别就是第二个参数的类型不同，这个参数如果是一个 MultiValueMap 的实例，
     * 则以 key/value 的形式发送，如果是一个普通对象，则会被转成 json 发送。
     * <p>
     * postForObject
     * postForObject 和 postForEntity 基本一致，就是返回类型不同而已，这里不再赘述。
     * <p>
     * postForLocation
     * postForLocation 方法的返回值是一个 Uri 对象，因为 POST 请求一般用来添加数据，有的时候需要将刚刚添加成功的数据的 URL 返回来，
     * 此时就可以使用这个方法，一个常见的使用场景如用户注册功能，用户注册成功之后，可能就自动跳转到登录页面了，此时就可以使用该方法
     * <p>
     * String url = "http://" + host + ":" + port + "/register";
     * MultiValueMap map = new LinkedMultiValueMap();
     * map.add("username", "牧码小子");
     * map.add("address", "深圳");
     * URI uri = restTemplate.postForLocation(url, map);
     * String s = restTemplate.getForObject(uri, String.class);
     * <p>
     * 这里首先调用 postForLocation 获取 Uri 地址，然后再利用 getForObject 请求 Uri
     * <p>
     * 注意：postForLocation 方法返回的 Uri 实际上是指响应头的 Location 字段，
     * 所以，provider 中 register 接口的响应头必须要有 Location 字段（即请求的接口实际上是一个重定向的接口），
     * 否则 postForLocation 方法的返回值为null，初学者很容易犯这个错误，如果这里出错，
     * 大家可以参考下我的源代码。
     * <p>
     * <p>
     * PUT请求：PUT 请求本身方法也比较少，只有三个，这三个重载的方法其参数其实和 POST 是一样的，
     * 可以用 key/value 的形式传参，也可以用 JSON 的形式传参，无论哪种方式，都是没有返回值的，
     * @PutMapping("/user/name")
     * @ResponseBody public void updateUserByUsername(User User) {
     * System.out.println(User);
     * }
     * @PutMapping("/user/address")
     * @ResponseBody public void updateUserByAddress(@RequestBody User User) {
     * System.out.println(User);
     * }
     * @GetMapping("/hello9") public void hello9() {
     * List<ServiceInstance> list = discoveryClient.getInstances("provider");
     * ServiceInstance instance = list.get(0);
     * String host = instance.getHost();
     * int port = instance.getPort();
     * String url1 = "http://" + host + ":" + port + "/user/name";
     * String url2 = "http://" + host + ":" + port + "/user/address";
     * MultiValueMap map = new LinkedMultiValueMap();
     * map.add("username", "牧码小子");
     * map.add("address", "深圳");
     * restTemplate.put(url1, map);
     * User u1 = new User();
     * u1.setAddress("广州");
     * u1.setUsername("江南一点雨");
     * restTemplate.put(url2, u1);
     * }
     * <p>
     * DELETE请求：DELETE 请求也是比较简单的，只有三个方法，不同于 POST 和 PUT ，DELETE 请求的参数只能在地址栏传送，
     * 可以是直接放在路径中，也可以用 key/value 的形式传递，当然，这里也是没有返回值的。
     * @DeleteMapping("/user/{id}")
     * @ResponseBody public void deleteUserById(@PathVariable Integer id) {
     * System.out.println(id);
     * }
     * @DeleteMapping("/user/")
     * @ResponseBody public void deleteUserByUsername(String username) {
     * System.out.println(username);
     * }
     * @GetMapping("/hello10") public void hello10() {
     * List<ServiceInstance> list = discoveryClient.getInstances("provider");
     * ServiceInstance instance = list.get(0);
     * String host = instance.getHost();
     * int port = instance.getPort();
     * String url1 = "http://" + host + ":" + port + "/user/{1}";
     * String url2 = "http://" + host + ":" + port + "/user/?username={username}";
     * Map<String,String> map = new HashMap<>();
     * map.put("username", "牧码小子");
     * restTemplate.delete(url1, 99);
     * restTemplate.delete(url2, map);
     */
    @GetMapping("/consumer/payment/create")
    public CommonResult create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    /**
     * getForObject 方法和 getForEntity 方法类似，getForObject 方法也有三个重载的方法，
     * 参数和 getForEntity 一样。
     * 这里主要说下 getForObject 和 getForEntity 的差异，这两个的差异主要体现在返回值的差异上，
     * getForObject 的返回值就是服务提供者返回的数据，使用 getForObject 无法获取到响应头。
     * <p>
     * String url = "http://" + host + ":" + port + "/hello?name=" + URLEncoder.encode(name, "UTF-8");
     * URI uri = URI.create(url);
     * String s = restTemplate.getForObject(uri, String.class);
     * 注意，这里返回的 s 就是 provider 的返回值，如果开发者只关心 provider 的返回值，
     * 并不关系 HTTP 请求的响应头，那么可以使用该方法。
     *
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
    }

    /**
     * 如果开发者需要获取响应头的话，那么就需要使用 getForEntity 来发送 HTTP 请求，
     * 此时返回的对象是一个 ResponseEntity 的实例。这个实例中包含了响应数据以及响应头。
     * ①、String url = "http://" + host + ":" + port + "/hello?name={1}";
     * ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, name);
     * getForEntity 方法。
     * 第一个参数是 url ，如果url 中有一个占位符 {1} ,如果有多个占位符分别用 {2} 、 {3} … 去表示，
     * 第二个参数是接口返回的数据类型，
     * 最后是一个可变长度的参数，用来给占位符填值。
     * <p>
     * 在返回的 ResponseEntity 中，可以获取响应头中的信息，
     * 其中 getStatusCode 方法用来获取响应状态码，
     * getBody 方法用来获取响应数据，
     * getHeaders 方法用来获取响应头，
     * <p>
     * ② Map<String, Object> map = new HashMap<>();
     * String url = "http://" + host + ":" + port + "/hello?name={name}";
     * map.put("name", name);
     * ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class, map);
     * 第一个是占位符不使用数字，而是使用参数的 key，同时将参数放入到一个 map 中。map 中的 key 和占位符的 key 相对应，
     * map 中的 value 就是参数的具体值
     * <p>
     * ③ 使用 Uri 对象，使用 Uri 对象时，参数可以直接拼接在地址中，
     * 但需要注意的是，这种传参方式，参数如果是中文的话，需要对参数进行编码，使用 URLEncoder.encode 方法来实现
     * String url = "http://" + host + ":" + port + "/hello?name="+ URLEncoder.encode(name,"UTF-8");
     * URI uri = URI.create(url);
     * ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
     *
     * @param id id
     * @return 结果
     */
    @GetMapping("/consumer/payment/getEntity/{id}")
    public CommonResult<Payment> getPaymentById2(@PathVariable("id") Long id) {
        ResponseEntity<CommonResult> responseEntity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        /*
        StringBuffer sb = new StringBuffer();
        HttpStatus statusCode = responseEntity.getStatusCode();
        String body = responseEntity.getBody();
        sb.append("statusCode：")
                .append(statusCode)
                .append("</br>")
                .append("body：")
                .append(body)
                .append("</br>");
        HttpHeaders headers = responseEntity.getHeaders();
        Set<String> keySet = headers.keySet();
        for (String s : keySet) {
            sb.append(s)
                    .append(":")
                    .append(headers.get(s))
                    .append("</br>");
        }
        return sb.toString();
        */
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            return new CommonResult<>(0, "操作失败！");
        }
    }

}
