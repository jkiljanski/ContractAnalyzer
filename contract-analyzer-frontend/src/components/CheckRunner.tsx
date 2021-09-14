import React, {useState} from "react";
import {Button, Input, InputGroup, InputGroupAddon, Table} from "reactstrap";
import classes from "./Styles.module.css";
import {useKeycloak} from "@react-keycloak/web";
import ReportViewer from "./reports/ReportViewer";
import AggregatedReportTableHeaders from "./reports/AggregatedReportTableHeaders";
import ReportTableHeaders from "./reports/ReportTableHeaders";
import Report from "../model/Report";
import {API_BASE_URL} from "../index";

interface Props {
    checksToRun: string[]
}


const CheckRunner: React.FC<Props> = (props: Props) => {


    let checksToRun = props.checksToRun

    const [report, setReport] = useState<Report>();
    const [host, setHost] = useState<string>('http://localhost:8080');
    const [errorMessage, setErrorMessage] = useState<string>('')
    const [isAggregated, setIsAggregated] = useState<boolean>(false)
    const {keycloak} = useKeycloak();

    async function runCheck() {


        if (!keycloak.authenticated) {
            alert("You're not logged in, please log in or check keycloak server")
        }

        let response;
        if (String(checksToRun).length === 0) {
            alert("Please choose checks to run first.")
            return;
        }

        try {

            if (checksToRun.length === 1) {
                setIsAggregated(false)
                response = await fetch(API_BASE_URL + '/checks/' + checksToRun + '/run?url=' + host, {
                    method: 'POST',
                    headers: {
                        'Authorization': 'Bearer ' + keycloak.token,
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                })
                if (!response.ok) {
                    throw new Error('Something went wrong here')
                }

            } else {
                setIsAggregated(true)
                response = await fetch('/aggregatedChecks/run?namesOfChecks=' + checksToRun + '&url=' + host, {
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

            }
        } catch (message: any) {
            setErrorMessage(message)
        }

        const dataReceived: Report = await response?.json();
        setReport(dataReceived)

        setErrorMessage('')

    }


    const userInputHandler = (event: { target: { value: React.SetStateAction<string>; }; }) => {
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

            {errorMessage.length > 0 ? <b>{errorMessage}</b> : ''}
            {report?.id ? <b>Your check was run and produced the following report: </b> : null} <br/>
            {report &&
            <Table>
                {isAggregated && <AggregatedReportTableHeaders/>}
                {!isAggregated && <ReportTableHeaders/>}
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