package org.sergiiz.rxkata;

import java.util.List;
import java.util.Map;
import java.util.concurrent.FutureTask;

import io.reactivex.Observable;
import io.reactivex.Single;

class CountriesServiceSolved implements CountriesService {

    @Override
    public Single<String> countryNameInCapitals(Country country) {
        return Single.just(country.getName().toUpperCase());

    }

    public Single<Integer> countCountries(List<Country> countries) {
        return Observable.fromIterable(countries).count().map(Long::intValue);

    }

    public Observable<Long> listPopulationOfEachCountry(List<Country> countries) {
        return Observable.fromIterable(countries).map(country -> country.population);
    }

    @Override
    public Observable<String> listNameOfEachCountry(List<Country> countries) {
        return Observable.fromIterable(countries).map(country -> country.name);
    }

    @Override
    public Observable<Country> listOnly3rdAnd4thCountry(List<Country> countries) {
        return Observable.fromIterable(countries).skip(2).take(2);

    }

    @Override
    public Single<Boolean> isAllCountriesPopulationMoreThanOneMillion(List<Country> countries) {
        return Observable.fromIterable(countries).all(country -> country.getPopulation()>1000000);
    }


    @Override
    public Observable<Country> listPopulationMoreThanOneMillion(List<Country> countries) {
        return Observable.fromIterable(countries).filter(country -> country.getPopulation() > 1000000).map(country -> country);
    }

    @Override
    public Observable<Country> listPopulationMoreThanOneMillion(FutureTask<List<Country>> countriesFromNetwork) {
        return Observable.fromFuture(countriesFromNetwork).flatMap(Observable::fromIterable).filter(country -> country.getPopulation()>10000000);
    }

    @Override
    public Observable<String> getCurrencyUsdIfNotFound(String countryName, List<Country> countries) {
        return Observable.fromIterable(countries).filter(country -> country.getName().equals(countryName)).map(Country::getCurrency).defaultIfEmpty("USD");
    }

    @Override
    public Observable<Long> sumPopulationOfCountries(List<Country> countries) {
        return Observable.fromIterable(countries).map(Country::getPopulation).reduce((seed, value) -> (seed + value)).toObservable();
    }

    @Override
    public Single<Map<String, Long>> mapCountriesToNamePopulation(List<Country> countries) {
        return Observable.fromIterable(countries).toMap(country -> country.name, Country::getPopulation);
    }
}
