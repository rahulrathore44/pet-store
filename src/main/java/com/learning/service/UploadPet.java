package com.learning.service;

import com.learning.dto.Format;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

public interface UploadPet {

    List<Integer> uploadPets(byte[] data, Format format);

    List<Integer> uploadPets(String data, Format format);
}
