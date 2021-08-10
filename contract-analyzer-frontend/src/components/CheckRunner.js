import React, {useState} from "react";
import {Button, Input, InputGroup, InputGroupAddon, Table} from "reactstrap";
import classes from "./Styles.module.css";
import {useKeycloak} from "@react-keycloak/web";
import ReportViewer from "./reports/ReportViewer";
import AggregatedReportTableHeaders from "./reports/AggregatedReportTableHeaders";
import ReportTableHeaders from "./reports/ReportTableHeaders";


const CheckRunner = (props) => {

    let checkToRun = props.checkToRun

    const initialMessage = '';

    const [report, setReport] = useState(initialMessage);
    const [host, setHost] = useState(['http://localhost:8080']);
    const [error, setError] = useState(null)
    const [isAggregated, setIsAggregated] = useState(false)


    const {keycloak} = useKeycloak();

    async function runCheck() {


        if (!keycloak.authenticated) {
            alert("You're not logged in, please log in or check keycloak server")
        }

        let response;
        if (String(checkToRun).length === 0) {
            alert("Please choose checks to run first.")
            return;
        }

        try {

        if (checkToRun.length === 1) {
            setIsAggregated(false)
            response = await fetch('/checks/' + checkToRun + '/run?url=' + host, {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + keycloak.token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
            })
            if (!response.ok) {
                throw new Error('Something went wrong here:')
            }

        } else {
            setIsAggregated(true)
            response = await fetch('/aggregatedChecks/run?namesOfChecks=' + checkToRun + '&url=' + host, {
                method: 'POST',
                headers: {
                    'Authorization': 'Bearer ' + keycloak.token,
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
            })

            if (!response.ok) {
                throw new Error('Something went wrong here!')
            }

        }} catch (error) {
            setError(error.message)}

        const dataReceived = await response.json();
        console.log(dataReceived)
        setReport(dataReceived)
        // setReport(JSON.stringify(dataReceived, null, 2)
        //     .replaceAll(/}|{|"/g, '')
        // )
        setError(null)
        // console.log(report)
    }


    const userInputHandler = (event) => {
        setHost(event.target.value)
    }

    return (
        <div>
            <InputGroup>
                <InputGroupAddon addonType="prepend"><Button className={classes.button} onClick={runCheck}>Run
                    check</Button></InputGroupAddon>
                <Input type="text" name="host" placeholder="Please enter host. The initial host is localhost:8080"
                       onChange={userInputHandler}/>
            </InputGroup>

            {error ? <b>{error}</b> :null}
            {report.id != null ? <b>Your check was run and produced the following report: </b> : null} <br/>
            {report &&
                <Table>
                    {isAggregated && <AggregatedReportTableHeaders></AggregatedReportTableHeaders> }
                    {!isAggregated && <ReportTableHeaders></ReportTableHeaders>}
                    <tbody>
                    {report.result === 'PASSED' ?
                        <ReportViewer style={classes.reportPassed} report={report}/> :
                        <ReportViewer style={classes.reportFailed} report={report}/>}
                    </tbody>
                </Table>
            }
        </div>
    )
};

export default CheckRunner