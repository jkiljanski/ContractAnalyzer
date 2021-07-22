import './App.css';
import ListOfChecks from "./components/ListOfChecks";
import React, {useCallback, useEffect, useState} from "react";
import CheckRunner from "./components/CheckRunner";
import Navigation from "./components/Navigation";
import {Redirect, Route} from "react-router-dom";
import ReportRunner from "./components/reports/ReportRunner";
import classes from "./components/Styles.module.css";
import QueuesChecks from "./components/queuesChecks/QueuesChecks"
import ListOfKafkaChecks from "./components/queuesChecks/ListOfKafkaChecks";
import {createStore} from "redux";

function App() {

    const [listOfChecks, setListOfChecks] = useState(['Loading checks...'])

    const [listOfKafkaChecks, setListOfKafkaChecks] = useState(['Loading Kafka checks...'])

    const [checksToRun, setChecksToRun] = useState([''])

    const [kafkaCheckToRun, setKafkaCheckToRun] = useState('');

    const [error, setError] = useState(null)

    const [kafkaError, setKafkaError] = useState(null);

    const [reports, setReports] = useState([]);

    const fetchListOfChecks = useCallback(async () => {

        try {
            const response = await fetch('/restContractChecks')
            if (!response.ok)
                throw new Error('Error fetching the list of checks')
            const dataReceived = await response.json();
            setListOfChecks(dataReceived.listOfChecks)
        } catch (error) {
            setError(error.message)
        }
        return fetchListOfChecks

    }, [setListOfChecks,setError])

    const fetchListOfKafkaChecks = useCallback(async () => {

        try {
            const response = await fetch('/kafkaCheck/')
            if (!response.ok)
                throw new Error('Error fetching the list of kafka checks')
            setListOfKafkaChecks(await response.json())
        } catch (error) {
            setKafkaError(error.message)
        }
        return fetchListOfKafkaChecks;
    }, [])

    const checkHandler = check => {

        console.log("im in check handler " + check)
        setChecksToRun(check)
    }
    const reportsHandler = reports => {
        setReports(reports);
    }

    const kafkaCheckHandler = kafkaCheck => {
        setKafkaCheckToRun(kafkaCheck);
    }

    useEffect(() => {
        fetchListOfChecks();
    }, [fetchListOfChecks]);

    useEffect(() => {
        fetchListOfKafkaChecks();
    }, [fetchListOfKafkaChecks]);


    return (
        <div className={classes.App}>
            <Navigation/>
            <Route path={'/'} exact>
                <Redirect to={'/rest'}/>
            </Route>

            <Route path={'/rest'}>

                <ListOfChecks checks={listOfChecks} checkHandler={checkHandler}/>
                <CheckRunner checkToRun={checksToRun}/>
                {error && <p className={classes.logoutButton}>{error}</p>}

            </Route>
            <Route path={'/queues'}>
                <ListOfKafkaChecks kafkaChecks={listOfKafkaChecks} kafkaChecksHandler={kafkaCheckHandler}></ListOfKafkaChecks>
                <QueuesChecks kafkaCheckToRun={kafkaCheckToRun}></QueuesChecks>
                {kafkaError && <p className={classes.brandSmall}>{kafkaError}</p>}
            </Route>
            <Route path={'/reports'}>
                <ReportRunner reportToRun={reports} reportsHandler={reportsHandler}/>
            </Route>
        </div>
    );

}

export default App;
