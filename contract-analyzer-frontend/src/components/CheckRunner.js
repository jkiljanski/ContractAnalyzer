import React, {useState} from "react";
import {Button, Input, InputGroup, InputGroupAddon} from "reactstrap";
import classes from "./Styles.module.css";
import {useKeycloak} from "@react-keycloak/web";


const CheckRunner = (props) => {

    let checkToRun = props.checkToRun

    const initialMessage = '';

    const [report, setReport] = useState(initialMessage);
    const [host, setHost] = useState(['http://localhost:8080']);
    const [error, setError] = useState(null)


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
        setReport(JSON.stringify(dataReceived, null, 2)
            .replaceAll(/}|{|"/g, '')
        )
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
                <Input type="text" name="host" id="dupa" placeholder="Please enter host. The initial host is localhost:8080"
                       onChange={userInputHandler}/>
            </InputGroup>

            <p className={classes.reporPassed}>
                {error ? <b>{error}</b> :null}
                {report.includes('id') ? <b>Your check was run and produced the following report: </b> : null} <br/>
                {report}
            </p>
        </div>
    )
}

export default CheckRunner