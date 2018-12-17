package sample.jsp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sample.mybatis.dao.CityDao;
import sample.mybatis.domain.Greeting;
import sample.mybatis.mapper.HotelMapper;

import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@RestController
public class RestAPIController {

  private static final String template = "Hello, %s!";
  private final AtomicLong counter = new AtomicLong();

  private final CityDao cityDao;
  private final HotelMapper hotelMapper;

  public RestAPIController(CityDao cityDao, HotelMapper hotelMapper) {
    this.cityDao = cityDao;
    this.hotelMapper = hotelMapper;
  }

  @RequestMapping("/api")
  public Greeting getData(@RequestParam(value = "name", defaultValue = "World") String name) {
    log.info("api");
    Greeting greeting = new Greeting();
    greeting.setCity(cityDao.selectCityById(1));
    greeting.setHotel(hotelMapper.selectByCityId(1));
    greeting.setContent(String.format(template, name));
    greeting.setId(counter.incrementAndGet());
    return greeting;
  }

  @GetMapping("/greeting")
  public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    log.info("greeting");
    Greeting greeting = new Greeting();
    greeting.setContent(String.format(template, name));
    greeting.setId(counter.incrementAndGet());
    return greeting;
  }
}
