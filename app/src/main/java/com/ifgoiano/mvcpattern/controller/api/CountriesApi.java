package com.ifgoiano.mvcpattern.controller.api;

import com.ifgoiano.mvcpattern.model.Country;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CountriesApi {
    @GET("all")
    Single<List<Country>> getCountries();
}
