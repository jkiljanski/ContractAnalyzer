package com.sciamus.contractanalyzer.domain.suites;

import com.sciamus.contractanalyzer.domain.checks.rest.CheckRepository;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReport;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuiteReportMapper;
import com.sciamus.contractanalyzer.domain.reporting.suites.SuitesReportsRepository;
import io.vavr.CheckedFunction1;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.collection.List;
import io.vavr.control.Try;
import org.springframework.stereotype.Repository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Predicate;

@Repository
public class SuitesRepository {

    java.util.List<CheckSuite> checkSuites;
    CheckRepository checkRepository;
    SuitesReportsRepository suitesReportsRepository;
    SuiteReportMapper mapper;

    public SuitesRepository(java.util.List<CheckSuite> checkSuites,
                            CheckRepository checkRepository,
                            SuitesReportsRepository suitesReportsRepository,
                            SuiteReportMapper mapper) {
        this.checkSuites = checkSuites;
        this.checkRepository = checkRepository;
        this.suitesReportsRepository = suitesReportsRepository;
        this.mapper = mapper;
    }

    public java.util.List<String> getAllSuites() {

        return List.ofAll(checkSuites.stream())
                .map(CheckSuite::getName)
                .collect(List.collector())
                .toJavaList();
    }


    public Function2<String, URL, SuiteReport> suiteReportFunctionWithURL = (name, url) ->
            List.ofAll(checkSuites.stream())
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
                .map(suitesReportsRepository::save)
                .get();
    }

    private <X extends Throwable> void throwBadURL(MalformedURLException e) {
        throw new RuntimeException("Bad URL!",e);
    }

    public java.util.List<SuiteReport> getAllReports() {

        return suitesReportsRepository.findAll();

    }



}
