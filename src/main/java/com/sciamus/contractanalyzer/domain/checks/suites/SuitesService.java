package com.sciamus.contractanalyzer.domain.checks.suites;

import com.sciamus.contractanalyzer.domain.checks.suites.reports.SuiteReport;
import com.sciamus.contractanalyzer.infrastructure.adapter.mongo.MongoSuitesReportsRepository;
import io.vavr.CheckedFunction1;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Predicate;

public class SuitesService {

    private final MongoSuitesReportsRepository mongoSuitesReportsRepository;

    private SuitesRepository suitesRepository;

    @Autowired
    public SuitesService(MongoSuitesReportsRepository mongoSuitesReportsRepository, SuitesRepository suitesRepository) {
        this.mongoSuitesReportsRepository = mongoSuitesReportsRepository;
        this.suitesRepository = suitesRepository;
    }

    public Function2<String, URL, SuiteReport> suiteReportFunctionWithURL = (name, url) ->
            List.ofAll(suitesRepository.getCheckSuites().stream())
                    .filter(getCheckSuitePredicate(name))
                    .headOption()
                    .map(runSuiteReport(url))
                    .getOrElseThrow(() -> new SuiteNotFoundException(name));


    private Function1<CheckSuite, SuiteReport> runSuiteReport(URL url) {
        return s -> s.run(url);
    }

    private Predicate<CheckSuite> getCheckSuitePredicate(String name) {
        return s -> s.getName().equals(name);
    }

    public CheckedFunction1<String, URL> toURL = URL::new;

    public Function2<String, String, Try<SuiteReport>> gluingFunction = (name, url) -> CheckedFunction1.liftTry(toURL)
            .apply(url)
            .map(suiteReportFunctionWithURL.apply(name));


    public SuiteReport runSuiteAndAddToRepository(String name, String url) throws RuntimeException {

        return gluingFunction.apply(name, url)
                .onFailure(MalformedURLException.class, this::throwBadURL)
                .map(mongoSuitesReportsRepository::save)
                .get();
    }

    private <X extends Throwable> void throwBadURL(MalformedURLException e) {
        throw new RuntimeException("Bad URL!",e);
    }

    public java.util.List<SuiteReport> getAllReports() {

        return mongoSuitesReportsRepository.findAll();

    }

    public URL createURL(String url) throws MalformedURLException {
        return Try.of(() -> new URL(url)).getOrElseThrow(t -> new RuntimeException(t));
        //return new URL(url));
    }

}
