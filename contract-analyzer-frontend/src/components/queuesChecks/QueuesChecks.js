import React, {useState, useRef} from "react";
import {Button, Form, InputGroup} from "reactstrap";
import { useKeycloak } from "@react-keycloak/web";
import ReportViewer from "../reports/ReportViewer";

const QueuesChecks = props => {

    const kafkaCheckToRun = props.kafkaCheckToRun;

    const errorMessage = "Error! Invalid input or a problem on the server side";

    const incomingTopicInputRef = useRef();
    const outgoingTopicInputRef = useRef();
    const hostInputRef = useRef();
    const portInputRef = useRef();

    const [kafkaCheckReport, setKafkaCheckReport] = useState('');

    const {keycloak} = useKeycloak();
    const [error, setError] = useState('');

    const [sending, setSending] = useState('');


    async function runKafkaCheck(incomingTopic, outgoingTopic, host, port) {
        setError('');

        if (!keycloak.authenticated) {
            alert("You're not logged in, please log in or check keycloak server")
        }


        setSending('Check is running, wait...')
        let response = await fetch('/kafkaCheck/' + kafkaCheckToRun + '/run' +
            '?incomingTopic=' + incomingTopic + '&outgoingTopic=' + outgoingTopic +
            '&host=' + host + '&port=' + port, {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + keycloak.token,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        });
        setSending('');

        if (response.status !== 200) {
            console.log(response);
            setError(errorMessage);
        }
        else {
            const kafkaCheckReport = await response.json();
            setKafkaCheckReport(kafkaCheckReport);
        }
    }

    const formSubmitHandler = event => {
        event.preventDefault();

        const incomingTopic = incomingTopicInputRef.current.value;
        const outgoingTopic = outgoingTopicInputRef.current.value;
        const host = hostInputRef.current.value;
        const port = portInputRef.current.value;

        runKafkaCheck(incomingTopic, outgoingTopic, host, port).then(() => cleanForm());
    }

    const cleanForm = () => {
        incomingTopicInputRef.current.value = '';
        outgoingTopicInputRef.current.value = '';
        hostInputRef.current.value = '';
        portInputRef.current.value = '';
    }

    return (
        <>
        <Form onSubmit={formSubmitHandler}>
            <InputGroup>
                <label>Incoming topic</label>
                <input type="text"
                       name="incomingTopic"
                       placeholder="Please enter incoming topic"
                       ref={incomingTopicInputRef} />
            </InputGroup>
            <InputGroup>
                <label>Outgoing topic</label>
                <input type="text"
                        name="outgoingTopic"
                        placeholder="Please enter outgoing topic"
                        ref={outgoingTopicInputRef} />
            </InputGroup>
            <InputGroup>
                <label>Host</label>
                <input type="text"
                       name="host"
                       placeholder="Please enter host. The initial host is localhost:8080"
                       ref={hostInputRef}/>
            </InputGroup>
            <InputGroup>
                <label>Port</label>
                <input type="text"
                       name="port"
                       placeholder="Please enter port"
                        ref={portInputRef} />
            </InputGroup>
                    <Button type="submit">Run check</Button>
        </Form>
            {sending}
            {error.length === 0 ? <ReportViewer report={kafkaCheckReport} /> : error}
        </>
    )
}

export default QueuesChecks;