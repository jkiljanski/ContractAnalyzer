import './App.css';
import ListOfRestChecks from "./components/ListOfRestChecks";
import React, {useCallback, useEffect, useState} from "react";
import CheckRunner from "./components/CheckRunner";
import Navigation from "./components/Navigation";
import {BrowserRouter, Redirect, Route} from "react-router-dom";
import ReportFetcher from "./components/reports/ReportRunner";
import classes from "./components/Styles.module.css";
import KafkaCheckRunner from "./components/queuesChecks/KafkaCheckRunner"
import ListOfKafkaChecks from "./components/queuesChecks/ListOfKafkaChecks";
import {createStore} from "redux";
import {API_BASE_URL} from "./index";
import Report from "./model/Report";

function App() {

    const [listOfRestChecks, setListOfChecks] = useState<Array<string> | null>(null)

    const [listOfKafkaChecks, setListOfKafkaChecks] = useState<Array<string> | null>(null)

    const [checksToRun, setChecksToRun] = useState(new Array<string>())

    const [kafkaCheckToRun, setKafkaCheckToRun] = useState('');

    const [error, setError] = useState(null)

    const [kafkaError, setKafkaError] = useState(null);

    const [reports, setReports] = useState(new Array<Report>());



    // @ts-ignore
    let fetchListOfChecks = useCallback(async () => {

        try {
            const response = await fetch(API_BASE_URL + '/restContractChecks')
            if (!response.ok)
                throw new Error('Error fetching the list of checks')
            const dataReceived = await response.json();
            setListOfChecks(dataReceived.listOfChecks)
        } catch (error: any) {
            setError(error.message)
        }
        return fetchListOfChecks;

    }, [setListOfChecks, setError]);


    // @ts-ignore
    let fetchListOfKafkaChecks = useCallback(async () => {

        try {
            const response = await fetch(API_BASE_URL + '/kafkaCheck/')
            if (!response.ok)
                throw new Error('Error fetching the list of kafka checks')
            const dataReceived = await response.json();


            setListOfKafkaChecks(dataReceived)
        } catch (error: any) {
            setKafkaError(error.message)
        }
        return fetchListOfKafkaChecks;
    }, [setListOfKafkaChecks, setKafkaError])


    useEffect(() => {
        fetchListOfChecks();
    }, [fetchListOfChecks]);

    useEffect(() => {
        fetchListOfKafkaChecks();
    }, [fetchListOfKafkaChecks]);

    const checkHandler = (check: Array<string> | React.SetStateAction<string[]>) => {

        setChecksToRun(check)
    }
    const reportsHandler = (reports: React.SetStateAction<Report[]>) => {
        setReports(reports);
    }
    const kafkaCheckHandler = (kafkaCheck: string | React.SetStateAction<string>) => {
        setKafkaCheckToRun(kafkaCheck);
    }



    return (

        <BrowserRouter>

        <div className={classes.App}>
            <Navigation/>
            <Route path={'/'} exact>
                <Redirect to={'/rest'}/>
            </Route>

            <Route path={'/rest'}>

                <ListOfRestChecks checks={listOfRestChecks} checkHandler={checkHandler}/>
                <CheckRunner checksToRun={checksToRun}/>
                {error && <p className={classes.logoutButton}>{error}</p>}

            </Route>
            <Route path={'/queues'}>
                <ListOfKafkaChecks kafkaChecksToRun={listOfKafkaChecks} checkHandler={kafkaCheckHandler}/>
                <KafkaCheckRunner checkToRun={kafkaCheckToRun}/>
                {kafkaError && <p className={classes.brandSmall}>{kafkaError}</p>}
            </Route>
            <Route path={'/reports'}>
                <ReportFetcher reportsToFetch={reports} reportsHandler={reportsHandler}/>
            </Route>
        </div>
        </BrowserRouter>
    );

}

export default App;
