package sample.mybatis.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import sample.mybatis.dao.CityDao;
import sample.mybatis.domain.Hotel;
import sample.mybatis.mapper.HotelMapper;

@Slf4j
@Service
public class SampleService {

  private final CityDao cityDao;
  private final HotelMapper hotelMapper;

  public SampleService(CityDao cityDao, HotelMapper hotelMapper) {
    this.cityDao = cityDao;
    this.hotelMapper = hotelMapper;
  }

  @Transactional
  public void insertHotel(Hotel hotel) {
    hotelMapper.insertHotel(hotel);
    hotel.setName("sample2");
    hotelMapper.insertHotel(hotel);
//    log.info("{}", 10/0);
  }
}
