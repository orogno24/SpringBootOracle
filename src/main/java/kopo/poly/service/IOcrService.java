package kopo.poly.service;

import kopo.poly.dto.FoodDTO;
import kopo.poly.dto.OcrDTO;

import java.util.List;

public interface IOcrService {

    OcrDTO getReadforImageText(OcrDTO pDTO) throws Exception;

}
